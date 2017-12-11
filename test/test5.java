package test;

import java.math.BigDecimal;

public class test5 {
	
	public static void main(String[] args) throws Exception{
		for(double constant = 0 ; constant <= 1.0 ; constant += 0.1){
			//BigDecimal a = new BigDecimal("0.1");
			BigDecimal b = new BigDecimal(constant);
			BigDecimal bd2 = b.setScale(1, BigDecimal.ROUND_HALF_UP);  //小数第２位
			constant = bd2.doubleValue();
			System.out.println(constant);
		}
	}
	
	

}
