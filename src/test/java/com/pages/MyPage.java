package com.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import com.knife.AppiumEmulator;

public class MyPage {
	AppiumEmulator driver = null;
	public MyPage(AppiumEmulator driver){
		this.driver = driver;
	}
	
	public WebElement getMy(){
		return this.driver.getElement("text", "我的");
	}
	public void clickMy(){
		this.driver.getElement("text", "我的").click();;
	}
	
	/**
	 * 我的页面>我的收藏按钮
	 * @return WebElement 我的收藏元素
	 */
	public WebElement getMyCollect(){
		return this.driver.getElement("text", "我的收藏");
	}

	/**
	 * 我的页面，我所有的订单入口
	 * @return 我的所有订单入口元素
	 */
	public WebElement getMyAllorders(){
		return this.driver.getElement("text", "我的订单");
	}
	
	public String getMyCoins(){
		WebElement myConinsE = driver.getElement("xpath", "/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout[1]/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout[1]/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.FrameLayout[1]/android.webkit.WebView/android.view.View[6]/android.view.View[1]");
		String myCoins = myConinsE.getAttribute("text");
		System.out.println(myCoins);
		return myCoins;
	}
	
}
