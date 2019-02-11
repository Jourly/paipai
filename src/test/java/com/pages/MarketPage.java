package com.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import com.knife.AppiumEmulator;

public class MarketPage {
	AppiumEmulator driver = null;
	public MarketPage(AppiumEmulator driver){
		this.driver = driver;
	}
	
	/**
	 * 底部的集市按钮
	 * @return WebElement 集市按钮元素
	 */
	public WebElement getMarket(){
		return this.driver.getElement("text", "集市");
	}
	
	/**
	 * 我加入的第一个集市元素，是否存在
	 * @return boolean 存在返回true，不存在返回false
	 */
	public boolean isMyJoinMarket(String firstMarket){
		try{
			this.driver.isElementExist("text", firstMarket);
		}catch(NoSuchElementException e){
			return false;
		}
		return true;
	}
	
}
