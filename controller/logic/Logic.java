package controller.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import model.*;

public class Logic {

	private static ArrayList<String> medicineNameList 
	= FileOperator.fileRead("C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\薬剤名\\medicine_name.txt");

	//	public static void main(String[] args) throws Exception{
	//
	//		//		ArrayList<Integer> idList = getRandomIdList(100,1,100);
	//		//		for(int id : idList){
	//		//			System.out.println(id);
	//		//		}
	//
	//		ArrayList<Integer> usedIdList = new ArrayList<Integer>();
	//		usedIdList.add(2);
	//		usedIdList.add(3);
	//		usedIdList.add(6);
	//		usedIdList.add(9);
	//		usedIdList.add(12);
	//
	//		ArrayList<Integer> additionalIdList = getAdditionalRandomIdList(10, 1, 30, usedIdList);
	//
	//		for(int id : additionalIdList){
	//			System.out.println(id);
	//		}
	//
	//	}


	//重複したKeyWordを削除
	public static ArrayList<KeyWord> deleteOverlappingFromListForKey
	(ArrayList<KeyWord> removeList, ArrayList<KeyWord> compareList){

		for(KeyWord key : compareList){
			String textBase = key.getKeyWordText();
			for(int i = removeList.size() - 1; i >= 0; i--){
				if(removeList.get(i).getKeyWordText().equals(textBase)){
					removeList.remove(i);
				}
			}
		}
		return removeList;
	}

	public static ArrayList<TripleSetInfo> deleteOverlappingFromListForTripleSetInfo
	(ArrayList<TripleSetInfo> removeList, ArrayList<TripleSetInfo> compareList){

		for(TripleSetInfo tripleSetInfo : compareList){
			int sentenceId = tripleSetInfo.getSentenceId();
			int medicinePhraseId = tripleSetInfo.getMedicinePhraseId();
			int targetPhraseId = tripleSetInfo.getTargetPhraseId();
			int effectPhraseId = tripleSetInfo.getEffectPhraseId();

			for(int i = removeList.size() - 1; i >= 0; i--){
				TripleSetInfo tSI = removeList.get(i);
				if(tSI.getSentenceId() == sentenceId && tSI.getMedicinePhraseId() == medicinePhraseId && 
						tSI.getTargetPhraseId() == targetPhraseId && tSI.getEffectPhraseId() == effectPhraseId){
					removeList.remove(i);
				}
			}
		}
		return removeList;
	}
	
	public static ArrayList<TripleSet> deleteOverlappingFromListForTripleSet
	(ArrayList<TripleSet> removeList, ArrayList<TripleSet> compareList){

		for(TripleSet tripleSet : compareList){
			int sentenceId = tripleSet.getSentenceId();
			int medicinePhraseId = tripleSet.getMedicinePhraseIndex();
			int targetPhraseId = tripleSet.getTargetElement().getPhraseIndex();
			int effectPhraseId = tripleSet.getEffectElement().getPhraseIndex();

			for(int i = removeList.size() - 1; i >= 0; i--){
				TripleSet ts = removeList.get(i);
				if(ts.getSentenceId() == sentenceId && ts.getMedicinePhraseIndex() == medicinePhraseId && 
					ts.getTargetElement().getPhraseIndex() == targetPhraseId && ts.getEffectElement().getPhraseIndex() == effectPhraseId){
					removeList.remove(i);
				}
			}
		}
		return removeList;
	}

	public static ArrayList<KeyWord> deleteOverlappingFromListForStringAndKey
	(ArrayList<KeyWord> removeList, ArrayList<String> compareList){

		for(String word : compareList){
			//String textBase = key.getKeyWordText();
			for(int i = removeList.size() - 1; i >= 0; i--){
				if(removeList.get(i).getKeyWordText().equals(word)){
					removeList.remove(i);
				}
			}
		}
		return removeList;
	}

	//重複した対象と効果の組を削除
	public static ArrayList<TripleSet> deleteSameSet(ArrayList<TripleSet> tripleSetList){
		ArrayList<TripleSet> tripleSetListBase = tripleSetList;
		for(int i = 0 ; i < tripleSetListBase.size() ; i++){
			int sameCount = 0;
			String medicineNameBase = tripleSetListBase.get(i).getMedicineName();
			String targetBase = tripleSetListBase.get(i).getTargetElement().getText();
			String effectBase = tripleSetListBase.get(i).getEffectElement().getText();
			for(TripleSet tripleSet : tripleSetList){
				String medicineName = tripleSet.getMedicineName();
				String target = tripleSet.getTargetElement().getText();
				String effect = tripleSet.getEffectElement().getText();
				if(medicineNameBase.equals(medicineName) && targetBase.equals(target) && effectBase.equals(effect)){
					sameCount++;
				}
			}
			if(sameCount>=2){
				tripleSetList.remove(tripleSetListBase.get(i));
			}
		}
		return tripleSetList;
	}

	//	public static ArrayList<String> deleteOverlappingFromListForString
	//	(ArrayList<String> removeList, ArrayList<String> compareList){
	//
	//		for(String text : compareList){
	//			//String textBase = key.getKeyWordText();
	//			for(int i = removeList.size() - 1; i >= 0; i--){
	//				if(removeList.get(i).equals(text)){
	//					removeList.remove(i);
	//				}
	//			}
	//		}
	//		return removeList;
	//	}

	//重複したSentenceを削除
	public static ArrayList<Sentence> deleteSameSentence(ArrayList<Sentence> sentenceList){

		ArrayList<Sentence> sentenceListBase = sentenceList;

		for(int i = 0 ; i < sentenceListBase.size() ; i++){
			int sameCount = 0;
			String sentenceTextBase = sentenceListBase.get(i).getText();

			for(Sentence sentence : sentenceList){
				String sentenceText= sentence.getText();

				if(sentenceTextBase.equals(sentenceText)){
					sameCount++;
				}
			}
			if(sameCount>=2){
				sentenceList.remove(sentenceList.get(i));
			}
		}
		return sentenceList;
	}

	//ゴミ取り
	public static String cleanWord(String word){
		word = word.replace(".", "");
		word = word.replace("｡", "");
		word = word.replace("。", "");
		word = word.replace("､", "");
		word = word.replace("、", "");
		word = word.replace("｣", "");
		word = word.replace("｢", "");
		word = word.replace("】", "");
		word = word.replace("【", "");
		word = word.replace("／", "");
		word = word.replace("/", "");
		word = word.replace("\\", "");
		word = word.replace("\"", "");
		word = word.replace("\'", "");
		word = word.replace("@", "");
		word = word.replace("（", "");
		word = word.replace("）", "");
		word = word.replace("(", "");
		word = word.replace(")", "");
		word = word.replace("-", "");
		word = word.replace(",", "");

		return word;
	}

	//薬剤名を含むか判定
	public static boolean containsMedicine(String word){
		for(String medicineName : medicineNameList){
			if(word.contains(medicineName)){return true;}
		}
		return false;
	}

	//手がかり語の適切性判定
	public static boolean properKeyWord(Morpheme morpheme){

		//手がかり語は名詞または動詞とする
		//if(!(morpheme.getPartOfSpeech().equals("名詞") || morpheme.getPartOfSpeech().equals("動詞"))){ return false; }

		//数字は不適
		if(morpheme.getPartOfSpeechDetails().equals("数")){ return false; }

		//薬剤名が含まれていた場合は不適
		if(containsMedicine(morpheme.getMorphemeText())){ return false; }

		return true;
	}

	//ランダムなIDリスト作成
	public static ArrayList<Integer> getRandomIdList(int idNum, int startIdIndex, int endIdIndex){

		ArrayList<Integer> randomIdList = new ArrayList<Integer>();
		Random rand = new Random();
		boolean isCreated;
		int id = rand.nextInt(endIdIndex + 1 - startIdIndex) + startIdIndex;

		randomIdList.add(id);

		for(int i=0; i < idNum-1; ){
			isCreated = false;
			id = rand.nextInt(endIdIndex + 1 - startIdIndex) + startIdIndex;
			for(Integer idInList : randomIdList){
				if(idInList == id){
					isCreated = true;
				}
			}
			if(!isCreated){
				randomIdList.add(id);
				i++;
			}
		}
		Collections.sort(randomIdList);
		return randomIdList;
	}

	//IDリストを更新
	public static ArrayList<Integer> getAdditionalRandomIdList(int idNum, int startIdIndex, int endIdIndex, ArrayList<Integer> usedIdList){

		ArrayList<Integer> additionalIdList = new ArrayList<Integer>();
		Random rand = new Random();
		boolean isUsedId = false;
		boolean isCreated;
		int id = 0;

		while(true){
			isUsedId = false;
			id = rand.nextInt(endIdIndex + 1 - startIdIndex) + startIdIndex;

			for(int usedId : usedIdList){
				if(id == usedId){
					isUsedId = true;
					break;
				}
			}
			if(!isUsedId){
				break;
			}
		}
		additionalIdList.add(id);
		for(int i=0; i < idNum-1; ){
			isCreated = false;

			while(true){
				isUsedId = false;
				id = rand.nextInt(endIdIndex + 1 - startIdIndex) + startIdIndex;

				for(int usedId : usedIdList){
					if(id == usedId){
						isUsedId = true;
						break;
					}
				}
				if(!isUsedId){ break; }
			}
			for(Integer idInList : additionalIdList){
				if(idInList == id){
					isCreated = true;
				}
			}
			if(!isCreated){
				additionalIdList.add(id);
				i++;
			}
		}
		Collections.sort(additionalIdList);
		return additionalIdList;
	}

	// 結果表示                                   出力数　　　　　　　　　　　　　　　正解三つ組リスト　　　     本来の正解数　　　　　　
	public static void displayResult(int allExtractionNum, ArrayList<TripleSet> correctTripleSetList,  int correctAnswerNum){

		int correctExtractionNum = correctTripleSetList.size();


		ArrayList<Double> resultList = Calculator.getResultList(allExtractionNum, correctExtractionNum, correctAnswerNum);

		System.out.println("\r\n＜評価結果＞");
		System.out.println("\r\n出力数　　　　：" + allExtractionNum);
		System.out.println("正解三つ組み数：" + correctExtractionNum);
		System.out.println("誤り三つ組み数：" + (allExtractionNum - correctExtractionNum));

		System.out.println("\r\n適合率(precision)：" + resultList.get(0));
		System.out.println("再現率(recall)：" + resultList.get(1));
		System.out.println("Ｆ値(F-measure)：" + resultList.get(2));
		System.out.println("\r\n＜正解出力結果＞");

		for(TripleSet tripleSet : correctTripleSetList){
			System.out.println("\r\nsentenceID = " + tripleSet.getSentenceId() + "　　　　使われた手がかり語・・・" + tripleSet.getUsedKeyWord());
			System.out.println("「"+ tripleSet.getSentenceText() + "」");
			System.out.println("（" + tripleSet.getMedicineName()+ " , " + tripleSet.getTargetElement().getText() + " , " 
					+tripleSet.getEffectElement().getText() + "）（" + tripleSet.getMedicinePhraseIndex() + " , " 
					+ tripleSet.getTargetElement().getPhraseIndex() + " , " + tripleSet.getEffectElement().getPhraseIndex() + "）");
		}
	}

	//正解三つ組情報取得
//	public static ArrayList<TripleSetInfo> getCorrectTripleSetInfoList
//	(ArrayList<TripleSetInfo> tripleSetInfoList, ArrayList<CorrectAnswer> correctAnswerList){
//
//		ArrayList<TripleSetInfo> correctTripleSetInfoList = new ArrayList<TripleSetInfo>();
//		//ArrayList<CorrectAnswer> correctAnswerList = SeedSetter.getCorrectAnswerList();
//
//		for(TripleSetInfo tripleSetInfo : tripleSetInfoList){
//			int sentenceId = tripleSetInfo.getSentenceId();
//			int medicinePhraseId = tripleSetInfo.getMedicinePhraseId();
//			int targetPhraseId = tripleSetInfo.getTargetPhraseId();
//			int effectPhraseId = tripleSetInfo.getEffectPhraseId();
//
//			for(CorrectAnswer correctAnswer : correctAnswerList){
//				if(correctAnswer.getSentenceId() == sentenceId && correctAnswer.getMedicinePhraseId() == medicinePhraseId
//						&& correctAnswer.getTargetPhraseId() == targetPhraseId && correctAnswer.getEffectPhraseId() == effectPhraseId){
//					correctTripleSetInfoList.add(tripleSetInfo);
//				}
//			}
//		}
//		return correctTripleSetInfoList;
//	}
	
	public static ArrayList<TripleSet> getCorrectTripleSetList
	(ArrayList<TripleSet> tripleSetList, ArrayList<CorrectAnswer> correctAnswerList){

		ArrayList<TripleSet> correctTripleSetList = new ArrayList<TripleSet>();
		//ArrayList<CorrectAnswer> correctAnswerList = SeedSetter.getCorrectAnswerList();

		for(TripleSet tripleSet : tripleSetList){
			int sentenceId = tripleSet.getSentenceId();
			int medicinePhraseId = tripleSet.getMedicinePhraseIndex();
			int targetPhraseId = tripleSet.getTargetElement().getPhraseIndex();
			int effectPhraseId = tripleSet.getEffectElement().getPhraseIndex();

			for(CorrectAnswer correctAnswer : correctAnswerList){
				if(correctAnswer.getSentenceId() == sentenceId && correctAnswer.getMedicinePhraseId() == medicinePhraseId
						&& correctAnswer.getTargetPhraseId() == targetPhraseId && correctAnswer.getEffectPhraseId() == effectPhraseId){
					correctTripleSetList.add(tripleSet);
				}
			}
		}
		return correctTripleSetList;
	}

	public static ArrayList<Phrase> copyPhraseList(ArrayList<Phrase> originPhraseList){

		ArrayList<Phrase> copyPhraseList = new ArrayList<Phrase>();
		for(Phrase originPhrase : originPhraseList){
			ArrayList<Morpheme> morphemeRestoreList = new ArrayList<Morpheme>();
			for(Morpheme morpheme : originPhrase.getMorphemeList()){
				morphemeRestoreList.add(new Morpheme(morpheme.getId(), morpheme.getMorphemeText(), morpheme.getFeature()));
			}
			Phrase restorePhrase = 
					new Phrase(originPhrase.getId(), originPhrase.getPhraseText(), originPhrase.getDependencyIndex(), morphemeRestoreList);
			copyPhraseList.add(restorePhrase);
		}
		return copyPhraseList;
	}



}
