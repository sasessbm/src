package controller.logic;

import java.util.ArrayList;
import model.KeyWord;
import model.Sentence;
import model.TripleSet;
import model.TripleSetInfo;

public class OverlapDeleter {

	//重複したKeyWordを削除
	public static ArrayList<KeyWord> deleteOverlappingFromListForKey
	(ArrayList<KeyWord> removeList, ArrayList<KeyWord> compareList){

		for(KeyWord key : compareList){
			String textBase = key.getText();
			for(int i = removeList.size() - 1; i >= 0; i--){
				if(removeList.get(i).getText().equals(textBase)){
					removeList.remove(i);
				}
			}
		}
		return removeList;
	}

	public static ArrayList<TripleSetInfo> deleteOverlappingFromListForTripleSetInfo
	(ArrayList<TripleSetInfo> removeList, ArrayList<TripleSetInfo> compareList){

		for(TripleSetInfo tripleSetInfo : compareList){
			int sentenceId = tripleSetInfo.getSentenceId();
			int medicinePhraseId = tripleSetInfo.getMedicinePhraseId();
			int targetPhraseId = tripleSetInfo.getTargetPhraseId();
			int effectPhraseId = tripleSetInfo.getEffectPhraseId();
			for(int i = removeList.size() - 1; i >= 0; i--){
				TripleSetInfo tSI = removeList.get(i);
				if(tSI.getSentenceId() == sentenceId && tSI.getMedicinePhraseId() == medicinePhraseId && 
						tSI.getTargetPhraseId() == targetPhraseId && tSI.getEffectPhraseId() == effectPhraseId){
					removeList.remove(i);
				}
			}
		}
		return removeList;
	}

	public static ArrayList<TripleSet> deleteOverlappingFromListForTripleSet
	(ArrayList<TripleSet> removeList, ArrayList<TripleSet> compareList){

		for(TripleSet tripleSet : compareList){
			int sentenceId = tripleSet.getSentenceId();
			int medicinePhraseId = tripleSet.getMedicinePhraseIndex();
			int targetPhraseId = tripleSet.getTargetElement().getPhraseIndex();
			int effectPhraseId = tripleSet.getEffectElement().getPhraseIndex();
			String medicineName = tripleSet.getMedicineName();
			for(int i = removeList.size() - 1; i >= 0; i--){
				TripleSet ts = removeList.get(i);
				if(ts.getSentenceId() == sentenceId && ts.getMedicinePhraseIndex() == medicinePhraseId && 
						ts.getTargetElement().getPhraseIndex() == targetPhraseId && 
						ts.getEffectElement().getPhraseIndex() == effectPhraseId && ts.getMedicineName().equals(medicineName)){
					removeList.remove(i);
				}
			}
		}
		return removeList;
	}

	public static ArrayList<KeyWord> deleteOverlappingFromListForStringAndKey
	(ArrayList<KeyWord> removeList, ArrayList<String> compareList){

		for(String word : compareList){
			//String textBase = key.getKeyWordText();
			for(int i = removeList.size() - 1; i >= 0; i--){
				if(removeList.get(i).getText().equals(word)){
					removeList.remove(i);
				}
			}
		}
		return removeList;
	}

	//重複した手がかり語削除
	public static ArrayList<KeyWord> deleteSameKeyWord(ArrayList<KeyWord> keyWordList){

		for(int i = 0; i < keyWordList.size() - 1; i++){
			KeyWord keyWordBase = keyWordList.get(i);
			String keyWordTextBase = keyWordBase.getText();
			for(int j = i+1; j < keyWordList.size();){
				KeyWord keyWord = keyWordList.get(j);
				String keyWordText = keyWord.getText();
				if(keyWordTextBase.equals(keyWordText)){
					keyWordList.remove(keyWord);
					j = i+1;
					continue;
				}
				j++;
			}
		}
		return keyWordList;
	}
	//重複した対象要素を削除
	public static ArrayList<TripleSet> deleteSameTarget(ArrayList<TripleSet> tripleSetList){

		for(int i = 0; i < tripleSetList.size() - 1; i++){
			TripleSet tripleSetBase = tripleSetList.get(i);
			String targetBase = tripleSetBase.getTargetOriginalElement().getText();
			for(int j = i+1; j < tripleSetList.size();){
				TripleSet tripleSet = tripleSetList.get(j);
				String target = tripleSet.getTargetOriginalElement().getText();
				if(targetBase.equals(target)){
					tripleSetList.remove(tripleSet);
					j = i+1;
					continue;
				}
				j++;
			}
		}
		return tripleSetList;
	}
	
	public static ArrayList<TripleSet> deleteSameSetUsedTargetPosition(ArrayList<TripleSet> tripleSetList){
		
		for(int i = 0; i < tripleSetList.size() - 1; i++){
			TripleSet tripleSetBase = tripleSetList.get(i);
			int sentenceIdBase = tripleSetBase.getSentenceId();
			int targetPhraseIdBase = tripleSetBase.getTargetElement().getPhraseIndex();
			String targetBase = tripleSetBase.getTargetOriginalElement().getText();
			
			for(int j = i+1; j < tripleSetList.size();){
				TripleSet tripleSet = tripleSetList.get(j);
				int sentenceId = tripleSet.getSentenceId();
				int targetPhraseId = tripleSet.getTargetElement().getPhraseIndex();
				String target = tripleSet.getTargetOriginalElement().getText();
				if(sentenceIdBase == sentenceId && targetPhraseIdBase == targetPhraseId && targetBase.equals(target)){
					tripleSetList.remove(tripleSet);
					j = i+1;
					continue;
				}
				j++;
			}
		}
		return tripleSetList;
	}

	//重複した三つ組を削除
	public static ArrayList<TripleSet> deleteSameSet(ArrayList<TripleSet> tripleSetList){

		for(int i = 0; i < tripleSetList.size() - 1; i++){
			TripleSet tripleSetBase = tripleSetList.get(i);
			int sentenceIdBase = tripleSetBase.getSentenceId();
			int medicinePhraseIdBase = tripleSetBase.getMedicinePhraseIndex();
			int targetPhraseIdBase = tripleSetBase.getTargetElement().getPhraseIndex();
			int effectPhraseIdBase = tripleSetBase.getEffectElement().getPhraseIndex();
			String medicineNameBase = tripleSetBase.getMedicineName();
			//String targetBase = tripleSetBase.getTargetElement().getText();
			String targetBase = tripleSetBase.getTargetOriginalElement().getText();
			String effectBase = tripleSetBase.getEffectElement().getText();

			for(int j = i+1; j < tripleSetList.size();){
				TripleSet tripleSet = tripleSetList.get(j);
				int sentenceId = tripleSet.getSentenceId();
				int medicinePhraseId = tripleSet.getMedicinePhraseIndex();
				int targetPhraseId = tripleSet.getTargetElement().getPhraseIndex();
				int effectPhraseId = tripleSet.getEffectElement().getPhraseIndex();
				String medicineName = tripleSet.getMedicineName();
				//String target = tripleSet.getTargetElement().getText();
				String target = tripleSet.getTargetOriginalElement().getText();
				String effect = tripleSet.getEffectElement().getText();

				if(sentenceIdBase == sentenceId && medicinePhraseIdBase == medicinePhraseId 
						&& targetPhraseIdBase == targetPhraseId && effectPhraseIdBase == effectPhraseId
						&& medicineNameBase.equals(medicineName) && targetBase.equals(target) && effectBase.equals(effect)){
					tripleSetList.remove(tripleSet);
					j = i+1;
					continue;
				}
				j++;
			}
		}
		return tripleSetList;
	}

	//重複した三つ組情報を削除
	public static ArrayList<TripleSetInfo> deleteSameInfo(ArrayList<TripleSetInfo> tripleSetInfoList){

		for(int i = 0; i < tripleSetInfoList.size() - 1; i++){
			TripleSetInfo tripleSetInfoBase = tripleSetInfoList.get(i);
			int sentenceIdBase = tripleSetInfoBase.getSentenceId();
			int medicinePhraseIdBase = tripleSetInfoBase.getMedicinePhraseId();
			int targetPhraseIdBase = tripleSetInfoBase.getTargetPhraseId();
			int effectPhraseIdBase = tripleSetInfoBase.getEffectPhraseId();

			for(int j = i+1; j < tripleSetInfoList.size();){
				TripleSetInfo tripleSetInfo = tripleSetInfoList.get(j);
				int sentenceId = tripleSetInfo.getSentenceId();
				int medicinePhraseId = tripleSetInfo.getMedicinePhraseId();
				int targetPhraseId = tripleSetInfo.getTargetPhraseId();
				int effectPhraseId = tripleSetInfo.getEffectPhraseId();

				if(sentenceIdBase == sentenceId && medicinePhraseIdBase == medicinePhraseId 
						&& targetPhraseIdBase == targetPhraseId && effectPhraseIdBase == effectPhraseId){
					tripleSetInfoList.remove(tripleSetInfo);
					j = i+1;
					continue;
				}
				j++;
			}
		}
		return tripleSetInfoList;
	}

	//重複したSentenceを削除
	public static ArrayList<Sentence> deleteSameSentence(ArrayList<Sentence> sentenceList){

		ArrayList<Sentence> sentenceListBase = sentenceList;
		for(int i = 0 ; i < sentenceListBase.size() ; i++){
			int sameCount = 0;
			String sentenceTextBase = sentenceListBase.get(i).getText();
			for(Sentence sentence : sentenceList){
				String sentenceText= sentence.getText();
				if(sentenceTextBase.equals(sentenceText)){ sameCount++; }
			}
			if(sameCount>=2){ sentenceList.remove(sentenceList.get(i)); }
		}
		return sentenceList;
	}

}
