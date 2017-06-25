package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FileOperator {

	// ファイル書き込み
	public static void writeTextInFile(String text, String filePath, boolean isAdditional) 
			throws UnsupportedEncodingException, FileNotFoundException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter
				(new FileOutputStream(filePath,isAdditional),"utf-8")));
		pw.println(text);
		pw.close();
	}
	
	// ファイル読み込み
	public static ArrayList<String> fileRead(String filePath) {
		ArrayList<String> textFileContentsList = new ArrayList<String>(); 
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				textFileContentsList.add(line);
				//System.out.println(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return textFileContentsList;
	}

}
