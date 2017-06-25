package controller.keyword;

import java.util.ArrayList;

import controller.Logic;
import model.*;

public class P4Searcher {

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
		boolean isKeyWordPhrase = false;

		for(Phrase phrase : phraseList){
			int dependencyIndex = phrase.getDependencyIndex();

			//if(phrase.getId() == targetId){ break; } //対象文節まで到達した時

			if(dependencyIndex == effectId){
				keyWordId = phrase.getId();
				isKeyWordPhrase = judgeKeyWordPhrase(keyWordId, phraseList ,medicineNameList);
				if(isKeyWordPhrase){ break; }
			}
			keyWordId = -1;
		}
		return keyWordId;
	}

	public static boolean judgeKeyWordPhrase(int keyWordId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){

		for(Phrase phrase : phraseList){
			//String text = phrase.getPhraseText();
			int dependencyIndex = phrase.getDependencyIndex();
			if(dependencyIndex == keyWordId && Logic.containsMedicine(phrase.getPhraseText())){
				return true;
			}
		}
		return false;
	}

}
