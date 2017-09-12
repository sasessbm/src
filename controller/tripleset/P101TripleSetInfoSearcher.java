package controller.tripleset;

import java.util.ArrayList;

import model.Morpheme;
import model.Phrase;
import model.Sentence;
import model.TripleSetInfo;

public class P101TripleSetInfoSearcher {

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
				if(!phrase.getPhraseText().contains(MEDICINE)){ continue; } //薬剤名がない

				//薬剤名・手がかり語の形態素位置取得
				ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
				int medicinePlaceIndex = PhraseChecker.getMedicinePlaceIndex(morphemeList);
				int keywordPlaceIndex = PhraseChecker.getKeywordPlaceIndex(morphemeList, keyWordText);

				//手がかり語文節の確定
				if(medicinePlaceIndex == -1 || keywordPlaceIndex == -1){ continue; } //存在するか
				if((keywordPlaceIndex - 1) != medicinePlaceIndex){ continue; } //隣り合っているか
				//if(!PhraseChecker.particleConditioning(morphemeList)){ continue; } //末尾の助詞確認
				
				//効果・対象文節探索
				int keyId = phrase.getId();
				if(keyId > phraseList.size() - 2){ continue; } //薬剤名文節が最後または最後から2番目だった場合
				//int keyDIndex = phrase.getDependencyIndex();
				int targetId = keyId + 1;
				int effectId = phraseList.get(targetId).getDependencyIndex();
				if(effectId == -1){ continue; } //対象要素の係り先があるか
				ArrayList<Morpheme> targetMorphemeList = phraseList.get(targetId).getMorphemeList();
				if(!PhraseChecker.judgeTargetPhrase(targetMorphemeList)){ continue; } // 助詞の条件付け
				
				//三つ組情報生成
				TripleSetInfo tripleSetInfo = new TripleSetInfo(sentenceId, sentenceText, keyId, targetId, effectId);
				tripleSetInfo.setUsedKeyWord(keyWordText);
				tripleSetInfo.setPatternType(101);
				tripleSetInfoList.add(tripleSetInfo);
			}
		}
		return tripleSetInfoList;
	}
}
