package com.ssipmt.passmgr;

import java.util.Random;

public class PMUtil {
	static Random r1 = new Random();
public static String enc(String s1){
	s1 = (char)getRChar() + s1;
	s1 = (char)getRChar() + s1;
	s1 = s1 + (char)getRChar();
	s1 = s1 + (char)getRChar();
	String res = "";
	int shift = s1.length()%4 +1;
	for(int i = 0; i<s1.length(); i++){
		int ch = s1.charAt(i);
		ch = ch +shift;
		res = res + (char)ch;
	}
	return res;
}
public static String dec(String s1){
	String res = "";
	int shift = s1.length()%4 +1;
	for(int i = 0; i<s1.length(); i++){
		int ch = s1.charAt(i);
		ch = ch - shift;
		res = res + (char)ch;
	}
	res = res.substring(2, res.length()-2);
	return res;
}

public static int getRChar(){
	int ch= 0;
	ch = r1.nextInt(32) + 65;
	return ch;
}
/*
public static Date parseDate(String strDate){
	Date sqlDate = null;
	try {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		java.util.Date utilDate = sdf.parse(strDate);
		sqlDate = new Date(utilDate.getTime());		
	} catch (Exception e) {
	}
	return sqlDate;

}
*/
}
