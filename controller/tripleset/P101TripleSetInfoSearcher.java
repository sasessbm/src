package controller.tripleset;

import java.util.ArrayList;
import java.util.TreeMap;

import model.Morpheme;
import model.Phrase;
import model.Sentence;
import model.TripleSetInfo;

public class P101TripleSetInfoSearcher {

	public static final String MEDICINE = "MEDICINE";

	public static ArrayList<TripleSetInfo> getTripleSetInfoList 
	(ArrayList<Sentence> sentenceList, String keyText, int targetParticleType) {
		ArrayList<TripleSetInfo> tripleSetInfoList = new ArrayList<TripleSetInfo>();

		for(Sentence sentence : sentenceList){
			//if(sentence.getSentenceId() != 717){ continue; } //デバッグ用
			ArrayList<Phrase> phraseList = sentence.getPhraseReplaceList();
			int sentenceId = sentence.getSentenceId();
			String sentenceText = sentence.getText();
			for(Phrase phrase : phraseList){
				
				//薬剤名文節探索
				ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
				int medicinePlaceIndex = PhraseChecker.getMedicinePlaceIndex(morphemeList);
				if(medicinePlaceIndex == -1){ continue; } //薬剤名がない
				int keywordPlaceIndex = PhraseChecker.getKeywordPlaceIndex(morphemeList, keyText);
				if(keywordPlaceIndex == -1){ continue; } //手がかり語がない

				//手がかり語文節の確定
				if((keywordPlaceIndex - 1) != medicinePlaceIndex){ continue; } //隣り合っているか
				//if(!PhraseChecker.particleConditioning(morphemeList)){ continue; } //末尾の助詞確認
				
				//薬剤名文節獲得
				int medicineId = phrase.getId();
				ArrayList<Integer> medicineIdList = LogicOfTripleSetInfoSearcher.getMedicineIdList(medicineId, phraseList);
				
				//効果・対象文節探索
				int keyId = phrase.getId();
				if(keyId > phraseList.size() - 2){ continue; } //薬剤名文節が最後または最後から2番目だった場合
				int targetId = keyId + 1;
				int effectId = phraseList.get(targetId).getDependencyIndex();
				if(effectId == -1){ continue; } //対象要素の係り先があるか
				
				//手がかり語リストに追加
				ArrayList<String> usedKeyList = new ArrayList<String>();
				usedKeyList.add(keyText);
				
				//ArrayList<Integer> targetIdList = new ArrayList<Integer>();
				//targetIdList.add(targetId);
				TreeMap<Integer, Integer> targetEffectIdMap = new TreeMap<Integer, Integer>();
				targetEffectIdMap = PhraseChecker.getTargetEffectIdMap(effectId, keyId, phraseList, targetEffectIdMap);
//				if(phraseList.get(effectId).getDependencyIndex() != -1){
//					keyId = effectId;
//					effectId = phraseList.get(effectId).getDependencyIndex();
//					//targetIdList.addAll(PhraseChecker.getTargetIdList(effectId, keyId, phraseList));
//					
//					
//				}
				
				//三つ組情報生成
				LogicOfTripleSetInfoSearcher.addTripleSetInfoList
				(tripleSetInfoList, targetEffectIdMap, phraseList, sentenceId, sentenceText, medicineIdList, 101, usedKeyList, targetParticleType);
				
			}
		}
		return tripleSetInfoList;
	}
}
