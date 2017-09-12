package controller.keyword;

import java.util.ArrayList;

import controller.logic.Logic;
import controller.tripleset.PhraseChecker;
import model.*;

public class P4KeyWordSearcher {

	public static ArrayList<Integer> getKeyWordIdList(int targetId, int effectId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>();
		for(Phrase phrase : phraseList){
			int id = phrase.getId();
			int dIndex = phrase.getDependencyIndex();
			if(id == targetId){ break; } //対象文節まで到達した時
			if(dIndex != effectId){ continue; }
			//if(!PhraseChecker.conditionPartOfSpeechDetails(phrase.getMorphemeList())){ continue; } //助詞の条件付け
			if(!judgeKeyWordPhrase(id, phraseList, medicineNameList)){ continue; }
			keyWordIdList.add(id);
		}
		return keyWordIdList;
	}

	public static boolean judgeKeyWordPhrase(int keyWordId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){
		for(Phrase phrase : phraseList){
			int dIndex = phrase.getDependencyIndex();
			if(dIndex == keyWordId && Logic.containsMedicine(phrase.getPhraseText())){
				//if(!PhraseChecker.conditionPartOfSpeech(phrase.getMorphemeList())){ return false; } //薬剤名文節の助詞の条件付け
				return true;
			}
		}
		return false;
	}

}
