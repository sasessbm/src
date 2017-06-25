package model;

import java.util.ArrayList;

public class KeyWord {
	
	private String keyWordText;
	private ArrayList<TripleSet> tripleSetList;
	
	public KeyWord(String keyWordText){
		this.keyWordText = keyWordText;
		tripleSetList = new ArrayList<TripleSet>();
	}

	public String getKeyWordText() {
		return keyWordText;
	}

	public void setKeyWordText(String keyWordText) {
		this.keyWordText = keyWordText;
	}

	public ArrayList<TripleSet> getTripleSetList() {
		return tripleSetList;
	}

	public void setTripleSetList(ArrayList<TripleSet> tripleSetList) {
		this.tripleSetList = tripleSetList;
	}
	
	public int getTripleSetNum(TripleSet tripleSet){
		int count = 0;
		String target = tripleSet.getTargetElement().getText();
		//String effect = tripleSet.getEffectElement().getText();
		//String keyWordText = keyWord.getKeyWordText();
		for(TripleSet set : tripleSetList){
//			if(set.getTargetElement().getText().equals(target) && set.getEffectElement().getText().equals(effect)){ 
//				count++; 
//			}
			if(set.getTargetElement().getText().equals(target)){ 
				count++; 
			}
		}
		return count;
	}

}
