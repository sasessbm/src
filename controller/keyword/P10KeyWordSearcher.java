package controller.keyword;

import java.util.ArrayList;

import model.Phrase;
import controller.logic.Logic;

public class P10KeyWordSearcher {
	
	public static int getEffectId(int targetDependencyIndex, ArrayList<Phrase> phraseList){
		int effectId = -1;
		for(Phrase phrase : phraseList){
			int phraseId = phrase.getId();
			if(phraseId == targetDependencyIndex){
				effectId = phraseId;
				break;
			}
		}
		return effectId;
	}

	public static int getKeyWordId(int targetId, int effectId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){
		int keyWordId = -1;
		int effectDIndex = -1;
		boolean isKeyWordPhrase = false;
		
		for(Phrase phrase : phraseList){
			if(phrase.getId() != effectId){ continue; }
			effectDIndex = phrase.getDependencyIndex();
		}
		
		if(effectDIndex == -1){ return keyWordId; } //係り先が無い
		
		for(Phrase phrase : phraseList){
			if(phrase.getId() == targetId){ break; } //対象文節まで到達した時
			if(phrase.getDependencyIndex() != effectDIndex){ continue; }
			String partOfSpeechDetails = phrase.getMorphemeList().get(phrase.getMorphemeList().size()-1).getPartOfSpeechDetails();
			//if(!(partOfSpeechDetails.contains("格助詞") || partOfSpeechDetails.contains("接続助詞"))){ continue; }
			keyWordId = phrase.getId();
			isKeyWordPhrase = judgeKeyWordPhrase(keyWordId, phraseList, medicineNameList);
			if(isKeyWordPhrase){ break; }
			keyWordId = -1;
		}
		
		return keyWordId;
	}

	public static boolean judgeKeyWordPhrase(int keyWordId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){
		for(Phrase phrase : phraseList){
			//String text = phrase.getPhraseText();
			int dependencyIndex = phrase.getDependencyIndex();
			if(dependencyIndex == keyWordId && Logic.containsMedicine(phrase.getPhraseText())){
				String partOfSpeech = phrase.getMorphemeList().get(phrase.getMorphemeList().size()-1).getPartOfSpeech();
				//if(!partOfSpeech.contains("助詞")){ continue; }
				return true;
			}
		}
		return false;
	}

}