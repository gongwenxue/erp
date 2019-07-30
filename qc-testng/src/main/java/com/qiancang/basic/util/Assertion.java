package com.qiancang.basic.util;

import com.qiancang.basic.log.LogUtils;
import org.testng.Assert;

/**
 * Description: 封装testng的断言的自定义断言类
 */
public class Assertion {

    public static boolean flag = true;


    public static void verifyEquals(Object actual, Object expected) {

        Assert.assertEquals(actual, expected);
        try {
            Assert.assertEquals(actual, expected);
        } catch (Error e) {
            LogUtils.error(e.getMessage(),e);
            flag = false;
        }
    }

    /**
     * @Description 封装断言相等
     * @Param actual 实际值
     * @param expected 期望值
     * @param message 断言失败的日志消息
     * @return void
     **/
    public static void verifyEquals(Object actual, Object expected, String message) {
        try {
            Assert.assertEquals(actual, expected, message);
        } catch (Error e) {
            LogUtils.error(e.getMessage(),e);
            flag = false;
        }
    }

    public static void verifyTrue(boolean actual, String msg) {

        try {
            Assert.assertTrue(actual, msg);

        } catch (Error e) {
            LogUtils.error(e.getMessage(),e);
            flag = false;
        }
    }

    public static void verifyTrue(boolean actual) {

        try {
            Assert.assertTrue(actual);
        } catch (Error e) {
            LogUtils.error(e.getMessage(),e);
            flag = false;
        }
    }

    /**
     * @Description 一个case中可能有多处断言,则需要在最后代码处执行此方法来确定是否所有断言都成功,如果成功表示用例pass,否则failed
     * @Param * @param
     * @return void
     **/
    public static void assertFinally(String functionname){
        if (Assertion.flag == true){
            LogUtils.info("["+functionname+"]功能断言成功,用例pass");
        }else {
            LogUtils.info("["+functionname+"]功能断言失败,用例failed");
            Assert.assertTrue(Assertion.flag);
        }

    }

}