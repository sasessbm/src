package view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeMap;

import model.*;
import controller.tripleset.*;
import controller.keyword.KeyWordSearcher;
import controller.keyword.Transformation;
import controller.logic.Calculator;
import controller.logic.Displayer;
import controller.logic.FileOperator;
import controller.logic.Logic;
import controller.logic.OverlapDeleter;
import controller.logic.SeedSetter;
import controller.logic.SeedSetterForBaseLine;
import controller.sentence.SentenceMaker;
import controller.sentence.SentenceMaker2;
import controller.tripleset.Filter;
import controller.tripleset.P3P4TripleSetInfoSearcher;
import controller.tripleset.PEvalDicSearcher;
import controller.tripleset.TripleSetMaker;

public class ExtractAfterAddKeyword {

	private static ArrayList<Sentence> sentenceForKeySearchList;
	private static ArrayList<String> medicineNameList;
	private static ArrayList<TripleSet> tripleSetFinalList;
	private static TreeMap<String, Double> targetFilalMap;
	private static ArrayList<String> targetFilteringList ;

	public static void run(ArrayList<String> keyWordSeedList, ArrayList<String> medicineNameList, 
			String testDataPath, ArrayList<String> targetFilteringList) throws Exception{

		ExtractAfterAddKeyword.medicineNameList = medicineNameList;
		ExtractAfterAddKeyword.targetFilteringList = targetFilteringList;

		System.out.println("手がかり語探索用データ読み込み中・・・");
		ArrayList<Sentence> sentenceForKeySearchList = SentenceMaker.getSentenceList3();
		System.out.println("取得文書数は " + sentenceForKeySearchList.size() + "文 です");
		ExtractAfterAddKeyword.sentenceForKeySearchList = sentenceForKeySearchList;

		System.out.println("三つ組抽出用データ読み込み中・・・");
		ArrayList<Sentence> sentenceForExtractTripleSetList = SentenceMaker.getSentenceList2();
		System.out.println("取得文書数は " + sentenceForExtractTripleSetList.size() + "文 です");

		for(double constant = 0 ; constant <= 1 ; constant += 0.1){
			int repeatCountMax = 2; //3
			int targetParticleTypeForKey = 0;
			int targetParticleTypeForTripleSet = 0;
			/* 0 →　なし
			 * 1 → 「が・は・を」
			 * 2 → 「が・は・を・も」
			 * 3 → 「が・は・を・に・も・にも」*/
			tripleSetFinalList = new ArrayList<TripleSet>();
			targetFilalMap = new TreeMap<String, Double>();
			BigDecimal b = new BigDecimal(constant);
			BigDecimal bd2 = b.setScale(1, BigDecimal.ROUND_HALF_UP);  //小数第２位
			constant = bd2.doubleValue();

			ArrayList<KeyWord> keyList = getKeyList(keyWordSeedList, testDataPath, constant, repeatCountMax, targetParticleTypeForKey);
			extractTripleSet(keyList, sentenceForExtractTripleSetList, constant, targetParticleTypeForTripleSet);
		}
	}

	public static void run2(ArrayList<String> keyWordSeedList, ArrayList<String> medicineNameList, 
			String testDataPath, ArrayList<String> targetFilteringList) throws Exception{

		int repeatCountMax = 2; //3
		double constant = 0.5;
		int targetParticleTypeForKey = 2;
		int targetParticleTypeForTripleSet = 0;
		/* 0 →　なし
		 * 1 → 「が・は・を」
		 * 2 → 「が・は・を・も」
		 * 3 → 「が・は・を・に・も・にも」*/

		ExtractAfterAddKeyword.medicineNameList = medicineNameList;
		ExtractAfterAddKeyword.targetFilteringList = targetFilteringList;
		tripleSetFinalList = new ArrayList<TripleSet>();
		targetFilalMap = new TreeMap<String, Double>();

		System.out.println("手がかり語探索用データ読み込み中・・・");
		ArrayList<Sentence> sentenceForKeySearchList = SentenceMaker.getSentenceList3();
		System.out.println("取得文書数は " + sentenceForKeySearchList.size() + "文 です");
		ExtractAfterAddKeyword.sentenceForKeySearchList = sentenceForKeySearchList;

		System.out.println("三つ組抽出用データ読み込み中・・・");
		ArrayList<Sentence> sentenceForExtractTripleSetList = SentenceMaker.getSentenceList2();
		System.out.println("取得文書数は " + sentenceForExtractTripleSetList.size() + "文 です");

		ArrayList<KeyWord> keyList = getKeyList(keyWordSeedList, testDataPath, constant, repeatCountMax, targetParticleTypeForKey);
		extractTripleSet(keyList, sentenceForExtractTripleSetList, constant, targetParticleTypeForTripleSet);
	}

	public static ArrayList<KeyWord> getKeyList(ArrayList<String> keyWordSeedList,
			String testDataPath, double constant, int repeatCountMax, int targetParticleType) throws Exception {

		ArrayList<KeyWord> seedList = Transformation.stringToKeyWord(keyWordSeedList); //シードセット
		ArrayList<KeyWord> keyWordFinalList = new ArrayList<KeyWord>();
		keyWordFinalList.addAll(seedList); //手がかり語最終リストに追加
		int repeatCount = 0;

		//文取得
		for(int i=0; i <= repeatCountMax; i++){
			System.out.println("\r\n" + i + "回目");
			repeatCount = i;

			//初期化
			ArrayList<TripleSet> tripleSetCandidateList = new ArrayList<TripleSet>();
			ArrayList<TripleSet> tripleSetForSearchList = new ArrayList<TripleSet>();
			ArrayList<KeyWord> keyWordCandidateList = new ArrayList<KeyWord>();
			boolean getFlag = false;

			//手がかり語から三つ組取得
			//if(i==0){ System.out.println("\r\n＜以下の初期手がかり語から三つ組探索＞"); }
			//else{ System.out.println("\r\n＜以下の手がかり語から新たな三つ組探索＞"); }
			for(KeyWord keyWord : keyWordFinalList){
				ArrayList<TripleSet> tripleSetForDisplayList = new ArrayList<TripleSet>();
				String keyWordText = keyWord.getText();
				//System.out.println("「" + keyWordText + "」");

				//三つ組取得(P3)
				ArrayList<TripleSetInfo> tripleSetInfoList = P3TripleSetInfoSearcher.getTripleSetInfoList(sentenceForKeySearchList, keyWordText, keyWordFinalList, targetParticleType);
				addTripleSetForKeyWordSetList(tripleSetInfoList, tripleSetCandidateList, tripleSetForDisplayList);

				//三つ組取得(P4)
				tripleSetInfoList = P4TripleSetInfoSearcher.getTripleSetInfoList(sentenceForKeySearchList, keyWordText, keyWordFinalList, targetParticleType);
				addTripleSetForKeyWordSetList(tripleSetInfoList, tripleSetCandidateList, tripleSetForDisplayList);

				//三つ組取得(P10)
				tripleSetInfoList = P10TripleSetInfoSearcher.getTripleSetInfoList(sentenceForKeySearchList, keyWordText, keyWordFinalList, targetParticleType);
				addTripleSetForKeyWordSetList(tripleSetInfoList, tripleSetCandidateList, tripleSetForDisplayList);

				//三つ組取得(P11)
				tripleSetInfoList = P11TripleSetInfoSearcher.getTripleSetInfoList(sentenceForKeySearchList, keyWordText, keyWordFinalList, targetParticleType);
				addTripleSetForKeyWordSetList(tripleSetInfoList, tripleSetCandidateList, tripleSetForDisplayList);
				
				//三つ組取得(P101)
				tripleSetInfoList = P101TripleSetInfoSearcher.getTripleSetInfoList(sentenceForKeySearchList, keyWordText, targetParticleType);
				addTripleSetForKeyWordSetList(tripleSetInfoList, tripleSetCandidateList, tripleSetForDisplayList);

				if(tripleSetForDisplayList.size() == 0){ continue; }
				//Displayer.displayExtractedTripleSet(keyWordText, tripleSetForDisplayList); //抽出三つ組表示
			}

			//手がかり語に三つ組リストセット
			for(TripleSet tripleSet : tripleSetCandidateList){
				ArrayList<String> usedKeyList = tripleSet.getUsedKeyList();
				for(String usedKey : usedKeyList){
					for(KeyWord key : keyWordFinalList){
						if(!key.getText().equals(usedKey)){ continue; }
						key.addTripleSetInList(tripleSet);
						key.setTripleSetList(OverlapDeleter.deleteSameSetUsedTargetPosition(key.getTripleSetList())); //重複削除
					}
				}
			}

			//三つ組候補リストの三つ組重複削除
			tripleSetCandidateList = OverlapDeleter.deleteSameSet(tripleSetCandidateList);

			//対象要素のエントロピー計算
			//System.out.println("\r\n＜抽出した対象要素のエントロピー計算＞\r\n");
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
				//Displayer.displayExtractionNum(targetNumList, targetAllNum); //抽出数表示

				//閾値計算
				threshold = constant * (Math.log(targetAllNum) / Math.log(2.0));
				if(i==0){ threshold = -1; }
				entropy = Calculator.calculateEntropy(targetNumList, targetAllNum); //エントロピー計算

				//エントロピーと閾値表示
				//Displayer.dixplayTripleSetEntropyAndThreshold(tripleSet, entropy, threshold);

				if(constant != 0 && entropy == 0 && i != 0){ continue; }

				//閾値以上の三つ組を増加リストに追加
				if(entropy >= threshold){ 
					getFlag = true;
					tripleSet.setEntropy(entropy);
					tripleSetForSearchList.add(tripleSet);
					tripleSetFinalList.add(tripleSet);
					targetFilalMap.put(tripleSet.getTargetOriginalElement().getText(), entropy);
				}
			}
			if(!getFlag){
				System.out.println("\r\n＜三つ組を抽出できませんでした＞");
				repeatCount = i;
				break;
			}
			getFlag = false;

			if(i == repeatCountMax){ break; }

			//三つ組リスト重複削除(探索用)
			tripleSetForSearchList = OverlapDeleter.deleteSameTarget(tripleSetForSearchList); //対象重複削除

			//対象要素から手がかり語取得
			//System.out.println("\r\n＜以下の対象要素から新たな手がかり語探索＞");
			for(TripleSet tripleSet : tripleSetForSearchList){
				ArrayList<KeyWord> keyWordTmpList = new ArrayList<KeyWord>();
				String target = tripleSet.getTargetOriginalElement().getText();
				//System.out.println("<" + target + ">");
				keyWordTmpList = KeyWordSearcher.getKeyWordList(medicineNameList, sentenceForKeySearchList, target, targetParticleType);
				if(keyWordTmpList.size() == 0){ continue; }

				//すでに取得しているものは取得しない
				keyWordTmpList = OverlapDeleter.deleteOverlappingFromListForKey(keyWordTmpList, keyWordFinalList);
				if(keyWordTmpList.size() == 0){ continue; }

				//対象に手がかり語リストセット
				tripleSet.setExtractKeyList(keyWordTmpList);

				//手がかり語候補リストに追加
				keyWordCandidateList.addAll(keyWordTmpList);
				//Displayer.displayExtractedKeyWord(target, keyWordTmpList); //抽出手がかり語表示
			}

			//手がかり語候補リストの重複削除
			keyWordCandidateList = OverlapDeleter.deleteSameKeyWord(keyWordCandidateList);
			keyWordCandidateList.sort( (a,b) -> a.getSentenceId() - b.getSentenceId() );

			//手がかり語のエントロピー計算
			//System.out.println("\r\n＜抽出した手がかり語のエントロピー計算＞\r\n");
			for(KeyWord keyWord : keyWordCandidateList){
				String keyWordText = keyWord.getText();
				double threshold = -1;
				double entropy = 0;
				int keyWordTextAllNum = 0;
				int keyWordTextNum = 0;
				ArrayList<Integer> keyWordNumList = new ArrayList<Integer>();

				//手がかり語カウント
				for(TripleSet tripleSet : tripleSetFinalList){
					keyWordTextNum = tripleSet.getKeyWordNum(keyWordText);
					if(keyWordTextNum == 0){ continue; }
					keyWordNumList.add(keyWordTextNum);
					keyWordTextAllNum += keyWordTextNum;
				}
				if(keyWordTextAllNum == 0){ continue; }
				//Displayer.displayExtractionNum(keyWordNumList, keyWordTextAllNum); //抽出数表示

				//閾値計算
				threshold = constant * (Math.log(keyWordTextAllNum) / Math.log(2.0));
				entropy = Calculator.calculateEntropy(keyWordNumList, keyWordTextAllNum); //エントロピー計算
				keyWord.setEntropy(entropy);

				//エントロピーと閾値表示
				//Displayer.dixplayKeyWordEntropyAndThreshold(keyWord, entropy, threshold);

				if(constant != 0 && entropy == 0){ continue; }
				//閾値以上の手がかり語をリストに追加
				if(entropy >= threshold){ 
					keyWordFinalList.add(keyWord);
					getFlag = true;
				}
			}
			if(!getFlag){
				System.out.println("\r\n＜手がかり語を抽出できませんでした＞");
				repeatCount = i;
				break;
			}
		}
		keyWordFinalList.sort( (a,b) -> a.getSentenceId() - b.getSentenceId() ); //ソート
		System.out.println("\r\n" + repeatCount + "回目で終了");
		return keyWordFinalList;
	}



	

	public static void extractTripleSet(ArrayList<KeyWord> keyList, ArrayList<Sentence> sentenceForExtractTripleSetList, double constant, int targetParticleType){

		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();
		for(KeyWord keyWord : keyList){
			String keyWordText = keyWord.getText();
			//System.out.println("「" + keyWordText + "」");

			//三つ組取得(P3)
			ArrayList<TripleSetInfo> tripleSetInfoList = P3TripleSetInfoSearcher.getTripleSetInfoList(sentenceForExtractTripleSetList, keyWordText, keyList, targetParticleType);
			addTripleSet(tripleSetInfoList, tripleSetList, sentenceForExtractTripleSetList);

			//三つ組取得(P4)
			tripleSetInfoList = P4TripleSetInfoSearcher.getTripleSetInfoList(sentenceForExtractTripleSetList, keyWordText, keyList, targetParticleType);
			addTripleSet(tripleSetInfoList, tripleSetList, sentenceForExtractTripleSetList);

			//三つ組取得(P10)
			tripleSetInfoList = P10TripleSetInfoSearcher.getTripleSetInfoList(sentenceForExtractTripleSetList, keyWordText, keyList, targetParticleType);
			addTripleSet(tripleSetInfoList, tripleSetList, sentenceForExtractTripleSetList);

			//三つ組取得(P11)
			tripleSetInfoList = P11TripleSetInfoSearcher.getTripleSetInfoList(sentenceForExtractTripleSetList, keyWordText, keyList, targetParticleType);
			addTripleSet(tripleSetInfoList, tripleSetList, sentenceForExtractTripleSetList);
			
			//三つ組取得(P101)
			tripleSetInfoList = P101TripleSetInfoSearcher.getTripleSetInfoList(sentenceForExtractTripleSetList, keyWordText, targetParticleType);
			addTripleSet(tripleSetInfoList, tripleSetList, sentenceForExtractTripleSetList);

			//Displayer.displayExtractedTripleSet(keyWordText, tripleSetList); //抽出三つ組表示
		}

		//評価表現辞書抽出
		//extractEvalDicPattern(sentenceForExtractTripleSetList, medicineNameList, tripleSetList);

		System.out.println("α＝ " + constant);
		Filter.filter(tripleSetList, targetFilteringList); // 対象単語辞書フィルタ
		//OverlapDeleter.deleteSameSet(tripleSetList);
		ArrayList<CorrectAnswer> correctAnswerList = SeedSetter.getCorrectAnswerList();
		ArrayList<TripleSet> correctTripleSetList = Logic.getCorrectTripleSetList(tripleSetList, correctAnswerList);
		ArrayList<TripleSet> wrongTripleSetList = Logic.getWrongTripleSetList(tripleSetList, correctAnswerList);
		correctTripleSetList = Logic.getCorrectTripleSetList(tripleSetList, correctAnswerList);

		//結果表示
		Displayer.displayAllKeyWord(keyList);
		Displayer.displayWrongTripleSet(wrongTripleSetList);
		Displayer.displayCorrectTripleSet(correctTripleSetList);
		Displayer.displayResult(tripleSetList.size(), correctTripleSetList, correctAnswerList.size(), keyList.size());
	}
	
	public static void addTripleSetForKeyWordSetList
	(ArrayList<TripleSetInfo> tripleSetInfoList, ArrayList<TripleSet> tripleSetCandidateList, ArrayList<TripleSet> tripleSetForDisplayList){

		//三つ組取得
		ArrayList<TripleSet> tripleSetTmpList = TripleSetMaker.getTripleSetList(tripleSetInfoList, sentenceForKeySearchList, medicineNameList);
		tripleSetTmpList.sort( (a,b) -> a.getSentenceId() - b.getSentenceId() );
		Filter.filterMedicineName(tripleSetTmpList); // 対象要素には薬剤名を含めない
		Filter.filter(tripleSetTmpList, targetFilteringList); // 対象単語辞書フィルタ

		//すでに取得しているものは取得しない
		tripleSetTmpList = OverlapDeleter.deleteOverlappingFromListForTripleSet(tripleSetTmpList, tripleSetFinalList);
		tripleSetTmpList = OverlapDeleter.deleteOverlappingFromListForTripleSet(tripleSetTmpList, tripleSetCandidateList);

		//すでに取得している対象単語を含む三つ組は、最終リストに追加
		for(TripleSet tripleSet : tripleSetTmpList){
			Iterator<String> it = targetFilalMap.keySet().iterator();
			while(it.hasNext()){
				String target = it.next();
				if(tripleSet.getTargetOriginalElement().getText().equals(target)){
					tripleSet.setEntropy(targetFilalMap.get(target));
					tripleSetFinalList.add(tripleSet);
					break;
				}
			}
		}
		tripleSetForDisplayList.addAll(tripleSetTmpList);

		//最終リストに追加した分を引く
		tripleSetTmpList = OverlapDeleter.deleteOverlappingFromListForTripleSet(tripleSetTmpList, tripleSetFinalList);

		//三つ組候補リストに追加
		tripleSetCandidateList.addAll(tripleSetTmpList);
	}

	public static void addTripleSet
	(ArrayList<TripleSetInfo> tripleSetInfoList, ArrayList<TripleSet> tripleSetList, ArrayList<Sentence> sentenceForExtractTripleSetList){

		//三つ組取得
		ArrayList<TripleSet> tripleSetTmpList = TripleSetMaker.getTripleSetList(tripleSetInfoList, sentenceForExtractTripleSetList, medicineNameList);
		tripleSetTmpList.sort( (a,b) -> a.getSentenceId() - b.getSentenceId() );
		Filter.filterMedicineName(tripleSetTmpList); //対象要素には薬剤名を含めない

		//すでに取得しているものは取得しない
		tripleSetTmpList = OverlapDeleter.deleteOverlappingFromListForTripleSet(tripleSetTmpList, tripleSetList);

		//三つ組候補リストに追加
		tripleSetList.addAll(tripleSetTmpList);
	}

	//評価表現辞書抽出
	public static void extractEvalDicPattern
	(ArrayList<Sentence> sentenceList, ArrayList<String> medicineNameList, ArrayList<TripleSet> tripleSetFinalList){
		ArrayList<TripleSetInfo> tripleSetInfoList = PEvalDicSearcher.getTripleSetInfoList(sentenceList);
		ArrayList<TripleSet> tripleSetList = TripleSetMaker.getTripleSetList(tripleSetInfoList, sentenceList, medicineNameList);
		Filter.filterMedicineName(tripleSetList); //対象要素には薬剤名を含めない
		tripleSetList = OverlapDeleter.deleteSameSet(tripleSetList); //三つ組重複削除
		tripleSetList = OverlapDeleter.deleteOverlappingFromListForTripleSet
				(tripleSetList, tripleSetFinalList); //すでに取得しているものは取得しない
		tripleSetFinalList.addAll(tripleSetList);
	}

}
