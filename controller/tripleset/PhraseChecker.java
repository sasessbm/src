package controller.tripleset;

import java.util.ArrayList;

import model.KeyWord;
import model.Morpheme;
import model.Phrase;
import model.TripleSetInfo;

public class PhraseChecker {

	public static final String MEDICINE = "MEDICINE";

	//薬剤名の形態素位置取得
	public static int getMedicinePlaceIndex(ArrayList<Morpheme> morphemeList){
		int medicinePlaceIndex = -1;
		for(int i = morphemeList.size() - 1; i >= 0; i--){
			String morphemeText = morphemeList.get(i).getMorphemeText();
			if(!morphemeText.contains(MEDICINE)){ continue; }
			medicinePlaceIndex = i;
			break;
		}
		return medicinePlaceIndex;
	}

	//手がかり語の形態素位置取得
	public static int getKeywordPlaceIndex(ArrayList<Morpheme> morphemeList, String keyWordText){
		int keywordPlaceIndex = -1;
		int morphemeIndex = -1;
		for(Morpheme morpheme : morphemeList){
			morphemeIndex ++;
			String originalForm = morpheme.getOriginalForm();
			if(originalForm.equals(keyWordText)){
				keywordPlaceIndex = morphemeIndex;
			}
		}
		return keywordPlaceIndex;
	}

	//助詞の条件付け（詳細な品詞）
	public static boolean conditionPartOfSpeechDetails(ArrayList<Morpheme> morphemeList){
		String partOfSpeechDetails = morphemeList.get(morphemeList.size()-1).getPartOfSpeechDetails();
		if(partOfSpeechDetails.contains("格助詞") || partOfSpeechDetails.contains("接続助詞")){ return true; }
		//if(!(partOfSpeechDetails.contains("格助詞") || partOfSpeechDetails.contains("接続助詞") || partOfSpeechDetails.contains("読点"))){ return true; }
		//if(!(partOfSpeechDetails.contains("助詞") || partOfSpeechDetails.contains("読点"))){ return true; }
		//if(!(partOfSpeechDetails.contains("助詞"))){ return true; }
		return false;
	}

	//助詞の条件付け（品詞）
	public static boolean conditionPartOfSpeech(ArrayList<Morpheme> morphemeList){
		String partOfSpeech = morphemeList.get(morphemeList.size()-1).getPartOfSpeech();
		if(!(partOfSpeech.contains("助詞"))){ return true; }
		return false;
	}
	
	//手がかり語文節の適切性判断
	public static boolean judgeKeyPhrase(int medicineDIndex, ArrayList<Phrase> phraseList, String keyWordText){
		Phrase medicineDPhrase = phraseList.get(medicineDIndex);
		ArrayList<Morpheme> morphemeList = medicineDPhrase.getMorphemeList();
		int keywordPlaceIndex = PhraseChecker.getKeywordPlaceIndex(morphemeList, keyWordText);
		if(keywordPlaceIndex != 0){ return false; } //最初の位置か
		//if(!conditionPartOfSpeechDetails(morphemeList)){ return false; } //末尾の助詞確認
		return true;
	}

	//対象文節ID取得
	public static ArrayList<Integer> getTargetIdList(int effectId, int keyId, ArrayList<Phrase> phraseList){
		ArrayList<Integer> targetIdList = new ArrayList<Integer>();
		// 逆から探索
		for(int i=1; i<=phraseList.size(); i++){
			int phraseId = phraseList.size() - i;
			if(phraseId == keyId){ break; } //「手がかり語」文節まで到達した時
			Phrase phrase = phraseList.get(phraseId);
			if(phrase.getDependencyIndex() != effectId){ continue; }
			ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
			if(!judgeTargetPhrase(morphemeList)){ continue; } // 助詞の条件付け
			targetIdList.add(phraseId);
		}
		return targetIdList;
	}
	
	//対象文節の助詞の条件付け
	public static boolean judgeTargetPhrase(ArrayList<Morpheme> morphemeList){
		String lastMorphemeText = morphemeList.get(morphemeList.size()-1).getMorphemeText();
		//if(!Filter.isGAorHAorWO(lastMorphemeText)){ continue; } 
		if(Filter.isGAorHAorWOorNIorMOorNIMO(lastMorphemeText)){ return true; } 
		return false;
	}
	
	//効果文節ID取得（P10用）
	public static ArrayList<Integer> getEffectIdList(int keyDIndex, int keyId, ArrayList<Phrase> phraseList){
		ArrayList<Integer> effectIdList = new ArrayList<Integer>();
		// 逆から探索
		for(int i=1; i<=phraseList.size(); i++){
			int phraseId = phraseList.size() - i;
			if(phraseId == keyId){ break; } //「手がかり語」文節まで到達した時
			Phrase phrase = phraseList.get(phraseId);
			if(phrase.getDependencyIndex() != keyDIndex){ continue; }
			effectIdList.add(phraseId);
		}
		return effectIdList;
	}
	
	//手がかり語の最終文節ID取得
	public static ArrayList<Integer> getKeyIdList(int id, ArrayList<Phrase> phraseList, ArrayList<KeyWord> keyList){
		ArrayList<Integer> keyIdList = new ArrayList<Integer>();
		keyIdList.add(id);
		Phrase phrase = phraseList.get(id);
		int dIndex = phrase.getDependencyIndex();
		while(true){
			phrase = phraseList.get(dIndex);
			if(!judgeContainsKeyInPhrase(phrase, keyList)){ break; } //文節内に手がかり語があるか
			if(phrase.getDependencyIndex() == -1){ break; } //文節の係り先が存在するか
			keyIdList.add(dIndex);
			dIndex = phrase.getDependencyIndex();
		}
		return keyIdList;
	}
	
	//文節内に手がかり語が含まれているか確認
	public static boolean judgeContainsKeyInPhrase(Phrase phrase, ArrayList<KeyWord> keyList){
		ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
		for(KeyWord key : keyList){
			if(getKeywordPlaceIndex(morphemeList, key.getText()) != -1){ return true; }
		}
		return false;
	}

}
