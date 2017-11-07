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
	
	public void addTripleSetInList(TripleSet tripleSet){
		tripleSetList.add(tripleSet);
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
		for(TripleSet set : tripleSetList){

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
