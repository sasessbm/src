package controller.tripleset;

import java.util.ArrayList;

import controller.logic.Logic;
import model.*;

public class Filter {

	//	private static ArrayList<String> targetFilteringList = 
	//			GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_dic_110_2_clean_human2.txt");

	//	private static ArrayList<String> targetFilteringList = 
	//			GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\辞書\\110&body.txt");

	//対象要素に薬剤名が入っていたら不適切
	public static void filterMedicineName(ArrayList<TripleSet> tripleSetList){
		for(int i = tripleSetList.size() - 1; i >= 0; i--){
			TripleSet tripleSet = tripleSetList.get(i);
			String targetText = tripleSet.getTargetElement().getText();
			if(Logic.containsMedicine(targetText)){
				tripleSetList.remove(i);
			}
		}
	}

	//110番辞書フィルタ
	public static void filter(ArrayList<TripleSet> tripleSetList, ArrayList<String> targetFilteringList){

		for(int i = tripleSetList.size() - 1; i >= 0; i--){
			TripleSet tripleSet = tripleSetList.get(i);
			ArrayList<Morpheme> morphemeList = tripleSet.getTargetOriginalElement().getMorphemeList();

			if(!isProper(morphemeList, targetFilteringList)){
				tripleSetList.remove(i);
			}
		}
	}


	//110番辞書フィルタ
	public static boolean isProper(ArrayList<Morpheme> morphemeList, ArrayList<String> targetFilteringList){
		boolean existInTargetFilteringList = false;
		String targetWord = "";
		//String dicWord = "";
		boolean nounFlag = false;

		for(Morpheme morpheme : morphemeList){
			String morphemeText = morpheme.getMorphemeText();
			String pos = morpheme.getPartOfSpeech();
			if(pos.equals("接頭詞")){
				if(nounFlag){
					//dicWord = Preprocessor.preprocessorTargetWord(dicWord);
					if(targetWord != ""){
						//pw.println(dicWord);
						if(searchTargetFilteringList(targetWord, targetFilteringList)){
							existInTargetFilteringList = true;
							break;
						}
						//System.out.println(targetWord);
						targetWord = "";
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
				//dicWord = Preprocessor.preprocessorTargetWord(dicWord);
				if(targetWord != ""){
					//pw.println(dicWord);
					if(searchTargetFilteringList(targetWord, targetFilteringList)){
						existInTargetFilteringList = true;
						break;
					}
					//System.out.println(targetWord);
					targetWord = "";
				}
				nounFlag = false;
			}
		}
		if(targetWord.length() != 0){
			if(searchTargetFilteringList(targetWord, targetFilteringList)){
				existInTargetFilteringList = true;
				targetWord = "";
			}
		}
		return existInTargetFilteringList;
	}



	public static boolean searchTargetFilteringList(String word, ArrayList<String> targetFilteringList){
		boolean existInList = false;
		for(String dicWord : targetFilteringList){
//			if(word.contains(dicWord)){
//				existInList = true;
//				System.out.println("辞書単語: " + dicWord);
//			}
			if(word.equals(dicWord)){
				existInList = true;
				System.out.println("辞書単語: " + dicWord);
			}
		}
		return existInList;
	}

	public static boolean isGAorHAorWOorMO(String text){
		return (text.equals("が") || text.equals("は") || text.equals("を") || text.equals("も"));
	}
	
	public static boolean isGAorHAorWO(String text){
		return (text.equals("が") || text.equals("は") || text.equals("を"));
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
