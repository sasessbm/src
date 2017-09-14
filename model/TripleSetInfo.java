package model;

import java.util.ArrayList;

public class TripleSetInfo {
	
	private int sentenceId;
	private String sentenceText;
	private int medicinePhraseId;
	private int targetPhraseId;
	private int effectPhraseId;
	//private String usedKeyWord;
	private int patternType;
	private ArrayList<String> usedKeyList;
	
	public TripleSetInfo(int sentenceId, String sentenceText, int medicinePhraseId,
			int targetPhraseId, int effectPhraseId) {
		this.sentenceId = sentenceId;
		this.sentenceText = sentenceText;
		this.medicinePhraseId = medicinePhraseId;
		this.targetPhraseId = targetPhraseId;
		this.effectPhraseId = effectPhraseId;
	}

	public int getSentenceId() {
		return sentenceId;
	}

	public void setSentenceId(int sentenceId) {
		this.sentenceId = sentenceId;
	}

	public String getSentenceText() {
		return sentenceText;
	}

	public void setSentenceText(String sentenceText) {
		this.sentenceText = sentenceText;
	}

	public int getMedicinePhraseId() {
		return medicinePhraseId;
	}
	
	public void setMedicinePhraseId(int medicinePhraseId) {
		this.medicinePhraseId = medicinePhraseId;
	}
	
	public int getTargetPhraseId() {
		return targetPhraseId;
	}
	
	public void setTargetPhraseId(int targetPhraseId) {
		this.targetPhraseId = targetPhraseId;
	}
	
	public int getEffectPhraseId() {
		return effectPhraseId;
	}
	
	public void setEffectPhraseId(int effectPhraseId) {
		this.effectPhraseId = effectPhraseId;
	}
	
	public ArrayList<String> getUsedKeyList(){
		return usedKeyList;
	}
	
	public void setUsedKeyList(ArrayList<String> usedKeyList){
		this.usedKeyList = usedKeyList;
	}
	
//	public String getUsedKeyWord(){
//		return usedKeyWord;
//	}
//	
//	public void setUsedKeyWord(String usedKeyWord){
//		this.usedKeyWord = usedKeyWord;
//	}

	public int getPatternType() {
		return patternType;
	}

	public void setPatternType(int patternType) {
		this.patternType = patternType;
	}
	
	

}
