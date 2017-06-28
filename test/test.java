package test;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controller.logic.FileOperator;
import controller.logic.SeedSetter;
import controller.sentence.SentenceMaker;
import model.*;



public class test {
	
	private static ArrayList<String> medicineNameList 
	= FileOperator.fileRead("C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\薬剤名\\medicine_name.txt");
	
	private static String testDataPath = "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\実験用データ\\testData.txt";

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		ArrayList<CorrectAnswer> correctAnswerList = SeedSetter.getCorrectAnswerList();
		ArrayList<Sentence> sentenceList = SentenceMaker.getSentenceList(testDataPath, medicineNameList);
		int counter = 0;
		int sentenceIdTmp = 0;
		
		for(CorrectAnswer correctAnswer : correctAnswerList){
			
			int sentenceId = correctAnswer.getSentenceId();
			int medicinePhraseId = correctAnswer.getMedicinePhraseId();
			int targetPhraseId = correctAnswer.getTargetPhraseId();
			int effectPhraseId = correctAnswer.getEffectPhraseId();
			
			if(sentenceIdTmp != sentenceId){
				counter++;
			}
			sentenceIdTmp = sentenceId;
			
			
			ArrayList<Phrase> phraseList = sentenceList.get(sentenceId-1).getPhraseRestoreList();
			
			System.out.println(sentenceId + " → " + phraseList.get(medicinePhraseId).getPhraseText() + "，" + 
			phraseList.get(targetPhraseId).getPhraseText() + "，" + phraseList.get(effectPhraseId).getPhraseText());
			
			
		}
		System.out.println(counter + "文から取得");
		System.out.println("正解三つ組数は" + correctAnswerList.size() + "個");

	}

}
