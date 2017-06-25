package model;

public class CorrectAnswer {
	
	private int sentenceId;
	private int medicinePhraseId;
	private int targetPhraseId;
	private int effectPhraseId;
	
	public CorrectAnswer(int sentenceId, int medicinePhraseId, int targetPhraseId, int effectPhraseId) {
		this.sentenceId = sentenceId;
		this.medicinePhraseId = medicinePhraseId;
		this.targetPhraseId = targetPhraseId;
		this.effectPhraseId = effectPhraseId;
	}

	public int getSentenceId() {
		return sentenceId;
	}

	public void setSentenceId(int sentenceId) {
		this.sentenceId = sentenceId;
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
}
