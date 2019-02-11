package com.testmvn;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String text = "您已领取 14 拍币";

		
		String[] texts = text.split(" ");
		if (texts.length == 3) {
			System.out.println(Integer.parseInt(texts[1]));
		} else {
			System.out.println(1);
		}
	}

}
