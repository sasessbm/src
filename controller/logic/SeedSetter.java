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
//		
		//seedList.add("で");
		//seedList.add("の");
		//seedList.add("は");
		//seedList.add("出る");
		
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
		correctAnswerList.add(new CorrectAnswer(38,1,3,4));
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
		
		correctAnswerList.add(new CorrectAnswer(301,13,15,16));
		correctAnswerList.add(new CorrectAnswer(305,0,2,2));
		correctAnswerList.add(new CorrectAnswer(314,3,5,9));
		correctAnswerList.add(new CorrectAnswer(318,0,5,6));
		correctAnswerList.add(new CorrectAnswer(323,0,2,3));
		correctAnswerList.add(new CorrectAnswer(323,0,5,6));
		correctAnswerList.add(new CorrectAnswer(324,0,2,3));
		correctAnswerList.add(new CorrectAnswer(335,8,10,12));
		correctAnswerList.add(new CorrectAnswer(339,0,3,5));
		correctAnswerList.add(new CorrectAnswer(345,1,4,6));
		correctAnswerList.add(new CorrectAnswer(345,1,5,6));
		correctAnswerList.add(new CorrectAnswer(346,2,6,7));
		correctAnswerList.add(new CorrectAnswer(346,3,6,7));
		
		correctAnswerList.add(new CorrectAnswer(356,4,9,10));
		correctAnswerList.add(new CorrectAnswer(356,5,9,10));
		correctAnswerList.add(new CorrectAnswer(356,6,9,10));
		correctAnswerList.add(new CorrectAnswer(386,5,7,8)); //一応2個
		correctAnswerList.add(new CorrectAnswer(386,5,7,8));
		correctAnswerList.add(new CorrectAnswer(390,3,6,7));
		correctAnswerList.add(new CorrectAnswer(390,3,11,12));
		
		correctAnswerList.add(new CorrectAnswer(408,10,13,14)); //一応2個
		correctAnswerList.add(new CorrectAnswer(408,10,13,14));
		correctAnswerList.add(new CorrectAnswer(412,3,6,7));
		correctAnswerList.add(new CorrectAnswer(417,4,7,9));
		correctAnswerList.add(new CorrectAnswer(418,1,4,5));
		correctAnswerList.add(new CorrectAnswer(421,0,2,3));
		correctAnswerList.add(new CorrectAnswer(427,6,10,12));
		correctAnswerList.add(new CorrectAnswer(427,7,10,12));
		correctAnswerList.add(new CorrectAnswer(427,6,10,16));
		correctAnswerList.add(new CorrectAnswer(427,7,10,16));
		correctAnswerList.add(new CorrectAnswer(438,0,1,2));
		correctAnswerList.add(new CorrectAnswer(439,0,3,5));
		correctAnswerList.add(new CorrectAnswer(443,3,5,6));
		correctAnswerList.add(new CorrectAnswer(444,1,0,2));
		correctAnswerList.add(new CorrectAnswer(448,2,1,6));
		
		correctAnswerList.add(new CorrectAnswer(452,4,5,6));
		correctAnswerList.add(new CorrectAnswer(456,0,6,7));
		correctAnswerList.add(new CorrectAnswer(456,2,6,7));
		correctAnswerList.add(new CorrectAnswer(461,2,4,5));
		correctAnswerList.add(new CorrectAnswer(461,13,16,17));
		correctAnswerList.add(new CorrectAnswer(462,7,2,3));
		correctAnswerList.add(new CorrectAnswer(462,7,4,6));
		correctAnswerList.add(new CorrectAnswer(465,0,4,5));
		correctAnswerList.add(new CorrectAnswer(469,2,7,9));
		correctAnswerList.add(new CorrectAnswer(473,0,0,1));
		correctAnswerList.add(new CorrectAnswer(473,0,2,4));
		correctAnswerList.add(new CorrectAnswer(482,2,1,4));
		correctAnswerList.add(new CorrectAnswer(482,3,1,4));
		correctAnswerList.add(new CorrectAnswer(487,3,1,2));
		correctAnswerList.add(new CorrectAnswer(488,1,4,5));
		correctAnswerList.add(new CorrectAnswer(488,1,8,9));
		correctAnswerList.add(new CorrectAnswer(490,6,7,8));
		correctAnswerList.add(new CorrectAnswer(495,2,0,1));
		
		correctAnswerList.add(new CorrectAnswer(504,0,4,5));
		correctAnswerList.add(new CorrectAnswer(504,0,6,8));
		correctAnswerList.add(new CorrectAnswer(505,3,5,6));
		correctAnswerList.add(new CorrectAnswer(510,0,3,5));
		correctAnswerList.add(new CorrectAnswer(514,3,12,14));
		correctAnswerList.add(new CorrectAnswer(520,10,12,13));
		correctAnswerList.add(new CorrectAnswer(526,0,4,5));
		correctAnswerList.add(new CorrectAnswer(533,1,4,5));
		correctAnswerList.add(new CorrectAnswer(539,10,14,12));
		correctAnswerList.add(new CorrectAnswer(552,6,11,14));
		correctAnswerList.add(new CorrectAnswer(556,5,0,4));
		correctAnswerList.add(new CorrectAnswer(563,3,9,11));
		correctAnswerList.add(new CorrectAnswer(570,0,1,2));
		correctAnswerList.add(new CorrectAnswer(573,0,2,3));
		correctAnswerList.add(new CorrectAnswer(588,0,3,4)); //一応2個
		correctAnswerList.add(new CorrectAnswer(588,0,3,4));
		correctAnswerList.add(new CorrectAnswer(589,0,3,5));
		correctAnswerList.add(new CorrectAnswer(590,0,1,3));
		correctAnswerList.add(new CorrectAnswer(591,0,2,3));
		correctAnswerList.add(new CorrectAnswer(591,0,4,5));
		correctAnswerList.add(new CorrectAnswer(597,0,1,3));
		correctAnswerList.add(new CorrectAnswer(597,0,4,8));
		correctAnswerList.add(new CorrectAnswer(597,0,5,8));
		correctAnswerList.add(new CorrectAnswer(597,0,6,8));
		correctAnswerList.add(new CorrectAnswer(597,0,7,8));
		
		correctAnswerList.add(new CorrectAnswer(600,10,12,13));
		correctAnswerList.add(new CorrectAnswer(601,4,5,6));
		correctAnswerList.add(new CorrectAnswer(604,7,9,10));
		correctAnswerList.add(new CorrectAnswer(618,0,4,5));
		correctAnswerList.add(new CorrectAnswer(621,5,0,8));
		correctAnswerList.add(new CorrectAnswer(621,6,0,8));
		correctAnswerList.add(new CorrectAnswer(622,3,5,7));
		correctAnswerList.add(new CorrectAnswer(625,4,0,3));
		correctAnswerList.add(new CorrectAnswer(625,4,8,10));
		correctAnswerList.add(new CorrectAnswer(625,4,1,3));
		correctAnswerList.add(new CorrectAnswer(632,3,7,8));
		correctAnswerList.add(new CorrectAnswer(635,2,4,6));
		correctAnswerList.add(new CorrectAnswer(640,0,1,3));
		correctAnswerList.add(new CorrectAnswer(647,1,3,4));
		correctAnswerList.add(new CorrectAnswer(649,1,3,6));
		correctAnswerList.add(new CorrectAnswer(649,1,5,6));
		
		correctAnswerList.add(new CorrectAnswer(651,1,6,9));
		correctAnswerList.add(new CorrectAnswer(651,1,7,9));
		correctAnswerList.add(new CorrectAnswer(654,0,9,10));
		correctAnswerList.add(new CorrectAnswer(654,3,9,10));
		correctAnswerList.add(new CorrectAnswer(654,4,9,10)); //一応2個
		correctAnswerList.add(new CorrectAnswer(654,4,9,10));
		correctAnswerList.add(new CorrectAnswer(654,5,9,10)); //一応2個
		correctAnswerList.add(new CorrectAnswer(654,5,9,10));
		correctAnswerList.add(new CorrectAnswer(670,0,2,3));
		correctAnswerList.add(new CorrectAnswer(672,2,6,7));
		correctAnswerList.add(new CorrectAnswer(684,9,11,12));
		
		correctAnswerList.add(new CorrectAnswer(701,0,7,8));
		correctAnswerList.add(new CorrectAnswer(707,2,3,3));
		correctAnswerList.add(new CorrectAnswer(717,1,2,3));
		correctAnswerList.add(new CorrectAnswer(717,1,4,5));
		correctAnswerList.add(new CorrectAnswer(724,0,1,3));
		correctAnswerList.add(new CorrectAnswer(724,0,5,8));
		correctAnswerList.add(new CorrectAnswer(726,0,1,2));
		correctAnswerList.add(new CorrectAnswer(726,0,4,5));
		correctAnswerList.add(new CorrectAnswer(735,6,9,10));
		correctAnswerList.add(new CorrectAnswer(745,4,0,1));
		correctAnswerList.add(new CorrectAnswer(748,9,12,13));
		
		correctAnswerList.add(new CorrectAnswer(751,0,4,5));
		correctAnswerList.add(new CorrectAnswer(753,0,3,4));
		correctAnswerList.add(new CorrectAnswer(753,0,9,10));
		correctAnswerList.add(new CorrectAnswer(755,0,2,3));
		correctAnswerList.add(new CorrectAnswer(756,8,10,10));
		correctAnswerList.add(new CorrectAnswer(758,3,8,9));
		correctAnswerList.add(new CorrectAnswer(758,4,8,9));
		correctAnswerList.add(new CorrectAnswer(758,5,8,9));
		correctAnswerList.add(new CorrectAnswer(760,2,4,4));
		correctAnswerList.add(new CorrectAnswer(768,5,6,7));
		correctAnswerList.add(new CorrectAnswer(769,1,4,5));
		correctAnswerList.add(new CorrectAnswer(769,1,7,8));
		correctAnswerList.add(new CorrectAnswer(771,4,8,9));
		correctAnswerList.add(new CorrectAnswer(772,0,2,3));
		correctAnswerList.add(new CorrectAnswer(774,11,18,19));
		correctAnswerList.add(new CorrectAnswer(775,1,0,2));
		correctAnswerList.add(new CorrectAnswer(780,4,7,11));
		correctAnswerList.add(new CorrectAnswer(785,6,8,9));
		correctAnswerList.add(new CorrectAnswer(796,0,6,7));
		
		correctAnswerList.add(new CorrectAnswer(801,3,4,5));
		correctAnswerList.add(new CorrectAnswer(806,9,11,12));
		correctAnswerList.add(new CorrectAnswer(806,9,17,18));
		correctAnswerList.add(new CorrectAnswer(808,8,10,12));
		correctAnswerList.add(new CorrectAnswer(808,8,11,12));
		correctAnswerList.add(new CorrectAnswer(824,2,4,5));
		correctAnswerList.add(new CorrectAnswer(826,4,0,1));
		correctAnswerList.add(new CorrectAnswer(832,5,8,9));
		correctAnswerList.add(new CorrectAnswer(833,1,6,7));
		correctAnswerList.add(new CorrectAnswer(837,1,4,5));
		correctAnswerList.add(new CorrectAnswer(841,1,5,6));
		
		correctAnswerList.add(new CorrectAnswer(851,2,6,7));
		correctAnswerList.add(new CorrectAnswer(852,1,4,8));
		correctAnswerList.add(new CorrectAnswer(852,1,5,8));
		correctAnswerList.add(new CorrectAnswer(852,1,6,8));
		correctAnswerList.add(new CorrectAnswer(863,1,3,4));
		correctAnswerList.add(new CorrectAnswer(870,5,8,9));
		correctAnswerList.add(new CorrectAnswer(870,6,8,9));
		correctAnswerList.add(new CorrectAnswer(873,0,2,4));
		correctAnswerList.add(new CorrectAnswer(874,13,4,6));
		correctAnswerList.add(new CorrectAnswer(874,13,7,8));
		correctAnswerList.add(new CorrectAnswer(874,13,9,10));
		correctAnswerList.add(new CorrectAnswer(876,13,16,17));
		correctAnswerList.add(new CorrectAnswer(883,2,7,6));
		correctAnswerList.add(new CorrectAnswer(894,3,6,8));
		
		correctAnswerList.add(new CorrectAnswer(900,0,3,3));
		correctAnswerList.add(new CorrectAnswer(928,3,5,6));
		correctAnswerList.add(new CorrectAnswer(930,1,4,5));
		correctAnswerList.add(new CorrectAnswer(930,1,4,9));
		correctAnswerList.add(new CorrectAnswer(932,13,16,17));
		correctAnswerList.add(new CorrectAnswer(937,0,1,2));
		correctAnswerList.add(new CorrectAnswer(948,0,3,4));
		correctAnswerList.add(new CorrectAnswer(949,0,2,3));
		
		correctAnswerList.add(new CorrectAnswer(952,4,8,8));
		correctAnswerList.add(new CorrectAnswer(956,2,1,4));
		correctAnswerList.add(new CorrectAnswer(961,12,9,15));
		correctAnswerList.add(new CorrectAnswer(962,4,9,10));
		correctAnswerList.add(new CorrectAnswer(970,0,6,10));
		correctAnswerList.add(new CorrectAnswer(971,4,1,9));
		correctAnswerList.add(new CorrectAnswer(977,2,4,5));
		correctAnswerList.add(new CorrectAnswer(987,2,4,5));
		correctAnswerList.add(new CorrectAnswer(990,5,6,7));
		
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
