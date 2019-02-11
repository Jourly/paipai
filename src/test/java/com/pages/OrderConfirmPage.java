package com.pages;

import org.openqa.selenium.WebElement;

import com.knife.AppiumEmulator;

public class OrderConfirmPage {
	AppiumEmulator driver = null;
	public OrderConfirmPage(AppiumEmulator driver){
		this.driver = driver;
	}
	
	/**
	 * 提交订单按钮
	 * @return
	 */
	public WebElement getPlaceOrder(){
		return this.driver.getElement("text", "提交订单");
	}
}
