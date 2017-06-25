package model;

import java.util.ArrayList;

public class Element {
	
	private String text;
	private ArrayList<Morpheme> morphemeList;
	private int phraseIndex;
	
	public Element() {
		
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public ArrayList<Morpheme> getMorphemeList() {
		return morphemeList;
	}
	
	public void setMorphemeList(ArrayList<Morpheme> morphemeList) {
		this.morphemeList = morphemeList;
	}

	public int getPhraseIndex() {
		return phraseIndex;
	}

	public void setPhraseIndex(int phraseIndex) {
		this.phraseIndex = phraseIndex;
	}
	
	

}
