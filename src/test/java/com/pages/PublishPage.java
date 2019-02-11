package com.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.knife.AppiumEmulator;
import com.util.MyUtil;

public class PublishPage {
	AppiumEmulator driver = null;

	public PublishPage(AppiumEmulator driver) {
		this.driver = driver;
	}

	public void clickSell() {
		driver.getElement("text", "卖闲置").click();
	}

	public void clickPublishXianzhi() {
		driver.getElement("text", "发布闲置").click();
	}

	public void setTitle(String title) {
		driver.getElement("partialText", "商品标题").sendKeys(title);
	}

	public void setDesc(String desc) {
		driver.getElement("text", "详细描述一下商品的新旧程度、使用情况及出售原因吧～").sendKeys(desc);
	}

	// 上传相册的第一张图片
	public void clickFirstPic() throws InterruptedException {
		Thread.sleep(1000);
		driver.tap(100, 800);
		String device = "oppo";
		if (device.equals("oppo")) {
			driver.tap(492, 250);
			driver.tap(950, 150);
			Thread.sleep(1000);
		} else {
			driver.getElement("class", "android.widget.CheckBox").click();
			driver.getElement("partialText", "完成").click();
		}
	}

	public void setPrice(String price, String oprice) {
		List<WebElement> lists = driver.getElements("partialText", "0.00");
		for (WebElement list : lists) {
			System.out.println(list);
		}
		lists.get(1).sendKeys(oprice);
		lists.get(0).sendKeys(price);
	}

	public void clickPublish() {
		driver.swipeDown();
		driver.getElement("text", "确定发布").click();
	}

	public void clickBlank() {
		driver.tap(700, 700);
	}
	
	public boolean isPublishOK(){
		return driver.isElementExist("partialText", "name1111");
	}
}
