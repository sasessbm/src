package controller.sentence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controller.logic.FileOperator;
import controller.logic.Logic;
import controller.logic.OverlapDeleter;
import controller.logic.PostProcessor;
import controller.logic.PreProcessor;
import model.*;

public class SentenceMaker2 {
	
	public static ArrayList<Sentence> getSentenceList(String testDataPath, ArrayList<String> medicineNameList) throws SAXException, IOException, ParserConfigurationException{
		
		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();
		ArrayList<String> testDataContentsList = FileOperator.fileRead(testDataPath);
		int recordId = 0;
		int sentenceId = 0;
		int sentenceCount = 0;
		int lineCount = 0;
		int recordIdPlaceIndex = 0;
		int sentenceIdPlaceIndex = 0;
		int spaceIndex = 0;
		
		for(String text : testDataContentsList){
			//if(sentenceCount == 1000){ break; }
			int lineType = lineCount % 3;
			switch(lineType){
			case 0:
				break;
				
			case 1:
				recordIdPlaceIndex = text.indexOf("=") + 2;
				spaceIndex = text.indexOf(" ", recordIdPlaceIndex);
				recordId = Integer.parseInt(text.substring(recordIdPlaceIndex, spaceIndex));
				sentenceIdPlaceIndex = text.indexOf("=", spaceIndex) + 2;
				sentenceId = Integer.parseInt(text.substring(sentenceIdPlaceIndex));
				break;
				
			case 2:
				sentenceCount++;
				//if(sentenceCount != 948){ break; }
				Sentence sentence = makeSentence(text, recordId, sentenceId, medicineNameList);
				sentenceList.add(sentence);
				break;
			}
			lineCount++ ;
		}
		return sentenceList;
	}
	
	public static Sentence makeSentence(String text, int recordId , int sentenceId, ArrayList<String> medicineNameList) 
																throws SAXException, IOException, ParserConfigurationException{
		String textBefore = text;
		
		//前処理
		text = PreProcessor.deleteParentheses(text);	//括弧削除
		//if(sentenceText.equals("")){ continue; }	//空白の文は対象としない
		TreeMap<Integer, String> medicineNameMap = 
				PreProcessor.getMedicineNameMap(text, medicineNameList); //薬剤名取得
		//text = PreProcessor.replaceMedicineName(text, medicineNameMap);	//薬剤名置き換え

		//構文解析結果をXml形式で取得
		ArrayList<String> xmlList = new ArrayList<String>();
		xmlList = SyntaxAnalyzer.GetSyntaxAnalysResultXml(text);

		//文節リスト取得　(phrase,morphemeの生成)
		ArrayList<Phrase> phraseRestoreList = XmlReader.GetPhraseList(xmlList);
		ArrayList<Phrase> phraseReplaceList = new ArrayList<Phrase>();

		//文節リスト更新
		phraseReplaceList = Logic.copyPhraseList(phraseRestoreList);
		
		//薬剤名を戻す
		//phraseRestoreList = PostProcessor.restoreMedicineName(phraseRestoreList, medicineNameMap);
		for(Phrase phrase : phraseReplaceList){
			phrase.setPhraseText(PreProcessor.replaceMedicineName(phrase.getPhraseText(), medicineNameMap));
		}
		
		//sentence生成
		Sentence sentence = new Sentence(textBefore, recordId, sentenceId, phraseReplaceList, phraseRestoreList);
		
		return sentence;
	}

}
