package com.qiancang.basic;

import org.openqa.selenium.WebElement;

public class Element {
	
	private WebElement webElement;
	
	public Element(WebElement webElement) {
		this.webElement = webElement;
	}
	
	public void inputText(String text) {
		webElement.sendKeys(text);
	}
	
	public void click() {
		webElement.click();
	}

}
