package controller.tripleset;

import java.util.ArrayList;
import java.util.TreeMap;

import controller.logic.OverlapDeleter;
import model.KeyWord;
import model.Morpheme;
import model.Phrase;
import model.Sentence;
import model.TripleSetInfo;

public class P4TripleSetInfoSearcher {

	public static final String MEDICINE = "MEDICINE";

	public static ArrayList<TripleSetInfo> getTripleSetInfoList 
	(ArrayList<Sentence> sentenceList, String keyText, ArrayList<KeyWord> keyList, int targetParticleType) {
		ArrayList<TripleSetInfo> tripleSetInfoList = new ArrayList<TripleSetInfo>();

		for(Sentence sentence : sentenceList){
			//if(sentence.getSentenceId() != 717){ continue; } //デバッグ用
			ArrayList<Phrase> phraseList = sentence.getPhraseReplaceList();
			int sentenceId = sentence.getSentenceId();
			String sentenceText = sentence.getText();
			for(Phrase phrase : phraseList){

				//薬剤名文節探索
				if(phrase.getDependencyIndex() == -1){ continue; } //係り先がない
				if(!phrase.getPhraseText().contains(MEDICINE)){ continue; } //薬剤名がない

				//薬剤名文節の助詞の条件付け
				//if(!PhraseChecker.conditionPartOfSpeech(phrase.getMorphemeList())){ continue; } 

				//手がかり語文節探索
				int medicineDIndex = phrase.getDependencyIndex();
				if(!PhraseChecker.judgeKeyPhrase(medicineDIndex, phraseList, keyText)){ continue; }
				
				//薬剤名文節獲得
				int medicineId = phrase.getId();
				ArrayList<Integer> medicineIdList = LogicOfTripleSetInfoSearcher.getMedicineIdList(medicineId, phraseList);
				
				//手がかり語を探索し、リストに追加
				ArrayList<Integer> keyIdList = PhraseChecker.getKeyIdList(medicineDIndex, phraseList, keyList);
				ArrayList<String> usedKeyList = LogicOfTripleSetInfoSearcher.getUsedKeyList(keyIdList, phraseList, keyList, keyText);
				
				//対象文節探索
				int keyId = keyIdList.get(keyIdList.size() - 1); //最後の手がかり語位置から探索
				int keyDIndex = phraseList.get(keyId).getDependencyIndex();
				TreeMap<Integer, Integer> targetEffectIdMap = new TreeMap<Integer, Integer>();
				targetEffectIdMap = PhraseChecker.getTargetEffectIdMap(keyDIndex, keyId, phraseList, targetEffectIdMap);
				if(targetEffectIdMap.size() == 0){ continue; }
				
				//三つ組情報生成
				LogicOfTripleSetInfoSearcher.addTripleSetInfoList
				(tripleSetInfoList, targetEffectIdMap, phraseList, sentenceId, sentenceText, medicineIdList, 4, usedKeyList, targetParticleType);
			}
		}
		return tripleSetInfoList;
	}

}
