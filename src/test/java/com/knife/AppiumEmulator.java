/*
 * Copyright (c) 2016-2017 Knife, Inc. and other contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.knife;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

/**
 * knife.appiumEmulator is based on Selenium3 and adds some enhancements
 *
 * @author bugmaster
 */
public class AppiumEmulator {

	static AndroidDriver driver;
	ChromeDriverService chromeServer;
	JavascriptExecutor javaScriptExecutor;

	int timeout = Integer.parseInt(GlobalSettings.timeout);
	int pageLoadMaxTime = Integer.parseInt(GlobalSettings.pageLoadMaxTime);

	public static WebDriver getWebDriver() {
		return driver;
	}

	/**
	 * 初始化启动微信页面
	 */
	public AppiumEmulator() throws MalformedURLException {
		// 手机配置
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "37KRX18C21012218");
		capabilities.setCapability("automationName", "Appium");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "8.0.0");
		capabilities.setCapability("appPackage", "com.tencent.mm");
		capabilities.setCapability("appActivity", "com.tencent.mm.ui.LauncherUI");
		capabilities.setCapability("sessionOverride", true);
		capabilities.setCapability("noReset", true);
		// 解决中文输入问题
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("resetKeyboard", true);
		// 启动app
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		
	}

	/**
	 * 初始化启动微信页面，并下滑打开指定小程序
	 */
	public AppiumEmulator(String miniProgramName) throws MalformedURLException {
		// 手机配置
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "37KRX18C21012218");
		capabilities.setCapability("automationName", "Appium");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "8.0.0");
		capabilities.setCapability("appPackage", "com.tencent.mm");
		capabilities.setCapability("appActivity", "com.tencent.mm.ui.LauncherUI");
		capabilities.setCapability("sessionOverride", true);
		capabilities.setCapability("noReset", true);
		// 解决中文输入问题
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("resetKeyboard", true);
		// 启动app
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//启动微信页面后，暂停3s钟，不然下滑不起作用
		this.swipeDown();
		this.getElement("xpath", "//android.widget.TextView[contains(@text,'" + miniProgramName + "')]").click();
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
//		//因为首页首次打开会有弹窗，需要下滑一下使弹窗消失
//		this.swipeDown();
	}

	/**
	 * Analyzing targeting element, and positioning element
	 *
	 * @param by,value
	 *            the element's
	 */
	public WebElement getElement(String by, String value) {
		this.waitElement(by, value, 30);
		if (by.equals("id")) {
			WebElement element = driver.findElement(By.id(value));
			return element;
		} else if (by.equals("name")) {
			WebElement element = driver.findElement(By.name(value));
			return element;
		} else if (by.equals("class")) {
			WebElement element = driver.findElement(By.className(value));
			return element;
		} else if (by.equals("xpath")) {
			WebElement element = driver.findElement(By.xpath(value));
			return element;
		} else if (by.equals("css")) {
			WebElement element = driver.findElement(By.cssSelector(value));
			return element;
		} else if (by.equals("aid")) {
			// 通过AccessibilityId（content-desc）
			WebElement element = driver.findElementByAccessibilityId(value);
			return element;
		} else if (by.equals("text")) {
			WebElement element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + value + "\")");
			return element;
		} else if (by.equals("partialText")) {
			WebElement element = driver
					.findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + value + "\")");
			return element;
		} else {
			Assert.fail(
					"Please enter the correct targeting elements,'id','name','class','xpath','css','aid','text','partialText'.");
		}
		return null;

	}

	/**
	 * Analyzing targeting elements, and positioning elements
	 * @param by,value
	 * @return a list of elements
	 */
	public List<WebElement> getElements(String by, String value) {
		this.waitElement(by, value, 60);
		if (by.equals("id")) {
			List<WebElement> elements = driver.findElements(By.id(value));
			return elements;
		} else if (by.equals("name")) {
			List<WebElement> elements = driver.findElements(By.name(value));
			return elements;
		} else if (by.equals("class")) {
			List<WebElement> elements = driver.findElements(By.className(value));
			return elements;
		} else if (by.equals("xpath")) {
			List<WebElement> elements = driver.findElements(By.xpath(value));
			return elements;
		} else if (by.equals("css")) {
			List<WebElement> elements = driver.findElements(By.cssSelector(value));
			return elements;
		} else if (by.equals("aid")) {
			// 通过AccessibilityId（content-desc）
			List<WebElement> elements = driver.findElementsByAccessibilityId(value);
			return elements;
		} else if (by.equals("text")) {
			List<WebElement> elements = driver
					.findElementsByAndroidUIAutomator("new UiSelector().text(\"" + value + "\")");
			return elements;
		} else if (by.equals("partialText")) {
			List<WebElement> elements = driver
					.findElementsByAndroidUIAutomator("new UiSelector().textContains(\"" + value + "\")");
			return elements;
		} else {
			Assert.fail(
					"Please enter the correct targeting elements,'id','name','class','xpath','css','aid','text','partialText'.");
		}
		return null;
	}

	/**
	 * Wait for an element within a certain amount of time
	 *
	 * @param xpath
	 *            the element's xpath the second
	 */
	public void waitElement(String by, String value, int second) {
		try {
			if (by.equals("id")) {
				(new WebDriverWait(driver, second)).until(ExpectedConditions.presenceOfElementLocated(By.id(value)));
			} else if (by.equals("name")) {
				(new WebDriverWait(driver, second)).until(ExpectedConditions.presenceOfElementLocated(By.name(value)));
			} else if (by.equals("class")) {
				(new WebDriverWait(driver, second))
						.until(ExpectedConditions.presenceOfElementLocated(By.className(value)));
			} else if (by.equals("xpath")) {
				(new WebDriverWait(driver, second)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(value)));
			} else if (by.equals("css")) {
				(new WebDriverWait(driver, second))
						.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(value)));
			} else if (by.equals("aid")) {
				//content-desc
				(new WebDriverWait(driver, second))
						.until(ExpectedConditions.visibilityOf(driver.findElementByAccessibilityId(value)));
			} else if (by.equals("text")) {
				(new WebDriverWait(driver, second)).until(ExpectedConditions.visibilityOf(
						driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + value + "\")")));
			} else if (by.equals("partialText")) {
				(new WebDriverWait(driver, second)).until(ExpectedConditions.visibilityOf(
						driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + value + "\")")));
			} else {
				Assert.fail(
						"Please enter the correct targeting elements,'id','name','class','xpath','css','aid','text','partialText'.");
			}
		} catch (NoSuchElementException e) {
			System.out.println("wait没有找到该元素 " + by + " " + value);
		} catch (TimeoutException e) {
			System.out.println("wait查找元素超时 " + by + " " + value);
		} catch (ElementNotVisibleException e) {
			System.out.println("元素不可见 " + by + " " + value);
		} catch (InvalidElementStateException e) {
			System.out.println("元素状态异常 " + by + " " + value);
		}

	}

	/***
	 * 指定时间内等待直到页面包含文本字符串
	 * 
	 * @param text
	 *            期望出现的文本
	 * @param seconds
	 *            超时时间
	 * @return Boolean 检查给定文本是否存在于指定元素中, 超时则捕获抛出异常并返回false
	
	public static Boolean waitUntilPageContainText(String text, long seconds) {
		boolean result = false;
		try {
			(new WebDriverWait(driver, second)).until(ExpectedConditions.textToBePresentInElement(locator, text))));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 */

/**
 * 判断某个元素是否存在
 * @param by 查找元素的方法
 * @param value 查找元素的值
 * @return boolean 如果存在返回true，如果不存在返回false
 */
	public boolean isElementExist(String by, String value) {
		boolean result = false;
		this.waitElement(by, value, 60);
		try {
			if (by.equals("id")) {
				driver.findElement(By.id(value));
			} else if (by.equals("name")) {
				driver.findElement(By.name(value));
			} else if (by.equals("class")) {
				driver.findElement(By.className(value));
			} else if (by.equals("xpath")) {
				driver.findElement(By.xpath(value));
			} else if (by.equals("css")) {
				driver.findElement(By.cssSelector(value));
			} else if (by.equals("aid")) {
				// 通过AccessibilityId（content-desc）
				driver.findElementByAccessibilityId(value);
			} else if (by.equals("text")) {
				driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + value + "\")");
			} else if (by.equals("partialText")) {
				driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + value + "\")");
			} else {
				Assert.fail(
						"Please enter the correct targeting elements,'id','name','class','xpath','css','aid','text','partialText'.");
			}
			result = true;
		} catch (NoSuchElementException e) {
			System.out.println("没有找到该元素 " + by + " " + value);
			result = false;
		}	finally{
			return result;
		}
	}

	/**
	 * Open the URL
	 *
	 * @param url
	 */
	public void open(String url) {
		// pause(stepInterval);
		try {
			driver.manage().timeouts().pageLoadTimeout(pageLoadMaxTime, TimeUnit.SECONDS);
			driver.get(url);
		} catch (TimeoutException e) {
			System.out.println("ҳ��򿪳�ʱ" + url);
		}
	}

	/**
	 * Set driver window wide and high.
	 *
	 * @param wide
	 * @param high
	 */
	public void setWindow(int wide, int high) {
		driver.manage().window().setSize(new Dimension(wide, high));
	}

	/**
	 * Setting driver window is maximized
	 *
	 */
	public void maxWindow() {

		driver.manage().window().maximize();
	}

	/**
	 * close the driver Simulates the user clicking the "close" button in the
	 * title bar of a pop up
	 */
	public void close() {
		driver.close();
	}

	/**
	 * Quit the driver
	 */
	public void quit() {
		driver.quit();
	}

	/**
	 * Click the page element
	 *
	 * @param xpath
	 *            the element's xpath
	 */
	public void click(String by, String value) {
		waitElement(by, value, timeout);
		WebElement element = getElement(by, value);
		try {
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	/**
	 * Type text at the page element<br>
	 * Before typing, try to clear existed text
	 *
	 * @param xpath
	 *            , the element's xpath
	 * @param text
	 *            , the input text
	 */
	public void type(String by, String value, String text) {

		waitElement(by, value, timeout);
		WebElement element = getElement(by, value);

		try {
			element.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			element.sendKeys(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Right click element.
	 *
	 * @param xpath
	 *            the element's xpath
	 */
	public void rightClick(String by, String value) {
		waitElement(by, value, timeout);
		Actions action = new Actions(driver);
		WebElement element = getElement(by, value);
		action.contextClick(element).perform();
	}

	/**
	 * Move to element.
	 * 
	 * @param xpath
	 *            the element's xpath
	 */
	public void moveTo(String by, String value) {
		waitElement(by, value, timeout);
		Actions action = new Actions(driver);
		WebElement element = getElement(by, value);
		action.moveToElement(element).perform();
	}

	/**
	 * click and hold element.
	 * @param xpath
	 *            the element's xpath
	 */
	public void clickAndHold(String by, String value) {
		waitElement(by, value, timeout);
		Actions action = new Actions(driver);
		WebElement element = getElement(by, value);
		action.clickAndHold(element).perform();
	}

	/**
	 * Drags an element a certain distance and then drops it.
	 *
	 * @param el_xpath
	 *            , the element's xpath
	 * @param ta_xpath
	 *            , the element's xpath
	 */
	public void dragAndDrop(String el_by, String el_value, String ta_by, String ta_value, int timeout) {
		waitElement(el_by, el_value, timeout);
		waitElement(ta_by, ta_value, timeout);
		Actions action = new Actions(driver);
		WebElement el = getElement(el_by, el_value);
		WebElement ta = getElement(ta_by, ta_value);
		action.dragAndDrop(el, ta).perform();
	}

	/**
	 * Click the element by the link text.
	 *
	 * @param text
	 *            , the element's link text
	
	public void clickText(String text) {
		WebElement element = driver.findElement(By.partialLinkText(text));
		try {
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 */

	/**
	 * Select the select tag value
	 *
	 * @param xpath
	 * @param value
	 */
	public void selectValue(String by, String value, String selectValue) {
		waitElement(by, value, timeout);
		WebElement element = getElement(by, value);
		Select sel = new Select(element);
		sel.selectByValue(selectValue);
	}

	/**
	 * Refresh the driver
	 */
	public void refresh() {
		driver.navigate().refresh();
	}

	/**
	 * Execute JavaScript scripts.
	 */
	public void js(String js) {
		((JavascriptExecutor) driver).executeScript(js);
	}

	/**
	 * Enter the iframe
	 *
	 * @param xpath
	 *            , the iframe's xpath
	 */
	public void enterFrame(String by, String value) {
		waitElement(by, value, timeout);
		WebElement element = getElement(by, value);
		driver.switchTo().frame(element);
	}

	/**
	 * Leave the iframe
	 */
	public void leaveFrame() {
		driver.switchTo().defaultContent();
	}

	/**
	 * Open the new window and switch the handle to the newly opened window.
	 *
	 * @param xpath
	 *            , the open windows element xpath
	 */
	public void openOneWindow(String by, String value) {
		waitElement(by, value, timeout);
		String sreach_handle = driver.getWindowHandle();
		WebElement element = getElement(by, value);
		try {
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set<String> handles = driver.getWindowHandles();
		for (String handle : handles) {
			if (handle.equals(sreach_handle) == false) {
				driver.switchTo().window(handle);
			}
		}
	}

	/**
	 * Return text from specified web element.
	 *
	 * @param xpath
	 * @return
	 */
	public String getText(String by, String value) {
		WebElement element = getElement(by, value);
		return element.getText();
	}

	/**
	 * Returns the title of the current page
	 *
	 * @return
	 */
	public String getTitle() {
		return driver.getTitle();
	}

	/**
	 * Returns the URL of the current page
	 *
	 * @return
	 */
	public String getUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * Gets the value of an element attribute.
	 *
	 * @return
	 */
	public String getAttribute(String by, String value, String attribute) {
		WebElement element = getElement(by, value);
		String attribute1 = element.getAttribute(attribute);
		return attribute1;
	}

	/**
	 * Accept warning box.
	 */
	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}

	/**
	 * Dismisses the alert available.
	 */
	public void dismissAlert() {
		driver.switchTo().alert().dismiss();
	}

	/**
	 * TakesScreenshot.
	 */
	public void TakesScreenshot(String file_path) {
		try {
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(file_path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * SwipeDown
	 * @throws InterruptedException 
	 */
	public void swipeDown() {
		System.out.println("---swipedown----");
		Duration duration = Duration.ofSeconds(1);
		int width = driver.manage().window().getSize().width;
		System.out.println("width"+width);
		int height = driver.manage().window().getSize().height;
		System.out.println("height"+height);
		TouchAction action1 = new TouchAction(driver).press(PointOption.point(width/2, height/4)).waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(width/2, height*3/4)).release();
		action1.perform();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * SwipeUp
	 */
	public void swipeUp() {
		Duration duration = Duration.ofSeconds(1);
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		TouchAction action = new TouchAction(driver).press(PointOption.point(width / 2, height * 3 / 4))
				.waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(width / 2, height / 4))
				.release();
		action.perform();
	}
	
	/**
	 * SwipeUp
	 */
	public void swipeUpLittle() {
		Duration duration = Duration.ofSeconds(1);
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		TouchAction action = new TouchAction(driver).press(PointOption.point(width / 2, height * 3 / 4))
				.waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(width / 2, height / 2))
				.release();
		action.perform();
		
	}
	
	/**
	 * 通过向上滑动页面来查找元素，最多滑动10次。如果底部有结束的文字，则传入
	 * @param by
	 * @param value
	 * @param byNomore
	 * @param valueNomore
	 * @return
	 */
	public WebElement swipeFindElement(String by, String value,String byNomore,String valueNomore){
		WebElement e = null;
		boolean resultGoal = false;
		//boolean resultNomore = false;
		for(int i=0;i<10;i++){
			resultGoal = this.isElementExist(by, value);
			System.out.println("是否存在"+resultGoal);
		//	resultNomore = this.isElementExist(byNomore, valueNomore);
		
			if(resultGoal==true){
				e = this.getElement(by, value);
				break;
			}
//			else if(resultNomore==true){
//				break;
//			}
			else{
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				this.swipeUp();
			}
		}		
		return e;		
	}
	
	public void tap(int x,int y){
		Duration duration = Duration.ofSeconds(1);
		new TouchAction(driver).tap(PointOption.point(x, y)).waitAction(WaitOptions.waitOptions(duration)).perform();
	}
	
}
