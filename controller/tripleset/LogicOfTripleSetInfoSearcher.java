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
	(ArrayList<TripleSetInfo> tripleSetInfoList, int sentenceId, String sentenceText, 
			int medicineId, int targetId, int effectId, int pattern, ArrayList<String> usedKeyList){
		TripleSetInfo tripleSetInfo = new TripleSetInfo(sentenceId, sentenceText, medicineId, targetId, effectId);
		tripleSetInfo.setUsedKeyList(usedKeyList);
		tripleSetInfo.setPatternType(pattern);
		tripleSetInfoList.add(tripleSetInfo);
	}

}
