package controller.keyword;

import java.util.ArrayList;

import model.*;

public class Transformation {

//	public static ArrayList<TripleSet> stringToTripleSet(ArrayList<String> seedTextList){
//
//		ArrayList<TripleSet> seedList = new ArrayList<TripleSet>();
//
//		for(String text : seedTextList){
//			String[] elementArray = text.split(",");
//			TripleSet tripleSet = new TripleSet();
//			Element targetElement = new Element();
//			Element effectElement = new Element();
//			targetElement.setText(elementArray[0]);
//			effectElement.setText(elementArray[1]);
//			tripleSet.setTargetElement(targetElement);
//			tripleSet.setEffectElement(effectElement);
//			seedList.add(tripleSet);
//		}
//
//		return seedList;
//	}
	
	public static ArrayList<KeyWord> stringToKeyWord(ArrayList<String> textList){
		
		ArrayList<KeyWord> keyWordList = new ArrayList<KeyWord>();
		
		for(String text : textList){
			keyWordList.add(new KeyWord(text));
		}
		
		return keyWordList;
	}

}
