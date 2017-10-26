package controller.keyword;

import java.util.ArrayList;

import controller.logic.Logic;
import model.Phrase;

public class LogicOfKeyWord {

	//P4,P10用
	public static boolean judgeKeyWordPhraseForP4(int keyWordId, ArrayList<Phrase> phraseList){
		for(Phrase phrase : phraseList){
			int dIndex = phrase.getDependencyIndex();
			if(dIndex == keyWordId && Logic.containsMedicine(phrase.getPhraseText())){
				//if(!PhraseChecker.conditionPartOfSpeech(phrase.getMorphemeList())){ return false; } //薬剤名文節の助詞の条件付け
				return true;
			}
		}
		return false;
	}

	//P4,P10用(手がかり語が続いている場合に対応)
	public static ArrayList<Integer> searchKeyWordPhraseForP4(int effectId, ArrayList<Phrase> phraseList){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>(); 
		for(Phrase phrase : phraseList){
			int id = phrase.getId();
			if(id >= effectId){ break; } //効果文節まで到達した時
			if(phrase.getDependencyIndex() != effectId){ continue; }
			if(judgeKeyWordPhraseForP4(id, phraseList)){ keyWordIdList.add(id); }
			else{ keyWordIdList.addAll(searchKeyWordPhraseForP4(id, phraseList)); }
		}
		return keyWordIdList;
	}

	//P3,P11用
	public static boolean judgeKeyWordPhraseForP3(Phrase phrase){
		//文節の中身が1形態素以下なら不適
		if(phrase.getMorphemeList().size() < 2){ return false; }
		//if(!PhraseChecker.conditionPartOfSpeechDetails(phrase.getMorphemeList())){ return false; } //助詞の条件付け
		return Logic.containsMedicine(phrase.getPhraseText());
	}

	//P3,P11用(手がかり語が続いている場合に対応)
	public static ArrayList<Integer> searchKeyWordPhraseForP3(int effectId, ArrayList<Phrase> phraseList){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>(); 
		for(Phrase phrase : phraseList){
			int id = phrase.getId();
			if(id >= effectId){ break; } //効果文節まで到達した時
			if(phrase.getDependencyIndex() != effectId){ continue; }
			if(judgeKeyWordPhraseForP3(phrase)){ keyWordIdList.add(id); }
			else{ keyWordIdList.addAll(searchKeyWordPhraseForP3(id, phraseList)); }
		}
		return keyWordIdList;
	}


}
