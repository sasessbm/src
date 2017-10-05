package controller.tripleset;

import java.util.ArrayList;
import java.util.Collections;

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
	
//	public static ArrayList<Integer> getTargetIdList(ArrayList<Integer> keyIdList, ArrayList<Phrase> phraseList){
//		int keyId = keyIdList.get(keyIdList.size() - 1); //最後の手がかり語位置から探索
//		int keyDIndex = phraseList.get(keyId).getDependencyIndex();
//		ArrayList<Integer> targetIdList = PhraseChecker.getTargetIdList(keyDIndex, keyId, phraseList);
//		return targetIdList;
//	}

}
