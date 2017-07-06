//package controller.tripleset;
//
//import java.util.ArrayList;
//
//import model.Phrase;
//import model.Sentence;
//import model.TripleSetInfo;
//
//public class P6TripleSetInfoSearcher {
//	
//	public static final String MEDICINE = "MEDICINE";
//	private static  ArrayList<Phrase> phraseList;
//	private static String keyWordText;
//	private static ArrayList<TripleSetInfo> tripleSetInfoList;
//	private static int medicinePhraseId;
//	private static int sentenceId;
//	private static String sentenceText;
//	
//	public static ArrayList<TripleSetInfo> getTripleSetInfoList (ArrayList<Sentence> sentenceList, String keyWordText) {
//		tripleSetInfoList = new ArrayList<TripleSetInfo>();
//		P6TripleSetInfoSearcher.keyWordText = keyWordText;
//		
//		for(Sentence sentence : sentenceList){
//			
//			P6TripleSetInfoSearcher.phraseList = sentence.getPhraseReplaceList();
//			sentenceId = sentence.getSentenceId();
//			sentenceText = sentence.getText();
//			
//			for(Phrase phrase : phraseList){
//				
//				
//				
//				
//			}
//			
//			
//		}
//		
//		
//	}
//
//}
