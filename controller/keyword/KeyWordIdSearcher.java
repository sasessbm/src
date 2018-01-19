package controller.keyword;

import java.util.ArrayList;

import model.Phrase;

public class KeyWordIdSearcher {

	public static ArrayList<Integer> searchPattern3(int targetId, int effectId, ArrayList<Phrase> phraseList){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>(); 
		//上から探索
		for(Phrase phrase : phraseList){
			int id = phrase.getId();
			if(id == targetId){ break; } //対象文節まで到達した時
			if(phrase.getDependencyIndex() != effectId){ continue; }
			if(LogicOfKeyWord.judgeKeyWordPhraseForP3(phrase)){ keyWordIdList.add(id); }
			else{ keyWordIdList = LogicOfKeyWord.searchKeyWordPhrase(id, phraseList, keyWordIdList, 3, false); }
		}
		return keyWordIdList;
	}

	public static ArrayList<Integer> searchPattern4(int targetId, int effectId, ArrayList<Phrase> phraseList){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>();
		for(Phrase phrase : phraseList){
			int id = phrase.getId();
			int dIndex = phrase.getDependencyIndex();
			if(id == targetId){ break; } //対象文節まで到達した時
			if(dIndex != effectId){ continue; }
			//if(!PhraseChecker.conditionPartOfSpeechDetails(phrase.getMorphemeList())){ continue; } //助詞の条件付け
			if(LogicOfKeyWord.judgeKeyWordPhraseForP4(id, phraseList)){ keyWordIdList.add(id); }
			else{ keyWordIdList.addAll(LogicOfKeyWord.searchKeyWordPhrase(id, phraseList, keyWordIdList, 4, false)); }
		}
		return keyWordIdList;
	}

	public static ArrayList<Integer> searchPattern10
	(int targetId, int effectId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>();
		int effectDIndex = phraseList.get(effectId).getDependencyIndex();
		if(effectDIndex == -1){ return keyWordIdList; } //係り先が無い
		for(Phrase phrase : phraseList){
			int id = phrase.getId();
			if(id == targetId){ break; } //対象文節まで到達した時
			if(phrase.getDependencyIndex() != effectDIndex){ continue; }
			//if(!PhraseChecker.conditionPartOfSpeechDetails(phrase.getMorphemeList())){ continue; } //助詞の条件付け
			if(LogicOfKeyWord.judgeKeyWordPhraseForP4(id, phraseList)){ keyWordIdList.add(id); }
			else{ keyWordIdList.addAll(LogicOfKeyWord.searchKeyWordPhrase(id, phraseList, keyWordIdList, 4, false)); }
		}
		return keyWordIdList;
	}

	public static ArrayList<Integer> searchPattern11
	(int targetId, int effectId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){
		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>();
		int effectDIndex = phraseList.get(effectId).getDependencyIndex();
		if(effectDIndex == -1){ return keyWordIdList; } //係り先が無い
		for(Phrase phrase : phraseList){
			int id = phrase.getId();
			if(id == targetId){ break; } //対象文節まで到達した時
			if(phrase.getDependencyIndex() != effectDIndex){ continue; }
			if(LogicOfKeyWord.judgeKeyWordPhraseForP3(phrase)){ keyWordIdList.add(id); }
			else{ keyWordIdList = LogicOfKeyWord.searchKeyWordPhrase(id, phraseList, keyWordIdList, 3, false); }
		}
		return keyWordIdList;
	}
	
	public static boolean searchPattern101(int targetId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){
		if(targetId == 0){ return false; }
		if(!LogicOfKeyWord.judgeKeyWordPhraseForP3(phraseList.get(targetId - 1))){ return false;}
		return true;
	}

}
