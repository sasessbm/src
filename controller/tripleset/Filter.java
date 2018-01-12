package controller.tripleset;

import java.util.ArrayList;

import controller.logic.Logic;
import model.*;

public class Filter {

	//対象要素に薬剤名が入っていたら不適切
	public static void filterMedicineName(ArrayList<TripleSet> tripleSetList){
		for(int i = tripleSetList.size() - 1; i >= 0; i--){
			TripleSet tripleSet = tripleSetList.get(i);
			String targetText = tripleSet.getTargetElement().getText();
			//String targetText = tripleSet.getTargetOriginalElement().getText();
			if(Logic.containsMedicine(targetText)){
				tripleSetList.remove(i);
			}
		}
	}

	//110番辞書フィルタ
	public static void filter(ArrayList<TripleSet> tripleSetList, ArrayList<String> targetFilteringList){
		for(int i = tripleSetList.size() - 1; i >= 0; i--){
			TripleSet tripleSet = tripleSetList.get(i);
			ArrayList<Morpheme> morphemeList = tripleSet.getTargetOriginalElement().getMorphemeList(); //三つ組生成規則前の対象要素
			//ArrayList<Morpheme> morphemeList = tripleSet.getTargetElement().getMorphemeList(); //三つ組生成規則後の対象要素
			String dicWord = getDicWord(morphemeList, targetFilteringList);
			
			if(dicWord == null){
				tripleSetList.remove(i);
			}else{
				//System.out.println(dicWord);
				tripleSet.setFilterWord(dicWord);
			}
		}
	}
	
	//110番辞書フィルタ
	public static String getDicWord(ArrayList<Morpheme> morphemeList, ArrayList<String> targetFilteringList){
		String targetWord = "";
		String dicWord = null;
		boolean nounFlag = false;
		for(Morpheme morpheme : morphemeList){
			String morphemeText = morpheme.getMorphemeText();
			String pos = morpheme.getPartOfSpeech();
			if(pos.equals("接頭詞")){
				if(nounFlag){
					if(targetWord != ""){
						dicWord = getFilterWord(targetWord, targetFilteringList);
						if(dicWord != null){ break; }
						targetWord = ""; //辞書に含まれていなかったとき，初期化して次の単語を作る
					}
					nounFlag = false;
				}else{ targetWord += morphemeText; }
			}
			else if(pos.equals("名詞")){
				targetWord += morphemeText;
				nounFlag = true;
			}
			else{
				if(!nounFlag){ continue; }
				if(targetWord != ""){
					dicWord = getFilterWord(targetWord, targetFilteringList);
					if(dicWord != null){ break; }
					targetWord = ""; //辞書に含まれていなかったとき，初期化して次の単語を作る
				}
				nounFlag = false;
			}
		}
		if(targetWord.length() != 0){
			dicWord = getFilterWord(targetWord, targetFilteringList);
		}
		return dicWord;
	}

	public static String getFilterWord(String word, ArrayList<String> targetFilteringList){
		String filterWord = null;
		for(String dicWord : targetFilteringList){
//			if(word.contains(dicWord)){
//				filterWord = dicWord;
//				break;
//			}
			if(word.equals(dicWord)){
				filterWord = dicWord;
				break;
			}
		}
		return filterWord;
	}
	
	public static boolean isGAorHAorWO(String text){
		return (text.equals("が") || text.equals("は") || text.equals("を"));
	}

	public static boolean isGAorHAorWOorMO(String text){
		return (text.equals("が") || text.equals("は") || text.equals("を") || text.equals("も"));
	}
	
	public static boolean isGAorHAorWOorNIorMOorNIMO(String text){
		return (text.equals("が") || text.equals("は") || text.equals("を") 
				|| text.equals("に") || text.equals("も") || text.equals("にも"));
	}
	
	public static boolean isGAorHAorWOorNIorMOorNIMOorKARAorMADEorTOKAorNO(String text){
		return (text.equals("が") || text.equals("は") || text.equals("を") 
				|| text.equals("に") || text.equals("も") || text.equals("にも")
				|| text.equals("から") || text.equals("まで") || text.equals("とか")
				|| text.equals("の") );
	}
	
	public static boolean isGAorHAorWOorNIorMOorNIMOorTOorMADEorTOKAorYAorNO(String text){
		return (text.equals("が") || text.equals("は") || text.equals("を") 
				|| text.equals("に") || text.equals("も") || text.equals("にも")
				|| text.equals("と") || text.equals("まで") || text.equals("とか")
				|| text.equals("や") || text.equals("の") );
	}


}
