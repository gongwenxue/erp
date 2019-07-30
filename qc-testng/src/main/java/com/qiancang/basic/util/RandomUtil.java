package com.qiancang.basic.util;

public class RandomUtil {
	
	private static String globlestring ="0123456789";
//	private String globlestring ="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static int getRandom(int count) {
		return (int) Math.round(Math.random()* (count));
	}
	
	public static  String getRandomString(int length) {
		StringBuffer sb = new StringBuffer(); 
		int len = globlestring.length();
		for (int i = 0; i <length; i++) {
			sb.append(globlestring.charAt(getRandom(len -1)));
		} 
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		RandomUtil ruRandomUtil = new RandomUtil(); 
		for (int i = 0; i < 10; i++)
		{ 
			System.out.println(ruRandomUtil.getRandomString(5));
		} 
	}
}
