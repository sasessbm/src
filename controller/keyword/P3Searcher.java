package controller.keyword;

import java.util.ArrayList;

import controller.Logic;
import model.*;

public class P3Searcher {
	
	//public static final String MEDICINE = "MEDICINE";

	public static int getEffectId(int targetDependencyIndex, String effect, ArrayList<Phrase> phraseList){

		int effectId = -1;
		for(Phrase phrase : phraseList){
			int phraseId = phrase.getId();
			if(phraseId == targetDependencyIndex){
				//String changeEffectForm = ChangePhraseForm.changePhraseForm(phrase.getMorphemeList(), 2);
				//if(changeEffectForm.contains(effect)){
					effectId = phraseId;
					break;
				//}
			}
		}
		return effectId;
	}

	public static int getKeyWordId(int targetId, int effectId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){

		int keyWordId = -1;
		
		//上から探索
		for(Phrase phrase : phraseList){
			int dependencyIndex = phrase.getDependencyIndex();
			//if(phrase.getId() == targetId){ break; } //対象文節まで到達した時
			if(dependencyIndex == effectId && judgeKeyWordPhrase(phrase, medicineNameList)){
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
