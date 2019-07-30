package com.qiancang.basic;

import com.google.common.base.Function;
import com.qiancang.basic.log.LogUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Param
 * @return
 **/
public class DriverBase {
	public WebDriver driver;
	public DriverBase(String browser){

        if(browser.equalsIgnoreCase("fireFox")){
            System.setProperty("webdriver.firefox.marionette",Constants.GECKODRIVER);
//            System.setProperty("webdriver.firefox.bin","C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            this.driver =  new FirefoxDriver();
        }else{
            System.setProperty("webdriver.chrome.driver",Constants.CHROMEDRIVER);

            /**不打开GUI模式启动chrome
             * 此功能对于chrome版本有要求，mac和linux要求59以上版本，
             * 而windows则要求60以上版本，且chromeDriver版本为2.30+；
             * 在此模式下，运行的时候你不会看到新打开的浏览器，但是代码可以正常运行；
             **/
            //ChromeOptions chromeOptions = new ChromeOptions();
            //chromeOptions.addArguments("--headless");
            //WebDriver driver = new ChromeDriver(chromeOptions);
            //this.driver = driver;

            //或者写为如下:
            //ChromeOptions chromeOptions=new ChromeOptions();
            //设置 chrome 的无头模式
            //chromeOptions.setHeadless(Boolean.TRUE);
            //启动一个 chrome 实例
            //webDriver = new ChromeDriver(chromeOptions);

            this.driver = new ChromeDriver();

        }
        driver.manage().window().maximize();

	}
	/**
	 * 获取driver
	 * */
	public WebDriver getDriver() {
        return driver;
    }
	
	public void stop(){

        try {
            driver.close();
        } catch (Exception e) {
            driver = null;
        }
        LogUtils.info("浏览器driver已经关闭了");
	}
	
	/**
	 * 封装Element方法
	 * */
	public WebElement findElement(By by){
		WebElement element = driver.findElement(by);
		return element;
	}
	/**
	 * 封装定位一组elements的方法
	 * */
	public List<WebElement> findElements(By by){
		return driver.findElements(by);
	}
	

    /**
     * get封装
     * */
    
    public void get(String url){
 	   driver.get(url);
    }
    
    /*
     * 返回
     * **/
    public void back(){
    	driver.navigate().back();
    }

    /**
     * 获取当前url
     * */
    public String getUrl(){
    	return driver.getCurrentUrl();
    }
    /**
     * 获取title
     * */
    public String getTitle(){
    	return driver.getTitle();
    }
    /**
     * 关闭浏览器 
     * */
    public void close(){
        if (null != driver) {
            driver.close();
        }
    }
    /**
     * 刷新页面
     * */
    public void refresh(){
        driver.navigate().refresh();
    }
    /**
     * 最大化浏览器窗口
     * */
    public void windowMaximize(){
        if (null != driver) {
            driver.manage().window().maximize();
        }

    }
    /**
     * 获取当前系统窗口list
     * */
    public List<String> getWindowsHandles(){
    	Set<String> winHandels = driver.getWindowHandles();
    	List<String> handles = new ArrayList<String>(winHandels);
    	return handles;
    }
    
    /*
     * 获取当前窗口
     * **/
    public String getWindowHandle(){
    	return driver.getWindowHandle();
    }
    
    /**
     * 切换windows窗口
     * */
    public void switchWindows(String name){
    	driver.switchTo().window(name);
    }
    
    /**
     * 切换alert窗口
     * */
    public void switchAlert(){
    	driver.switchTo().alert();
    }
    /**
     * 模态框切换
     * */
    public void switchToMode(){
    	driver.switchTo().activeElement();
    }
    /**
     * actionMoveElement
     * */
    public void moveToElement(WebElement element){
    	Actions action =new Actions(driver);
    	action.moveToElement(element).perform();
    }

    /**
     * 鼠标移动到元素上,并且点击
     * */
    public void moveToElementAndclick(WebElement element){
        Actions action =new Actions(driver);
        action.moveToElement(element).click().perform();
    }
    /**
     * 获取cookcie
     * @return 
     * */
    public Set<Cookie> getCookie(){
    	Set<Cookie> cookies = driver.manage().getCookies();
    	return cookies;
    }
    
    /**
     * 删除cookie
     * */
    public void deleteCookie(){
    	driver.manage().deleteAllCookies();
    }
    /**
     * 设置cookie
     * */
    public void setCookie(Cookie cookie){
    	driver.manage().addCookie(cookie);
    }

    //隐式等待:时间自定义,如果参数不为空,则使用这个时间,如果不传递参数,则默认等待1s
    public  void implicitlyWait( int... seconds){
        if (seconds != null && seconds.length>=1){
            driver.manage().timeouts().implicitlyWait(seconds[0], TimeUnit.SECONDS);
        }else {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS); //1指等待1秒
        }

    }

    //强制等待
    public void sleepWait(int mseconds){
        try {
            Thread.sleep(mseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description 等待页面加载完毕后再操作
     **/
    public  void waitForPageLoad(){
        Function<WebDriver,Boolean> waitFn = new Function<WebDriver,Boolean>(){
            @Override
            public Boolean apply(WebDriver driver){
                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(waitFn);
    }




}
