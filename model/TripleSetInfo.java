package model;

public class TripleSetInfo {
	
	private int sentenceId;
	private String sentenceText;
	private int medicinePhraseId;
	private int targetPhraseId;
	//private ArrayList<Integer> targetPhraseIdList;
	private int effectPhraseId;
	private String usedKeyWord;
	
	
	
//	public TripleSetInfo2() {
//		medicinePhraseId = -1;
//		//targetPhraseId = -1;
//		targetPhraseIdList = new ArrayList<Integer>();
//		effectPhraseId = -1;
//		usedKeyWord = "";
//	}
	
	public TripleSetInfo(int sentenceId, String sentenceText, int medicinePhraseId,
			int targetPhraseId, int effectPhraseId, String usedKeyWord) {
		this.sentenceId = sentenceId;
		this.sentenceText = sentenceText;
		this.medicinePhraseId = medicinePhraseId;
		this.targetPhraseId = targetPhraseId;
		this.effectPhraseId = effectPhraseId;
		this.usedKeyWord = usedKeyWord;
	}

	public int getSentenceId() {
		return sentenceId;
	}

	public void setSentenceId(int sentenceId) {
		this.sentenceId = sentenceId;
	}
	
	

	
//	public ArrayList<Integer> getTargetPhraseIdList() {
//		return targetPhraseIdList;
//	}
//
//	public void setTargetPhraseIdList(ArrayList<Integer> targetPhraseIdList) {
//		this.targetPhraseIdList = targetPhraseIdList;
//	}

	public String getSentenceText() {
		return sentenceText;
	}

	public void setSentenceText(String sentenceText) {
		this.sentenceText = sentenceText;
	}

	public int getMedicinePhraseId() {
		return medicinePhraseId;
	}
	
	public void setMedicinePhraseId(int medicinePhraseId) {
		this.medicinePhraseId = medicinePhraseId;
	}
	
	public int getTargetPhraseId() {
		return targetPhraseId;
	}
	
	public void setTargetPhraseId(int targetPhraseId) {
		this.targetPhraseId = targetPhraseId;
	}
	
	public int getEffectPhraseId() {
		return effectPhraseId;
	}
	
	public void setEffectPhraseId(int effectPhraseId) {
		this.effectPhraseId = effectPhraseId;
	}
	
	public String getUsedKeyWord(){
		return usedKeyWord;
	}
	
	public void setUsedKeyWord(String usedKeyWord){
		this.usedKeyWord = usedKeyWord;
	}

}
