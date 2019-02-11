package com.testmvn;

import org.testng.annotations.Test;

import com.knife.AppiumEmulator;
import com.pages.HomePage;
import com.pages.MarketPage;
import com.pages.MyCollectPage;
import com.pages.MyOrderPage;
import com.pages.MyPage;
import com.pages.OrderConfirmPage;
import com.pages.ProductPage;
import com.pages.PublishPage;
import com.util.MyUtil;

import org.testng.annotations.BeforeMethod;
import java.net.MalformedURLException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class TestCase2 {

	AppiumEmulator driver = null;

	@BeforeMethod
	public void beforeMethod() throws MalformedURLException, InterruptedException {
		// 初始化打开微信首页，打开拍拍二手小程序
		driver = new AppiumEmulator("拍拍二手");
	}

	/**
	 * 验证签到是否成功，获得的拍币是否成功
	 * @throws InterruptedException
	 */
	@Test
	public void testHomeSign() throws InterruptedException {
		MyUtil myUtil = new MyUtil();
		int beforeSignCoins = myUtil.getMyCoin();
		HomePage homePage = new HomePage(driver);
		homePage.clickSign();
		Thread.sleep(3000);
		boolean isSignOk = homePage.isSignOK(beforeSignCoins);
		Assert.assertTrue(isSignOk);		
	}
	
//	@Test
	public void testPublishZhimai() throws InterruptedException{
//		HomePage homePage = new HomePage(driver);
//		Thread.sleep(5000);
//		homePage.swipeUp();
		PublishPage publishPage = new PublishPage(driver);
		publishPage.clickPublishXianzhi();
		Thread.sleep(2000);
		publishPage.clickFirstPic();		
		publishPage.setDesc("desc$$$$$");
		publishPage.setPrice("99","100");
		publishPage.setTitle("name1111");
		publishPage.clickBlank();
		Thread.sleep(2000);
		publishPage.clickPublish();	
		Thread.sleep(5000);
		Assert.assertTrue(publishPage.isPublishOK());
	}
	

	
	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
