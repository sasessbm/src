package controller.keyword;

import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import controller.Logic;
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
	(ArrayList<String> medicineNameList, ArrayList<Sentence> sentenceList, String target, String effect) 
			throws SAXException, IOException, ParserConfigurationException{
		ArrayList<KeyWord> keyWordList = new ArrayList<KeyWord>();

		for(Sentence sentence : sentenceList){
			ArrayList<Integer> P3keyWordIdList = new ArrayList<Integer>();
			ArrayList<Integer> P4keyWordIdList = new ArrayList<Integer>();
			ArrayList<Phrase> phraseRestoreList = sentence.getPhraseRestoreList();

			//手がかり語探索
			P3keyWordIdList.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, effect, 3));
			P4keyWordIdList.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, effect, 4));

			//手がかり語リストに追加
			if(P3keyWordIdList.size() != 0){
				keyWordList = addKeyWord(keyWordList, P3keyWordIdList, phraseRestoreList, 3);
			}
			if(P4keyWordIdList.size() != 0){
				keyWordList = addKeyWord(keyWordList, P4keyWordIdList, phraseRestoreList, 4);
			}
		}
		return keyWordList;
	}

	public static ArrayList<Integer> getKeyWordIdList
	(ArrayList<String> medicineNameList, ArrayList<Phrase> phraseList, String target, String effect, int patternType){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>(); 
		int keyWordId = -1;
		int targetDependencyIndex = -1;
		int effectId = -1;
		//int searchIndex = phraseList.size() - 1;
		//int phraseId = 0;
		//Phrase phrase = phraseList.get(searchIndex);
		//ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
		//		while(searchIndex > 0){
		//			ArrayList<Morpheme> targetMorphemeList = new ArrayList<Morpheme>();
		//			
		//			phraseId = searchIndex;
		////			while(searchIndex > 0){
		////				Collections.reverse(morphemeList);
		////				targetMorphemeList.addAll(morphemeList);
		////				Collections.reverse(morphemeList);
		////				searchIndex--;
		////				phrase = phraseList.get(searchIndex);
		////				morphemeList = phrase.getMorphemeList();
		////				if(!morphemeList.get(morphemeList.size()-1).getMorphemeText().equals("の")){ break; }
		////			}
		//			targetMorphemeList.addAll(morphemeList);
		//			String lastMorphemeText = targetMorphemeList.get(0).getMorphemeText();
		//			if(!(lastMorphemeText.equals("が") || lastMorphemeText.equals("は") 
		//												|| lastMorphemeText.equals("を"))){ continue; }
		//			Collections.reverse(targetMorphemeList);
		//			String targetForm = ChangePhraseForm.changePhraseForm(targetMorphemeList, 1);
		//			//if(!targetForm.contains(target)){ continue; }
		//			if(!targetForm.equals(target)){ continue; }
		//			targetDependencyIndex = phraseList.get(phraseId).getDependencyIndex();
		//			switch(patternType){
		//			case 3:
		//				effectId = P3Searcher.getEffectId(targetDependencyIndex, effect, phraseList);
		//				if(effectId == -1){ continue; }
		//				keyWordId = P3Searcher.getKeyWordId(phraseId, effectId, phraseList, medicineNameList);
		//				break;
		//			case 4:
		//				effectId = P4Searcher.getEffectId(targetDependencyIndex, effect, phraseList);
		//				if(effectId == -1){ continue; }
		//				keyWordId = P4Searcher.getKeyWordId(phraseId, effectId, phraseList, medicineNameList);
		//				break;
		//			}
		//			if(keyWordId == -1 || phraseId == keyWordId){ continue; }
		//			keyWordIdList.add(keyWordId);
		//		} 

		for(int phraseId = 0; phraseId < phraseList.size(); phraseId++){

			ArrayList<Morpheme> targetMorphemeList = new ArrayList<Morpheme>();
			ArrayList<Morpheme> morphemeList = phraseList.get(phraseId).getMorphemeList();
			targetMorphemeList.addAll(morphemeList);
			String lastMorphemeText = targetMorphemeList.get(targetMorphemeList.size()-1).getMorphemeText();
			if(!(lastMorphemeText.equals("が") || lastMorphemeText.equals("は") 
					|| lastMorphemeText.equals("を"))){ continue; }

			Element targetOriginalElement = TripleSetMaker.getOriginalElement(targetMorphemeList, 1);
			String targetText = targetOriginalElement.getText();
			//if(!targetForm.contains(target)){ continue; }
			if(!targetText.equals(target)){ continue; }
			targetDependencyIndex = phraseList.get(phraseId).getDependencyIndex();
			switch(patternType){
			case 3:
				effectId = P3Searcher.getEffectId(targetDependencyIndex, effect, phraseList);
				if(effectId == -1){ continue; }
				keyWordId = P3Searcher.getKeyWordId(phraseId, effectId, phraseList, medicineNameList);
				break;
			case 4:
				effectId = P4Searcher.getEffectId(targetDependencyIndex, effect, phraseList);
				if(effectId == -1){ continue; }
				keyWordId = P4Searcher.getKeyWordId(phraseId, effectId, phraseList, medicineNameList);
				break;
			}
			if(keyWordId == -1 || phraseId == keyWordId){ continue; }
			keyWordIdList.add(keyWordId);
		}


		return keyWordIdList;
	}

	public static ArrayList<KeyWord> addKeyWord
	(ArrayList<KeyWord> keyWordList, ArrayList<Integer> keyWordIdList, ArrayList<Phrase> phraseList, int pattern){
		int keyWordPlace = -1;
		int medicinePlaceIndex = -1;
		for(int id : keyWordIdList){
			Phrase phrase = phraseList.get(id);
			ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();

			//P3の時は、薬剤名のすぐ後ろを手がかり語とする
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
			//P4の時は、最初の形態素を手がかり語とする
			else if(pattern == 4){ keyWordPlace = 0; }

			//文節の末尾確認
			if(!(morphemeList.get(morphemeList.size()-1).getPartOfSpeechDetails().contains("格助詞")
					||morphemeList.get(morphemeList.size()-1).getPartOfSpeechDetails().contains("接続助詞")
					|| morphemeList.get(morphemeList.size()-1).getPartOfSpeechDetails().contains("読点"))){ continue; }
			if(keyWordPlace >= morphemeList.size()){ continue; } //形態素存在チェック
			Morpheme morpheme = morphemeList.get(keyWordPlace);
			if(morpheme.getPartOfSpeechDetails().equals("数")){ 
				if(keyWordPlace + 1 >= morphemeList.size()){ continue; } //形態素存在チェック
				morpheme = morphemeList.get(keyWordPlace + 1); 
			}

			//手がかり語の適切性判断
			if(Logic.properKeyWord(morpheme) == false){ continue; }

			//ゴミ取り
			String morphemeText = Logic.cleanWord(morpheme.getMorphemeText());
			if(morphemeText.equals("")){ continue; }
			String morphemeOriginalText = morpheme.getOriginalForm();

			if(!morphemeOriginalText.equals("*")){
				keyWordList.add(new KeyWord(morphemeOriginalText));
			}else{
				keyWordList.add(new KeyWord(morphemeText));
			}
		}
		return keyWordList;
	}

}
