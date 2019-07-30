package com.qiancang.action;

import com.qiancang.basic.DriverBase;
import com.qiancang.basic.log.LogUtils;
import com.qiancang.basic.util.Assertion;
import com.qiancang.page.HomePage;
import com.qiancang.page.LoginPage;
import org.openqa.selenium.WebElement;

public class LoginAction {

	private DriverBase driver;
	private LoginPage lp;
	private HomePage hp;
	
	public LoginAction(DriverBase driver) {
		this.driver = driver;
		lp = new LoginPage(driver);
		hp = new HomePage(driver);
	}


	public void login(String userName, String password) {
		lp.sendkeys(lp.getUserNameElement(),userName);
		lp.sendkeys(lp.getPasswordElement(),password);
		lp.click(lp.getLoginButton());
	}


	public boolean AssertLogin(String ckLogin) {
		try {
			WebElement accountname = hp.getAccountNameEle();
			String accountnameText = accountname.getText();
			Assertion.verifyEquals(accountnameText,ckLogin,"登录后的账号显示值和期望值不一致");
		} catch (Exception e) {
			LogUtils.error("断言过程抛出异常,断言失败",e);
			Assertion.flag = false;

		}
		return Assertion.flag;
    }


}




