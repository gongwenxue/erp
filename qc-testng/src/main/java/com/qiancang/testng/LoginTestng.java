package com.qiancang.testng;

import com.qiancang.action.LoginAction;
import com.qiancang.basic.CaseBase;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.listener.TestNGListenerScreen;
import com.qiancang.basic.log.LogUtils;
import com.qiancang.basic.util.Assertion;
import com.qiancang.basic.util.HandleCookie;
import com.qiancang.basic.util.ProUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@Listeners({TestNGListenerScreen.class})
public class LoginTestng extends CaseBase {

    private DriverBase driver;
    private LoginAction la ;
    private ProUtil pro;
    private HandleCookie handcookie;

    @BeforeMethod
    public void BeforeMethod() {
        this.driver= CaseBase.driver;
        pro = new ProUtil(Constants.CFG_LOGINTEST);
        la = new LoginAction(driver);
        handcookie = new HandleCookie(this.driver);
    }

   @Test
    public void testCorrectLogin() {
        driver.get(pro.getPro("url"));

        this.driver.driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        String username = pro.getPro("username");
        String password = pro.getPro("password");
        la.login(username, password);
       LogUtils.info("正在登录[username:"+username+",password:"+password+"]");
       boolean ckLogin = la.AssertLogin(pro.getPro("ckLogin"));
       if(ckLogin){
           handcookie.writeCookie();
       }
       Assertion.assertFinally("登录");
    }

}
