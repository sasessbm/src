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
		word = word.replace("＆", "");
		word = word.replace("？", "");
		word = word.replace("・", "");
		word = word.replace("β", "");

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
					break;
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
