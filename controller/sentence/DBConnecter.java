package controller.sentence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import model.*;

public class DBConnecter {

	//プロキシ設定
	//private static Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.nagaokaut.ac.jp", 8080));

	public static ArrayList<Record> getRecordList(ArrayList<Integer> idList){

		Connection con = null;
		//Record record = new Record();
		ArrayList<Record> recordlist = new ArrayList<Record>();

		try {
			// JDBCドライバのロード - JDBC4.0（JDK1.6）以降は不要
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// MySQLに接続
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tobyo_db?useSSL=false", "root", "databasetest86");
			System.out.println("MySQLに接続できました。");
			
			for(int id : idList){
				String sql = "select * from tobyo_table_no_double_renumber where id = " + id;
				Record record = new Record();
				Statement stm = con.createStatement();
				System.out.println(sql);
				ResultSet rs = stm.executeQuery(sql);
				
				while(rs.next()){
					Snippet snippet = new Snippet(rs.getString("snippet"));
					record.setId(id);
					record.setSnippet(snippet);
					record.setMedicineName(rs.getString("medicineName"));
					record.setDiseaseName(rs.getString("diseaseName"));
					record.setSex(rs.getString("sex"));
					record.setTitle_blog(rs.getString("title_blog"));
					record.setTitle_blogArticle(rs.getString("title_blogArticle"));
					record.setUrl_blogArticle(rs.getString("url_blogArticle"));
					record.setAge(rs.getString("age"));
					record.setBlogArticle(rs.getString("blogArticle"));
					recordlist.add(record);
				}
			}
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.out.println("JDBCドライバのロードに失敗しました。");
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println("MySQLに接続できませんでした。" + e);
		} finally {
			if (con != null) {
				try {
					con.close();
					System.out.println("MySQLのクローズに成功しました。");
				} catch (SQLException e) {
					System.out.println("MySQLのクローズに失敗しました。");
				}
			}
		}
		return recordlist;
	}

	public static  ArrayList<Record> getRecordList(int startRecordNum, int endRecordNum) throws Exception {

		ArrayList<Record> recordList = new ArrayList<Record>();
		Connection con = null;
		int getRecordNum = 0;
		//boolean[] randomNumArray = new boolean[endRecordNum - startRecordNum];
		//randomNumArray = makeRandomNumArray(recordNum, startRecordNum, endRecordNum);

		try {
			// JDBCドライバのロード - JDBC4.0（JDK1.6）以降は不要
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// MySQLに接続
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tobyo_db?useSSL=false", "root", "databasetest86");
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb?useSSL=false", "root", "databasetest86");
			System.out.println("MySQLに接続できました。");

			//for(int id=startRecordNum; id<endRecordNum; id++){
			//if(!randomNumArray[id-startRecordNum]){ continue; }

			Statement stm = con.createStatement();
			String sql = "";
			Record record;
			Snippet snippet;
			//sql = "select * from tobyo_table_no_double where id = " + id;
			//				sql = "select * from tobyo_table_no_double where id >=" + startRecordNum + " and id <= " + endRecordNum
			//						+ " order by id";
			sql = "select * from tobyo_table_no_double_renumber where id >=" + startRecordNum + " and id <= " + endRecordNum
					+ " order by id";
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);

			while(rs.next()){
				snippet = new Snippet(rs.getString("snippet"));
				record = new Record(rs.getInt("id"), snippet, rs.getString("medicineName"), 
						rs.getString("diseaseName"), rs.getString("sex"),rs.getString("title_blog"),
						rs.getString("title_blogArticle"),rs.getString("url_blogArticle"),
						rs.getString("age"),rs.getString("blogArticle") );
				recordList.add(record);
				getRecordNum++;
			}
			//}


		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.out.println("JDBCドライバのロードに失敗しました。");
		} catch (SQLException e) {
			System.out.println("MySQLに接続できませんでした。" + e);
		} finally {
			if (con != null) {
				try {
					con.close();
					System.out.println("MySQLのクローズに成功しました。");
					System.out.println("取得レコード数は　" + getRecordNum + "　です");
				} catch (SQLException e) {
					System.out.println("MySQLのクローズに失敗しました。");
				}
			}
		}

		return recordList;

	}

	public static boolean[] makeRandomNumArray(int recordNum, int startRange, int endRange){

		boolean[] randomNumArray = new boolean[endRange-startRange];
		Random rand = new Random();

		// すべての重複判定用配列をfalseにしておく
		for(int i=0; i<endRange-startRange; i++){
			randomNumArray[i] = false;
		}

		//要素数回数をループ
		for(int i=0; i < recordNum; ){

			//System.out.println(i);
			int p = rand.nextInt(endRange - startRange);
			//System.out.println(p);
			if(randomNumArray[p] == false){ //まだ使ってない値か判定
				randomNumArray[p] = true; //使った値はtrueにしておく
				i++; //ループ用の値をインクリメント
			}
			//System.out.println(randomNumArray[p]);
		}
		return randomNumArray;
	}


}
