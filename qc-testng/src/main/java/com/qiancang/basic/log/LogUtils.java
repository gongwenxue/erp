package com.qiancang.basic.log;

import com.qiancang.basic.Constants;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.InputStream;

public class LogUtils {

    private static Logger log = null;
    public static String logconfigprop = Constants.CFG_LOG4J_PROP;
    public static String logconfigxml = Constants.CFG_LOG4J_XML;

//    private static boolean isDebug = true;
    private static int LOG_LEVEL = 6;
    private static int LOG_ERROR = 1;
    private static int LOG_WARN = 2;
    private static int LOG_INFO = 3;
    private static int LOG_DEBUG = 4;
    private static int LOG_FATAL = 5;


    private static String LOG_SHORTLINEBREAKER = "==================== case class start ========================";

    static {
//        DOMConfigurator.configure(logconfigxml);
        InputStream inputStream = LogUtils.class.getClassLoader().getResourceAsStream(logconfigprop);
        PropertyConfigurator.configure(inputStream);

        log = Logger.getLogger(LogUtils.class.getName());
    }
    /**
     * @param testcaseName
     */
    public static void startTestCase(String testcaseName) {

        LogUtils.info(LOG_SHORTLINEBREAKER);
        LogUtils.info(testcaseName+"用例类开始执行");
    }

    /**
     * @param testcaseName
     */
    public static void endTestCase(String testcaseName) {
        LogUtils.info(testcaseName+"用例类执行结束");
        //LogUtils.info("\r\n");
    }

    public static void info(String message){
        if(LOG_LEVEL>LOG_INFO){
            log.info(message);
        }

    }

    public static void debug(String message){
        if(LOG_LEVEL>LOG_DEBUG){
            log.debug(message);
        }
    }

    public static void warn(String message){
        if(LOG_LEVEL>LOG_WARN){
            log.warn(message);
        }
    }
    public static void error(String message,Throwable throwable){
        if(LOG_LEVEL>LOG_ERROR){
            log.error(message,throwable);

        }
    }
    public static void error(String message){
        if(LOG_LEVEL>LOG_ERROR){
            log.error(message);
        }
    }
    public static void fatal(String message){
        if(LOG_LEVEL>LOG_FATAL){
            log.fatal(message);

        }
    }


}
