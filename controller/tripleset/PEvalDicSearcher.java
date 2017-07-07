package controller.tripleset;

import java.util.ArrayList;

import controller.*;
import controller.logic.FileOperator;
import model.*;

public class PEvalDicSearcher {

	public static final String MEDICINE = "MEDICINE";
	private static ArrayList<String> evaldicList = 
			FileOperator.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\EVALDIC_ver1.01");
	private static  ArrayList<Phrase> phraseList;
	private static ArrayList<TripleSetInfo> tripleSetInfoList;
	private static ArrayList<Integer> medicinePhraseIdList;
	private static int sentenceId;
	private static String sentenceText;
	//private static TriplePhrase triplePhrase;

	public static ArrayList<TripleSetInfo> getTripleSetInfoList (ArrayList<Sentence> sentenceList) {
		tripleSetInfoList = new ArrayList<TripleSetInfo>();

		for(Sentence sentence : sentenceList){
			PEvalDicSearcher.phraseList = sentence.getPhraseReplaceList();
			sentenceId = sentence.getSentenceId();
			sentenceText = sentence.getText();
			medicinePhraseIdList = new ArrayList<Integer>();
			
			for(Phrase phrase : phraseList){
				if(phrase.getPhraseText().contains(MEDICINE)){ medicinePhraseIdList.add(phrase.getId()); }
			}

			for(Phrase phrase : phraseList){
				int phraseId = phrase.getId();
				boolean checked = false;
				for(Morpheme morpheme : phrase.getMorphemeList()){
					for(String evalWord : evaldicList){
						//評価表現でない場合
						//if(!morpheme.getOriginalForm().contains(evalWord)){ continue; }
						if(!morpheme.getOriginalForm().equals(evalWord)){ continue; }
						judgeTargetPhrase(phraseId);
						//					System.out.println("評価表現:" + evalWord);
						//					System.out.println("原形:" + morpheme.getOriginalForm());
						
						checked = true;
						break;
					}
					if(checked){ break; }
				}
				
			}
		}
		return tripleSetInfoList;
	}

	//「対象」要素存在文節判定
	public static void judgeTargetPhrase(int id){

		for(Phrase phrase : phraseList){
			if(phrase.getDependencyIndex() != id){ continue; }
			String lastCharacter 
			= phrase.getPhraseText().substring(phrase.getPhraseText().length() - 1, phrase.getPhraseText().length());
			if(Filter.isSpecificParticle(lastCharacter)){
				for(int medicinePhraseId : medicinePhraseIdList){
					TripleSetInfo tripleSetInfo 
					= new TripleSetInfo(sentenceId, sentenceText, medicinePhraseId, phrase.getId(), id);
					tripleSetInfoList.add(tripleSetInfo);
				}
			}
		}
	}


}
