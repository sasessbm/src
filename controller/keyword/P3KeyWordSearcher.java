package controller.keyword;

import java.util.ArrayList;

import controller.logic.Logic;
import controller.tripleset.PhraseChecker;
import model.*;

public class P3KeyWordSearcher {

	public static ArrayList<Integer> getKeyWordIdList(int targetId, int effectId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>(); 
		//上から探索
		for(Phrase phrase : phraseList){
			int id = phrase.getId();
			int dIndex = phrase.getDependencyIndex();
			if(id == targetId){ break; } //対象文節まで到達した時
			if(dIndex == effectId && judgeKeyWordPhrase(phrase, medicineNameList)){
				keyWordIdList.add(id);
			}
		}
		return keyWordIdList;
	}

	public static boolean judgeKeyWordPhrase(Phrase phrase, ArrayList<String> medicineNameList){
		//文節の中身が1形態素以下なら不適
		if(phrase.getMorphemeList().size() < 2){ return false; }
		//if(!PhraseChecker.conditionPartOfSpeechDetails(phrase.getMorphemeList())){ return false; } //助詞の条件付け
		return Logic.containsMedicine(phrase.getPhraseText());
	}

}
