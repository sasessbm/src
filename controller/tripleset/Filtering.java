package controller.tripleset;

import java.util.ArrayList;
import model.*;

public class Filtering {
	
//	private static ArrayList<String> targetFilteringList = 
//			GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_dic_110_2_clean_human2.txt");
	
//	private static ArrayList<String> targetFilteringList = 
//			GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\辞書\\110&body.txt");
	
	//110番辞書フィルタ
	public static boolean filterTarget(TripleSet tripleSet, ArrayList<String> targetFilteringList){

		boolean existInTargetFilteringList = false;

		String targetWord = "";
		
		//String dicWord = "";
		boolean nounFlag = false;

		for(Morpheme morpheme : tripleSet.getTargetElement().getMorphemeList()){
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
				}else{
					targetWord += morphemeText;
				}
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
		
		boolean exist = false;
		
		for(String dicWord : targetFilteringList){
//			if(word.contains(dicWord)){
//				exist = true;
//				System.out.println("辞書単語: " + dicWord);
//			}
			if(word.equals(dicWord)){
				exist = true;
				System.out.println("辞書単語: " + dicWord);
			}
		}
		
		return exist;
	}


}
