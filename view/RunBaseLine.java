package view;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.*;
import controller.logic.Displayer;
import controller.logic.Logic;
import controller.logic.OverlapDeleter;
import controller.logic.SeedSetter;
import controller.sentence.SentenceMaker;
import controller.tripleset.PEvalDicSearcher;
import controller.tripleset.Filter;
import controller.tripleset.P3P4TripleSetInfoSearcher;
import controller.tripleset.TripleSetMaker;

public class RunBaseLine {
	
	public static void run
	(ArrayList<String> keyWordSeedList, String testDataPath, ArrayList<String> medicineNameList, ArrayList<String> targetFilteringList) 
															throws SAXException, IOException, ParserConfigurationException{
		
		System.out.println("テストデータ読み込み中・・・");
		ArrayList<Sentence> sentenceList = SentenceMaker.getSentenceList(testDataPath, medicineNameList);
		ArrayList<TripleSetInfo> tripleSetInfoList = new ArrayList<TripleSetInfo>();
		ArrayList<TripleSetInfo> tripleSetInfoIncreaseFinalList = new ArrayList<TripleSetInfo>();
		ArrayList<TripleSet> tripleSetTmpList = new ArrayList<TripleSet>();
		ArrayList<TripleSet> tripleSetFinalList = new ArrayList<TripleSet>();
		System.out.println("取得文書数は " + sentenceList.size() + "文 です");
		
		//手がかり語から三つ組の位置抽出
		for(String keyWordText : keyWordSeedList){
			//System.out.println("\r\n" + keyWordText);
			tripleSetInfoList = P3P4TripleSetInfoSearcher.getTripleSetInfoList(sentenceList ,keyWordText);
			
			if(tripleSetInfoList.size() == 0){ continue; }
			tripleSetInfoIncreaseFinalList.addAll(tripleSetInfoList);
			
			tripleSetTmpList = TripleSetMaker.getTripleSetList(tripleSetInfoList, sentenceList, medicineNameList);
			
			System.out.println("\r\n「"+ keyWordText + "」から、以下の三つ組を取得");
			for(TripleSet tripleSet : tripleSetTmpList){
				System.out.println(tripleSet.getMedicineName()+ " , " + tripleSet.getTargetElement().getText() 
									+ " , " +tripleSet.getEffectElement().getText() + "（" + tripleSet.getMedicinePhraseIndex() + " , " 
					+ tripleSet.getTargetElement().getPhraseIndex() + " , " + tripleSet.getEffectElement().getPhraseIndex() + "）"
					 + "    sID =  " + tripleSet.getSentenceId());
			}
		}
		
		//評価表現辞書のパターンから三つ組の位置抽出
		tripleSetInfoList = PEvalDicSearcher.getTripleSetInfoList(sentenceList);
		if(tripleSetInfoList.size() != 0){
			//すでに取得しているものは取得しない
			tripleSetInfoList = OverlapDeleter.deleteOverlappingFromListForTripleSetInfo(tripleSetInfoList, tripleSetInfoIncreaseFinalList);
			tripleSetTmpList = TripleSetMaker.getTripleSetList(tripleSetInfoList, sentenceList, medicineNameList);
			System.out.println("\r\n評価表現辞書抽出から、以下の三つ組を取得");
			for(TripleSet tripleSet : tripleSetTmpList){
				System.out.println(tripleSet.getMedicineName()+ " , " + tripleSet.getTargetElement().getText() 
									+ " , " +tripleSet.getEffectElement().getText() + "（" + tripleSet.getMedicinePhraseIndex() + " , " 
					+ tripleSet.getTargetElement().getPhraseIndex() + " , " + tripleSet.getEffectElement().getPhraseIndex() + "）"
					 + "    sID =  " + tripleSet.getSentenceId());
			}
			tripleSetInfoIncreaseFinalList.addAll(tripleSetInfoList);
		}
		
		//三つ組生成
		tripleSetFinalList = TripleSetMaker.getTripleSetList(tripleSetInfoIncreaseFinalList, sentenceList, medicineNameList);
		
		System.out.println("\r\nフィルタ前");
		
		for(TripleSet tripleSet : tripleSetFinalList){
			System.out.println(tripleSet.getMedicineName() + " , " + tripleSet.getTargetElement().getText() 
								+ " , " +tripleSet.getEffectElement().getText() + "    sID =  " + tripleSet.getSentenceId());
		}
		
		//フィルタリング
		Filter.filter(tripleSetFinalList, targetFilteringList);
		
		System.out.println("\r\nフィルタ後");
		
		for(TripleSet tripleSet : tripleSetFinalList){
			System.out.println(tripleSet.getMedicineName() + " , " + tripleSet.getTargetElement().getText() 
								+ " , " +tripleSet.getEffectElement().getText() + "    sID =  " + tripleSet.getSentenceId());
		}
		
		ArrayList<CorrectAnswer> correctAnswerList = SeedSetter.getCorrectAnswerList();
		ArrayList<TripleSet> correctTripleSetList = Logic.getCorrectTripleSetList(tripleSetFinalList, correctAnswerList);
		
		Displayer.displayResult
		(tripleSetFinalList.size(), correctTripleSetList, correctAnswerList.size(), 0);
	}
	
}
