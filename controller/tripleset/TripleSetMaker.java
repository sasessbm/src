package controller.tripleset;

import java.util.ArrayList;
import java.util.Collections;

import controller.logic.Logic;
import controller.logic.PostProcessor;
import model.*;

public class TripleSetMaker {
	
//	public static void main(String[] args) throws Exception {
//		
//		ArrayList<Morpheme> morphemeList = new ArrayList<Morpheme>();
//		morphemeList.add(new Morpheme())
//		
//		Element element = getElement("胃腸が");
//		
//	}

	public static ArrayList<TripleSet> getTripleSetList
	(ArrayList<TripleSetInfo> tripleSetInfoList, ArrayList<Sentence> sentenceList, ArrayList<String> medicineNameList){

		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();

		for(TripleSetInfo tripleSetInfo : tripleSetInfoList){
			ArrayList<Phrase> phraseRestoreList = new ArrayList<Phrase>();
			if(sentenceList.size() != 1){
				phraseRestoreList = sentenceList.get(tripleSetInfo.getSentenceId()-1).getPhraseRestoreList();
			}else{
				phraseRestoreList = sentenceList.get(0).getPhraseRestoreList(); // デバグ用
			}
			tripleSetList.addAll(getTripleSet(tripleSetInfo, phraseRestoreList, medicineNameList));
		}
		
		return tripleSetList;
	}

	public static ArrayList<TripleSet> getTripleSet
	(TripleSetInfo tripleSetInfo, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList) {

		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();
		int targetPhraseId = tripleSetInfo.getTargetPhraseId();
		int effectPhraseId = tripleSetInfo.getEffectPhraseId();
		int medicinePhraseId = tripleSetInfo.getMedicinePhraseId();
		int effectRepeatCount = tripleSetInfo.getEffectRepeatCount();
		ArrayList<String> medicineNameListInPhrase = new ArrayList<String>(); 
		String sentenceText = tripleSetInfo.getSentenceText();
		int sentenceId = tripleSetInfo.getSentenceId();

		// とりあえず、薬剤名文節に薬剤名が複数含まれていた場合に対応
		for(Morpheme morpheme : phraseList.get(medicinePhraseId).getMorphemeList()){
			if(!morpheme.getPartOfSpeechDetails().equals("固有名詞") && 
					!morpheme.getPartOfSpeechDetails().equals("一般")){ continue; }
			for(String name : medicineNameList){
				String morphemeText = morpheme.getMorphemeText();
				if(morphemeText.contains(name)){ medicineNameListInPhrase.add(name); }
			}
		}
		ArrayList<Phrase> targetPhraseList = new ArrayList<Phrase>();
		ArrayList<Phrase> effectPhraseList = new ArrayList<Phrase>();
		int searchIndex = targetPhraseId;
		boolean isRelation = false;

		// 対象要素の形態素リスト取得
		Phrase targetPhrase = phraseList.get(searchIndex);
		while(true){
			targetPhraseList.add(targetPhrase);
			if(searchIndex == 0){ break; } // 最初の文節まで到達した場合
			searchIndex--;
			targetPhrase = phraseList.get(searchIndex);
			//係り受け関係になっている場合は続ける
			if(targetPhrase.getDependencyIndex() != phraseList.get(searchIndex + 1).getId()){ break; }
			isRelation = true;
		}
		Collections.reverse(targetPhraseList);
		// 要素取得
		Element targetOriginalElement = getOriginalElement(phraseList.get(targetPhraseId).getMorphemeList(), 1);
		Element targetElement = getTargetElement(targetPhraseList, 1, isRelation);
		targetElement.setPhraseIndex(targetPhraseId);
		
		// 効果要素の形態素リスト取得
		searchIndex = effectPhraseId;
		isRelation = false;
		Phrase effectPhrase = phraseList.get(searchIndex);
		while(true){
			effectPhraseList.add(effectPhrase);
			if(searchIndex == 0){ break; } // 最初の文節まで到達した場合
			searchIndex--;
			effectPhrase = phraseList.get(searchIndex);
			//係り受け関係になっている場合は続ける
			if(effectPhrase.getDependencyIndex() != phraseList.get(searchIndex + 1).getId()){ break; }
			isRelation = true;
		}
		Collections.reverse(effectPhraseList);
		Element effectElement = getOriginalElement(phraseList.get(effectPhraseId).getMorphemeList(), 2);
		effectElement.setPhraseIndex(effectPhraseId);
		for(String medicineName : medicineNameListInPhrase){
			TripleSet tripleSet = new TripleSet(medicineName,targetElement, effectElement, tripleSetInfo.getUsedKeyList(),
					sentenceText, sentenceId, medicinePhraseId, tripleSetInfo.getPatternType());
			tripleSet.setTargetOriginalElement(targetOriginalElement);
			tripleSet.setEffectRepeatCount(effectRepeatCount);
			PostProcessor.deleteParentheses(tripleSet);
			tripleSetList.add(tripleSet);
		}
		return tripleSetList;
	}
	
	public static Element getTargetElement(ArrayList<Phrase> phraseList, int elementType, boolean isRelation){
		Element element = new Element();
		String text = "";
		ArrayList<Morpheme> elementMorphemeList = new ArrayList<Morpheme>();
		ArrayList<Morpheme> searchMorphemeList = new ArrayList<Morpheme>();
		ArrayList<Phrase> searchPhraseList = Logic.copyPhraseList(phraseList);
		boolean isVerb = false;
		int particlePhraseIndex = -1;
		int particleMorphemeIndex = -1;
		//係り受け関係である
		if(isRelation){
			int searchIndex = phraseList.size() - 2;
			//存在文節に最も近くに係っている助詞の位置特定
			for(int i = searchIndex; i >= 0; i--){
				searchMorphemeList = searchPhraseList.get(i).getMorphemeList();
				for(int j = 0; j < searchMorphemeList.size(); j++){
					Morpheme morpheme = searchMorphemeList.get(j);
					if(isParticle(morpheme)){
						particlePhraseIndex = i;
						particleMorphemeIndex = j;
						break;
					}
				}
				//助詞が見つかった
				if(particlePhraseIndex != -1 && particleMorphemeIndex != -1){ break; }
			}
			//助詞が見つからなかった場合
			if(particlePhraseIndex == -1 && particleMorphemeIndex == -1){
				addMorphemeInList(elementMorphemeList, searchPhraseList);
			}
			//助詞が見つかっていた場合
			else{ addMorphemeInList(elementMorphemeList, searchPhraseList, particlePhraseIndex, particleMorphemeIndex); }
		}
		//係り受け関係でない
		else{ addMorphemeInList(elementMorphemeList, searchPhraseList); }
		if(elementMorphemeList.size() == 0){
			for(Phrase phrase : phraseList){
				System.out.println(phrase.getPhraseText());
				System.out.println(isRelation);
			}
		}
		//最後の形態素が動詞であるか確認
		if(elementMorphemeList.get(elementMorphemeList.size()-1).getPartOfSpeech().equals("動詞")){ isVerb = true; }
		//三つ組要素のテキスト生成
		for(int i = 0; i < elementMorphemeList.size(); i++){
			if(isVerb && i == elementMorphemeList.size() - 1 && elementType == 2){
				text += elementMorphemeList.get(i).getOriginalForm(); //「効果」要素で、最後が動詞だった時
			}else{
				text += elementMorphemeList.get(i).getMorphemeText();
			}
		}
		//element生成&リターン
		text = Logic.cleanWord(text);
		element.setText(text);
		element.setMorphemeList(elementMorphemeList);
		return element;
	}
	
	public static void addMorphemeInList(ArrayList<Morpheme> addList, ArrayList<Phrase> phraseList){
		boolean particleHasAppeared = false;
		for(Phrase phrase : phraseList){
			for(Morpheme morpheme: phrase.getMorphemeList()){
				if(isParticle(morpheme)){
					particleHasAppeared = true;
					break;
				}
				addList.add(morpheme);
			}
			if(particleHasAppeared){ break; }
		}
		if(addList.size() == 0){ addList.add(phraseList.get(0).getMorphemeList().get(0)); }
	}
	
	public static void addMorphemeInList
	(ArrayList<Morpheme> addList, ArrayList<Phrase> phraseList, int particlePhraseIndex, int particleMorphemeIndex){
		int tmp = 0;
		for(int i = particlePhraseIndex; i < phraseList.size(); i++){
			ArrayList<Morpheme> morphemeList = phraseList.get(i).getMorphemeList();
			//形態素の位置更新
			if(i == particlePhraseIndex){ tmp = particleMorphemeIndex; }
			else{ tmp = 0; }
			//リストに追加
			for(int j = tmp; j < morphemeList.size(); j++){
				Morpheme morpheme = morphemeList.get(j);
				if(isParticle(morpheme)){ break; }
				addList.add(morpheme);
			}
		}
		if(addList.size() == 0){ addList.add(phraseList.get(0).getMorphemeList().get(0)); }
	}
	
	public static boolean isParticle(Morpheme morpheme){
		return morpheme.getPartOfSpeech().equals("助詞") & 
				!(morpheme.getOriginalForm().equals("の") || morpheme.getPartOfSpeechDetails().equals("接続助詞"));
	}


	public static Element getOriginalElement(ArrayList<Morpheme> morphemeList, int elementType){
		Element element = new Element();
		String text = "";
		ArrayList<Morpheme> elementMorphemeList = new ArrayList<Morpheme>();
		boolean isVerb = false;
		for(Morpheme morpheme : morphemeList){
			//助詞が出現
			if(morpheme.getPartOfSpeech().equals("助詞")){ break; }
			if(morpheme.getOriginalForm().equals("、") || morpheme.getOriginalForm().equals("。")){ break; }
			if(morpheme.getPartOfSpeech().equals("動詞")){
				isVerb = true;
			}else{
				isVerb = false;
			}
			elementMorphemeList.add(morpheme);
		}
		for(int i = 0; i < elementMorphemeList.size(); i++){
			if(isVerb && i == elementMorphemeList.size() - 1 && elementType == 2){
				text += elementMorphemeList.get(i).getOriginalForm(); //「効果」要素で、最後が動詞だった時
			}else{
				text += elementMorphemeList.get(i).getMorphemeText();
			}
		}
		element.setText(text);
		element.setMorphemeList(elementMorphemeList);
		return element;
	}

	//重複した組を削除
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
			if(sameCount>=2){ tripleSetList.remove(tripleSetListBase.get(i)); }
		}
		return tripleSetList;
	}



}
