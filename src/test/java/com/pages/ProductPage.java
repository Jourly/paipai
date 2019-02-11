package com.pages;

import org.openqa.selenium.WebElement;

import com.knife.AppiumEmulator;

public class ProductPage {
	AppiumEmulator driver = null;
	public ProductPage(AppiumEmulator driver){
		this.driver = driver;
	}
	
	public WebElement getBuy(){
		return this.driver.getElement("text", "立即买");
	}
}
