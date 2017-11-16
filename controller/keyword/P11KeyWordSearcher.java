package controller.keyword;

import java.util.ArrayList;

import model.Phrase;
import controller.logic.Logic;

public class P11KeyWordSearcher {

	public static ArrayList<Integer> getKeyWordIdList
						(int targetId, int effectId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>();
		int effectDIndex = phraseList.get(effectId).getDependencyIndex();
		if(effectDIndex == -1){ return keyWordIdList; } //係り先が無い
		for(Phrase phrase : phraseList){
			int id = phrase.getId();
			if(id == targetId){ break; } //対象文節まで到達した時
			if(phrase.getDependencyIndex() != effectDIndex){ continue; }
			if(LogicOfKeyWord.judgeKeyWordPhraseForP3(phrase)){ keyWordIdList.add(id); }
			//else{ keyWordIdList.addAll(LogicOfKeyWord.searchKeyWordPhraseForP3(id, phraseList)); }
			else{ keyWordIdList = LogicOfKeyWord.searchKeyWordPhrase(id, phraseList, keyWordIdList, 3, false); }
		}
		return keyWordIdList;
	}

}
