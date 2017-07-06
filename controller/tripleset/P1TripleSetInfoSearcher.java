package controller.tripleset;

import java.util.ArrayList;

import model.Morpheme;
import model.Phrase;
import model.Sentence;
import model.TripleSetInfo;

public class P1TripleSetInfoSearcher {

	public static final String MEDICINE = "MEDICINE";
	private static  ArrayList<Phrase> phraseList;
	private static String keyWordText;
	private static ArrayList<TripleSetInfo> tripleSetInfoList;
	private static int medicinePhraseId;
	private static int sentenceId;
	private static String sentenceText;

	public static ArrayList<TripleSetInfo> getTripleSetInfoList (ArrayList<Sentence> sentenceList, String keyWordText) {

		tripleSetInfoList = new ArrayList<TripleSetInfo>();
		P1TripleSetInfoSearcher.keyWordText = keyWordText;
		for(Sentence sentence : sentenceList){
			P1TripleSetInfoSearcher.phraseList = sentence.getPhraseReplaceList();
			sentenceId = sentence.getSentenceId();
			sentenceText = sentence.getText();

			for(Phrase phrase : phraseList){
				String phraseText = phrase.getPhraseText();
				int dIndex = phrase.getDependencyIndex();
				if(!phraseText.contains(MEDICINE)){ continue; }
				medicinePhraseId = phrase.getId();
				ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
				int medicinePlaceIndex = -1;

				// 薬剤名の形態素位置取得
				for(int i = morphemeList.size() - 1; i >= 0; i--){
					String morphemeText = morphemeList.get(i).getMorphemeText();
					if(!morphemeText.contains(MEDICINE)){ continue; }
					medicinePlaceIndex = i;
					break;
				}
				//薬剤名のすぐ後ろが手がかり語でない場合
				if(getKeywordPlaceIndex(phrase.getMorphemeList()) - 1 != medicinePlaceIndex){ continue; }
				judgeTargetPhrase(dIndex);
			}

		}

		return tripleSetInfoList;
	}

	//「対象」要素存在文節判定
	public static void judgeTargetPhrase(int medicineDIndex){
		for(Phrase phrase : phraseList){
			int phraseId = phrase.getId();
			if(phraseId != medicineDIndex){ continue; }
			String lastMorphemeText = phrase.getMorphemeList().
					get(phrase.getMorphemeList().size()-1).getMorphemeText();
			if(!(lastMorphemeText.equals("が") || lastMorphemeText.equals("は") 
					|| lastMorphemeText.equals("を")|| lastMorphemeText.equals("も"))){ continue; }
			judgeEffectPhrase(phraseId, phrase.getDependencyIndex());
		}
	}

	//「効果」要素存在文節判定
	public static void judgeEffectPhrase(int targetPhraseId, int targetDIndex){
		for(Phrase phrase : phraseList){
			int phraseId = phrase.getId();
			if(phraseId != targetDIndex){ continue; }

			TripleSetInfo tripleSetInfo = new TripleSetInfo(sentenceId, sentenceText, medicinePhraseId, targetPhraseId, phraseId);
			tripleSetInfo.setUsedKeyWord(keyWordText);
			tripleSetInfo.setPatternType(1);
			tripleSetInfoList.add(tripleSetInfo);
		}
	}

	// 手がかり語の位置を探索
	public static int getKeywordPlaceIndex(ArrayList<Morpheme> morphemeList){
		int keywordPlaceIndex = -1;
		int morphemeIndex = -1;
		for(Morpheme morpheme : morphemeList){
			morphemeIndex ++;
			String originalForm = morpheme.getOriginalForm();
			if(originalForm.equals(keyWordText)){
				//System.out.println(keyword);
				keywordPlaceIndex = morphemeIndex;
			}
		}
		return keywordPlaceIndex;
	}
}
