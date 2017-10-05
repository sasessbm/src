package controller.keyword;

import java.util.ArrayList;

import model.Phrase;
import controller.logic.Logic;

public class P10KeyWordSearcher {

	public static ArrayList<Integer> getKeyWordIdList
						(int targetId, int effectId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>();
		int effectDIndex = phraseList.get(effectId).getDependencyIndex();
		if(effectDIndex == -1){ return keyWordIdList; } //係り先が無い
		for(Phrase phrase : phraseList){
			int id = phrase.getId();
			if(id == targetId){ break; } //対象文節まで到達した時
			if(phrase.getDependencyIndex() != effectDIndex){ continue; }
			//if(!PhraseChecker.conditionPartOfSpeechDetails(phrase.getMorphemeList())){ continue; } //助詞の条件付け
			if(LogicOfKeyWord.judgeKeyWordPhrase(id, phraseList)){ keyWordIdList.add(id); }
			else{ keyWordIdList.addAll(LogicOfKeyWord.searchKeyWordPhrase(id, phraseList)); }
		}
		return keyWordIdList;
	}

}
