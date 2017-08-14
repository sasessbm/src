package controller.tripleset;

import java.util.ArrayList;

import model.*;

public class P3P4TripleSetInfoSearcher {

	public static final String MEDICINE = "MEDICINE";
	private static  ArrayList<Phrase> phraseList;
	private static String keyWordText;
	private static ArrayList<TripleSetInfo> tripleSetInfoList;
	private static int medicinePhraseId;
	private static int sentenceId;
	private static String sentenceText;
	private static int patternType;

	public static ArrayList<TripleSetInfo> getTripleSetInfoList (ArrayList<Sentence> sentenceList, String keyWordText) {
		tripleSetInfoList = new ArrayList<TripleSetInfo>();
		P3P4TripleSetInfoSearcher.keyWordText = keyWordText;

		for(Sentence sentence : sentenceList){
			//if(sentence.getSentenceId() != 252){ continue; } //デバッグ用
			P3P4TripleSetInfoSearcher.phraseList = sentence.getPhraseReplaceList();
			sentenceId = sentence.getSentenceId();
			sentenceText = sentence.getText();

			for(Phrase phrase : phraseList){
				String phraseText = phrase.getPhraseText();
				int dIndex = phrase.getDependencyIndex();
				if(!phraseText.contains(MEDICINE)){ continue; }

				// 手がかり語の形態素位置取得
				int keywordPlaceIndex = getKeywordPlaceIndex(phrase.getMorphemeList());
				ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
				int medicinePlaceIndex = -1;

				// 薬剤名の形態素位置取得
				for(int i = morphemeList.size() - 1; i >= 0; i--){
					String morphemeText = morphemeList.get(i).getMorphemeText();
					if(!morphemeText.contains(MEDICINE)){ continue; }
					medicinePlaceIndex = i;
					break;
				}
				if(medicinePlaceIndex == -1){ continue; } // 薬剤名がない
				medicinePhraseId = phrase.getId();

				// 対象薬剤名のすぐ後ろに手がかり語があるか探索
				// 同じ文節内にある（P3）
				if(keywordPlaceIndex > 0){
					if((keywordPlaceIndex - 1) != medicinePlaceIndex){ continue; } // 隣り合っていなければ不適切
					patternType = 3;
					// 自身のIDを渡す
					judgeKeywordPhrase(phrase.getId());
				}

				// 同じ文節内にはない (が、「対象薬剤名＋助詞」になっている)（P4）
				else{
					if(dIndex == -1){ continue; } // 係り先がそもそも無い
					//係り先の手がかり語の位置を取得
					keywordPlaceIndex = getKeywordPlaceIndex(phraseList.get(dIndex).getMorphemeList());
					if(keywordPlaceIndex != 0){ continue; } // 最初の位置か
					//if(morphemeList.size() <= medicinePlaceIndex + 1){ continue; }
					if(!morphemeList.get(morphemeList.size()-1).getPartOfSpeech().equals("助詞")){ continue; }
					patternType = 4;
					//係り先番号を渡す
					judgeKeywordPhrase(dIndex);
				}
			}
		}
		return tripleSetInfoList;
	}

	// 手がかり語の位置を探索
	public static int getKeywordPlaceIndex(ArrayList<Morpheme> morphemeList){
		int keywordPlaceIndex = -1;
		int morphemeIndex = -1;
		for(Morpheme morpheme : morphemeList){
			morphemeIndex ++;
			String originalForm = morpheme.getOriginalForm();
			if(originalForm.equals(keyWordText)){
				keywordPlaceIndex = morphemeIndex;
			}
		}
		return keywordPlaceIndex;
	}

	//「手がかり語」要素存在文節判定
	public static void judgeKeywordPhrase(int dependencyIndex){
		for(Phrase phrase : phraseList){
			if(phrase.getId() != dependencyIndex){ continue; }
			if(getKeywordPlaceIndex(phrase.getMorphemeList()) != -1){
				//一番最後の文節が、格助詞または接続助詞か確認
				String partOfSpeechDetails = phrase.getMorphemeList()
						.get(phrase.getMorphemeList().size()-1).getPartOfSpeechDetails();
				//if(!(partOfSpeechDetails.contains("格助詞") || partOfSpeechDetails.contains("接続助詞"))){ continue; }
				//if(!(partOfSpeechDetails.contains("格助詞") || partOfSpeechDetails.contains("接続助詞") || partOfSpeechDetails.contains("読点"))){ continue; }
				//if(!(partOfSpeechDetails.contains("助詞") || partOfSpeechDetails.contains("読点"))){ continue; }
				//if(!(partOfSpeechDetails.contains("助詞"))){ continue; }
				judgeEffectPhrase(phrase.getDependencyIndex(), dependencyIndex);
				break;
			}
		}
	}

	//「効果」要素存在文節判定
	public static void judgeEffectPhrase(int effectId, int keyId){
		for(Phrase phrase : phraseList){
			int phraseId = phrase.getId();
			if(phraseId == effectId){
				judgeTargetPhrase(effectId, keyId);
				break;
			}
		}
	}

	//「対象」要素存在文節判定
	public static void judgeTargetPhrase(int effectId, int keyId){
		// 逆から探索
		for(int i=1; i<=phraseList.size(); i++){
			int currentIndex = phraseList.size()-i;
			Phrase phrase = phraseList.get(currentIndex);
			int phraseDependencyIndex = phrase.getDependencyIndex();
			int phraseId = phrase.getId();
			if(phraseId == keyId){ break; } //「手がかり語」文節まで到達した時

			//対象文節候補発見
			if(phraseDependencyIndex == effectId){
				String lastMorphemeText = phrase.getMorphemeList()
						.get(phrase.getMorphemeList().size()-1).getMorphemeText();
				//if(!Filter.isGAorHAorWO(lastMorphemeText)){ continue; } // 助詞の条件付け
				//if(!Filter.isGAorHAorWOorNIorMOorNIMO(lastMorphemeText)){ continue; } // 助詞の条件付け
				TripleSetInfo tripleSetInfo = new TripleSetInfo(sentenceId, sentenceText, medicinePhraseId, phraseId, effectId);
				tripleSetInfo.setUsedKeyWord(keyWordText);
				tripleSetInfo.setPatternType(patternType);
				tripleSetInfoList.add(tripleSetInfo);
			}
		}
	}

}
