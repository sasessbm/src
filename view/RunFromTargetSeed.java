package view;

import java.util.ArrayList;

import model.KeyWord;
import model.Sentence;
import model.TripleSet;
import controller.keyword.KeyWordSearcher;
import controller.logic.Displayer;
import controller.logic.FileOperator;
import controller.logic.OverlapDeleter;
import controller.logic.SeedSetter;
import controller.sentence.SentenceMaker;

public class RunFromTargetSeed {
	
	private static ArrayList<Sentence> sentenceList;
	private static String testDataPath = "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\実験用データ\\testData.txt";
	private static ArrayList<String> medicineNameList 
	= FileOperator.fileRead("C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\薬剤名\\medicine_name.txt");
	private static ArrayList<String> keyWordSeedList = SeedSetter.getKeyWordSeedList();
	
	public static void main(String[] args) throws Exception{
		System.out.println("テストデータ読み込み中・・・");
		ArrayList<Sentence> sentenceList = SentenceMaker.getSentenceList(testDataPath, medicineNameList);
		System.out.println("取得文書数は " + sentenceList.size() + "文 です");
		RunFromTargetSeed.sentenceList = sentenceList;
		ArrayList<TripleSet> tripleSetIncreaseList = new ArrayList<TripleSet>();
		String target = "痛み";
		
		run(target,keyWordSeedList,medicineNameList,testDataPath,tripleSetIncreaseList);
	}
	
	public static void run(String target, ArrayList<String> keyWordSeedList, ArrayList<String> medicineNameList, 
			String testDataPath, ArrayList<TripleSet> tripleSetIncreaseList) throws Exception {
		
		//三つ組リスト更新
		ArrayList<TripleSet> tripleSetForSearchList = new ArrayList<TripleSet>();
		tripleSetForSearchList.addAll(tripleSetIncreaseList);
		tripleSetForSearchList = OverlapDeleter.deleteSameTarget(tripleSetForSearchList); //対象重複削除
		
		//対象要素から手がかり語取得
		System.out.println("\r\n＜以下の対象要素から新たな手がかり語探索＞");
		
			ArrayList<KeyWord> keyWordTmpList = new ArrayList<KeyWord>();
			System.out.println("<" + target + ">");
			keyWordTmpList = KeyWordSearcher.getKeyWordList(medicineNameList, sentenceList, target);
			if(keyWordTmpList.size() == 0){ System.out.println("取得できませんでした"); }

			//すでに取得しているものは取得しない
			//keyWordTmpList = OverlapDeleter.deleteOverlappingFromListForKey(keyWordTmpList, keyWordFinalList);
			//if(keyWordTmpList.size() == 0){ continue; }
			
			//手がかり語候補リストに追加
			//keyWordCandidateList.addAll(keyWordTmpList);
			Displayer.displayExtractedKeyWord(target, keyWordTmpList); //抽出手がかり語表示
		
	}

}
