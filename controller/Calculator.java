package controller;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Calculator {

	public static void main(String[] args) {

//		ArrayList<Double> resultList = getResultList(866, 97, 340);
//
//		System.out.println("適合率" + resultList.get(0));
//		System.out.println("再現率" + resultList.get(1));
//		System.out.println("F値" + resultList.get(2));

		//double ans = caluculateCooccurrenceProbability(2,3);
		//double ans = caluculateInformationContent(0.25);

		double ans = 0;

		ArrayList<Integer> candidateCooccurrenceList = new ArrayList<Integer>();
		candidateCooccurrenceList.add(2);
		//candidateCooccurrenceList.add(2);
		//candidateCooccurrenceList.add(1);
		//candidateCooccurrenceList.add(3);
		//candidateCooccurrenceList.add(2);
		//candidateCooccurrenceList.add(2);

		ans = calculateEntropy(candidateCooccurrenceList, 2);



		System.out.println(ans);

	}

	// エントロピー計算
	public static double calculateEntropy(ArrayList<Integer> candidateCooccurrenceList, int candidateTotalNum){

		double cooccurrenceProbability = 0;
		double informationContent = 0;
		double entropy = 0;

		for(int candidateCooccurrenceNum : candidateCooccurrenceList){
			if(candidateCooccurrenceNum == 0){ continue; }
			cooccurrenceProbability = (double)candidateCooccurrenceNum / (double)candidateTotalNum;
			//System.out.println("確率" + cooccurrenceProbability);

			informationContent = cooccurrenceProbability * (Math.log(cooccurrenceProbability) / Math.log(2.0));
			//System.out.println("情報量" + informationContent);

			entropy += informationContent;
			//System.out.println(entropy);
		}

		if(entropy != 0.0){
			entropy = -entropy;
		}
		return roundOff(entropy).doubleValue();
	}

	// 再現率・適合率・F値計算                            　　出力数　　　　　　　　抽出した正解数           本来の正解数
	public static ArrayList<Double> getResultList(int allExtractionNum, int correctExtractionNum, int correctAnswerNum){

		ArrayList<Double> resultList = new ArrayList<Double>();
		
		double precision = 0;
		double recall = (double)correctExtractionNum / (double)correctAnswerNum;
		double fMeasure = 0;
		
		// 1つも三つ組が抽出できなかったら適合率は0
		if(allExtractionNum != 0){
			precision = (double)correctExtractionNum / (double)allExtractionNum;
		}
		
		// 0で割るのを防ぐ
		if((precision + recall) != 0){
			fMeasure = (2 * precision * recall) / (precision + recall);
		}
		
		resultList.add(roundOff(precision * 100).doubleValue());
		resultList.add(roundOff(recall * 100).doubleValue());
		resultList.add(roundOff(fMeasure * 100).doubleValue());

		return resultList;
	}

	// 小数第2位を四捨五入
	public static BigDecimal roundOff(double value){

		BigDecimal originBd = new BigDecimal(value);
		BigDecimal roundOffBd = originBd.setScale(1, BigDecimal.ROUND_HALF_UP);

		return roundOffBd;
	}

}
