package model;

import java.util.ArrayList;

public class TripleSet {
	
	private String medicineName;
	private Element targetElement;
	private Element effectElement;
	private String usedKeyWord;
	private ArrayList<KeyWord> keyWordList;
	private String sentenceText;
	int sentenceId;
	int medicinePhraseIndex;
	private Element targetOriginalElement;
	private int patternType;


//	public TripleSet() {
//		keyWordList = new ArrayList<KeyWord>();
//	}
	
	public TripleSet(String medicineName, Element targetElement,
			Element effectElement, String usedKeyWord, String sentenceText,
			int sentenceId, int medicinePhraseIndex, int patternType) {
		keyWordList = new ArrayList<KeyWord>();
		this.medicineName = medicineName;
		this.targetElement = targetElement;
		this.effectElement = effectElement;
		this.usedKeyWord = usedKeyWord;
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
	
	public String getUsedKeyWord(){
		return usedKeyWord;
	}
	
	public void setUsedKeyWord(String usedKeyWord){
		this.usedKeyWord = usedKeyWord;
	}


	public ArrayList<KeyWord> getKeyWordList() {
		return keyWordList;
	}


	public void setKeyWordList(ArrayList<KeyWord> keyWordList) {
		
//		for(KeyWord keyWord : keyWordList){
//			KeyWord copyKeyWord = new KeyWord(keyWord.getText());
//			copyKeyWord.setSentenceId(keyWord.getSentenceId());
//			this.keyWordList.add(copyKeyWord);
//		}
		this.keyWordList = keyWordList;
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
		for(KeyWord key : keyWordList){
			if(key.getText().equals(keyWordText)){ count++; }
		}
		return count;
	}
	
}
