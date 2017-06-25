package view;
//package bootstrapping;
//
//import java.util.ArrayList;
//import java.util.LinkedHashSet;
//
//import makeTriplicity.TripleSet;
//
//public class RunFromTargetSeed {
//	
//	public static void run(ArrayList<String> medicineNameList) throws Exception {
//
//		//ArrayList<SeedSet> seedSetList = FileOperation.makeSeedList(seedFilePath);
//		//ArrayList<TripleSet> tripleSetList = makeTriplicity.Main.run(0, 500);
////		ArrayList<Integer> idList = new ArrayList<Integer>();
////		idList.add(2);
////		idList.add(8);
////		idList.add(19);
////		idList.add(65);
////		idList.add(115);
////		idList.add(125);
////		idList.add(167);
////		idList.add(185);
////		idList.add(194);
////		idList.add(204);
////		idList.add(225);
////		idList.add(226);
////		idList.add(257);
////		idList.add(272);
////		idList.add(298);
////		idList.add(335);
////		idList.add(350);
////		idList.add(372);
////		idList.add(377);
////		idList.add(390);
////		idList.add(412);
////		idList.add(422);
////		idList.add(460);
////		idList.add(480);
////		idList.add(482);
////		idList.add(485);
//
//		//シードセット
//		ArrayList<TripleSet> tripleSetIncreaseList = SeedSetter.getTripleSetSeedList();
//		ArrayList<String> keyWordTextIncreaseList = new ArrayList<String>();
//		
//		ArrayList<TripleSet> tripleSetIncreaseFinalList = new ArrayList<TripleSet>();
//		ArrayList<KeyWord> keyWordIncreaseFinalList = new ArrayList<KeyWord>();
//		
//		ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(3001, 4000, medicineNameList);
//		//ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(idList, medicineNameList);
//		
//		double threshold = 0;
//		double constant = 0.5;
//		int repeatCount = 5;
//		
//		
//		for(int i=0; i < repeatCount; i++){
//			
//			System.out.println(i+1 + "回目");
//			ArrayList<TripleSet> tripleSetForSearchList = new ArrayList<TripleSet>();
//			ArrayList<String> keyWordTextForSearchList = new ArrayList<String>();
//			int tripleSetUsedNum = 0;
//			int keyWordUsedNum = 0;
//			
//			//三つ組から手がかり語取得
//			for(TripleSet tripleSet : tripleSetIncreaseList){
//				ArrayList<KeyWord> keyWordTmpList = new ArrayList<KeyWord>();
//				String target = tripleSet.getTargetElement().getText();
//				String effect = tripleSet.getEffectElement().getText();
//				System.out.println(target + " , " + effect);
//				keyWordTmpList = GetKeyWordList.getKeyWordList(medicineNameList, sentenceList, target, effect);
//				if(keyWordTmpList == null){ continue; }
//				
//				//手がかり語リストセット
//				for(KeyWord keyWord : keyWordTmpList){
//					keyWordTextForSearchList.add(keyWord.getKeyWordText());
//				}
//				tripleSet.setKeyWordList(keyWordTmpList);
//				tripleSetUsedNum ++;
//			}
//
//			//手がかり語リストの重複削除
//			keyWordTextForSearchList = new ArrayList<String>(new LinkedHashSet<>(keyWordTextForSearchList));
//			
//			//閾値計算
//			if(i != 0){
//				threshold = constant * (Math.log(tripleSetUsedNum) / Math.log(2.0));
//				System.out.println(threshold);
//			}
//
//			//手がかり語のエントロピー計算
//			for(String keyWordText : keyWordTextForSearchList){
//				double entropy = 0;
//				int keyWordTextAllNum = 0;
//				int keyWordTextNum = 0;
//				ArrayList<Integer> keyWordNumList = new ArrayList<Integer>();
//
//				for(TripleSet tripleSet : tripleSetIncreaseList){
//					keyWordTextNum = tripleSet.getKeyWordNum(keyWordText);
//					if(keyWordTextNum == 0){ continue; }
//					keyWordNumList.add(keyWordTextNum);
//					keyWordTextAllNum += keyWordTextNum;
//				}
//				entropy = Calculator.calculateEntropy(keyWordNumList, keyWordTextAllNum);
//				System.out.println(keyWordText + "　→　" + entropy);
//				
//				//閾値以上の手がかり語をリストに追加
//				if(entropy >= threshold){
//					keyWordTextIncreaseList.add(keyWordText);
//				}
//			}
//			
//			//三つ組増加リスト初期化
//			tripleSetIncreaseList.clear();
//
//			ArrayList<KeyWord> keyWordIncreaseList = Transformation.stringToKeyWord(keyWordTextIncreaseList);
//			
//			keyWordIncreaseFinalList.addAll(keyWordIncreaseList);
//
//			//手がかり語から三つ組取得
//			for(KeyWord keyWord : keyWordIncreaseList){
//				String keyWordText = keyWord.getKeyWordText();
//				//tripleSetIncreaseList = GetTripleSetList.getTripleSetList(keyWordText, sentenceList, medicineNameList);
//				ArrayList<TripleSet> tripleSetTmpList = GetTripleSetList.getTripleSetList(keyWordText, sentenceList, medicineNameList);
//				if(tripleSetTmpList.size() == 0){ continue; }
//				tripleSetForSearchList.addAll(tripleSetTmpList);
//				
//				//手がかり語に三つ組リストセット
//				keyWord.setTripleSetList(tripleSetTmpList);
//				keyWordUsedNum ++;
//			}
//
//			//三つ組リストの重複削除
//			tripleSetForSearchList = makeTriplicity.GetTripleSetList.deleteSameSet(tripleSetForSearchList);
//			
//			//閾値計算
//			if(i != 0){
//				threshold = constant * (Math.log(keyWordUsedNum) / Math.log(2.0));
//				System.out.println(threshold);
//			}
//
//			//三つ組のエントロピー計算
//			for(TripleSet tripleSet : tripleSetForSearchList){
//				double entropy = 0;
//				int tripleSetAllNum = 0;
//				int tripleSetNum = 0;
//				ArrayList<Integer> tripleSetNumList = new ArrayList<Integer>();
//
//				for(KeyWord keyWord : keyWordIncreaseList){
//					tripleSetNum = keyWord.getTripleSetNum(tripleSet);
//					if(tripleSetNum == 0){ continue; }
//					tripleSetNumList.add(tripleSetNum);
//					tripleSetAllNum += tripleSetNum;
//				}
//				entropy = Calculator.calculateEntropy(tripleSetNumList, tripleSetAllNum);
//				System.out.println(tripleSet.getMedicineName()+ " , " + tripleSet.getTargetElement().getText() + " , " 
//						+tripleSet.getEffectElement().getText() +"　→　" + entropy + "　→　" + tripleSet.getUsedKeyWord());
//				
//				//閾値以上の三つ組をリストに追加
//				if(entropy >= threshold){
//					tripleSetIncreaseList.add(tripleSet);
//				}
//			}
//			//手がかり語増加リスト初期化
//			//keyWordIncreaseList.clear();
//			keyWordTextIncreaseList.clear();
//			
//			tripleSetIncreaseFinalList.addAll(tripleSetIncreaseList);
//			
//		}
//		
//		System.out.println("\r\n獲得結果");
//		
//		for(KeyWord keyWord : keyWordIncreaseFinalList){
//			System.out.println(keyWord.getKeyWordText());
//		}
//		
//		for(TripleSet tripleSet : tripleSetIncreaseFinalList){
//			System.out.println(tripleSet.getMedicineName()+ " , " + tripleSet.getTargetElement().getText() + " , " 
//					+tripleSet.getEffectElement().getText());
//		}
//
//		
//
//	
//		
//	}
//
//}
