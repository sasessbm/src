package controller.tripleset;

import java.util.ArrayList;

import model.Morpheme;
import model.Phrase;
import model.Sentence;
import model.TripleSetInfo;

public class P10TripleSetInfoSearcher {

	public static final String MEDICINE = "MEDICINE";

	public static ArrayList<TripleSetInfo> getTripleSetInfoList (ArrayList<Sentence> sentenceList, String keyWordText) {
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
				//ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
				//if(!PhraseChecker.conditionPartOfSpeech(morphemeList)){ continue; } 

				//手がかり語文節探索
				int medicineDIndex = phrase.getDependencyIndex();
				if(!PhraseChecker.judgeKeyPhrase(medicineDIndex, phraseList, keyWordText)){ continue; }

				//効果・対象文節探索
				int keyId = medicineDIndex;
				int keyDIndex = phraseList.get(keyId).getDependencyIndex();
				ArrayList<Integer> effectIdList = PhraseChecker.getEffectIdList(keyDIndex, keyId, phraseList);
				for(int effectId : effectIdList){
					ArrayList<Integer> targetIdList = PhraseChecker.getTargetIdList(effectId, keyId, phraseList);
					if(targetIdList.size() == 0){ continue; }
					//三つ組情報生成
					for(int targetId : targetIdList){
						TripleSetInfo tripleSetInfo = new TripleSetInfo(sentenceId, sentenceText, phrase.getId(), targetId, effectId);
						tripleSetInfo.setUsedKeyWord(keyWordText);
						tripleSetInfo.setPatternType(10);
						tripleSetInfoList.add(tripleSetInfo);
					}
				}
			}
		}
		return tripleSetInfoList;
	}

}
