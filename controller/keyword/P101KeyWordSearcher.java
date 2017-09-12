package controller.keyword;

import java.util.ArrayList;

import model.Phrase;
import controller.logic.Logic;

public class P101KeyWordSearcher {

	public static boolean judgeKeyWordId(int targetId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){
		
		if(targetId == 0){ return false; }
		if(!judgeKeyWordPhrase(phraseList.get(targetId - 1), medicineNameList)){ return false;}
		return true;
	}

	public static boolean judgeKeyWordPhrase(Phrase phrase, ArrayList<String> medicineNameList){
		//文節の中身が1形態素以下なら不適
		if(phrase.getMorphemeList().size() < 2){ return false; }
		return Logic.containsMedicine(phrase.getPhraseText());
	}


}
