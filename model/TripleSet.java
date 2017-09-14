package model;

import java.util.ArrayList;

public class TripleSet {
	
	private String medicineName;
	private Element targetElement;
	private Element effectElement;
	//private String usedKeyWord;
	private ArrayList<KeyWord> extractKeyList;
	private ArrayList<String> usedKeyList;
	private String sentenceText;
	int sentenceId;
	int medicinePhraseIndex;
	private Element targetOriginalElement;
	private int patternType;


//	public TripleSet() {
//		keyWordList = new ArrayList<KeyWord>();
//	}
	
	public TripleSet(String medicineName, Element targetElement,
			Element effectElement, ArrayList<String> usedKeyList, String sentenceText,
			int sentenceId, int medicinePhraseIndex, int patternType) {
		extractKeyList = new ArrayList<KeyWord>();
		this.medicineName = medicineName;
		this.targetElement = targetElement;
		this.effectElement = effectElement;
		//this.usedKeyWord = usedKeyWord;
		this.usedKeyList = usedKeyList;
		this.sentenceText = sentenceText;
		this.sentenceId = sentenceId;
		this.medicinePhraseIndex = medicinePhraseIndex;
		this.patternType = patternType;
	}
	

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public Element getTargetElement() {
		return targetElement;
	}

	public void setTargetElement(Element targetElement) {
		this.targetElement = targetElement;
	}

	public Element getEffectElement() {
		return effectElement;
	}

	public void setEffectElement(Element effectElement) {
		this.effectElement = effectElement;
	}

	public ArrayList<KeyWord> getExtractKeyList() {
		return extractKeyList;
	}
	
	public void setExtractKeyList(ArrayList<KeyWord> extractKeyList) {
		this.extractKeyList = extractKeyList;
	}

	public ArrayList<String> getUsedKeyList() {
		return usedKeyList;
	}

	public void setUsedKeyList(ArrayList<String> usedKeyList) {
		this.usedKeyList = usedKeyList;
	}
	
	public String getSentenceText() {
		return sentenceText;
	}

	public void setSentenceText(String sentenceText) {
		this.sentenceText = sentenceText;
	}

	public int getSentenceId() {
		return sentenceId;
	}

	public void setSentenceId(int sentenceId) {
		this.sentenceId = sentenceId;
	}
		
	public int getMedicinePhraseIndex() {
		return medicinePhraseIndex;
	}

	public void setMedicinePhraseIndex(int medicinePhraseIndex) {
		this.medicinePhraseIndex = medicinePhraseIndex;
	}

	public Element getTargetOriginalElement() {
		return targetOriginalElement;
	}

	public void setTargetOriginalElement(Element targetOriginalElement) {
		this.targetOriginalElement = targetOriginalElement;
	}
	
	public int getPatternType() {
		return patternType;
	}

	public void setPatternType(int patternType) {
		this.patternType = patternType;
	}

	public int getKeyWordNum(String keyWordText){
		int count = 0;
		//String keyWordText = keyWord.getKeyWordText();
		for(KeyWord key : extractKeyList){
			if(key.getText().equals(keyWordText)){ count++; }
		}
		return count;
	}
	
}
