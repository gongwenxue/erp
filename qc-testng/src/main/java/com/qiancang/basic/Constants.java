package com.qiancang.basic;

public class Constants {

	//浏览器驱动文件
	//public static String DRIVERNAME = "fireFox";
	public static String DRIVERNAME = "chrome";
	public static String CHROMEDRIVER = "file\\driver\\chromedriver.exe";
	public static String GECKODRIVER = "file\\driver\\geckodriver.exe";

	//cookie读取配置文件
	public static String CFG_COOKIE = "config\\cookie.properties";

	//日志配置文件
	public static String CFG_LOG4J_XML = "config\\log4j.xml";
	public static String CFG_LOG4J_PROP = "config\\log4j.properties";


	//邮件服务器,数据库服务器配置文件
	public static String CFG_CONF = "config\\conf.properties";


	public static String CFG_ELEMENT = "config\\element.properties";
	public static String CFG_LOGINTEST = "config\\loginTest.properties";


	//文件下载(导出)默认目录
	public static  String DOWNLOADDIR = "C:\\Users\\EDZ\\Downloads\\";
	//excel_expected excel目录
	public static  String XLS_EXPECTEDDIR = "C:\\Users\\EDZ\\Desktop\\wenxue\\work\\demo\\qc-testng\\file\\exceldata\\expected\\";

///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// 下面是具体的元素定位和用例数据的配置文件 //////////////////////////
	//页面元素定位配置文件
	public static String CFG_PURCHASEMANAGEPAGE = "config\\page\\purchaseManagePage.properties";
	public static String CFG_GOODSMANAGEPAGE = "config\\page\\goodsManagePage.properties";
	public static String CFG_SUPPLIERMANAGEPAGE = "config\\page\\supplierManagePage.properties";
	public static String CFG_REALTIMESTOCKMANAGEPAGE = "config\\page\\realTimeStockManagePage.properties";
	public static String CFG_PURCHASEINBOUNDPAGE = "config\\page\\purchaseInBoundPage.properties";


	//用例数据配置文件
	public static String CFG_PURCHASEMANAGETESTNG = "\\config\\page\\purchaseManageTestng.properties";
	public static String CFG_GOODSMANAGETESTNG = "\\config\\page\\goodsManageTestng.properties";
	public static String CFG_SUPPLIERMANAGETESTNG = "\\config\\page\\supplierManageTestng.properties";
	public static String CFG_REALTIMESTOCKMANAGETESTNG = "\\config\\page\\realTimeStockManageTestng.properties";
	public static String CFG_PURCHASEINBOUNDTESTNG = "\\config\\page\\purchaseInBoundTestng.properties";

}
