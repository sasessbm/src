package test;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.KeyWord;
import model.Phrase;
import model.Sentence;
import controller.keyword.KeyWordSearcher;
import controller.logic.FileOperator;
import controller.sentence.SentenceMaker;

public class test3 {
	
	private static ArrayList<String> medicineNameList 
	= FileOperator.fileRead("C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\薬剤名\\medicine_name.txt");
	
	private static String testDataPath = "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\実験用データ\\testData.txt";
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		
		ArrayList<Sentence> sentenceList = SentenceMaker.getSentenceList(testDataPath, medicineNameList);
		
		ArrayList<Phrase> phraseRestoreList = sentenceList.get(989).getPhraseRestoreList();
		
		ArrayList<Integer> P101keyWordIdList = new ArrayList<Integer>();
		P101keyWordIdList.addAll(KeyWordSearcher.getKeyWordIdList(medicineNameList, phraseRestoreList, "副作用", 101));
		ArrayList<KeyWord> keyWordList = new ArrayList<KeyWord>();
		//keyWordList = KeyWordSearcher.addKeyWord(keyWordList, P101keyWordIdList, phraseRestoreList, 101);
		System.out.println(keyWordList.get(0).getText());
	}

}
