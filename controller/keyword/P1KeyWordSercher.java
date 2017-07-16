package controller.keyword;

import java.util.ArrayList;

import controller.logic.Logic;
import model.Phrase;

public class P1KeyWordSercher {

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
		//上から探索
		for(Phrase phrase : phraseList){
			int dependencyIndex = phrase.getDependencyIndex();
			//if(phrase.getId() == targetId){ break; } //対象文節まで到達した時
			if(dependencyIndex == targetId && judgeKeyWordPhrase(phrase, medicineNameList)){
				keyWordId = phrase.getId();
			}
		}
		return keyWordId;
	}
	
	public static boolean judgeKeyWordPhrase(Phrase phrase, ArrayList<String> medicineNameList){
		//文節の中身が1形態素以下なら不適
		if(phrase.getMorphemeList().size() < 2){ return false; }
		return Logic.containsMedicine(phrase.getPhraseText());
	}

}
