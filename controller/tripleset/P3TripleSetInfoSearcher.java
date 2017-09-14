package controller.tripleset;

import java.util.ArrayList;

import controller.logic.OverlapDeleter;
import model.KeyWord;
import model.Morpheme;
import model.Phrase;
import model.Sentence;
import model.TripleSetInfo;

public class P3TripleSetInfoSearcher {

	public static final String MEDICINE = "MEDICINE";

	public static ArrayList<TripleSetInfo> getTripleSetInfoList (ArrayList<Sentence> sentenceList, String keyText, ArrayList<KeyWord> keyList) {
		ArrayList<TripleSetInfo> tripleSetInfoList = new ArrayList<TripleSetInfo>();

		for(Sentence sentence : sentenceList){
			//if(sentence.getSentenceId() != 717){ continue; } //デバッグ用
			ArrayList<Phrase> phraseList = sentence.getPhraseReplaceList();
			int sentenceId = sentence.getSentenceId();
			String sentenceText = sentence.getText();
			for(Phrase phrase : phraseList){

				//薬剤名文節探索
				if(phrase.getDependencyIndex() == -1){ continue; } //係り先がない
				ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
				int medicinePlaceIndex = PhraseChecker.getMedicinePlaceIndex(morphemeList);
				if(medicinePlaceIndex == -1){ continue; } //薬剤名がない
				int keywordPlaceIndex = PhraseChecker.getKeywordPlaceIndex(morphemeList, keyText);
				if(keywordPlaceIndex == -1){ continue; } //手がかり語がない
				
				//手がかり語文節の確定
				if((keywordPlaceIndex - 1) != medicinePlaceIndex){ continue; } //隣り合っているか
				//if(!PhraseChecker.conditionPartOfSpeechDetails(morphemeList)){ continue; } //末尾の助詞確認
				
				//効果・対象文節探索
				int medicineId = phrase.getId();
				ArrayList<Integer> keyIdList = PhraseChecker.getKeyIdList(medicineId, phraseList, keyList);
				ArrayList<String> usedKeyTmpList = new ArrayList<String>();
				for(int keyId : keyIdList){
					
					//手がかり語リストに追加
					ArrayList<String> usedKeyList = new ArrayList<String>();
					String usedKey = keyText;
					if(keyId != medicineId){ usedKey = LogicOfTripleSetInfoSearcher.getUsedKey(keyId, phraseList); }
					usedKeyTmpList.add(usedKey);
					usedKeyList.addAll(usedKeyTmpList);
					
					//対象文節探索
					int keyDIndex = phraseList.get(keyId).getDependencyIndex();
					ArrayList<Integer> targetIdList = PhraseChecker.getTargetIdList(keyDIndex, keyId, phraseList);
					if(targetIdList.size() == 0){ continue; }
					
					//三つ組情報生成
					for(int targetId : targetIdList){
						LogicOfTripleSetInfoSearcher.addTripleSetInfoList
						(tripleSetInfoList, sentenceId, sentenceText, medicineId, targetId, keyDIndex, 3, usedKeyList);
					}
				}
				OverlapDeleter.deleteSameInfo(tripleSetInfoList);
			}
		}
		return tripleSetInfoList;
	}


}
