package controller.tripleset;
//package makeTriplicity;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//public class GetTriplePhraseListSecond {
//
//	private static  ArrayList<Phrase> phraseList;
//	private static ArrayList<String> evaldicList = 
//			GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\EVALDIC_ver1.01");
//	//private static TriplePhrase triplePhrase;
//
//	public static ArrayList<TriplePhrase> getTriplePhrase(ArrayList<Phrase> phraseList) {
//
//		GetTriplePhraseListSecond.phraseList = new ArrayList<Phrase>();
//		GetTriplePhraseListSecond.phraseList = phraseList;
//
//		ArrayList<TriplePhrase> triplePhraseList = new ArrayList<TriplePhrase>();
//
//		//ArrayList<Phrase> effectPhraseList = new ArrayList<Phrase>();
//		//ArrayList<Phrase> targetPhraseList = new ArrayList<Phrase>();
//
//		for(int phraseIndex = 1; phraseIndex < phraseList.size(); phraseIndex++){
//
//			TriplePhrase triplePhrase = new TriplePhrase();
//			Phrase phrase = new Phrase();
//			phrase = phraseList.get(phraseIndex);
//
//			ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
//
//			for(int morphemeIndex = 0; morphemeIndex < morphemeList.size(); morphemeIndex++){
//				for(String evalWord : evaldicList){
//
//					Morpheme morpheme = morphemeList.get(morphemeIndex);
//
//					//評価表現でない場合
//					//if(!morpheme.getOriginalForm().contains(evalWord)){ continue; }
//					if(!morpheme.getOriginalForm().equals(evalWord)){ continue; }
//
//					ArrayList<Phrase> targetPhraseList = new ArrayList<Phrase>();
//					targetPhraseList =	judgeTargetPhrase(phrase.getId());
//
//					if(targetPhraseList.size() == 0){ continue; }
//					triplePhrase.setTargetPhraseList(targetPhraseList);
//					triplePhrase.setEffectPhrase(phrase);
//					//					System.out.println("評価表現:" + evalWord);
//					//					System.out.println("原形:" + morpheme.getOriginalForm());
//					//					for(Phrase targetPhrase : targetPhraseList){
//					//						System.out.println("対象::" + targetPhrase.getPhraseText());
//					//					}
//					triplePhraseList.add(triplePhrase);
//					//System.out.println("------------------------------------------");
//				}
//			}
//
//		}
//		
//		deleteSameSet(triplePhraseList);
//
//		return triplePhraseList;
//	}
//
//	//「対象」要素存在文節判定
//	public static ArrayList<Phrase> judgeTargetPhrase(int id){
//
//		ArrayList<Phrase> targetPhraseList = new ArrayList<Phrase>();
//		boolean findPhrase = false;
//
//		//逆から探索
//		for(int i=1; i<=phraseList.size(); i++){
//			int currentIndex = phraseList.size()-i;
//			Phrase phrase = phraseList.get(currentIndex);
//
//			if(phrase.getDependencyIndex() == id || findPhrase){
//
//				String lastMorphemeText = phrase.getMorphemeList()
//						.get(phrase.getMorphemeList().size()-1)
//						.getMorphemeText();
//
//				if(findPhrase){
//					if(lastMorphemeText.equals("の")){
//						//targetPhraseList.add(phraseList.get(currentIndex));
//						targetPhraseList.add(phrase);
//						//targetText = phrase.getPhraseText() + targetText;
//					}else{
//						Collections.reverse(targetPhraseList);
//						//targetPhraseList.add(phrase);
//						break;
//					}
//				}else{
//					if(lastMorphemeText.equals("が") || lastMorphemeText.equals("は") 
//							|| lastMorphemeText.equals("を") || lastMorphemeText.equals("も")){
//						findPhrase = true;
//						targetPhraseList.add(phrase);
//					}
//				}
//
//			}
//		}
//		return targetPhraseList;
//	}
//
//	//重複した組を削除
//	public static ArrayList<TriplePhrase> deleteSameSet(ArrayList<TriplePhrase> triplePhraseList){
//		
//		ArrayList<TriplePhrase> triplePhraseListBase = triplePhraseList;
//
//		
//
//		for(int i = 0 ; i < triplePhraseListBase.size() ; i++){
//			
//			int sameCount = 0;
//
//			for(TriplePhrase triplePhrase : triplePhraseList){
//				if(triplePhraseListBase.get(i).getEffectPhrase().equals(triplePhrase.getEffectPhrase()) 
//						&& triplePhraseListBase.get(i).getTargetPhraseList().equals(triplePhrase.getTargetPhraseList())){
//					sameCount++;
//				}
//			}
//			
//			if(sameCount>=2){
//				triplePhraseList.remove(triplePhraseListBase.get(i));
//			}
//
//		}
//
//		return triplePhraseList;
//	}
//
//
//}
