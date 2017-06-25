package model;

public class Morpheme {
	
	private int id;
	private String morphemeText;
	private String feature;
	private String partOfSpeech;
	private String partOfSpeechDetails;
	private String originalForm;
	
	
	//コンストラクタ
	public Morpheme(int id, String morphemeText, String feature) {
		this.id = id;
		this.morphemeText = morphemeText;
		this.feature = feature;
		String[] featureArray = feature.split(",");
		this.partOfSpeech = featureArray[0];
		this.partOfSpeechDetails = featureArray[1];
		this.originalForm = featureArray[6];
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMorphemeText(){
		return morphemeText;
	}
	
	public void setMorphemeText(String morphemeText){
		this.morphemeText = morphemeText;
	}
	
	public String getFeature(){
		return feature;
	}
	
	public void setFeature(String feature){
		this.feature = feature;
	}


	public String getPartOfSpeech() {
		return partOfSpeech;
	}


	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}


	public String getPartOfSpeechDetails() {
		return partOfSpeechDetails;
	}


	public void setPartOfSpeechDetails(String partOfSpeechDetails) {
		this.partOfSpeechDetails = partOfSpeechDetails;
	}
	
	public String getOriginalForm() {
		return originalForm;
	}

	public void setOriginalForm(String originalForm) {
		this.originalForm = originalForm;
	}
	
}
