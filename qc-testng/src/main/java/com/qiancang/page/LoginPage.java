package com.qiancang.page;

import com.qiancang.basic.BasePage;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.util.ElementLocator;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

	public LoginPage(DriverBase driver) {
		super(driver);

		this.pagecfgpath = Constants.CFG_ELEMENT;
		this.locator = new ElementLocator(pagecfgpath);

	}


	/**
	 * 用户名输入框控件
	 */
	public WebElement getUserNameElement() {
		return element(locator.getLocator("username"));
	}
	
	/**
	 * 密码输入框控件
	 */
	public WebElement getPasswordElement() {
		return element(locator.getLocator("password"));
	}
	
	/**
	 * 登录按钮
	 */
	public WebElement getLoginButton() {

		return element(locator.getLocator("loginbutton"));
	}

}
 