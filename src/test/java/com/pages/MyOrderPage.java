package com.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import com.knife.AppiumEmulator;

public class MyOrderPage {
	AppiumEmulator driver = null;
	public MyOrderPage(AppiumEmulator driver){
		this.driver = driver;
	}
	
	
	public WebElement myAllorders(){
		return this.driver.getElement("text", "我的订单");
	}
	
	public boolean isToPayExists(){
		return this.driver.isElementExist("text", "去付款");
	}
	
}
