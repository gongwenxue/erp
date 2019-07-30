package com.qiancang.testng;

import com.qiancang.action.PurchaseInBoundAction;
import com.qiancang.action.PurchaseManageAction;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 采购入库测试用例库类
 */
@Listeners({TestNGListenerScreen.class})
public class PurchaseInBoundTestng extends CaseBase {
    private DriverBase driver;
    private ProUtil pro;
    private PurchaseInBoundAction piba;
    private PurchaseManageAction pma ;



    @BeforeMethod
    public void beforeMethod() {
        this.driver = CaseBase.driver;
        pro = new ProUtil(Constants.CFG_PURCHASEINBOUNDTESTNG);
        piba = new PurchaseInBoundAction(driver);
        pma = new PurchaseManageAction(driver);

        driver.sleepWait(1000);
        driver.waitForPageLoad();
        driver.get(pro.getPro("hp.url"));
        driver.sleepWait(1000);
        driver.waitForPageLoad();

    }

    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin"})
    public void testStartPurhcaseInBound() {

        //前置条件,新建一个采购订单
        // (功能依赖于采购订单的建立,即需要先建立一个采购订单,有了之后在这个基础上进行入库和部分入库操作)
        String shop = pro.getPro("addpurchase.shop");
        String surplier = pro.getPro("addpurchase.surplier");
        String datetime = pro.getPro("addpurchase.datetime");

        String gflag = pro.getPro("addgood.flag");
        String gcode = pro.getPro("addgood.gcode");
        String gname = pro.getPro("addgood.gname");
        String catagory = pro.getPro("addgood.catagory");
        String gkeyword = "";
        if ("1".equals(gflag)){
            gkeyword = gcode;
        }else if ("2".equals(gflag)){
            gkeyword = gname;
        }

        //ArrayList<Map<String,String>> glist = new ArrayList<>();
        Map<String,String> gMap = new HashMap<>();
        gMap.put("gflag",gflag);
        gMap.put("gkeyword",gkeyword);
        gMap.put("catagory",catagory);

        //进入到采购管理页面,进行新增一个采购订单
        driver.get(pro.getPro("purchasemanage.url"));
        driver.sleepWait(1000);
        driver.waitForPageLoad();

        LogUtils.info("前置条件:开始进入采购管理页面,进行新增一个采购订单");
        pma.addPurchace(shop,surplier,datetime,gMap);
        LogUtils.info("前置条件:新建一个采购订单完成");

        //查询新增的采购单号
        LogUtils.info("前置条件,查询新增的采购单号");

        String onumber = pma.getPurchaseOrderNumber();
        LogUtils.info("前置条件,新增的采购单号为:"+onumber);


        LogUtils.info("开始进行采购入库操作");
        LogUtils.info("进入到采购入库页面");

        //进行入库操作(采购订单的商品一次性全部入库)
        //进入到采购入库页面
        driver.get(pro.getPro("hp.url"));
        driver.sleepWait(1000);
        driver.waitForPageLoad();

        piba.startPurhcaseInBound();

        //对入库功能断言
        piba.assertStartPurchaseInBound(onumber);

        //最终断言
        Assertion.assertFinally("采购入库--采购订单一次性全部入库操作");



    }
}
