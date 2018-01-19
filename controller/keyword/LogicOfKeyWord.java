package controller.keyword;

import java.util.ArrayList;

import controller.logic.Logic;
import model.KeyWord;
import model.Phrase;

public class LogicOfKeyWord {

	//手がかり語文節判定（P3,P11用）
	public static boolean judgeKeyWordPhraseForP3(Phrase phrase){
		//文節の中身が1形態素以下なら不適
		if(phrase.getMorphemeList().size() < 2){ return false; }
		return Logic.containsMedicine(phrase.getPhraseText());
	}

	//手がかり語文節判定（P4,P10用）
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

	//手がかり語が続いている場合に対応
	public static ArrayList<Integer> searchKeyWordPhrase
	(int effectId, ArrayList<Phrase> phraseList, ArrayList<Integer> keyWordIdList, int type, boolean isKeyId){
		if(isKeyId){ return keyWordIdList; }
		for(Phrase phrase : phraseList){
			int id = phrase.getId();
			if(id >= effectId){ break; } //効果文節まで到達した時
			if(phrase.getDependencyIndex() != effectId){ continue; }
			if((type == 3 && judgeKeyWordPhraseForP3(phrase)) || (type == 4 && judgeKeyWordPhraseForP4(id, phraseList))){
				keyWordIdList.add(id);
				isKeyId = true;
				break;
			}
			else{ 
				keyWordIdList.add(id);
				keyWordIdList = searchKeyWordPhrase(id, phraseList, keyWordIdList, type, isKeyId);
			}
		}
		if(!isKeyId){ keyWordIdList.clear(); } //薬剤名文節が無かった時，手がかり語ではない
		return keyWordIdList;
	}

	//手がかり語の型変換
	public static ArrayList<KeyWord> transformStringToKeyWord(ArrayList<String> textList){
		ArrayList<KeyWord> keyWordList = new ArrayList<KeyWord>();
		for(String text : textList){
			keyWordList.add(new KeyWord(text));
		}
		return keyWordList;
	}


}
