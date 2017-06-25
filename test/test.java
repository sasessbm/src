package test;

import java.util.ArrayList;

public class test {

	public static void main(String[] args) {
		String text = "4月から眠れない日がずっと、続いて";
		ArrayList<String> xmlList = SyntaxAnalys.GetSyntaxAnalysResultXml(text);
		
		for(String str : xmlList){
			System.out.println(str);
		}

	}

}
