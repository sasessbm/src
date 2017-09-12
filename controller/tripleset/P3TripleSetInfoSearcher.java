package controller.tripleset;

import java.util.ArrayList;

import model.Morpheme;
import model.Phrase;
import model.Sentence;
import model.TripleSetInfo;

public class P3TripleSetInfoSearcher {

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

				//薬剤名・手がかり語の形態素位置取得
				ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
				int medicinePlaceIndex = PhraseChecker.getMedicinePlaceIndex(morphemeList);
				int keywordPlaceIndex = PhraseChecker.getKeywordPlaceIndex(morphemeList, keyWordText);

				//手がかり語文節の確定
				if(medicinePlaceIndex == -1 || keywordPlaceIndex == -1){ continue; } //存在するか
				if((keywordPlaceIndex - 1) != medicinePlaceIndex){ continue; } //隣り合っているか
				//if(!PhraseChecker.conditionPartOfSpeechDetails(morphemeList)){ continue; } //末尾の助詞確認

				//効果・対象文節探索
				int keyId = phrase.getId();
				int keyDIndex = phrase.getDependencyIndex();
				ArrayList<Integer> targetIdList = PhraseChecker.getTargetIdList(keyDIndex, keyId, phraseList);
				if(targetIdList.size() == 0){ continue; }
				
				//三つ組情報生成
				for(int targetId : targetIdList){
					TripleSetInfo tripleSetInfo = new TripleSetInfo(sentenceId, sentenceText, keyId, targetId, keyDIndex);
					tripleSetInfo.setUsedKeyWord(keyWordText);
					tripleSetInfo.setPatternType(3);
					tripleSetInfoList.add(tripleSetInfo);
				}
			}
		}
		return tripleSetInfoList;
	}


}
