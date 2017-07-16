package view;

import java.util.ArrayList;

import model.*;
import controller.tripleset.*;
import controller.keyword.KeyWordSearcher;
import controller.keyword.Transformation;
import controller.logic.Calculator;
import controller.logic.Displayer;
import controller.logic.Logic;
import controller.logic.OverlapDeleter;
import controller.logic.SeedSetter;
import controller.sentence.SentenceMaker;
import controller.tripleset.Filter;
import controller.tripleset.P3P4TripleSetInfoSearcher;
import controller.tripleset.TripleSetMaker;

public class RunFromKeyWordSeed {

	public static void run(ArrayList<String> keyWordSeedList, ArrayList<String> medicineNameList, 
								String testDataPath, ArrayList<String> targetFilteringList) throws Exception {
		ArrayList<TripleSet> tripleSetFinalList = new ArrayList<TripleSet>();
		ArrayList<KeyWord> keyWordFinalList = new ArrayList<KeyWord>();
		System.out.println("テストデータ読み込み中・・・");
		ArrayList<Sentence> sentenceList = SentenceMaker.getSentenceList(testDataPath, medicineNameList);
		System.out.println("取得文書数は " + sentenceList.size() + "文 です");
		double constant = 0.9;
		int repeatCount = 10;
		
		ArrayList<KeyWord> keyWordIncreaseList = new ArrayList<KeyWord>();
		ArrayList<KeyWord> keyWordForSearchList = new ArrayList<KeyWord>();
		ArrayList<TripleSet> tripleSetIncreaseList = new ArrayList<TripleSet>();

		for(int i=1; i <= repeatCount; i++){
			System.out.println("\r\n" + i + "回目");
			
			ArrayList<TripleSet> tripleSetForSearchList = new ArrayList<TripleSet>();
			
			//手がかり語
			if(i==1){ keyWordForSearchList = Transformation.stringToKeyWord(keyWordSeedList); } //シードセット

			//手がかり語から三つ組取得
			if(i==1){ System.out.println("\r\n＜以下の初期手がかり語から三つ組探索＞"); }
			else{ System.out.println("\r\n＜以下の手がかり語から新たな三つ組探索＞"); }
			for(KeyWord keyWord : keyWordForSearchList){
				String keyWordText = keyWord.getText();
				
				//三つ組情報取得
				ArrayList<TripleSetInfo> tripleSetInfoList = P3P4TripleSetInfoSearcher.getTripleSetInfoList(sentenceList, keyWordText);
				tripleSetInfoList.addAll(P1TripleSetInfoSearcher.getTripleSetInfoList(sentenceList, keyWordText));
				tripleSetInfoList.addAll(P101TripleSetInfoSearcher.getTripleSetInfoList(sentenceList, keyWordText));
				tripleSetInfoList.addAll(P102TripleSetInfoSearcher.getTripleSetInfoList(sentenceList, keyWordText));
				if(tripleSetInfoList.size() == 0){ continue; }

				//三つ組取得
				ArrayList<TripleSet> tripleSetTmpList = TripleSetMaker.getTripleSetList(tripleSetInfoList, sentenceList, medicineNameList);
				tripleSetTmpList.sort( (a,b) -> a.getSentenceId() - b.getSentenceId() );
				Filter.filterMedicineName(tripleSetTmpList); //対象要素には薬剤名を含めない
				tripleSetTmpList = OverlapDeleter.deleteSameSet(tripleSetTmpList); //三つ組重複削除
				tripleSetTmpList = OverlapDeleter.deleteOverlappingFromListForTripleSet
														(tripleSetTmpList, tripleSetFinalList); //すでに取得しているものは取得しない
				if(tripleSetTmpList.size() == 0){ continue; }

				//手がかり語に三つ組リストセット
				keyWord.setTripleSetList(tripleSetTmpList);
				
				//三つ組探索用リストに追加
				tripleSetForSearchList.addAll(tripleSetTmpList);
				Displayer.displayExtractedTripleSet(keyWordText, tripleSetTmpList); //抽出三つ組表示
			}
			if(tripleSetForSearchList.size() == 0){
				System.out.println("\r\n＜三つ組を抽出できませんでした＞");
				break;
			}
			
			//三つ組探索用リストの三つ組重複削除
			tripleSetForSearchList = OverlapDeleter.deleteSameSet(tripleSetForSearchList);

			//対象要素のエントロピー計算
			System.out.println("\r\n＜抽出した対象要素のエントロピー計算＞\r\n");
			for(TripleSet tripleSet : tripleSetForSearchList){
				double threshold = -1;
				double entropy = 0;
				int targetAllNum = 0;
				int targetNum = 0;
				ArrayList<Integer> targetNumList = new ArrayList<Integer>();
				
				//三つ組カウント
				for(KeyWord keyWord : keyWordForSearchList){
					targetNum = keyWord.getTargetNum(tripleSet);
					if(targetNum == 0){ continue; }
					targetNumList.add(targetNum);
					targetAllNum += targetNum;
				}
				if(targetAllNum == 0){ continue; }
				//Displayer.displayExtractionNum(targetNumList, targetAllNum); //抽出数表示

				//閾値計算
				//if(i != 1){ threshold = constant * (Math.log(targetNumList.size()) / Math.log(2.0)); }
				threshold = constant * (Math.log(targetNumList.size()) / Math.log(2.0));
				entropy = Calculator.calculateEntropy(targetNumList, targetAllNum); //エントロピー計算
				
				//エントロピーと閾値表示
				Displayer.dixplayTripleSetEntropyAndThreshold(tripleSet, entropy, threshold);

				//閾値以上の三つ組をリストに追加
				if(entropy >= threshold){ tripleSetIncreaseList.add(tripleSet); }
			}
			
			//三つ組リスト更新
			tripleSetFinalList.addAll(tripleSetIncreaseList);
			tripleSetForSearchList = tripleSetIncreaseList;
			tripleSetForSearchList = OverlapDeleter.deleteSameTarget(tripleSetForSearchList);
			
			//三つ組増加リスト初期化
			tripleSetIncreaseList.clear();
			
			keyWordForSearchList.clear();

			//対象要素から手がかり語取得
			System.out.println("\r\n＜以下の対象要素から新たな手がかり語探索＞");
			for(TripleSet tripleSet : tripleSetForSearchList){
				ArrayList<KeyWord> keyWordTmpList = new ArrayList<KeyWord>();
				String target = tripleSet.getTargetOriginalElement().getText();
				//System.out.println("<" + target + ">");
				keyWordTmpList = KeyWordSearcher.getKeyWordList(medicineNameList, sentenceList, target);
				if(keyWordTmpList.size() == 0){ continue; }

				//すでに取得しているものは取得しない
				keyWordTmpList = OverlapDeleter.deleteOverlappingFromListForStringAndKey(keyWordTmpList, keyWordSeedList);
				keyWordTmpList = OverlapDeleter.deleteOverlappingFromListForKey(keyWordTmpList, keyWordFinalList);
				if(keyWordTmpList.size() == 0){ continue; }

				//対象に手がかり語リストセット
				tripleSet.setKeyWordList(keyWordTmpList);
				
				//手がかり語探索用リストに追加
				keyWordTmpList = OverlapDeleter.deleteSameKeyWord(keyWordTmpList); //重複削除
				keyWordForSearchList.addAll(keyWordTmpList);
				Displayer.displayExtractedKeyWord(target, keyWordTmpList); //抽出手がかり語表示
			}
			if(keyWordForSearchList.size() == 0){
				System.out.println("\r\n＜手がかり語を抽出できませんでした＞");
				break;
			}
			
			//手がかり語探索用リストの重複削除
			keyWordForSearchList = OverlapDeleter.deleteSameKeyWord(keyWordForSearchList);

			//手がかり語のエントロピー計算
			System.out.println("\r\n＜抽出した手がかり語のエントロピー計算＞\r\n");
			for(KeyWord keyWord : keyWordForSearchList){
				String keyWordText = keyWord.getText();
				double threshold = -1;
				double entropy = 0;
				int keyWordTextAllNum = 0;
				int keyWordTextNum = 0;
				ArrayList<Integer> keyWordNumList = new ArrayList<Integer>();
				
				//手がかり語カウント
				for(TripleSet tripleSet : tripleSetForSearchList){
					keyWordTextNum = tripleSet.getKeyWordNum(keyWordText);
					if(keyWordTextNum == 0){ continue; }
					keyWordNumList.add(keyWordTextNum);
					keyWordTextAllNum += keyWordTextNum;
				}
				if(keyWordTextAllNum == 0){ continue; }
				
				//閾値計算
				//if(i != 1){ threshold = constant * (Math.log(keyWordNumList.size()) / Math.log(2.0)); }
				threshold = constant * (Math.log(keyWordNumList.size()) / Math.log(2.0));
				entropy = Calculator.calculateEntropy(keyWordNumList, keyWordTextAllNum); //エントロピー計算
				
				//エントロピーと閾値表示
				Displayer.dixplayKeyWordEntropyAndThreshold(keyWord, entropy, threshold);

				//閾値以上の手がかり語をリストに追加
				if(entropy >= threshold){ keyWordIncreaseList.add(keyWord); }
			}
			//手がかり語増加リスト更新
			//keyWordIncreaseList = Transformation.stringToKeyWord(keyWordTextIncreaseList);

			//手がかり語リスト更新
			keyWordFinalList.addAll(keyWordIncreaseList); //手がかり語最終リストに追加
			keyWordForSearchList = keyWordIncreaseList;
			
			//手がかり語探索用リストの重複削除
			keyWordForSearchList = OverlapDeleter.deleteSameKeyWord(keyWordForSearchList);

		}
		//フィルタリング
		//Filter.filter(tripleSetFinalList, targetFilteringList);

		//全抽出結果表示
		//Displayer.displayAllKeyWordAndTripleSet(keyWordIncreaseFinalList, tripleSetFinalList);

		//最終結果表示
		ArrayList<CorrectAnswer> correctAnswerList = SeedSetter.getCorrectAnswerList();
		ArrayList<TripleSet> correctTripleSetList = Logic.getCorrectTripleSetList(tripleSetFinalList, correctAnswerList);
		Displayer.displayResult(tripleSetFinalList.size(), correctTripleSetList, correctAnswerList.size());
	}

}
