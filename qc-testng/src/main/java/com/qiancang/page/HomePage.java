package com.qiancang.page;


import com.qiancang.basic.BasePage;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.util.ElementLocator;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
	public HomePage(DriverBase driver){
		super(driver);
	this.pagecfgpath = Constants.CFG_ELEMENT;
		this.locator = new ElementLocator(pagecfgpath);
	}


	public WebElement getAccountNameEle(){
		return waitforElement(locator.getLocator("accountname"),3);
	}

}
