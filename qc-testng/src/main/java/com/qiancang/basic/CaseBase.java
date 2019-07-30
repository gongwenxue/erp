package com.qiancang.basic;

import com.qiancang.basic.log.LogUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description testng默认的case类实例化的过程,启动testng框架,默认通过无参数的构造方法创建一个testcase类的实例对象(每一个@test方法都会独立创建这样一个实例)
 * 因此构造方法必须提供一个无参数的,也可以有其它的构造方法(但无参数的必须有,不写构造方法,默认有一个无参数的构造,用于反射创建实例对象)
 * 构造方法的执行顺序>beforeclass,因为testng先通过构造方法创建实例对象后,才执行beforeclass,然后执行具体的test方法
 **/
public class CaseBase {

public static DriverBase driver;


	/**
	 * @Description testng 的beforeclass继承的执行过程:如果子类有自己的beforeclass则子类只会执行自己的beforeclass(覆盖父类的beforeclass,即父类的不会执行)
	 * 如果子类没有beforeclass 则会执行一遍父类的beforeclass
	 **/

	@BeforeSuite
	public void BeforeSuite() {
		CaseBase.driver = InitDriver(Constants.DRIVERNAME);
		CaseBase.driver.windowMaximize();
	}

	@BeforeClass
	public void BeforeClass() {
		LogUtils.startTestCase(getFullTestClassName());
	}

	/**
	 * @Description testng 的afterClass继承的执行过程:如果子类有自己的afterClass则子类只会执行自己的afterClass(覆盖父类的afterClass,即父类的不会执行)
	 * 如果子类没有afterClass 则会执行一遍父类的afterClass
	 **/
	@AfterClass
	public void afterClass(){
		LogUtils.endTestCase(getFullTestClassName());
	}
	@AfterSuite
	public void AfterSuite(){
		CaseBase.driver.close();
	}

	/**
	 * @Description 获得当前正在执行的测试用例类名称
	 **/
	public String getFullTestClassName(){
		ITestResult it = Reporter.getCurrentTestResult();
		return it.getTestClass().getName();
	}



	public DriverBase InitDriver(String browser){
		return new DriverBase(browser);
	}
	public void setDriver(DriverBase driver) {
		CaseBase.driver = driver;
	}

	/**
	 * @Description 返回静态的driverbase对象,内含有driver
	 * @Param
	 * @return
	 **/
	public DriverBase getDriver() {
		return CaseBase.driver;
	}



	/**
	 * 自动截图
	 * 这里没有将截图功能放入到监听器中,原因为:
	 * 之所以将截图功能放在casebase类中是因为可以获得具体的类名和方法名
	 * 从而截图的命名方式为类名+方法名等
	 * */
	public void takeScreenShot() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		String dateStr = sf.format(date);
		String path = this.getClass().getSimpleName() + "_" + dateStr + ".png";
		takeScreenShot((TakesScreenshot) this.getDriver().getDriver(), path);

	}

	/**
	 * 传入参数截图
	 * */
	public void takeScreenShot(TakesScreenshot drivername, String path) {
		String currentPath = System.getProperty("user.dir"); // get current work
		File scrFile = drivername.getScreenshotAs(OutputType.FILE);
		path = currentPath + "\\file\\screenshot\\" + path;
		try {
			FileUtils.copyFile(scrFile, new File(path));
			LogUtils.info("截图完成,路径为:"+ path);
			Reporter.log(path);
			LogUtils.info("<img src=\""+path+"\" >");
			// 把截图写入到Html报告中方便查看
			Reporter.log("<img src=\"" + path + "\"/>");
		} catch (Exception e) {
			LogUtils.error("截图失败,应该生成截图的路径为:"+currentPath + "\\file\\screenshot\\" + path);
			LogUtils.error(e.getMessage(),e);
		}


	}

}
