package controller.keyword;

import java.util.ArrayList;

import controller.logic.Logic;
import controller.tripleset.PhraseChecker;
import model.*;

public class P4KeyWordSearcher {

	public static ArrayList<Integer> getKeyWordIdList(int targetId, int effectId, ArrayList<Phrase> phraseList){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>();
		for(Phrase phrase : phraseList){
			int id = phrase.getId();
			int dIndex = phrase.getDependencyIndex();
			if(id == targetId){ break; } //対象文節まで到達した時
			if(dIndex != effectId){ continue; }
			//if(!PhraseChecker.conditionPartOfSpeechDetails(phrase.getMorphemeList())){ continue; } //助詞の条件付け
			if(LogicOfKeyWord.judgeKeyWordPhrase(id, phraseList)){ keyWordIdList.add(id); }
			else{ keyWordIdList.addAll(LogicOfKeyWord.searchKeyWordPhrase(id, phraseList)); }
		}
		return keyWordIdList;
	}

	

}
