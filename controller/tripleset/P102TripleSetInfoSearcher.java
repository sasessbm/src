//package controller.tripleset;
//
//import java.util.ArrayList;
//
//import model.Morpheme;
//import model.Phrase;
//import model.Sentence;
//import model.TripleSetInfo;
//
//public class P102TripleSetInfoSearcher {
//
//	public static final String MEDICINE = "MEDICINE";
//	private static  ArrayList<Phrase> phraseList;
//	private static String keyWordText;
//	private static ArrayList<TripleSetInfo> tripleSetInfoList;
//	private static int medicinePhraseId;
//	private static int sentenceId;
//	private static String sentenceText;
//
//	public static ArrayList<TripleSetInfo> getTripleSetInfoList (ArrayList<Sentence> sentenceList, String keyWordText) {
//
//		tripleSetInfoList = new ArrayList<TripleSetInfo>();
//		P102TripleSetInfoSearcher.keyWordText = keyWordText;
//		for(Sentence sentence : sentenceList){
//			P102TripleSetInfoSearcher.phraseList = sentence.getPhraseReplaceList();
//			sentenceId = sentence.getSentenceId();
//			sentenceText = sentence.getText();
//
//			for(Phrase phrase : phraseList){
//				String phraseText = phrase.getPhraseText();
//				if(!phraseText.contains(MEDICINE)){ continue; }
//				medicinePhraseId = phrase.getId();
//				ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
//				int medicinePlaceIndex = -1;
//
//				// 薬剤名の形態素位置取得
//				for(int i = morphemeList.size() - 1; i >= 0; i--){
//					String morphemeText = morphemeList.get(i).getMorphemeText();
//					if(!morphemeText.contains(MEDICINE)){ continue; }
//					medicinePlaceIndex = i;
//					break;
//				}
//				//薬剤名のすぐ後ろが手がかり語でない場合
//				if(getKeywordPlaceIndex(phrase.getMorphemeList()) - 1 != medicinePlaceIndex){ continue; }
//				//薬剤名文節が最後または最後から2番目だった場合
//				if(medicinePhraseId > phraseList.size()-3){ continue; }
//				judgeTargetPhrase(medicinePhraseId);
//			}
//		}
//		return tripleSetInfoList;
//	}
//
//	//「対象」要素存在文節判定
//	public static void judgeTargetPhrase(int medicinePhraseId){
//		Phrase phrase = phraseList.get(medicinePhraseId + 1);
//		String lastMorphemeText = phrase.getMorphemeList().
//				get(phrase.getMorphemeList().size()-1).getMorphemeText();
//		//if(!Filter.isSpecificParticle(lastMorphemeText)){ return; } //助詞の条件付け
//		judgeEffectPhrase(phrase.getId());
//	}
//
//	//「効果」要素存在文節判定
//	public static void judgeEffectPhrase(int targetPhraseId){
//		TripleSetInfo tripleSetInfo 
//					= new TripleSetInfo(sentenceId, sentenceText, medicinePhraseId, targetPhraseId, targetPhraseId + 1);
//		tripleSetInfo.setUsedKeyWord(keyWordText);
//		tripleSetInfo.setPatternType(102);
//		tripleSetInfoList.add(tripleSetInfo);
//	}
//
//	// 手がかり語の位置を探索
//	public static int getKeywordPlaceIndex(ArrayList<Morpheme> morphemeList){
//		int keywordPlaceIndex = -1;
//		int morphemeIndex = -1;
//		for(Morpheme morpheme : morphemeList){
//			morphemeIndex ++;
//			String originalForm = morpheme.getOriginalForm();
//			if(originalForm.equals(keyWordText)){
//				//System.out.println(keyword);
//				keywordPlaceIndex = morphemeIndex;
//			}
//		}
//		return keywordPlaceIndex;
//	}
//
//
//
//
//}
