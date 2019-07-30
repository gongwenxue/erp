package com.qiancang.basic.util;

import com.qiancang.basic.log.LogUtils;
import org.openqa.selenium.Cookie;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class ProUtil {
	private Properties prop;
	private String filePath;
	/**
	 * 构造方法
	 * */
	public ProUtil(String filePath){
		this.filePath = filePath;
		this.prop = readProperties();
	}
	
	/**
	 * 读取配置文件
	 * */
	private Properties readProperties(){
		Properties properties = new Properties();
		try {
			//这种方式要求filepath是绝对路径,不易于维护路径,舍弃
			//InputStream inputStream = new FileInputStream(filePath);
			//拿不到资源:原因这种方式会从当前类的目录下去找，这个文件如果不和该类在一个目录下，就找不到
			//InputStream inputStream = this.getClass().getResourceAsStream(filePath);

			//这种方式中ClassLoader就是从整个classes目录找的，所以前面无需再加/
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
			BufferedInputStream in = new BufferedInputStream(inputStream);
			properties.load(in);
			
		} catch (IOException e) {
			LogUtils.error(e.getMessage(),e);
		}
		return properties;
	}
	
	/**
	 * 根据key读取关键字内容
	 * */
	public String  getPro(String key){
		if(prop.containsKey(key)){
			String username = prop.getProperty(key);
			return username;
		}else{
			LogUtils.info("你获取key值不存在于配置文件中(key:"+key+")");
			return "";
		}	
	}
	/**
	 * 写入内容
	 * */
	public void writePro(String key,String value){
		Properties pro = new Properties();
			try {
				FileOutputStream file = new FileOutputStream(filePath);
				pro.setProperty(key, value);
				pro.store(file, key);
				file.close();
			} catch (IOException e) {
                LogUtils.error(e.getMessage(),e);
			}
		}


	public void writePro(Set<Cookie> cookies){
        Properties pro = new Properties();
        FileOutputStream file = null;
        try {

            for(Cookie cookie :cookies){

            	//这两种方式都可以正确获得到项目的根目录C:/Users/EDZ/Desktop/wenxue/work/demo/qc-testng/target/classes/
				String resource = this.getClass().getResource("/").getPath();
//				String resource1 = this.getClass().getClassLoader().getResource("").getPath();
				file = new FileOutputStream(resource+filePath);
                pro.setProperty(cookie.getName(),cookie.getValue());
                pro.store(file, cookie.getName());
//                file.close();
            }
			LogUtils.info("cookie已经成功写到配置文件中保存");
        } catch (IOException e) {
            LogUtils.error(e.getMessage(),e);
        }

    }
}
