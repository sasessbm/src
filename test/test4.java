package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import model.Sentence;

public class test4 {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		String outputPath = "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\sentenceList10000.dat";
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(outputPath));
		ArrayList<Sentence> sentenceList = (ArrayList<Sentence>) ois.readObject();
		
		for(Sentence sentence : sentenceList){
			if(sentence.getText().contains("ぐらいが")){
				System.out.println(sentence.getText()); 
			}
			
		}

	}

}
