package com.qiancang.testng;

import com.qiancang.action.RealTimeStockManageAction;
import com.qiancang.action.StockFlowAction;
import com.qiancang.basic.CaseBase;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.listener.TestNGListenerScreen;
import com.qiancang.basic.log.LogUtils;
import com.qiancang.basic.util.Assertion;
import com.qiancang.basic.util.ProUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Description: 实时库存管理类测试用例集
 */

@Listeners({TestNGListenerScreen.class})
public class RealTimeStockManageTestng extends CaseBase {

    private DriverBase driver;
    private ProUtil pro;
    private RealTimeStockManageAction rtsma;
    private StockFlowAction sfa;


    @BeforeMethod
    public void beforeMethod() {
        this.driver = CaseBase.driver;
        pro = new ProUtil(Constants.CFG_REALTIMESTOCKMANAGETESTNG);
        rtsma = new RealTimeStockManageAction(driver);
        sfa = new StockFlowAction(driver);

        driver.sleepWait(1000);
        driver.waitForPageLoad();
        driver.get(pro.getPro("stockQuery.url"));
        driver.sleepWait(1000);
        driver.waitForPageLoad();

    }

    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin"})
    public void testSearchRealTimeStock() {

        //读取查询参数
        String shop = this.pro.getPro("search.shop");
        String sKeyword = null;
        String flag = this.pro.getPro("search.flag");
        String gcode = this.pro.getPro("search.gcode");
        String gname = this.pro.getPro("search.gname");
        if ("1".equals(flag)){
            sKeyword = gcode;
        }else {
            sKeyword = gname;
        }

        LogUtils.info("读取实时库存查询的配置参数完成,开始进行查询操作");

        //开始查询
        rtsma.searchRealTimeStock(shop,sKeyword,flag);

        LogUtils.info("查询结束,开始对查询结果进行断言");

        //对查询结果进行断言
        rtsma.assertSearchRealTimeStock();

        LogUtils.info("对实时库存查询结果断言结束");

        //最终断言
        Assertion.assertFinally("实时库存查询");
    }


    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin","com.qiancang.testng.RealTimeStockManageTestng.testSearchRealTimeStock"})
    public void testSearchStockDistributeAndFlow() {


        //开始查询指定商品库存分布
        LogUtils.info("开始查询指定商品库存分布");
        rtsma.searchStockDistribute();

        //对商品库存分布查询结果进行断言
        rtsma.assertSearchStockDistribute();
        LogUtils.info("对库存分布查询结果断言结束");

        //对库存流水开始查询
        rtsma.searchStockFlow();
        LogUtils.info("开始查询指定商品库存流水");

        //对库存流水进行断言
        rtsma.assertSearchStockFlow();
        LogUtils.info("对库存流水查询结果断言结束");

        //最终断言
        Assertion.assertFinally("库存分布查询");
    }


    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin"})
    public void testSearchStockFlow() {

        //准备工作:读取查询参数
        String shop = this.pro.getPro("flow.shop");
        String sKeyword = null;
        String flag = this.pro.getPro("flow.flag");
        String gcode = this.pro.getPro("flow.gcode");
        String gname = this.pro.getPro("flow.gname");
        if ("1".equals(flag)){
            sKeyword = gcode;
        }else {
            sKeyword = gname;
        }
        String stype = this.pro.getPro("flow.stockType");
        String scode = this.pro.getPro("flow.stockCode");
        String startToEnd = this.pro.getPro("flow.startToEnd");

        LogUtils.info("读取实时库存查询的配置参数完成,开始进行查询操作");

        //开始查询
        sfa.searchStockFlow(shop,flag,sKeyword,stype,scode,startToEnd);

        //对查询结果进行断言
        sfa.assertSearchStockFlow();

        //最终断言
        Assertion.assertFinally("库存流水查询");
    }


}
