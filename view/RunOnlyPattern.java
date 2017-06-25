package view;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.*;
import controller.*;
import controller.sentence.SentenceMaker;
import controller.tripleset.TripleSetInfoSearcher;
import controller.tripleset.TripleSetMaker;

public class RunOnlyPattern {
	
	public static void run(ArrayList<String> keyWordSeedList, ArrayList<String> medicineNameList, String testDataPath) 
															throws SAXException, IOException, ParserConfigurationException{
		
		System.out.println("テストデータ読み込み中・・・");
		ArrayList<Sentence> sentenceList = SentenceMaker.getSentenceList(testDataPath, medicineNameList);
		ArrayList<TripleSetInfo> tripleSetInfoIncreaseFinalList = new ArrayList<TripleSetInfo>();
		ArrayList<TripleSet> tripleSetFinalList = new ArrayList<TripleSet>();
		System.out.println("取得文書数は " + sentenceList.size() + "文 です");
		
		//手がかり語から三つ組取得
		for(String keyWordText : keyWordSeedList){
			//System.out.println("\r\n" + keyWordText);
			ArrayList<TripleSetInfo> tripleSetInfoList = TripleSetInfoSearcher.getTripleSetInfoList(sentenceList ,keyWordText);
			tripleSetInfoIncreaseFinalList.addAll(tripleSetInfoList);
			
			if(tripleSetInfoList.size() == 0){ continue; }
			
			//すでに取得しているものは取得しない
			//tripleSetInfoList = Logic.deleteOverlappingFromListForTripleSetInfo(tripleSetInfoList, tripleSetInfoIncreaseFinalList);
			//tripleSetInfoIncreaseFinalList.addAll(tripleSetInfoList);
			ArrayList<TripleSet> tripleSetList = TripleSetMaker.getTripleSetList(tripleSetInfoList, sentenceList, medicineNameList);
			//tripleSetForSearchList.addAll(tripleSetTmpList);
			
			System.out.println("\r\n「"+ keyWordText + "」から、以下の三つ組を取得");
			for(TripleSet tripleSet : tripleSetList){
				System.out.println(tripleSet.getMedicineName()+ " , " + tripleSet.getTargetElement().getText() 
																	+ " , " +tripleSet.getEffectElement().getText());
			}
		}
		
		ArrayList<CorrectAnswer> correctAnswerList = SeedSetter.getCorrectAnswerList();
		ArrayList<TripleSetInfo> correctTripleSetInfoList = Logic.getCorrectTripleSetInfoList(tripleSetInfoIncreaseFinalList, correctAnswerList);
		ArrayList<TripleSet> correctTripleSetList = TripleSetMaker.getTripleSetList(correctTripleSetInfoList, sentenceList, medicineNameList);
		
		Logic.displayResult
		(tripleSetInfoIncreaseFinalList.size(), correctTripleSetList, correctAnswerList.size());
	}
}
