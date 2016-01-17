package es.bewom.economy;

import es.bewom.BewomByte;

public class Bank {
	
	public static void addMoney(int m){
		int am = m + getMoney();
		BewomByte.m.executeQuery("UPDATE `bank` SET `BAN_money`='" + am + "'", null);
	}
	
	public static int getMoney(){
		return Integer.parseInt(BewomByte.m.executeQuery("SELECT `BAN_money` FROM `bank`", "BAN_money").get(0));		
	}
	
	public static void substractMoney(int m){
		int am = getMoney() - m;
		BewomByte.m.executeQuery("UPDATE `bank` SET `BAN_money`='" + am + "'", null);
	}

}
