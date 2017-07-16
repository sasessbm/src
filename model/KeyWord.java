package model;

import java.util.ArrayList;

public class KeyWord {
	
	private String text;
	private ArrayList<TripleSet> tripleSetList;
	private int sentenceId;
	
	public KeyWord(String text){
		this.text = text;
		tripleSetList = new ArrayList<TripleSet>();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArrayList<TripleSet> getTripleSetList() {
		return tripleSetList;
	}

	public void setTripleSetList(ArrayList<TripleSet> tripleSetList) {
		this.tripleSetList = tripleSetList;
	}
	
	public int getSentenceId() {
		return sentenceId;
	}

	public void setSentenceId(int sentenceId) {
		this.sentenceId = sentenceId;
	}

	public int getTargetNum(TripleSet tripleSet){
		int count = 0;
		String target = tripleSet.getTargetOriginalElement().getText();
		//String effect = tripleSet.getEffectElement().getText();
		//String keyWordText = keyWord.getKeyWordText();
		for(TripleSet set : tripleSetList){
//			if(set.getTargetElement().getText().equals(target) && set.getEffectElement().getText().equals(effect)){ 
//				count++; 
//			}
//			if(set.getTargetElement().getText().equals(target)){ 
//				count++; 
//			}
			if(set.getTargetOriginalElement().getText().equals(target)){ 
				count++; 
			}
		}
		return count;
	}

}
