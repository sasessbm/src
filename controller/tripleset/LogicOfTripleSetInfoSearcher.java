package controller.tripleset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

import controller.logic.Logic;
import model.KeyWord;
import model.Phrase;
import model.TripleSetInfo;

public class LogicOfTripleSetInfoSearcher {

	public static String getUsedKey(int keyId, ArrayList<Phrase> phraseList){
		String usedKey = phraseList.get(keyId).getMorphemeList().get(0).getOriginalForm();
		if(usedKey.equals("*")){ usedKey = phraseList.get(keyId).getMorphemeList().get(0).getMorphemeText(); }
		return usedKey;
	}

	public static void addTripleSetInfoList
	(ArrayList<TripleSetInfo> tripleSetInfoList, TreeMap<Integer, Integer> targetEffectIdMap, ArrayList<Phrase> phraseList, int sentenceId, 
	String sentenceText, ArrayList<Integer> medicineIdList, int pattern, ArrayList<String> usedKeyList, int targetParticleType){
		int effectIdTmp = -1;
		int effectRepeatCount = -1;
		
		Iterator<Integer> it = targetEffectIdMap.keySet().iterator();
		while(it.hasNext()){
			int targetId = it.next();
			Phrase targetPhrase = phraseList.get(targetId);
			int effectId = targetEffectIdMap.get(targetId);
			if(effectId != effectIdTmp){ effectRepeatCount++; }
			effectIdTmp = effectId;
			if(!PhraseChecker.judgeTargetPhrase(targetPhrase.getMorphemeList(), targetParticleType)){ continue; }// 助詞の条件付け
			for(int medicineId : medicineIdList){
				TripleSetInfo tripleSetInfo = new TripleSetInfo(sentenceId, sentenceText, medicineId, targetId, effectId);
				tripleSetInfo.setUsedKeyList(usedKeyList);
				tripleSetInfo.setPatternType(pattern);
				tripleSetInfo.setEffectRepeatCount(effectRepeatCount);
				tripleSetInfoList.add(tripleSetInfo);
			}
		}
	}
	
	public static ArrayList<String> getUsedKeyList
	(ArrayList<Integer> keyIdList, ArrayList<Phrase> phraseList, ArrayList<KeyWord> keyList, String keyText){
		Collections.sort(keyIdList);
		ArrayList<String> usedKeyList = new ArrayList<String>();
		usedKeyList.add(keyText);
		for(int i=1; i<keyIdList.size(); i++){ //1番目は飛ばす（P3に対応）
			usedKeyList.add(LogicOfTripleSetInfoSearcher.getUsedKey(keyIdList.get(i), phraseList));
		}
		return usedKeyList;
	}
	
	public static ArrayList<Integer> getMedicineIdList(int medicineId, ArrayList<Phrase> phraseList){
		ArrayList<Integer> medicineIdList = new ArrayList<Integer>();
		medicineIdList.add(medicineId);
		medicineId = medicineId - 1;
		while(medicineId != -1){
			Phrase phrase = phraseList.get(medicineId);
			if(!phrase.getPhraseText().contains("MEDICINE")){ break; } //薬剤名が含まれているか
			if(phrase.getDependencyIndex() != medicineId + 1){ break; } //係り先か
			medicineIdList.add(medicineId);
			medicineId = medicineId - 1;
		}
		return medicineIdList;
	}

}
