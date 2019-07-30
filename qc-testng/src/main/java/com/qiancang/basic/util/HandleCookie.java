package com.qiancang.basic.util;

import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.log.LogUtils;
import org.openqa.selenium.Cookie;

import java.util.Set;



public class HandleCookie {
	public DriverBase driver;
	public ProUtil pro;
	public HandleCookie(DriverBase driver){
		this.driver = driver;
		pro = new ProUtil(Constants.CFG_COOKIE);
	}

	/**
	 * @Description 登陆前从配置文件获取cookie,让driver携带到服务器
	 * @Param * @param
	 * @return void
	 **/
	public void setCookie(){

		Cookie access_token = new Cookie("access_token",pro.getPro("access_token"),"test.erp.qiancangkeji.cn","/",null);
		Cookie session_token = new Cookie("session_token",pro.getPro("session_token"),"test.erp.qiancangkeji.cn","/",null);
		Cookie route = new Cookie("route",pro.getPro("route"),"test.erp.qiancangkeji.cn","/",null);
		Cookie XSRF_TOKEN = new Cookie("XSRF-TOKEN",pro.getPro("XSRF-TOKEN"),"test.erp.qiancangkeji.cn","/",null);
		//先设置XSRF_TOKEN
		driver.setCookie(XSRF_TOKEN);
		//再设置access_token
		driver.setCookie(access_token);
		driver.setCookie(route);
		driver.setCookie(session_token);


		LogUtils.info("cookie设置成功,driver将携带cookie信息到服务器获取资源");
	}
	/**
	 * 获取cookie,并写到配置文件中保存
	 * */
	public void writeCookie(){
		Set<Cookie> cookies = driver.getCookie();
		pro.writePro(cookies);

	}
}
