package controller.logic;

import java.util.ArrayList;

import model.Element;
import model.KeyWord;
import model.TripleSet;

public class Displayer {

	public static void displayAllKeyWord(ArrayList<KeyWord> keyWordList){
		System.out.println("\r\n＜全抽出結果＞");
		System.out.println("\r\n＜手がかり語＞");
		//keyWordList.sort((a,b) -> (int)(a.getEntropy()) - (int)(b.getEntropy()) );
//		for(KeyWord keyWord : keyWordList){
//			System.out.println("「" + keyWord.getText() + "」 →　" + keyWord.getEntropy());
//		}
		ArrayList<String> keyList = SeedSetterForBaseLine.getKeyWordSeedList();
		
		int count = 0;
		for(KeyWord keyWord : keyWordList){
			System.out.println(keyWord.getText());
			for(String key : keyList){
				if(key.equals(keyWord.getText())){ count++; }
			}
			
		}
		for(KeyWord keyWord : keyWordList){
			System.out.println(keyWord.getEntropy());
		}
		System.out.println("先行研究の手がかり語との重複数：" + count);
	}

	// 結果表示                                   出力数　　　　　　　　　　　　　　　正解三つ組リスト　　　     本来の正解数　　抽出手がかり語数　
	public static void displayResult(int allExtractionNum, ArrayList<TripleSet> correctTripleSetList,  int correctAnswerNum, int keyWordNum){
		int correctExtractionNum = correctTripleSetList.size();
		ArrayList<Double> resultList = Calculator.getResultList(allExtractionNum, correctExtractionNum, correctAnswerNum);
		System.out.println("\r\n全正解数　　　：" + correctAnswerNum);
		System.out.println("\r\n＜評価結果＞");
		System.out.println("\r\n抽出手がかり語数　　　：" + keyWordNum);
		System.out.println("\r\n出力数　　　：" + allExtractionNum);
		System.out.println("正解三つ組数：" + correctExtractionNum);
		System.out.println("誤り三つ組数：" + (allExtractionNum - correctExtractionNum));
		System.out.println("\r\n適合率(precision)：" + resultList.get(0));
		System.out.println("再現率(recall)   ：" + resultList.get(1));
		System.out.println("Ｆ値(F-measure)  ：" + resultList.get(2));
	}
	
	public static void displayCorrectTripleSet(ArrayList<TripleSet> correctTripleSetList){
		System.out.println("\r\n＜正解三つ組＞");
		displayTripleSet(correctTripleSetList);
	}
	
	public static void displayWrongTripleSet(ArrayList<TripleSet> wrongTripleSetList){
		System.out.println("\r\n＜誤り三つ組＞");
		displayTripleSet(wrongTripleSetList);
	}
	
	public static void displayTripleSet(ArrayList<TripleSet> tripleSetList){
		//int sentenceId = 0;
		//int sentenceIdTmp = 0;
		//sentenceID順にソート
		tripleSetList.sort( (a,b) -> a.getSentenceId() - b.getSentenceId() );
		for(TripleSet tripleSet : tripleSetList){
			//sentenceId = tripleSet.getSentenceId();
			//if(sentenceId != sentenceIdTmp){ sentenceCount++; }
			//sentenceIdTmp = sentenceId;
			ArrayList<String> usedKeyList = tripleSet.getUsedKeyList();
			String usedKey = "";
			if(usedKeyList != null){
				for(int i = 0; i < usedKeyList.size(); i++){
					usedKey += usedKeyList.get(i);
					if(i != usedKeyList.size()-1){ usedKey += "→"; }
				}
			}
			System.out.println("\r\nsentenceID = " + tripleSet.getSentenceId() + "       使われた手がかり語・・・" + usedKey
					+ "    回数・・・"+ tripleSet.getEffectRepeatCount() + "回     抽出パターン・・・" + tripleSet.getPatternType());
			System.out.println("「"+ tripleSet.getSentenceText() + "」");
			System.out.println("（" + tripleSet.getMedicineName()+ " , " + tripleSet.getTargetElement().getText() + " , " 
					+tripleSet.getEffectElement().getText() + "）（" + tripleSet.getMedicinePhraseIndex() + " , " 
					+ tripleSet.getTargetElement().getPhraseIndex() + " , " + tripleSet.getEffectElement().getPhraseIndex() + "）  →  "
					+ tripleSet.getEntropy() + "   辞書単語：" + tripleSet.getFilterWord());
		}
		
	}

	public static void displayExtractedTripleSet(String keyWordText, ArrayList<TripleSet> tripleSetList){
		System.out.println("\r\n「"+keyWordText + "」から、以下の三つ組を抽出");
		for(TripleSet tripleSet : tripleSetList){
			Element targetElement = tripleSet.getTargetElement();
			Element effectElement = tripleSet.getEffectElement();
			ArrayList<String> usedKeyList = tripleSet.getUsedKeyList();
			String usedKey = "";
			if(usedKeyList != null){
				for(int i = 0; i < usedKeyList.size(); i++){
					usedKey += usedKeyList.get(i);
					if(i != usedKeyList.size()-1){ usedKey += "→"; }
				}
			}
			System.out.println("（" + tripleSet.getMedicineName()+ "，" + targetElement.getText() 
					+ "，" + effectElement.getText() + "）（" + tripleSet.getMedicinePhraseIndex() + 
					"，" + targetElement.getPhraseIndex() + "，" + effectElement.getPhraseIndex() +
					" ）（" + usedKey +  "）p = "+ tripleSet.getPatternType() +"   sID = " + tripleSet.getSentenceId());
		}
	}

	public static void dixplayTripleSetEntropyAndThreshold(TripleSet tripleSet, double entropy, double threshold){
		System.out.println("「" +tripleSet.getTargetOriginalElement().getText()+ "」" + "（，" 
				+tripleSet.getEffectElement().getText() +"）　→　" + entropy + "   閾値・・・" + threshold
				+ "  （" + tripleSet.getSentenceId() + "，" + tripleSet.getMedicinePhraseIndex() + "，"
				+ tripleSet.getTargetElement().getPhraseIndex() + "，" + tripleSet.getEffectElement().getPhraseIndex() + "）");
	}

	public static void dixplayKeyWordEntropyAndThreshold(KeyWord keyWord, double entropy, double threshold){
		System.out.println("「" + keyWord.getText() + "」 →　" + entropy + "   閾値・・・" + 
				threshold + "  sId=" + keyWord.getSentenceId());
	}
	
	public static void displayExtractionNum(ArrayList<Integer> targetNumList, int targetAllNum){
		String str = "";
		for(int num : targetNumList){ str += num + ","; }
		str += " AllNum・・・" + targetAllNum;
		System.out.println(str);
	}
	
	public static void displayExtractedKeyWord(String target, ArrayList<KeyWord> keyWordList){
		System.out.println("\r\n「"+target + "」から、以下の手がかり語を抽出");
		for(KeyWord keyWord : keyWordList){
			System.out.println("「"+ keyWord.getText() + "」");
		}
		
	}

}
