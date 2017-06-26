package view;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.*;
import controller.*;
import controller.sentence.SentenceMaker;
import controller.tripleset.BaseLine2Searcher;
import controller.tripleset.TripleSetInfoSearcher;
import controller.tripleset.TripleSetMaker;

public class RunBaseLine {
	
	public static void run(ArrayList<String> keyWordSeedList, ArrayList<String> medicineNameList, String testDataPath) 
															throws SAXException, IOException, ParserConfigurationException{
		
		System.out.println("テストデータ読み込み中・・・");
		ArrayList<Sentence> sentenceList = SentenceMaker.getSentenceList(testDataPath, medicineNameList);
		ArrayList<TripleSetInfo> tripleSetInfoIncreaseFinalList = new ArrayList<TripleSetInfo>();
		//ArrayList<TripleSet> tripleSetFinalList = new ArrayList<TripleSet>();
		System.out.println("取得文書数は " + sentenceList.size() + "文 です");
		
		//手がかり語から三つ組取得
//		for(String keyWordText : keyWordSeedList){
//			//System.out.println("\r\n" + keyWordText);
//			ArrayList<TripleSetInfo> tripleSetInfoList = TripleSetInfoSearcher.getTripleSetInfoList(sentenceList ,keyWordText);
//			
//			if(tripleSetInfoList.size() == 0){ continue; }
//			tripleSetInfoIncreaseFinalList.addAll(tripleSetInfoList);
//			
//			//すでに取得しているものは取得しない
//			ArrayList<TripleSet> tripleSetList = TripleSetMaker.getTripleSetList(tripleSetInfoList, sentenceList, medicineNameList);
//			
//			System.out.println("\r\n「"+ keyWordText + "」から、以下の三つ組を取得");
//			for(TripleSet tripleSet : tripleSetList){
//				System.out.println(tripleSet.getMedicineName()+ " , " + tripleSet.getTargetElement().getText() 
//																	+ " , " +tripleSet.getEffectElement().getText());
//			}
//		}
		
		ArrayList<TripleSetInfo> tripleSetInfoList = BaseLine2Searcher.getTripleSetInfoList(sentenceList);
		ArrayList<TripleSet> tripleSetList = TripleSetMaker.getTripleSetList(tripleSetInfoList, sentenceList, medicineNameList);
		System.out.println("\r\n以下の三つ組を取得");
		for(TripleSet tripleSet : tripleSetList){
			System.out.println(tripleSet.getMedicineName() + " , " + tripleSet.getTargetElement().getText() 
								+ " , " +tripleSet.getEffectElement().getText() + "    sID =  " + tripleSet.getSentenceId());
		}
		tripleSetInfoIncreaseFinalList.addAll(tripleSetInfoList);
		
		ArrayList<CorrectAnswer> correctAnswerList = SeedSetter.getCorrectAnswerList();
		ArrayList<TripleSetInfo> correctTripleSetInfoList = Logic.getCorrectTripleSetInfoList(tripleSetInfoIncreaseFinalList, correctAnswerList);
		ArrayList<TripleSet> correctTripleSetList = TripleSetMaker.getTripleSetList(correctTripleSetInfoList, sentenceList, medicineNameList);
		
		Logic.displayResult
		(tripleSetInfoIncreaseFinalList.size(), correctTripleSetList, correctAnswerList.size());
	}
	
}
