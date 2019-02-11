package com.pages;

import com.knife.AppiumEmulator;
import com.util.MyUtil;

public class HomePage {
	AppiumEmulator driver = null;

	public HomePage(AppiumEmulator driver) {
		this.driver = driver;
	}

	public void clickSign() {
		driver.getElement("text", "签到").click();
	}

	public void clickShareSign() {
		driver.getElement("partialText", "分享好友").click();
	}

	public void clickBack() {
		driver.getElement("aid", "返回").click();
	}
	
	/**
	 * 获取今日签到获得的拍币
	 * @return
	 */
	public int getSignCoin() {
		String text = driver.getElement("partialText", "您已领取").getAttribute("text");		
		String[] texts = text.split(" ");
		if (texts.length == 3) {
			return Integer.parseInt(texts[1]);
		} else {
			return -1;
		}
	}

	/**
	 * -->传入签到前的总拍币
	 * 1.如果今天签到获得的拍币获取不到，直接返回失败。
	 * 2.如果今天签到的拍币获取到了：
	 * 	2-1:如果是第一次点击签到，则点击分享按钮，再验证总拍币=今签到前总金额+今日奖励金额+3
	 * 	2-2:如果不是第一次当日签到,则可以获取到今日已签到的标签，返回True
	 * @param beforeSignCoin
	 * @return 是否签到成功
	 */
	@SuppressWarnings("finally")
	public boolean isSignOK(int beforeSignCoin) {
		boolean result = false;
		int signCoin = this.getSignCoin();
		if(signCoin <=0){
			return false;
		}
		System.out.println("today sign get coin:"+signCoin);
		try {
			boolean firstSign = driver.isElementExist("text", "签到成功");
			boolean afterShare = false;
			if (firstSign) {
				this.clickShareSign();
				this.clickBack();
				MyUtil myUtil = new MyUtil();
				int afterSignCoin = myUtil.getMyCoin();
				if ((beforeSignCoin + this.getSignCoin() + 3) == afterSignCoin) {
					result= true;
				}
			} else {
				afterShare = driver.isElementExist("text", "今日已签到");
				if ((firstSign || afterShare) == true) {
					result = true;
				} else {
					result =  false;
				}
			}
		} catch (Exception e) {
			result =  false;
		}	finally{
			return result;
		}
	}
	
	public void swipeUp(){
		driver.swipeUpLittle();
	}
	
	public void tap(int x,int y){
		driver.tap(x, y);
	}

}
