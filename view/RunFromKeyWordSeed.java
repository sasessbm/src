package view;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import model.*;
import controller.tripleset.*;
import controller.keyword.KeyWordSearcher;
import controller.keyword.Transformation;
import controller.logic.Calculator;
import controller.logic.Logic;
import controller.logic.SeedSetter;
import controller.sentence.SentenceMaker;
import controller.tripleset.P3P4Searcher;
import controller.tripleset.TripleSetMaker;

public class RunFromKeyWordSeed {

	public static void run(ArrayList<String> keyWordSeedList, ArrayList<String> medicineNameList, String testDataPath) 
																											throws Exception {

		//シードセット
		ArrayList<TripleSet> tripleSetIncreaseList = new ArrayList<TripleSet>();
		ArrayList<KeyWord> keyWordIncreaseList = Transformation.stringToKeyWord(keyWordSeedList);
		ArrayList<String> keyWordTextIncreaseList = new ArrayList<String>();

		ArrayList<TripleSet> tripleSetFinalList = new ArrayList<TripleSet>();
		ArrayList<KeyWord> keyWordIncreaseFinalList = new ArrayList<KeyWord>();
		//ArrayList<TripleSetInfo> tripleSetInfoIncreaseFinalList = new ArrayList<TripleSetInfo>();

		System.out.println("テストデータ読み込み中・・・");
		ArrayList<Sentence> sentenceList = SentenceMaker.getSentenceList(testDataPath, medicineNameList);
		//ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(3001, 4000, medicineNameList);
		//ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(3500, 4000, medicineNameList);
		//ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(idList, medicineNameList);
		
		System.out.println("取得文書数は " + sentenceList.size() + "文 です");

		
		double constant = 0.1;
		int repeatCount = 10;

		for(int i=1; i <= repeatCount; i++){

			System.out.println("\r\n" + i + "回目");
			double threshold = 0;
			ArrayList<TripleSet> tripleSetForSearchList = new ArrayList<TripleSet>();
			ArrayList<String> keyWordTextForSearchList = new ArrayList<String>();
			int tripleSetUsedNum = 0;
			int keyWordUsedNum = 0;

			if(i==1){ System.out.println("\r\n＜以下の初期手がかり語から三つ組探索＞"); }
			else{ System.out.println("\r\n＜以下の手がかり語から新たな三つ組探索＞"); }
			
			//手がかり語から三つ組取得
			for(KeyWord keyWord : keyWordIncreaseList){
				String keyWordText = keyWord.getKeyWordText();
				//System.out.println("\r\n" + keyWordText);
				ArrayList<TripleSetInfo> tripleSetInfoList = P3P4Searcher.getTripleSetInfoList(sentenceList ,keyWordText);
				tripleSetInfoList.addAll(P1Searcher.getTripleSetInfoList(sentenceList ,keyWordText));
				
				if(tripleSetInfoList.size() == 0){ continue; }
				
				//すでに取得しているものは取得しない
				//tripleSetInfoList = Logic.deleteOverlappingFromListForTripleSetInfo(tripleSetInfoList, tripleSetInfoIncreaseFinalList);
				//tripleSetInfoList = Logic.deleteOverlappingFromListForTripleSet(tripleSetInfoList, tripleSetIncreaseFinalList);
				//tripleSetInfoIncreaseFinalList.addAll(tripleSetInfoList);
				ArrayList<TripleSet> tripleSetTmpList = TripleSetMaker.getTripleSetList(tripleSetInfoList, sentenceList, medicineNameList);
				tripleSetTmpList = Logic.deleteOverlappingFromListForTripleSet(tripleSetTmpList, tripleSetFinalList);
				Filtering.filterMedicineName(tripleSetTmpList); //対象要素には薬剤名を含めない
				if(tripleSetTmpList.size() == 0){ continue; }
				
				tripleSetForSearchList.addAll(tripleSetTmpList);
				
				System.out.println("\r\n「"+keyWordText + "」から、以下の三つ組を抽出");
				for(TripleSet tripleSet : tripleSetTmpList){
					System.out.println("（" + tripleSet.getMedicineName()+ "，" + tripleSet.getTargetElement().getText() 
							+ "，" +tripleSet.getEffectElement().getText() + "）  　　sID = " + tripleSet.getSentenceId());
				}

				//手がかり語に三つ組リストセット
				keyWord.setTripleSetList(tripleSetTmpList);
				keyWordUsedNum ++;
			}

			if(keyWordUsedNum == 0){
				System.out.println("\r\n＜三つ組を抽出できませんでした＞");
				break;
			}

			//三つ組探索用リストの重複削除
			tripleSetForSearchList = Logic.deleteSameSet(tripleSetForSearchList);

			//閾値計算
//			if(i > 2){
//				//System.out.println("keyWordUsedNum・・・"+keyWordUsedNum);
//				threshold = constant * (Math.log(keyWordUsedNum) / Math.log(2.0));
//				System.out.println("閾値・・・" + threshold);
//			}

			System.out.println("\r\n＜抽出した対象要素のエントロピー計算＞\r\n");
			
			//三つ組のエントロピー計算
			for(TripleSet tripleSet : tripleSetForSearchList){
				double entropy = 0;
				int tripleSetAllNum = 0;
				int tripleSetNum = 0;
				ArrayList<Integer> tripleSetNumList = new ArrayList<Integer>();

				for(KeyWord keyWord : keyWordIncreaseList){
					tripleSetNum = keyWord.getTripleSetNum(tripleSet);
					if(tripleSetNum == 0){ continue; }
					tripleSetNumList.add(tripleSetNum);
					tripleSetAllNum += tripleSetNum;
				}
				if(tripleSetAllNum != 0){
					//System.out.println("tripleSetAllNum・・・" + tripleSetAllNum);
					entropy = Calculator.calculateEntropy(tripleSetNumList, tripleSetAllNum);
				}
				System.out.println("「" +tripleSet.getTargetOriginalElement().getText()+ "」" + "（，" 
						+tripleSet.getEffectElement().getText() +"）　→　" + entropy );

				//閾値以上の三つ組をリストに追加
				if(entropy >= threshold){
					tripleSetIncreaseList.add(tripleSet);
				}
			}
			
			tripleSetFinalList.addAll(tripleSetIncreaseList);
			
			//手がかり語増加リスト初期化
			//keyWordIncreaseList.clear();
			keyWordTextIncreaseList.clear();

			System.out.println("\r\n＜以下の対象要素から新たな手がかり語探索＞\r\n");
			
			//三つ組から手がかり語取得
			for(TripleSet tripleSet : tripleSetIncreaseList){
				ArrayList<KeyWord> keyWordTmpList = new ArrayList<KeyWord>();
				//String target = tripleSet.getTargetElement().getText();
				String target = tripleSet.getTargetOriginalElement().getText();
				String effect = tripleSet.getEffectElement().getText();
				System.out.println("「" + target + "」（，" + effect + "）");
				keyWordTmpList = KeyWordSearcher.getKeyWordList(medicineNameList, sentenceList, target, effect);
				if(keyWordTmpList == null){ continue; }
				
				//すでに取得しているものは取得しない
				keyWordTmpList = Logic.deleteOverlappingFromListForStringAndKey(keyWordTmpList, keyWordSeedList);
				keyWordTmpList = Logic.deleteOverlappingFromListForKey(keyWordTmpList, keyWordIncreaseFinalList);
				
				if(keyWordTmpList == null || keyWordTmpList.size() == 0){ continue; }
				
				//手がかり語リストセット
				for(KeyWord keyWord : keyWordTmpList){
					System.out.println("「" + target + "」から 「"+ keyWord.getKeyWordText() + "」 を抽出しました\r\n");
					
					keyWordTextForSearchList.add(keyWord.getKeyWordText());
				}
				tripleSet.setKeyWordList(keyWordTmpList);
				tripleSetUsedNum ++;
			}

			if(tripleSetUsedNum == 0){
				System.out.println("\r\n＜手がかり語を抽出できませんでした＞");
				break;
			}

			//手がかり語探索用リストの重複削除
			keyWordTextForSearchList = new ArrayList<String>(new LinkedHashSet<>(keyWordTextForSearchList));

			//閾値計算
			//if(i != 1){
				//System.out.println("tripleSetUsedNum・・・"+tripleSetUsedNum);
//				threshold = constant * (Math.log(tripleSetUsedNum) / Math.log(2.0));
//				System.out.println("閾値・・・" + threshold);
			//}

			System.out.println("\r\n＜抽出した手がかり語のエントロピー計算＞\r\n");
			
			//手がかり語のエントロピー計算
			for(String keyWordText : keyWordTextForSearchList){
				double entropy = 0;
				int keyWordTextAllNum = 0;
				int keyWordTextNum = 0;
				ArrayList<Integer> keyWordNumList = new ArrayList<Integer>();

				for(TripleSet tripleSet : tripleSetIncreaseList){
					keyWordTextNum = tripleSet.getKeyWordNum(keyWordText);
					if(keyWordTextNum == 0){ continue; }
					keyWordNumList.add(keyWordTextNum);
					keyWordTextAllNum += keyWordTextNum;
				}
				if(keyWordTextAllNum != 0){
					//System.out.println("keyWordTextAllNum・・・" + keyWordTextAllNum);
					entropy = Calculator.calculateEntropy(keyWordNumList, keyWordTextAllNum);
				}
				System.out.println("「" + keyWordText + "」 →　" + entropy);

				//閾値以上の手がかり語をリストに追加
				if(entropy >= threshold){
					keyWordTextIncreaseList.add(keyWordText);
				}
			}
			
			//手がかり語増加リスト更新
			keyWordIncreaseList = Transformation.stringToKeyWord(keyWordTextIncreaseList);
			
			//手がかり語最終増加リスト更新
			keyWordIncreaseFinalList.addAll(keyWordIncreaseList);

			//三つ組最終増加リスト更新
			//tripleSetIncreaseFinalList.addAll(tripleSetIncreaseList);
			
			//三つ組増加リスト初期化
			tripleSetIncreaseList.clear();
		}
		
//		tripleSetIncreaseFinalList 
//					= TripleSetMaker.getTripleSetList(tripleSetInfoIncreaseFinalList, sentenceList, medicineNameList);
		
		
		
		System.out.println("\r\n＜全抽出結果＞");
		
		System.out.println("\r\n＜手がかり語＞");
		for(KeyWord keyWord : keyWordIncreaseFinalList){
			System.out.println("「" + keyWord.getKeyWordText() + "」");
		}

		System.out.println("\r\n＜三つ組＞");
		for(TripleSet tripleSet : tripleSetFinalList){
			System.out.println("（" + tripleSet.getMedicineName()+ "，" + tripleSet.getTargetElement().getText() + "，" 
					+tripleSet.getEffectElement().getText() + "）");
		}
		
		ArrayList<CorrectAnswer> correctAnswerList = SeedSetter.getCorrectAnswerList();
		//ArrayList<TripleSetInfo> correctTripleSetInfoList = Logic.getCorrectTripleSetInfoList(tripleSetInfoIncreaseFinalList, correctAnswerList);
		//ArrayList<TripleSet> correctTripleSetList = TripleSetMaker.getTripleSetList(correctTripleSetInfoList, sentenceList, medicineNameList);
		ArrayList<TripleSet> correctTripleSetList = Logic.getCorrectTripleSetList(tripleSetFinalList, correctAnswerList);
		
		Logic.displayResult
		(tripleSetFinalList.size(), correctTripleSetList, correctAnswerList.size());
	}

}
