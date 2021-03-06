package view;
import java.util.ArrayList;

import controller.logic.FileOperator;
import controller.logic.SeedSetter;
import controller.logic.SeedSetterForBaseLine;

public class Main {

	private static String testDataPath = "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\実験用データ\\testData.txt";
	
	private static ArrayList<String> medicineNameList 
	= FileOperator.fileRead("C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\薬剤名\\medicine_name.txt");
	
	private static ArrayList<String> keyWordSeedList = SeedSetter.getKeyWordSeedList();
	private static ArrayList<String> keyWordSeedListForBaseLine = SeedSetterForBaseLine.getKeyWordSeedList();
	//private static ArrayList<String> keyWordSeedList = SeedSetter.getTestKeyWordSeedList();

	private static String keyWordIncreaseFilePath 
	= "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\手がかり語\\keyword_increase.txt";

	private static String keyWordIncreaseFinalFilePath 
	= "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\手がかり語\\keyword_increase_final.txt";
	
	private static ArrayList<String> targetFilteringList 
	//= FileOperator.fileRead("C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\辞書\\medicine_dic_110.txt");
	= FileOperator.fileRead("C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\辞書\\medicine_dic_110_2_clean_human2.txt");

	public static void main(String[] args) throws Exception {
		
		//RunFromTargetSeed.run(medicineNameList);
		
		//Logic.medicineNameList = medicineNameList;
		
		//既存手法
		//RunBaseLine.run(keyWordSeedListForBaseLine, testDataPath, medicineNameList, targetFilteringList);
		
		//ブートストラップ
		//RunFromKeyWordSeed.run(keyWordSeedList, medicineNameList, testDataPath, targetFilteringList);
		
		//追加実験
		//ExtractAfterAddKeyword.run(keyWordSeedList, medicineNameList, testDataPath, targetFilteringList);
		ExtractAfterAddKeyword.run2(keyWordSeedList, medicineNameList, testDataPath, targetFilteringList);
	}

}
