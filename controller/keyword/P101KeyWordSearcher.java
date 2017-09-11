package controller.keyword;

import java.util.ArrayList;

import model.Phrase;
import controller.logic.Logic;

public class P101KeyWordSearcher {

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

	public static int getKeyWordId(int targetId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){
		int keyWordId = -1;
		if(targetId == 0){ return keyWordId; }
		if(judgeKeyWordPhrase(phraseList.get(targetId - 1), medicineNameList)){
			keyWordId = targetId - 1;
		}
		return keyWordId;
	}

	public static boolean judgeKeyWordPhrase(Phrase phrase, ArrayList<String> medicineNameList){
		//文節の中身が1形態素以下なら不適
		if(phrase.getMorphemeList().size() < 2){ return false; }
		return Logic.containsMedicine(phrase.getPhraseText());
	}


}