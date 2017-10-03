package controller.tripleset;

import java.util.ArrayList;

import model.Phrase;
import model.TripleSetInfo;

public class LogicOfTripleSetInfoSearcher {

	public static String getUsedKey(int keyId, ArrayList<Phrase> phraseList){
		String usedKey = phraseList.get(keyId).getMorphemeList().get(0).getOriginalForm();
		if(usedKey.equals("*")){ usedKey = phraseList.get(keyId).getMorphemeList().get(0).getMorphemeText(); }
		return usedKey;
	}

	public static void addTripleSetInfoList
	(ArrayList<TripleSetInfo> tripleSetInfoList, ArrayList<Integer>targetIdList, ArrayList<Phrase> phraseList, 
				int sentenceId, String sentenceText, int medicineId, int pattern, ArrayList<String> usedKeyList){
		int effectIdTmp = -1;
		int effectRepeatCount = -1;
		for(int targetId : targetIdList){
			Phrase targetPhrase = phraseList.get(targetId);
			int effectId = targetPhrase.getDependencyIndex();
			if(effectId != effectIdTmp){ effectRepeatCount++; }
			effectIdTmp = effectId;
			if(!PhraseChecker.judgeTargetPhrase(targetPhrase.getMorphemeList())){ continue; }// 助詞の条件付け
			TripleSetInfo tripleSetInfo = new TripleSetInfo(sentenceId, sentenceText, medicineId, targetId, effectId);
			tripleSetInfo.setUsedKeyList(usedKeyList);
			tripleSetInfo.setPatternType(pattern);
			tripleSetInfo.setEffectRepeatCount(effectRepeatCount);
			tripleSetInfoList.add(tripleSetInfo);
		}
	}
	
	

}
