package com.pages;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.knife.AppiumEmulator;

public class MyCollectPage {
	AppiumEmulator driver = null;
	public MyCollectPage(AppiumEmulator driver){
		this.driver = driver;
	}

	/**
	 * 查找我收藏夹的测试商品标题
	 * @return
	 */
	public void clickTestProduct(String productName){
		WebElement testProduct = driver.swipeFindElement("partialText", productName, "partialText", "无更多商品");		
		if(testProduct == null){
			Assert.fail("我的收藏里没有【"+productName+"】该商品。");
		}
		testProduct.click();
	
	
	
	}
}
