package com.qiancang.basic.util;

import org.openqa.selenium.By;

public class ElementLocator {

    private String pageConfigPath;

    public String getPageConfigPath() {
        return pageConfigPath;
    }

    public void setPageConfigPath(String pageConfigPath) {
        this.pageConfigPath = pageConfigPath;
    }

    public ElementLocator(String pageConfigPath) {
        this.pageConfigPath = pageConfigPath;
    }

    public  By getLocator(String key){
//		ProUtil pro = new ProUtil(Constants.CFG_ELEMENT);
        ProUtil pro = new ProUtil(pageConfigPath);
		String locator = pro.getPro(key);
		String locatorType = locator.split(">")[0];
		String locatorValue = locator.split(">")[1];
        return  getBy(locatorType,locatorValue);
	}

	public static By getLocator(String key,String pageConfigPath){
        ProUtil pro = new ProUtil(pageConfigPath);
		String locator = pro.getPro(key);
		String locatorType = locator.split(">")[0];
		String locatorValue = locator.split(">")[1];
        return  getBy(locatorType,locatorValue);
	}

	/**
	 * @Description //TODO
	 * @Param * @param locatorType
	 * @param locatorValue
	 * @return org.openqa.selenium.By
	 **/
	public static By getBy(String locatorType,String locatorValue){
        if(locatorType.equals("id")){
            return By.id(locatorValue);
        }else if(locatorType.equals("name")){
            return By.name(locatorValue);
        }else if(locatorType.equals("tagName")){
            return By.tagName(locatorValue);
        }else if(locatorType.equals("linkText")){
            return By.linkText(locatorValue);
        }else if(locatorType.equals("partialLinkText")){
            return By.partialLinkText(locatorValue);
        }else if(locatorType.equals("className")){
            return By.className(locatorValue);
        }else if(locatorType.equals("cssSelector")){
            return By.cssSelector(locatorValue);
        }else{
            return By.xpath(locatorValue);
        }
    }
}
