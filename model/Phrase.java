package model;

import java.util.ArrayList;

public class Phrase {
	
	private int id;
	private String phraseText;
	private int dependencyIndex;
	private ArrayList<Morpheme> morphemeList;
	private String phraseType;
	private ArrayList<String> evalWordList;

	//コンストラクタ
	public Phrase(int id, String phraseText, int dependencyIndex, ArrayList<Morpheme> morphemeList) {
		this.id = id;
		this.phraseText = phraseText;
		this.dependencyIndex = dependencyIndex;
		this.morphemeList = morphemeList;
		this.evalWordList = new ArrayList<String>();
	}
	
	public Phrase(){
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getPhraseText() {
		return phraseText;
	}
	
	public void setPhraseText(String phraseText) {
		this.phraseText = phraseText;
	}
	
	public int getDependencyIndex() {
		return dependencyIndex;
	}
	
	public void setDependencyIndex(int dependencyIndex) {
		this.dependencyIndex = dependencyIndex;
	}
	
	public ArrayList<Morpheme> getMorphemeList() {
		return morphemeList;
	}
	
	public void setMorphemeList(ArrayList<Morpheme> morphemeList) {
		this.morphemeList = morphemeList;
	} 
	
	public String getPhraseType() {
		return phraseType;
	}

	public void setPhraseType(String phraseType) {
		this.phraseType = phraseType;
	}
	
	public ArrayList<String> getEvalWordList() {
		return evalWordList;
	}

	public void setEvalWord(String evalWord) {
		this.evalWordList.add(evalWord);
	}

}
