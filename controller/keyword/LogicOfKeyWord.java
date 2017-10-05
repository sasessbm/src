package controller.keyword;

import java.util.ArrayList;

import controller.logic.Logic;
import model.Phrase;

public class LogicOfKeyWord {

	//P4,P10用
	public static boolean judgeKeyWordPhrase(int keyWordId, ArrayList<Phrase> phraseList){
		for(Phrase phrase : phraseList){
			int dIndex = phrase.getDependencyIndex();
			if(dIndex == keyWordId && Logic.containsMedicine(phrase.getPhraseText())){
				//if(!PhraseChecker.conditionPartOfSpeech(phrase.getMorphemeList())){ return false; } //薬剤名文節の助詞の条件付け
				return true;
			}
		}
		return false;
	}
	
	//P4,P10用
	public static ArrayList<Integer> searchKeyWordPhrase(int id, ArrayList<Phrase> phraseList){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>(); 
		for(Phrase phrase : phraseList){
			if(phrase.getDependencyIndex() != id){ continue; }
			if(judgeKeyWordPhrase(id, phraseList)){ keyWordIdList.add(id); }
			else{ keyWordIdList.addAll(searchKeyWordPhrase(phrase.getId(), phraseList)); }
		}
		return keyWordIdList;
	}

}
