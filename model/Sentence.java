package model;

import java.util.ArrayList;

public class Sentence {
	
	private String text;
	private int recordId;
	private int sentenceId;
	private ArrayList<Phrase> phraseReplaceList;
	private ArrayList<Phrase> phraseRestoreList;
	
	public Sentence
	(String text, int recordId, int sentenceId, ArrayList<Phrase> phraseReplaceList, ArrayList<Phrase> phraseRestoreList){
		this.text = text;
		this.recordId = recordId;
		this.sentenceId = sentenceId;
		this.phraseReplaceList = phraseReplaceList;
		this.phraseRestoreList = phraseRestoreList;
	}
	
	public Sentence
	(String text, int recordId, ArrayList<Phrase> phraseReplaceList, ArrayList<Phrase> phraseRestoreList){
		this.text = text;
		this.recordId = recordId;
		//this.sentenceId = sentenceId;
		this.phraseReplaceList = phraseReplaceList;
		this.phraseRestoreList = phraseRestoreList;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int id) {
		this.recordId = id;
	}
	
	public int getSentenceId() {
		return sentenceId;
	}

	public void setSentenceId(int sentenceId) {
		this.sentenceId = sentenceId;
	}

	public ArrayList<Phrase> getPhraseReplaceList() {
		return phraseReplaceList;
	}

	public void setPhraseReplaceList(ArrayList<Phrase> phraseReplaceList) {
		this.phraseReplaceList = phraseReplaceList;
	}

	public ArrayList<Phrase> getPhraseRestoreList() {
		return phraseRestoreList;
	}

	public void setPhraseRestoreList(ArrayList<Phrase> phraseRestoreList) {
		this.phraseRestoreList = phraseRestoreList;
	}
	
	

}
