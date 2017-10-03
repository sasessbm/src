package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CaboChaTest {

	public static void main(String[] args) {

		Run("カペシタビン単体では髪の毛が抜けたり吐き気がしたりといった副作用がなかったのにエンドキサンを飲み始めてから食欲がなくなり髪の毛が抜けて白血球数も減少");
	}

	//String型で文字列を受け取り、それを係り受け解析する関数
	public static ArrayList<String> Run(String text){
		
		ArrayList<String> xmlList = new ArrayList<String>();
		//String xmlText = "";
		
		try {

			//UTF-8のBOMを除去するための準備←textファイルから読み込む場合を考慮
			byte [] bytes = {-17, -69, -65};
			String btmp= new String(bytes, "UTF-8");

			//BOM除去
			text=text.replaceAll(btmp, "");

			//cabochaの実行開始　ラティス形式で出力(-f3の部分で決定、詳しくはcabochaのhelp参照)
			//ProcessBuilder pb = new ProcessBuilder("cabocha", "-f3");
			ProcessBuilder pb = new ProcessBuilder("cabocha");
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
				//out.add(Line);
				xmlList.add(Line);

				//行をコンソールへ表示
				//xmlText += Line;
				System.out.println(Line);
				
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
