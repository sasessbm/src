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

public class RunFromKeyWordSeed2 {

	public static void run(ArrayList<String> keyWordSeedList, ArrayList<String> medicineNameList, 
								String testDataPath, ArrayList<String> targetFilteringList) throws Exception {
		
		ArrayList<TripleSet> tripleSetFinalList = new ArrayList<TripleSet>();
		ArrayList<TripleSet> tripleSetForSearchList = new ArrayList<TripleSet>();
		ArrayList<KeyWord> keyWordFinalList = Transformation.stringToKeyWord(keyWordSeedList); //シードセット
		System.out.println("テストデータ読み込み中・・・");
		ArrayList<Sentence> sentenceList = SentenceMaker.getSentenceList(testDataPath, medicineNameList);
		System.out.println("取得文書数は " + sentenceList.size() + "文 です");
		double constant = 0.7;
		//double constant2 = 0.9;
		int repeatCountMax = 10;
		int repeatCount = 0;
		
		for(int i=1; i <= repeatCountMax; i++){
			System.out.println("\r\n" + i + "回目");
			
			//初期化
			ArrayList<TripleSet> tripleSetCandidateList = new ArrayList<TripleSet>();
			ArrayList<TripleSet> tripleSetIncreaseList = new ArrayList<TripleSet>();
			ArrayList<KeyWord> keyWordCandidateList = new ArrayList<KeyWord>();
			ArrayList<KeyWord> keyWordIncreaseList = new ArrayList<KeyWord>();
			boolean getFlag = false;

			//手がかり語から三つ組取得
			if(i==1){ System.out.println("\r\n＜以下の初期手がかり語から三つ組探索＞"); }
			else{ System.out.println("\r\n＜以下の手がかり語から新たな三つ組探索＞"); }
			for(KeyWord keyWord : keyWordFinalList){
				String keyWordText = keyWord.getText();
				System.out.println("「" + keyWordText + "」");
				
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
				
				//三つ組候補リストに追加
				tripleSetCandidateList.addAll(tripleSetTmpList);
				Displayer.displayExtractedTripleSet(keyWordText, tripleSetTmpList); //抽出三つ組表示
			}
			
			//三つ組候補リストの三つ組重複削除
			tripleSetCandidateList = OverlapDeleter.deleteSameSet(tripleSetCandidateList);

			//対象要素のエントロピー計算
			System.out.println("\r\n＜抽出した対象要素のエントロピー計算＞\r\n");
			for(TripleSet tripleSet : tripleSetCandidateList){
				double threshold = -1;
				double entropy = 0;
				int targetAllNum = 0;
				int targetNum = 0;
				ArrayList<Integer> targetNumList = new ArrayList<Integer>();
				
				//三つ組カウント
				for(KeyWord keyWord : keyWordFinalList){
					targetNum = keyWord.getTargetNum(tripleSet);
					if(targetNum == 0){ continue; }
					targetNumList.add(targetNum);
					targetAllNum += targetNum;
				}
				if(targetAllNum == 0){ continue; }
				Displayer.displayExtractionNum(targetNumList, targetAllNum); //抽出数表示

				//閾値計算
				//if(i != 1){ threshold = constant * (Math.log(targetNumList.size()) / Math.log(2.0)); }
				threshold = constant * (Math.log(targetNumList.size()) / Math.log(2.0));
				entropy = Calculator.calculateEntropy(targetNumList, targetAllNum); //エントロピー計算
				
				//エントロピーと閾値表示
				Displayer.dixplayTripleSetEntropyAndThreshold(tripleSet, entropy, threshold);

				//閾値以上の三つ組を増加リストに追加
				if(entropy >= threshold){ 
					getFlag = true;
					tripleSetIncreaseList.add(tripleSet); 
				}
			}
			if(!getFlag){
				System.out.println("\r\n＜三つ組を抽出できませんでした＞");
				repeatCount = i;
				break;
			}
			getFlag = false;
			
			//三つ組リスト更新
			tripleSetFinalList.addAll(tripleSetIncreaseList); //最終リスト追加
			tripleSetForSearchList.addAll(tripleSetIncreaseList);
			tripleSetForSearchList = OverlapDeleter.deleteSameTarget(tripleSetForSearchList); //対象重複削除

			//対象要素から手がかり語取得
			System.out.println("\r\n＜以下の対象要素から新たな手がかり語探索＞");
			for(TripleSet tripleSet : tripleSetForSearchList){
				ArrayList<KeyWord> keyWordTmpList = new ArrayList<KeyWord>();
				String target = tripleSet.getTargetOriginalElement().getText();
				System.out.println("<" + target + ">");
				keyWordTmpList = KeyWordSearcher.getKeyWordList(medicineNameList, sentenceList, target);
				if(keyWordTmpList.size() == 0){ continue; }

				//すでに取得しているものは取得しない
				keyWordTmpList = OverlapDeleter.deleteOverlappingFromListForStringAndKey(keyWordTmpList, keyWordSeedList);
				keyWordTmpList = OverlapDeleter.deleteOverlappingFromListForKey(keyWordTmpList, keyWordFinalList);
				if(keyWordTmpList.size() == 0){ continue; }

				//対象に手がかり語リストセット
				tripleSet.setKeyWordList(keyWordTmpList);
				
				//手がかり語候補リストに追加
				keyWordCandidateList.addAll(keyWordTmpList);
				Displayer.displayExtractedKeyWord(target, keyWordTmpList); //抽出手がかり語表示
			}
			
			//手がかり語候補リストの重複削除
			keyWordCandidateList = OverlapDeleter.deleteSameKeyWord(keyWordCandidateList);
			keyWordCandidateList.sort( (a,b) -> a.getSentenceId() - b.getSentenceId() );

			//手がかり語のエントロピー計算
			System.out.println("\r\n＜抽出した手がかり語のエントロピー計算＞\r\n");
			for(KeyWord keyWord : keyWordCandidateList){
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
				
				Displayer.displayExtractionNum(keyWordNumList, keyWordTextAllNum); //抽出数表示
				
				//閾値計算
				//if(i != 1){ threshold = constant * (Math.log(keyWordNumList.size()) / Math.log(2.0)); }
				threshold = constant * (Math.log(keyWordNumList.size()) / Math.log(2.0));
				entropy = Calculator.calculateEntropy(keyWordNumList, keyWordTextAllNum); //エントロピー計算
				
				//エントロピーと閾値表示
				Displayer.dixplayKeyWordEntropyAndThreshold(keyWord, entropy, threshold);

				//閾値以上の手がかり語をリストに追加
				if(entropy >= threshold){ 
					keyWordIncreaseList.add(keyWord);
					getFlag = true;
				}
			}
			if(!getFlag){
				System.out.println("\r\n＜手がかり語を抽出できませんでした＞");
				repeatCount = i;
				break;
			}

			//手がかり語リスト更新
			keyWordFinalList.addAll(keyWordIncreaseList); //手がかり語最終リストに追加
		}
		//フィルタリング
		//Filter.filter(tripleSetFinalList, targetFilteringList);
		
		keyWordFinalList.sort( (a,b) -> a.getSentenceId() - b.getSentenceId() );
		tripleSetFinalList.sort( (a,b) -> a.getSentenceId() - b.getSentenceId() );

		//全抽出結果表示
		Displayer.displayAllKeyWordAndTripleSet(keyWordFinalList, tripleSetFinalList);

		//最終結果表示
		ArrayList<CorrectAnswer> correctAnswerList = SeedSetter.getCorrectAnswerList();
		ArrayList<TripleSet> correctTripleSetList = Logic.getCorrectTripleSetList(tripleSetFinalList, correctAnswerList);
		Displayer.displayResult(tripleSetFinalList.size(), correctTripleSetList, correctAnswerList.size(), keyWordFinalList.size());
		System.out.println("\r\n" + repeatCount + "回目で終了");
	}

}
