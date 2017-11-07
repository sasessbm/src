package controller.sentence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class SyntaxAnalyzer {

	//String型で文字列を受け取り、係り受け解析する関数
	public static ArrayList<String> GetSyntaxAnalysResultXml(String text){

		ArrayList<String> xmlList = new ArrayList<String>();

		try {

			//UTF-8のBOMを除去するための準備←textファイルから読み込む場合を考慮
			byte [] bytes = {-17, -69, -65};
			String btmp= new String(bytes, "UTF-8");

			//BOM除去
			text=text.replaceAll(btmp, "");

			//cabochaの実行開始　ラティス形式で出力(-f1の部分で決定、詳しくはcabochaのhelp参照)
			ProcessBuilder pb = new ProcessBuilder("cabocha", "-f3");
			//ProcessBuilder pb = new ProcessBuilder("cabocha", "-f1");
			Process process = pb.start();

			//実行途中で文字列を入力(コマンドプロンプトで文字を入力する操作)
			OutputStreamWriter osw = new OutputStreamWriter(process.getOutputStream(), "UTF-8");
			osw.write(text);
			osw.close();

			//出力結果を読み込む
			InputStream is = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

			//一行ずつ読み込むための文字列の変数を用意
			String Line="";

			//出力結果の各行毎に配列へ格納するためのリストを用意
			//ArrayList out = new ArrayList();

			//最後の行までやり続ける
			while ((Line = br.readLine()) != null) {
				
				//読み込んだ行をリストへ格納
				xmlList.add(Line);

			}
			//プロセス終了
			process.destroy();
			process.waitFor();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return xmlList;
	}

}
