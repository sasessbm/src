package controller.logic;

import java.util.ArrayList;
import model.*;

public class Logic {

	private static ArrayList<String> medicineNameList 
	= FileOperator.fileRead("C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\薬剤名\\medicine_name.txt");

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
			if(word.contains(medicineName)){ return true; }
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

	//正解三つ組情報取得
	public static ArrayList<TripleSet> getCorrectTripleSetList(ArrayList<TripleSet> tripleSetList, ArrayList<CorrectAnswer> correctAnswerList){
		ArrayList<TripleSet> correctTripleSetList = new ArrayList<TripleSet>();
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

	//誤り三つ組情報取得
	public static ArrayList<TripleSet> getWrongTripleSetList(ArrayList<TripleSet> tripleSetList, ArrayList<CorrectAnswer> correctAnswerList){
		ArrayList<TripleSet> wrongTripleSetList = new ArrayList<TripleSet>();
		for(TripleSet tripleSet : tripleSetList){
			boolean isCorrect = false;
			int sentenceId = tripleSet.getSentenceId();
			int medicinePhraseId = tripleSet.getMedicinePhraseIndex();
			int targetPhraseId = tripleSet.getTargetElement().getPhraseIndex();
			int effectPhraseId = tripleSet.getEffectElement().getPhraseIndex();
			for(CorrectAnswer correctAnswer : correctAnswerList){
				if(correctAnswer.getSentenceId() == sentenceId && correctAnswer.getMedicinePhraseId() == medicinePhraseId
						&& correctAnswer.getTargetPhraseId() == targetPhraseId && correctAnswer.getEffectPhraseId() == effectPhraseId){
					isCorrect = true;
					break;
				}
			}
			if(!isCorrect){ wrongTripleSetList.add(tripleSet); }
		}
		return wrongTripleSetList;
	}

	//文節リストコピー
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
