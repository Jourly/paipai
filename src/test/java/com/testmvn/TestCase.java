package com.testmvn;

import org.testng.annotations.Test;

import com.knife.AppiumEmulator;
import com.pages.MarketPage;
import com.pages.MyCollectPage;
import com.pages.MyOrderPage;
import com.pages.MyPage;
import com.pages.OrderConfirmPage;
import com.pages.ProductPage;

import org.testng.annotations.BeforeMethod;
import java.net.MalformedURLException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class TestCase {

	AppiumEmulator driver = null;

	@BeforeMethod
	public void beforeMethod() throws MalformedURLException, InterruptedException {
		// 初始化打开微信首页，打开拍拍二手小程序
		driver = new AppiumEmulator("拍拍二手");
	}

	/**
	 * 目的：验证集市首页，我的集市功能模块 
	 * 步骤：
	 *  1.点击我的集市按钮
	 *  2.验证我的第一个集市为'测试'
	 * @throws InterruptedException
	 */
	
	public void testMyMarkets() throws InterruptedException {
		MarketPage marketPage = new MarketPage(driver);
		marketPage.getMarket().click();
		boolean isMyMarket = marketPage.isMyJoinMarket("测试");
		System.out.println("------------" + isMyMarket);
		Assert.assertEquals(isMyMarket, true);
	}

	/**
	 * 目的：验证下单流程
	 * 步骤：
	 *  1.点击我的收藏
	 *  2.查找特定商品标题
	 *  3.进入商祥页
	 *  4.点击立即买按钮
	 *  5.点击提交订单按钮
	 *  6.关掉程序重新进入我的页面
	 *  7.点击我的全部订单
	 *  8.验证去支付按钮存在
	 * @throws MalformedURLException 
	 */
	@Test
	public void testCreateOrder() throws MalformedURLException {
		MyPage myPage = new MyPage(driver);
//		myPage.getMy().click();
//		myPage.getMyCollect().click();
//		MyCollectPage myCollectPage = new MyCollectPage(driver);
//		myCollectPage.clickTestProduct("test");
//		ProductPage productPage = new ProductPage(driver);
//		productPage.getBuy().click();
//		OrderConfirmPage orderConfirmPage = new OrderConfirmPage(driver);
//		orderConfirmPage.getPlaceOrder().click();
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		this.driver.quit();
//		driver = new AppiumEmulator("拍拍二手");
		myPage.getMy().click();
		myPage.getMyAllorders().click();
		MyOrderPage myOrderPage= new MyOrderPage(driver);
		Assert.assertTrue(myOrderPage.isToPayExists());
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
