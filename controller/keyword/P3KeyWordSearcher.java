package controller.keyword;

import java.util.ArrayList;

import controller.logic.Logic;
import controller.tripleset.PhraseChecker;
import model.*;

public class P3KeyWordSearcher {

	public static ArrayList<Integer> getKeyWordIdList(int targetId, int effectId, ArrayList<Phrase> phraseList){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>(); 
		//上から探索
		for(Phrase phrase : phraseList){
			int id = phrase.getId();
			if(id == targetId){ break; } //対象文節まで到達した時
			if(phrase.getDependencyIndex() != effectId){ continue; }
			if(LogicOfKeyWord.judgeKeyWordPhraseForP3(phrase)){ keyWordIdList.add(id); }
			else{ keyWordIdList.addAll(LogicOfKeyWord.searchKeyWordPhraseForP3(id, phraseList)); }
		}
		return keyWordIdList;
	}

}
