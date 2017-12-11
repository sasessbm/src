package test;

import java.math.BigDecimal;

public class test5 {
	
	public static void main(String[] args) throws Exception{
//		for(double constant = 0 ; constant <= 1.0 ; constant += 0.1){
//			//BigDecimal a = new BigDecimal("0.1");
//			BigDecimal b = new BigDecimal(constant);
//			BigDecimal bd2 = b.setScale(1, BigDecimal.ROUND_HALF_UP);  //小数第２位
//			constant = bd2.doubleValue();
//			System.out.println(constant);
//		}
		
		int a = 2;
		System.out.println(test(a));
	}
	
	public static boolean test(int a){
		
		boolean flag = true;
		boolean rFlag = false;
		
		switch(a){
			case 1:
				if(flag){ rFlag = false; }
				break;
			case 2:
				if(flag){ rFlag = true; }
				break;
			case 3:
				if(flag) { rFlag = false; }
				break;
		}
		
		return rFlag;
	}

}
