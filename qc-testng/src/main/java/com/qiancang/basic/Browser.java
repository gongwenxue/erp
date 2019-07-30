package com.qiancang.basic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Browser {
	
	private static WebDriver driver;
	
	/**
	 * 打开浏览器
	 * @param url
	 * @return
	 */
	public static WebDriver OpenBrowser(String url) {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\EDZ\\Desktop\\wenxue\\work\\demo\\qc-testng\\file\\driver\\chromedriver.exe"); // 必须加入
		//System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe"); // 必须加入
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		return driver;
	}
	
	/**
	 * 关闭浏览器
	 */
	public static void CloseBrowser() {
		if (null != driver) {
			driver.close();
		}
	}
	
	/**
	 * 获取当前页面上的焦点所属于的元素
	 * @return
	 */
	public static WebElement getCurrentFocusElement() {
		if (null != driver) {
			return driver.switchTo().activeElement();
		}
		return null;
	}

}
