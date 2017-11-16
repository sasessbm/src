package controller.keyword;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controller.logic.Logic;
import controller.tripleset.Filter;
import controller.tripleset.TripleSetMaker;
import model.*;

public class KeyWordSearcher {

	//	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
	//
	//		String sentence = "TARGETMEDICINE後、９か月目のMRIの結果・・ 以前壊死を起こしていて、TARGETMEDICINE治療で壊死が消えていた部分一部に、壊死が再発していました";
	//		String target = "壊死";
	//		String effect = "消えて";
	//		String keyWord = "";
	//
	//		keyWord = getKeyWord(sentence, target, effect);
	//
	//		System.out.println(keyWord);
	//
	//	}

	public static ArrayList<KeyWord> getKeyWordList
	(ArrayList<String> medicineNameList, ArrayList<Sentence> sentenceList, String target) 
			throws SAXException, IOException, ParserConfigurationException{
		ArrayList<KeyWord> keyWordList = new ArrayList<KeyWord>();
		for(Sentence sentence : sentenceList){
			ArrayList<Integer> keyWordIdListExtractAfterMedicine = new ArrayList<Integer>();
			ArrayList<Integer> keyWordIdListExtractFirstPlace = new ArrayList<Integer>();
			ArrayList<Integer> keyWordIdList = new ArrayList<Integer>();
			ArrayList<Phrase> phraseRestoreList = sentence.getPhraseRestoreList();
			int sentenceId = sentence.getSentenceId();

			//手がかり語探索
//			keyWordIdListExtractAfterMedicine.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, 3));
//			keyWordIdListExtractAfterMedicine.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, 101));
//			keyWordIdListExtractAfterMedicine.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, 11));
//			keyWordIdListExtractFirstPlace.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, 4));
//			keyWordIdListExtractFirstPlace.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, 10));
			keyWordIdList.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, 3));
			keyWordIdList.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, 101));
			keyWordIdList.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, 11));
			keyWordIdList.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, 4));
			keyWordIdList.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, 10));
			
			//重複削除
			//keyWordIdListExtractAfterMedicine = new ArrayList<Integer>(new HashSet<>(keyWordIdListExtractAfterMedicine));
			//keyWordIdListExtractFirstPlace = new ArrayList<Integer>(new HashSet<>(keyWordIdListExtractFirstPlace));
			keyWordIdList = new ArrayList<Integer>(new HashSet<>(keyWordIdList));
			
//			if(keyWordIdListExtractAfterMedicine.size() != 0){
//				keyWordList = addKeyWord(keyWordList, keyWordIdListExtractAfterMedicine, phraseRestoreList, sentenceId, 3);
//			}
//			if(keyWordIdListExtractFirstPlace.size() != 0){
//				keyWordList = addKeyWord(keyWordList, keyWordIdListExtractFirstPlace, phraseRestoreList, sentenceId, 4);
//			}
			keyWordList = addKeyWord2(keyWordList, keyWordIdList, phraseRestoreList, sentenceId);
			
		}
		return keyWordList;
	}

	public static ArrayList<Integer> getKeyWordIdList
	(ArrayList<String> medicineNameList, ArrayList<Phrase> phraseList, String target, int patternType){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>(); 
		for(int id = 0; id < phraseList.size(); id++){
			ArrayList<Morpheme> morphemeList = phraseList.get(id).getMorphemeList();
			String lastMorphemeText = morphemeList.get(morphemeList.size()-1).getMorphemeText();
			if(!Filter.isGAorHAorWOorNIorMOorNIMO(lastMorphemeText)){ continue; } // 助詞の条件付け
			//if(!Filter.isGAorHAorWOorNIorMOorNIMOorKARAorMADEorTOKAorNO(lastMorphemeText)){ continue; } // 助詞の条件付け
			//if(!Filter.isGAorHAorWOorMO(lastMorphemeText)){ continue; } // 助詞の条件付け
			Element targetOriginalElement = TripleSetMaker.getOriginalElement(morphemeList, 1);
			String targetText = targetOriginalElement.getText();
			if(!targetText.equals(target)){ continue; }
			int effectId = phraseList.get(id).getDependencyIndex();
			if(effectId == -1){ continue; }// 対象文節の係り先がない
			switch(patternType){
			case 3:
				//keyWordIdList = P3KeyWordSearcher.getKeyWordIdList(id, effectId, phraseList);
				keyWordIdList.addAll(P3KeyWordSearcher.getKeyWordIdList(id, effectId, phraseList));
				break;
			case 4:
				keyWordIdList.addAll(P4KeyWordSearcher.getKeyWordIdList(id, effectId, phraseList));
				//keyWordIdList = P4KeyWordSearcher.getKeyWordIdList(id, effectId, phraseList);
				break;
			case 101:
				if(P101KeyWordSearcher.judgeKeyWordId(id, phraseList, medicineNameList)){ keyWordIdList.add(id-1); }
				break;
			case 10:
				keyWordIdList.addAll(P10KeyWordSearcher.getKeyWordIdList(id, effectId, phraseList, medicineNameList));
				//keyWordIdList = P10KeyWordSearcher.getKeyWordIdList(id, effectId, phraseList, medicineNameList);
				break;
			case 11:
				keyWordIdList.addAll(P11KeyWordSearcher.getKeyWordIdList(id, effectId, phraseList, medicineNameList));
				//keyWordIdList = P10KeyWordSearcher.getKeyWordIdList(id, effectId, phraseList, medicineNameList);
				break;
			}
		}
		return keyWordIdList;
	}

	public static ArrayList<KeyWord> addKeyWord
	(ArrayList<KeyWord> keyWordList, ArrayList<Integer> keyWordIdList, ArrayList<Phrase> phraseList, int sentenceId, int pattern){
		int keyWordPlace = -1;
		int medicinePlaceIndex = -1;
		for(int id : keyWordIdList){
			Phrase phrase = phraseList.get(id);
			ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();

			//P3、P101の時は、薬剤名のすぐ後ろを手がかり語とする
			if(pattern == 3){
				// 薬剤名の形態素位置取得
				for(int i = 0; i<morphemeList.size(); i++){
					String morphemeText = morphemeList.get(i).getMorphemeText();
					if(!Logic.containsMedicine(morphemeText)){ continue; }
					medicinePlaceIndex = i;
					break;
				}
				keyWordPlace = medicinePlaceIndex + 1;
			}
			//P4、P10の時は、最初の形態素を手がかり語とする
			else if(pattern == 4){ keyWordPlace = 0; }

			if(keyWordPlace >= morphemeList.size()){ continue; } //形態素存在チェック
			Morpheme morpheme = morphemeList.get(keyWordPlace);
			if(morpheme.getPartOfSpeechDetails().equals("数") || morpheme.getPartOfSpeechDetails().contains("括弧")){ 
				if(keyWordPlace + 1 >= morphemeList.size()){ continue; } //形態素存在チェック
				morpheme = morphemeList.get(keyWordPlace + 1); 
			}

			//手がかり語の適切性判断
			if(Logic.properKeyWord(morpheme) == false){ continue; }

			//ゴミ取り
			String morphemeText = morpheme.getMorphemeText();
			morphemeText = Logic.cleanWord(morphemeText);
			if(morphemeText.equals("")){ continue; }
			String morphemeOriginalText = morpheme.getOriginalForm();
			if(!morphemeOriginalText.equals("*")){
				KeyWord keyWord = new KeyWord(morphemeOriginalText);
				keyWord.setSentenceId(sentenceId);
				keyWordList.add(keyWord);
			}else{
				KeyWord keyWord = new KeyWord(morphemeText);
				keyWord.setSentenceId(sentenceId);
				keyWordList.add(keyWord);
			}
			
		}
		return keyWordList;
	}
	
	public static ArrayList<KeyWord> addKeyWord2
	(ArrayList<KeyWord> keyWordList, ArrayList<Integer> keyWordIdList, ArrayList<Phrase> phraseList, int sentenceId){
		int keyWordPlace = -1;
		int medicinePlaceIndex = -1;
		for(int id : keyWordIdList){
			Phrase phrase = phraseList.get(id);
			ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();

			//文節内に薬剤名を含む時は，薬剤名のすぐ後ろを手がかり語とする
			if(Logic.containsMedicine(phrase.getPhraseText())){
				// 薬剤名の形態素位置取得
				for(int i = 0; i<morphemeList.size(); i++){
					String morphemeText = morphemeList.get(i).getMorphemeText();
					if(!Logic.containsMedicine(morphemeText)){ continue; }
					medicinePlaceIndex = i;
					break;
				}
				keyWordPlace = medicinePlaceIndex + 1;
			}
			//文節内に薬剤名を含まない時は，最初の形態素を手がかり語とする
			else { keyWordPlace = 0; }

			if(keyWordPlace >= morphemeList.size()){ continue; } //形態素存在チェック
			Morpheme morpheme = morphemeList.get(keyWordPlace);
			if(morpheme.getPartOfSpeechDetails().equals("数") || morpheme.getPartOfSpeechDetails().contains("括弧")){ 
				if(keyWordPlace + 1 >= morphemeList.size()){ continue; } //形態素存在チェック
				morpheme = morphemeList.get(keyWordPlace + 1); 
			}

			//手がかり語の適切性判断
			if(Logic.properKeyWord(morpheme) == false){ continue; }

			//ゴミ取り
			String morphemeText = morpheme.getMorphemeText();
			morphemeText = Logic.cleanWord(morphemeText);
			if(morphemeText.equals("")){ continue; }
			String morphemeOriginalText = morpheme.getOriginalForm();
			if(!morphemeOriginalText.equals("*")){
				KeyWord keyWord = new KeyWord(morphemeOriginalText);
				keyWord.setSentenceId(sentenceId);
				keyWordList.add(keyWord);
			}else{
				KeyWord keyWord = new KeyWord(morphemeText);
				keyWord.setSentenceId(sentenceId);
				keyWordList.add(keyWord);
			}
			
		}
		return keyWordList;
	}

}
