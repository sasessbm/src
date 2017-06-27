package controller.logic;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

public class PreProcessor {
	
//	public static void main(String[] args) throws Exception{
//		String str = "ン）あぁ、口が苦くなってきたな（アモバン）";
//		//String str ="（就寝前2錠）（4）";
//		str = deleteParentheses(str);
//		System.out.println(str);
//	}

	//スペース削除
	public static String deleteSpace(String sentenceText){

		//半角
		sentenceText = sentenceText.replace(" ", "");
		//全角
		sentenceText = sentenceText.replace("　", "");

		return sentenceText;
	}

	//文単位に区切る & sentenceリスト取得
	public static ArrayList<String> getSentenceTextList(String snippetText){
		ArrayList<String> sentenceTextList = new ArrayList<String>();
		int indexStart = -1;
		int indexPeriod = -1;
		while (true){
			indexPeriod = snippetText.indexOf("。", indexStart + 1);
			if(indexPeriod == -1){ 
				sentenceTextList.add(snippetText.substring(indexStart + 1));
				break; 
			}
			sentenceTextList.add(snippetText.substring(indexStart + 1, indexPeriod));
			indexStart = indexPeriod;
		}
		return sentenceTextList;
	}

	//薬剤名を"MEDICINE"に置き換える
	public static String replaceMedicineName(String sentenceText, TreeMap<Integer, String> medicineNameMap){
		for(Entry<Integer, String> map : medicineNameMap.entrySet()){
			String medicineName = map.getValue();
			if(sentenceText.contains(medicineName)){
				sentenceText = sentenceText.replace(medicineName,"MEDICINE");
			}
		}
		return sentenceText;
	}

	//薬剤名マップ取得
	public static TreeMap<Integer, String> getMedicineNameMap(String sentenceText, ArrayList<String> medicineNameList){
		TreeMap<Integer, String> medicineNameMap = new TreeMap<Integer, String>();

		for(String medicineNameInList : medicineNameList){
			int searchIndex = 0;
			if(sentenceText.contains(medicineNameInList)){
				searchIndex = sentenceText.indexOf(medicineNameInList, searchIndex);
				while(searchIndex != -1){
					medicineNameMap.put(searchIndex, medicineNameInList);
					searchIndex = sentenceText.indexOf(medicineNameInList, searchIndex + 1);
				}
			}
		}
		return medicineNameMap;
	}

	//薬剤名を含まない()削除
	public static String deleteParentheses(String sentenceText){
		//半角
		sentenceText = deleteTextBetweenTwoCharacer(sentenceText ,"(" , ")");
		//全角
		sentenceText = deleteTextBetweenTwoCharacer(sentenceText ,"（" , "）");
		//半角＋全角
		sentenceText = deleteTextBetweenTwoCharacer(sentenceText ,"(" , "）");
		//全角＋半角
		sentenceText = deleteTextBetweenTwoCharacer(sentenceText ,"（" , ")");
		return sentenceText;
	}

	//2文字間の文字を削除
	public static String deleteTextBetweenTwoCharacer(String sentenceText, String firstCharacter, String secondCharacter){
		String textBetweenTwoCharacter = "";
		int indexStart = -1;
		int indexFirstCharacter = -1;
		int indexSecondCharacter = -1;

		while (true){
			indexFirstCharacter = sentenceText.indexOf(firstCharacter, indexStart + 1);
			indexSecondCharacter = sentenceText.indexOf(secondCharacter, indexStart + 1);
			if(indexFirstCharacter == -1 || indexSecondCharacter == -1){ break; }
			if(indexFirstCharacter < indexSecondCharacter){
				textBetweenTwoCharacter = sentenceText.substring(indexFirstCharacter, indexSecondCharacter + 1);
				if(Logic.containsMedicine(textBetweenTwoCharacter)){		
					indexStart = indexSecondCharacter;
				}
				else{
					sentenceText = sentenceText.replace(textBetweenTwoCharacter, "");
					indexStart = -1;
				}
			}
			else{ indexStart = indexSecondCharacter; }
		}
		return sentenceText;	
	}

	public static String deleteBothSideDots(String snippetText){
		snippetText = snippetText.substring(3, snippetText.length()-3);
		return snippetText;
	}

}
