package com.qiancang.basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Action {
	
	private Actions actions;
	
	public Action(DriverBase driver) {
		actions = new Actions(driver.getDriver());
	}
	
//	/**
//	 * 文本输入
//	 * @param webElement 待输入的控件
//	 * @param text 待输入的文本
//	 */
//	public void inputText(WebElement webElement, String text) {
//		actions.sendKeys(webElement, text);
//	}
//
//	/**
//	 * 鼠标单击
//	 * @param webElement 待点击的控件
//	 */
//	public void click(WebElement webElement) {
//		actions.click(webElement);
//	}
//
//	/**
//	 * 右键点击
//	 * @param webElement 待点击的目标
//	 */
//	public void rightClick(WebElement webElement) {
//		actions.contextClick(webElement);
//	}


	//隐式等待wait机制的实现
	public  boolean wait(DriverBase driver , final By by, long time){
		boolean wait = new WebDriverWait(driver.getDriver(), time).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElement(by).isDisplayed();
			} });
		return wait;
	}

}
