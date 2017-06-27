package controller.logic;

import java.util.ArrayList;

import controller.keyword.Transformation;
import model.*;

public class SeedSetter {

	public static ArrayList<String> getKeyWordSeedList(){

		ArrayList<String> seedList = new ArrayList<String>();

		seedList.add("副作用");
		seedList.add("おかげ");
		seedList.add("せい");
		seedList.add("影響");
		seedList.add("戻す");
		seedList.add("入る");
		seedList.add("飲む");
		seedList.add("始める");
		seedList.add("増える");
		seedList.add("打つ");
		seedList.add("変える");
		seedList.add("試す");
		seedList.add("使用");
		seedList.add("服用");
		seedList.add("注入");
		seedList.add("投与");
		seedList.add("変更");
		seedList.add("切る");
		seedList.add("止める");
		seedList.add("やる");
		seedList.add("使う");
		seedList.add("減らす");
		seedList.add("処方");
		seedList.add("増量");
		seedList.add("減量");
		seedList.add("注射");
		seedList.add("吸入");
		seedList.add("開始");
		seedList.add("点滴");
		seedList.add("終了");
		seedList.add("後");
		seedList.add("内服");
		seedList.add("お陰");
		seedList.add("お蔭");
		seedList.add("所為");
		seedList.add("追加");
		seedList.add("変わる");
		seedList.add("残る");
		seedList.add("塗る");
		seedList.add("のむ");
		seedList.add("治療");
		seedList.add("中止");
		seedList.add("復活");
		seedList.add("抜ける");
		seedList.add("食べる");
		seedList.add("最後");
		seedList.add("なる");
		seedList.add("効く");
		seedList.add("再開");
		seedList.add("増やす");
		seedList.add("する");
		seedList.add("落ちる");
		seedList.add("併用");
		seedList.add("飲用");
		seedList.add("止まる");
		seedList.add("やめる");
		seedList.add("mg");
		seedList.add("なくなる");
		
		seedList.add("で");
		seedList.add("の");
		seedList.add("は");
		
		return seedList;


	}
	
	public static ArrayList<String> getTestKeyWordSeedList(){
		
		ArrayList<String> seedList = new ArrayList<String>();
		
		seedList.add("で");
		seedList.add("によって");
		seedList.add("は");
		//seedList.add("副作用");
		//seedList.add("せい");
		//seedList.add("減る");
		//seedList.add("調整");
		
		return seedList;
		
	}
	
	
	// 正解データ取得用関数
	public static ArrayList<CorrectAnswer> getCorrectAnswerList(){
		
		ArrayList<CorrectAnswer> correctAnswerList = new ArrayList<CorrectAnswer>();
		
		correctAnswerList.add(new CorrectAnswer(3,0,1,3));
		correctAnswerList.add(new CorrectAnswer(7,0,1,2));
		
		correctAnswerList.add(new CorrectAnswer(11,2,5,6));
		correctAnswerList.add(new CorrectAnswer(14,6,8,9));
		
		correctAnswerList.add(new CorrectAnswer(20,3,4,5));
		
		correctAnswerList.add(new CorrectAnswer(32,3,10,12));
		correctAnswerList.add(new CorrectAnswer(38,1,2,4));
		correctAnswerList.add(new CorrectAnswer(39,0,5,2));
		correctAnswerList.add(new CorrectAnswer(39,0,7,8));
		correctAnswerList.add(new CorrectAnswer(39,0,7,9));
		
		correctAnswerList.add(new CorrectAnswer(41,3,5,6));
		correctAnswerList.add(new CorrectAnswer(41,3,5,9));
		correctAnswerList.add(new CorrectAnswer(43,5,9,10));
		
		correctAnswerList.add(new CorrectAnswer(50,0,4,5));
		correctAnswerList.add(new CorrectAnswer(50,1,4,5));
		correctAnswerList.add(new CorrectAnswer(56,4,8,9));
		
		correctAnswerList.add(new CorrectAnswer(64,7,3,4));
		correctAnswerList.add(new CorrectAnswer(64,7,3,5));
		correctAnswerList.add(new CorrectAnswer(64,8,3,4));
		correctAnswerList.add(new CorrectAnswer(64,8,3,5));
		correctAnswerList.add(new CorrectAnswer(64,7,9,9));
		correctAnswerList.add(new CorrectAnswer(64,8,9,9));
		
		correctAnswerList.add(new CorrectAnswer(70,4,9,8));
		correctAnswerList.add(new CorrectAnswer(71,7,9,10));
		correctAnswerList.add(new CorrectAnswer(71,7,11,12));
		correctAnswerList.add(new CorrectAnswer(71,7,13,14));
		
		correctAnswerList.add(new CorrectAnswer(92,0,4,7));
		correctAnswerList.add(new CorrectAnswer(93,7,17,18));
		correctAnswerList.add(new CorrectAnswer(93,8,17,18));
		
		correctAnswerList.add(new CorrectAnswer(116,0,1,3));
		correctAnswerList.add(new CorrectAnswer(116,0,8,9));
		
		correctAnswerList.add(new CorrectAnswer(120,7,3,10));
		
		correctAnswerList.add(new CorrectAnswer(141,1,3,4));
		correctAnswerList.add(new CorrectAnswer(147,0,3,4));
		correctAnswerList.add(new CorrectAnswer(149,2,0,4));
		
		correctAnswerList.add(new CorrectAnswer(151,7,26,27));
		correctAnswerList.add(new CorrectAnswer(151,10,26,27));
		correctAnswerList.add(new CorrectAnswer(151,7,28,29));
		correctAnswerList.add(new CorrectAnswer(151,10,28,29));
		correctAnswerList.add(new CorrectAnswer(153,0,2,3));
		correctAnswerList.add(new CorrectAnswer(154,0,5,7));
		correctAnswerList.add(new CorrectAnswer(159,8,0,11));
		
		correctAnswerList.add(new CorrectAnswer(160,10,17,18));
		correctAnswerList.add(new CorrectAnswer(161,7,4,10));
		
		correctAnswerList.add(new CorrectAnswer(171,4,7,8));
		
		correctAnswerList.add(new CorrectAnswer(180,4,10,11));
		correctAnswerList.add(new CorrectAnswer(183,0,3,5));
		correctAnswerList.add(new CorrectAnswer(183,0,4,5));
		correctAnswerList.add(new CorrectAnswer(183,0,7,8));
		correctAnswerList.add(new CorrectAnswer(186,0,4,5));
		correctAnswerList.add(new CorrectAnswer(186,0,6,8));
		correctAnswerList.add(new CorrectAnswer(187,1,4,4));
		correctAnswerList.add(new CorrectAnswer(187,1,8,10));
		correctAnswerList.add(new CorrectAnswer(188,0,1,2));
		
		correctAnswerList.add(new CorrectAnswer(190,2,5,6));
		correctAnswerList.add(new CorrectAnswer(191,0,3,4));
		correctAnswerList.add(new CorrectAnswer(195,5,11,12));
		correctAnswerList.add(new CorrectAnswer(195,7,11,12));
		correctAnswerList.add(new CorrectAnswer(197,0,4,4));
		
		correctAnswerList.add(new CorrectAnswer(212,8,6,7));
		correctAnswerList.add(new CorrectAnswer(213,4,5,5));
		correctAnswerList.add(new CorrectAnswer(213,4,6,8));
		correctAnswerList.add(new CorrectAnswer(214,1,0,8));
		correctAnswerList.add(new CorrectAnswer(216,0,2,3));
		
		correctAnswerList.add(new CorrectAnswer(220,3,1,2));
		correctAnswerList.add(new CorrectAnswer(221,2,5,6));
		correctAnswerList.add(new CorrectAnswer(221,2,12,13));
		correctAnswerList.add(new CorrectAnswer(225,9,15,16));
		correctAnswerList.add(new CorrectAnswer(226,5,8,10));
		correctAnswerList.add(new CorrectAnswer(226,5,9,10));
		
		correctAnswerList.add(new CorrectAnswer(232,0,2,3));
		correctAnswerList.add(new CorrectAnswer(232,0,4,6));
		correctAnswerList.add(new CorrectAnswer(233,0,2,5));
		correctAnswerList.add(new CorrectAnswer(233,0,3,5));
		correctAnswerList.add(new CorrectAnswer(233,0,6,8));
		correctAnswerList.add(new CorrectAnswer(234,1,2,3));
		correctAnswerList.add(new CorrectAnswer(236,0,4,6));
		
		correctAnswerList.add(new CorrectAnswer(241,0,2,3));
		correctAnswerList.add(new CorrectAnswer(245,7,9,10));
		correctAnswerList.add(new CorrectAnswer(246,7,8,9));
		correctAnswerList.add(new CorrectAnswer(248,0,1,2));
		correctAnswerList.add(new CorrectAnswer(248,5,6,7));
		
		correctAnswerList.add(new CorrectAnswer(251,2,7,8));
		correctAnswerList.add(new CorrectAnswer(252,1,4,6));
		correctAnswerList.add(new CorrectAnswer(253,10,14,15));
		correctAnswerList.add(new CorrectAnswer(255,3,9,10));
		correctAnswerList.add(new CorrectAnswer(257,0,3,4));
		correctAnswerList.add(new CorrectAnswer(257,0,3,5));
		
		correctAnswerList.add(new CorrectAnswer(262,3,6,7));
		correctAnswerList.add(new CorrectAnswer(263,1,3,4));
		correctAnswerList.add(new CorrectAnswer(267,2,5,6));
		correctAnswerList.add(new CorrectAnswer(268,15,17,18));
		
		correctAnswerList.add(new CorrectAnswer(270,0,2,4));
		correctAnswerList.add(new CorrectAnswer(270,0,5,7));
		correctAnswerList.add(new CorrectAnswer(271,2,6,7));
		correctAnswerList.add(new CorrectAnswer(277,3,5,6));
		correctAnswerList.add(new CorrectAnswer(279,5,9,10));
		
		correctAnswerList.add(new CorrectAnswer(280,4,2,7));
		correctAnswerList.add(new CorrectAnswer(280,4,3,7));
		correctAnswerList.add(new CorrectAnswer(280,4,9,10));
		correctAnswerList.add(new CorrectAnswer(284,1,3,4));
		correctAnswerList.add(new CorrectAnswer(285,6,3,4));
		
		correctAnswerList.add(new CorrectAnswer(290,7,9,10));
		correctAnswerList.add(new CorrectAnswer(298,3,5,6));
		correctAnswerList.add(new CorrectAnswer(298,3,10,12));
		correctAnswerList.add(new CorrectAnswer(299,10,5,6));
		
		return correctAnswerList;
	}
	
	

//	public static ArrayList<TripleSet> getTripleSetSeedList(){
//
//		ArrayList<String> seedTextList = new ArrayList<String>();
//
//		//		seedTextList.add("わかっ,硝子体内混濁");
//		//		seedTextList.add("効き目,ある");
//		//		seedTextList.add("生理,止まる");
//		//		seedTextList.add("カルボプラチン,望んだのです");
//		//		seedTextList.add("副作用,観察する");
//		//		seedTextList.add("TJ療法,する");
//		//		seedTextList.add("アバスチンの説明書,もらったのです");
//		//		seedTextList.add("こと,整理できた");
//		//		seedTextList.add("組み合わせ,あ");
//		//		seedTextList.add("クリーム,塗る");
//		//		seedTextList.add("効果,得られる");
//		//		seedTextList.add("今のところ,続ける");
//		//		seedTextList.add("ブロ友さんの記事で,～");
//		//		seedTextList.add("鼻,かみすぎる");
//		//		seedTextList.add("6本の点滴,終わるの");
//		//		seedTextList.add("新生血管の瘢痕跡,見えなくなる");
//		//		seedTextList.add("血,止まりにくい");
//		//		seedTextList.add("CT,確認できない");
//		//		seedTextList.add("CEA,上がる");
//		//		seedTextList.add("壊死,消える");
//		//		seedTextList.add("異常,きたす");
//		//		seedTextList.add("２か月？の間隔,空ける");
//		//		seedTextList.add("今回,効く");
//		//		seedTextList.add("鼻血,出す");
//		//		seedTextList.add("血圧,上がりやすくなる");
//		//		seedTextList.add("副作用,だす");
//		//		seedTextList.add("高血圧,ある");
//		//		seedTextList.add("血圧上昇,ある");
//
//		seedTextList.add("生理,止まる");
//		seedTextList.add("血,止まりにくい");
//		seedTextList.add("壊死,消える");
//		seedTextList.add("鼻血,出す");
//		seedTextList.add("血圧,上がりやすくなる");
//		seedTextList.add("高血圧,ある");
//		seedTextList.add("血圧上昇,ある");
//		seedTextList.add("胃潰瘍,出来る");
//		seedTextList.add("血圧,高くなる");
//		seedTextList.add("左足の動き,改善する");
//		seedTextList.add("塞栓,ある");
//		seedTextList.add("生存期間,延長する");
//		seedTextList.add("出血,ある");
//		seedTextList.add("鼻血,酷い");
//		seedTextList.add("高血圧,あります");
//		seedTextList.add("転移性の乳癌,消滅した");
//		seedTextList.add("高血圧,あるのです");
//		seedTextList.add("肌荒れ,酷くなる");
//		seedTextList.add("便秘,酷くなる");
//		seedTextList.add("便秘,ある");
//		seedTextList.add("調子,悪くなる");
//		seedTextList.add("効果,なかったようだ");
//		seedTextList.add("調子,よくなりました");
//		seedTextList.add("蕁麻疹,ありました");
//		seedTextList.add("気持ち,落ち着かせる");
//
//		return Transformation.stringToTripleSet(seedTextList);
//	}



}
