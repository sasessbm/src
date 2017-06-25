package controller.sentence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controller.FileOperator;
import controller.Logic;
import controller.PostProcessor;
import controller.PreProcessor;
import model.*;

public class SentenceMaker {
	
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
			if(sentenceCount == 300){ break; }
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
				Sentence sentence = makeSentence(text, recordId, sentenceId, medicineNameList);
				sentenceList.add(sentence);
				sentenceCount++;
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
		text = PreProcessor.replaceMedicineName(text, medicineNameMap);	//薬剤名置き換え

		//構文解析結果をXml形式で取得
		ArrayList<String> xmlList = new ArrayList<String>();
		xmlList = SyntaxAnalyzer.GetSyntaxAnalysResultXml(text);

		//文節リスト取得　(phrase,morphemeの生成)
		ArrayList<Phrase> phraseReplaceList = XmlReader.GetPhraseList(xmlList);
		ArrayList<Phrase> phraseRestoreList = new ArrayList<Phrase>();

		//文節リスト更新
		phraseRestoreList = Logic.copyPhraseList(phraseReplaceList);
		
		//薬剤名を戻す
		phraseRestoreList = PostProcessor.restoreMedicineName(phraseRestoreList, medicineNameMap);
		
		//sentence生成
		Sentence sentence = new Sentence(textBefore, recordId, sentenceId, phraseReplaceList, phraseRestoreList);
		
		return sentence;
	}

	public static ArrayList<Sentence> getSentenceList
	(ArrayList<Integer> idList, ArrayList<String> medicineNameList) throws Exception{

		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();

		//recordList取得　(recordの生成)
		ArrayList<Record> recordList = DBConnecter.getRecordList(idList);

		sentenceList = makeSentenceList(recordList, medicineNameList);

		return sentenceList;
	}

	public static ArrayList<Sentence> getSentenceList
	(int startRecordNum, int endRecordNum, ArrayList<String> medicineNameList) throws Exception{

		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();

		//recordList取得　(recordの生成)
		ArrayList<Record> recordList = DBConnecter.getRecordList(startRecordNum, endRecordNum);

		sentenceList = makeSentenceList(recordList, medicineNameList);

		return sentenceList;
	}

	public static ArrayList<Sentence> getRandomSentenceList
	(int sentenceNum, int startIdIndex, int endIdIndex, ArrayList<String> medicineNameList) 
			throws SAXException, IOException, ParserConfigurationException{

		ArrayList<Integer> randomIdList = Logic.getRandomIdList(sentenceNum, startIdIndex, endIdIndex);
		ArrayList<Record> recordList = DBConnecter.getRecordList(randomIdList);
		ArrayList<Sentence> sentenceList = makeTestDataSentenceList(recordList, medicineNameList);
		ArrayList<Integer> usedIdList = randomIdList;
		ArrayList<Integer> additionalRandomIdList = new ArrayList<Integer>();
		
		
		while(true){
			int diff = sentenceNum - sentenceList.size();
			if(diff == 0){ break; }
			additionalRandomIdList = Logic.getAdditionalRandomIdList(diff, startIdIndex, endIdIndex, usedIdList);
			recordList = DBConnecter.getRecordList(additionalRandomIdList);
			sentenceList.addAll(makeTestDataSentenceList(recordList, medicineNameList));
			sentenceList = Logic.deleteSameSentence(sentenceList); // 重複文削除
			usedIdList.addAll(additionalRandomIdList);
		}
		
		//レコードID順にソート
		sentenceList.sort( (a,b) -> a.getRecordId() - b.getRecordId() );
		
		//sentenceId付与
		int sentenceId = 0;
		for(Sentence sentence : sentenceList){
			sentenceId ++;
			sentence.setSentenceId(sentenceId);
		}

		return sentenceList;
	}

	public static ArrayList<Sentence> makeSentenceList(ArrayList<Record> recordList, ArrayList<String> medicineNameList) 
			throws SAXException, IOException, ParserConfigurationException{

		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();
		ArrayList<String> sentenceTextList = new ArrayList<String>();
		int sentenceId = 0;

		//レコード単位
		for(Record record : recordList){
			String snippetText = record.getSnippet().getSnippetText();
			String TargetMedicineName = record.getMedicineName();
			int recordId = record.getId();
			if(!snippetText.contains(TargetMedicineName)){ continue; }  //対象薬剤名が無いスニペットは対象としない

			//SentenceList取得
			snippetText = PreProcessor.deleteBothSideDots(snippetText);	//両サイドの「・・・」を削除
			sentenceTextList = PreProcessor.getSentenceTextList(snippetText);

			for(String sentenceText : sentenceTextList){
				//if(sentence.equals(null) || sentence.equals("")){ continue; }	//空白の文は対象としない
				if(!sentenceText.contains(TargetMedicineName)){ continue; } //対象薬剤名を含まない文は対象としない

				//前処理
				sentenceText = PreProcessor.deleteParentheses(sentenceText);	//括弧削除
				sentenceText = PreProcessor.deleteSpace(sentenceText);	//スペース削除
				if(sentenceText.equals(null) || sentenceText.equals("")){ continue; }	//空白の文は対象としない
				
				sentenceId++;

				TreeMap<Integer, String> medicineNameMap = 
						PreProcessor.getMedicineNameMap(sentenceText, medicineNameList); //薬剤名取得
				sentenceText = PreProcessor.replaceMedicineName(sentenceText, medicineNameMap);	//薬剤名置き換え
				//System.out.println(sentenceText);

				//構文解析結果をXml形式で取得
				ArrayList<String> xmlList = new ArrayList<String>();
				xmlList = SyntaxAnalyzer.GetSyntaxAnalysResultXml(sentenceText);

				//文節リスト取得　(phrase,morphemeの生成)
				ArrayList<Phrase> phraseReplaceList = XmlReader.GetPhraseList(xmlList);
				ArrayList<Phrase> phraseRestoreList = new ArrayList<Phrase>();

				//文節リスト更新
				for(Phrase replacePhrase : phraseReplaceList){
					ArrayList<Morpheme> morphemeRestoreList = new ArrayList<Morpheme>();
					for(Morpheme morpheme : replacePhrase.getMorphemeList()){
						morphemeRestoreList.add(new Morpheme(morpheme.getId(), morpheme.getMorphemeText(), morpheme.getFeature()));
					}
					Phrase restorePhrase = 
							new Phrase(replacePhrase.getId(), replacePhrase.getPhraseText(), replacePhrase.getDependencyIndex(), morphemeRestoreList);
					phraseRestoreList.add(restorePhrase);
				}

				//薬剤名を戻す
				phraseRestoreList = PostProcessor.restoreMedicineName(phraseRestoreList, medicineNameMap);

				//sentence生成
				Sentence sentence = new Sentence(sentenceText, recordId, sentenceId, phraseReplaceList, phraseRestoreList);
				sentenceList.add(sentence);
			}
		}
		return sentenceList;
	}

	public static ArrayList<Sentence> makeTestDataSentenceList
	(ArrayList<Record> recordList, ArrayList<String> medicineNameList) throws SAXException, IOException, ParserConfigurationException{

		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();

		for(Record record : recordList){
			String snippetText = record.getSnippet().getSnippetText();
			ArrayList<String> sentenceTextCheckList = PreProcessor.getSentenceTextList(snippetText);
			int recordId = record.getId();
			if(sentenceTextCheckList.size() <= 2){ continue; } //3文以上のスニペットを適用

			for(int i = 0; i < sentenceTextCheckList.size(); i++){

				if(i==0 || i == sentenceTextCheckList.size() - 1){ continue; } //最初と最後以外の文を適用
				String sentenceText = sentenceTextCheckList.get(i);
				if(!Logic.containsMedicine(sentenceText)){ continue; } //薬剤名が含まれる文を適用
				
				//文の不自然な区切りを防ぐ
				if(sentenceText.contains("......") || sentenceText.contains("&amp") 
						|| sentenceText.contains("&quot")|| sentenceText.contains("&gt")){ continue; } 
				
				// 文として成り立っているものを取得
				if(sentenceText.contains("●") || sentenceText.contains("■") || sentenceText.contains("◆")|| sentenceText.contains("○")
						|| sentenceText.contains("トラックバック") || sentenceText.contains("コメント")
						|| sentenceText.contains("テーマ") || sentenceText.contains("タグ")
						|| sentenceText.contains("Re") || sentenceText.contains("|") 
						|| sentenceText.contains("返事を書く") || sentenceText.contains("スポンサー広告")
						|| sentenceText.contains("http:")){ continue; }

				sentenceText = PreProcessor.deleteSpace(sentenceText);	//スペース削除
				String sentenceTextBefore = sentenceText;
				
				//前処理
				sentenceText = PreProcessor.deleteParentheses(sentenceText);	//括弧削除
				//if(sentenceText.equals("")){ continue; }	//空白の文は対象としない
				
				TreeMap<Integer, String> medicineNameMap = 
						PreProcessor.getMedicineNameMap(sentenceText, medicineNameList); //薬剤名取得
				sentenceText = PreProcessor.replaceMedicineName(sentenceText, medicineNameMap);	//薬剤名置き換え

				//構文解析結果をXml形式で取得
				ArrayList<String> xmlList = new ArrayList<String>();
				xmlList = SyntaxAnalyzer.GetSyntaxAnalysResultXml(sentenceText);

				//文節リスト取得　(phrase,morphemeの生成)
				ArrayList<Phrase> phraseReplaceList = XmlReader.GetPhraseList(xmlList);
				
				if(phraseReplaceList.size() < 3){ continue; } // 3文節以上文を取得対象とする
				
				ArrayList<Phrase> phraseRestoreList = new ArrayList<Phrase>();

				//文節リスト更新
				for(Phrase replacePhrase : phraseReplaceList){
					ArrayList<Morpheme> morphemeRestoreList = new ArrayList<Morpheme>();
					for(Morpheme morpheme : replacePhrase.getMorphemeList()){
						morphemeRestoreList.add(new Morpheme(morpheme.getId(), morpheme.getMorphemeText(), morpheme.getFeature()));
					}
					Phrase restorePhrase = 
							new Phrase(replacePhrase.getId(), replacePhrase.getPhraseText(), replacePhrase.getDependencyIndex(), morphemeRestoreList);
					phraseRestoreList.add(restorePhrase);
				}
				
				//薬剤名を戻す
				phraseRestoreList = PostProcessor.restoreMedicineName(phraseRestoreList, medicineNameMap);
				
				//sentence生成
				Sentence sentence = new Sentence(sentenceTextBefore, recordId, phraseReplaceList, phraseRestoreList);
				sentenceList.add(sentence);
				break; //1レコードから1文とする
			}
		}
		return sentenceList;
	}

}
