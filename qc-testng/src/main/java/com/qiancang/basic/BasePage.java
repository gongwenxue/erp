package com.qiancang.basic;

import com.qiancang.basic.util.ElementLocator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class BasePage {
	public DriverBase driver;
	public String pagecfgpath;
	public ElementLocator locator;
	public BasePage(DriverBase driver){
		this.driver = driver;
	}

	
	/**
	 * 定位Element
	 * @param
	 * */
	public WebElement element(By by){
		WebElement element = driver.findElement(by);
		return element;
	}
	
	/**
	 * 层级定位，通过父节点定位到子节点
	 * 需要传入父节点和子节点的by
	 * */
	public WebElement nodeElement(By by,By nodeby){
		WebElement el = this.element(by);
		return el.findElement(nodeby);
	}
	/**
	 * 层级定位传入element，以及子的by
	 *
	 * */
	public WebElement nodeElement(WebElement element,By by){
		return element.findElement(by);
	}



	/**
	 * 定位一组elements
	 * */
	public List<WebElement> elements(By by){
		return driver.findElements(by);
	}

	/**
	 * 通过父节点定位一组elements
	 * */
	public List<WebElement> elements(WebElement pelement,By by){
		return pelement.findElements(by);
	}



	/*
	 * 返回
	 * **/
	public void back(){
		driver.sleepWait(50);
		driver.back();
	}

	/**
	 * 获取当前url
	 * */
	public String getUrl(){
		return driver.getUrl();
	}
	/**
	 * 切换alert窗口
	 * */
	public void switchAlert(){
		driver.switchAlert();
		driver.sleepWait(50);
	}

	/**
	 * 模态框切换
	 * */
	public void switchToMode(){
		driver.switchToMode();
		driver.sleepWait(50);
	}
	/**
	 * 刷新页面
	 * */
	public void refresh(){
		driver.sleepWait(50);
		driver.refresh();
		driver.sleepWait(50);
	}
	/**
	 * 封装点击
	 * */
	public void click(WebElement element){
		if(element !=null){
			try {
				element.click();
			} catch (Exception e) {
				jsClick(element);
			}

		}
		driver.sleepWait(50);
	}

	/**
	 * @Description 原因：用selenium模拟用户单击元素时，JS有一个操作鼠标悬浮的时候会对元素进行修改
	 * 报错为://ElementNotVisibleException: element not interactable
	 * 解决办法：用JS来操作元素(使用等待好像也可以解决)
	 * @Param * driverBase
	 * @param element
	 * @return void
	 **/
	public void jsClick(WebElement element){
		JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
		js.executeScript("arguments[0].click();",element);

	}
	/**
	 * 封装输入
	 * */
	public void sendkeys(WebElement element,String content){
		driver.sleepWait(50);
		if(element !=null){
			//先清除之前的内容
			try {
				element.click();
				element.clear();
				element.sendKeys(Keys.BACK_SPACE );
				element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				element.sendKeys(Keys.DELETE);
//				for (int i = 0; i < content.length(); i++) {
//					element.sendKeys(content.charAt(i) + "");
//				}
				element.sendKeys(content);
				element.click();
			} catch (Exception e) {

			}
			driver.sleepWait(50);
//			System.out.println(element.getAttribute("value"));//value==content

//			if (!content.isEmpty()&& element.getAttribute("value").equals(content)){
//
//			}


		}
	}

	/**
	 * 判断元素是否显示
	 * */
	public boolean assertElementIs(WebElement element){
		driver.sleepWait(50);
		return element.isDisplayed();
	}
	
	/**
	 * 获取文本信息
	 * */
	public String getText(WebElement element){
		return element.getText();
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
		driver.close();
	}
	
	/**
	 * 获取当前窗口所有的windows
	 * */
    public List<String> getWindowsHandles(){
    	List<String> handles = driver.getWindowsHandles();
    	return handles;
    }
    
    /**
     * 根据title切换新窗口
     * */
    public boolean switchToWindow_Title(String windowTitle) {
		driver.sleepWait(50);
        boolean flag = false;
        try {
            String currentHandle = driver.getWindowHandle();
            List<String> handles = driver.getWindowsHandles();
            for (String s : handles) {
                if (s.equals(currentHandle))
                    continue;
                else {
                    driver.switchWindows(s);
                    if (driver.getTitle().contains(windowTitle)) {
                        flag = true;
                        System.out.println("切换windows成功: " + windowTitle);
                        break;
                    } else
                        continue;
                }
            }
        } catch (NoSuchWindowException e) {
            System.out.println("Window: " + windowTitle + " 没找到!!!"
                    + e.fillInStackTrace());
            flag = false;
        }
        return flag;
    }
	
    
    /**
     * moveToElement事件
     * */
    public void moveToElement(WebElement element){
		driver.sleepWait(20);
    	driver.moveToElement(element);
    }
	/**
	 * moveToElementAndclick事件:鼠标先移动(悬浮)到元素上,然后点击元素
	 * */
	public void moveToElementAndclick(WebElement element){
		driver.sleepWait(30);
		driver.moveToElementAndclick(element);
	}

	//隐式等待wait机制的实现
    //isDisplayed表示的是页面html代码是否存在这个元素(标签),及时看不见或hiden属性的元素其isDisplayed属性也是true
	public static boolean wait(DriverBase driver , final By by, long seconds){
		boolean wait = new WebDriverWait(driver.getDriver(), seconds).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElement(by).isDisplayed();
			} });
		return wait;
	}

	/**
	 * @Description 隐式等待wait机制的实现
	 **/
	public  boolean waitEleDisplay( By by, long seconds){
		boolean wait = new WebDriverWait(driver.getDriver(), seconds).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElement(by).isDisplayed();
			} });
		return wait;
	}

	/**
	 * @Description 隐式等待wait机制的实现
	 **/
	public  boolean waitEleDisplay( WebElement element, long seconds){
		boolean wait = new WebDriverWait(driver.getDriver(), seconds).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return element.isDisplayed();
			} });
		return wait;
	}

	/**
	 * 等待元素出现,且元素的状态是displayed,即元素可以是操作的元素.
	 * */
	public WebElement waitforElement(By by,int timeoutSeconds){
		WebDriverWait wait = new WebDriverWait(driver.getDriver(),timeoutSeconds);
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		if(!element.isDisplayed()){
			element = null;
		}
		return element;
	}
	//隐式等待
	public  void implicitlyWait( int seconds){
		driver.implicitlyWait(seconds);   //1指等待1秒
	}

	public  void implicitlyWait(){
		driver.implicitlyWait();
	}


	/**
	 * @Description 根据row获得所有的cells
	 * @Param *  row 表格行row
	 * @param cellLocator 列cell定位器
	 * @return 列对象
	 **/
	public List<WebElement> getCells(WebElement row,String cellLocator) {
		driver.sleepWait(50);
		return row.findElements(locator.getLocator(cellLocator));
	}

	/**
	 * 向上滑动页面.
	 * */
	public void scrollPageUp(){
		//上拉到页面顶端
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}

	/**
	 * 向上滑动页面.
	 * */
	public void scrollPageDown(){
		//下拉到页面底部
        ((JavascriptExecutor) driver.getDriver()).executeScript("window.scrollTo(0,document.body.scrollHeight)");

	}

}
