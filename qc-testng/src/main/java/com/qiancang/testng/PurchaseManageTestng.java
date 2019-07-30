package com.qiancang.testng;

import com.qiancang.action.PurchaseManageAction;
import com.qiancang.basic.CaseBase;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.listener.TestNGListenerScreen;
import com.qiancang.basic.log.LogUtils;
import com.qiancang.basic.util.Assertion;
import com.qiancang.basic.util.HandleCookie;
import com.qiancang.basic.util.ImageUtil;
import com.qiancang.basic.util.ProUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 采购管理类测试用例集
 */

@Listeners({TestNGListenerScreen.class})
public class PurchaseManageTestng extends CaseBase {

    private DriverBase driver;
    private PurchaseManageAction pma ;
    private ProUtil pro;
    private HandleCookie handcookie;

    @BeforeMethod
    public void beforeMethod() {
        this.driver = CaseBase.driver;
        pro = new ProUtil(Constants.CFG_PURCHASEMANAGETESTNG);
        pma = new PurchaseManageAction(driver);

        driver.sleepWait(300);
        driver.waitForPageLoad();
        driver.get(pro.getPro("purchasemanage.url"));
        driver.waitForPageLoad();




    }

    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin"})
    public void testSearchPurchace() {

        //读取参数
        String shopsite = this.pro.getPro("psearch.shopsite");
        String flag = this.pro.getPro("psearch.flag");
        String goodKeyWord = "";
        if ("1".equals(flag)){
            goodKeyWord = this.pro.getPro("psearch.goodcode");
        }else {
            goodKeyWord = this.pro.getPro("psearch.goodname");
        }
        String orderNumber = this.pro.getPro("psearch.orderNumber");
        String surplier = this.pro.getPro("psearch.surplier");
        String man = this.pro.getPro("psearch.man");
        String startToEnd = this.pro.getPro("psearch.startToEnd");
        String start ="";
        String end ="";
        if (startToEnd != null && !"".equals(startToEnd)){
            start = startToEnd.split(">")[0];
            end = startToEnd.split(">")[1];
        }


        //传递参数进行查询
        pma.searchPurchace(shopsite,flag,goodKeyWord,
                orderNumber,surplier,man,start,end);

        //等待页面刷新完毕
        driver.sleepWait(2000);

        //断言查询结果
        pma.assertSearchPurchace();

        //最终断言
        Assertion.assertFinally("采购订单查询");

    }

    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin"})
    public void testAddPurchace() {
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

        LogUtils.info("已经准备好了商品信息:从配置文件中提取到了商品的各项信息");
        LogUtils.info("开始执行添加商品操作");
        pma.addPurchace(shop,surplier,datetime,gMap);

        //对新增结果进行断言

        pma.assertAddPurchase();



        //最终断言:判断商品添加逻辑是否正确,商品修改是否成功
        Assertion.assertFinally("采购订单新增");

        //删除新增的记录
        pma.deleteAddPurchase();

    }


    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin"})
    public void testEditPurchace() {
        String orderNumber = pro.getPro("editpurchase.orderNumber");
        String arrivalTime = pro.getPro("editpurchase.arrivalTime");
        String purchaseAmount = pro.getPro("editpurchase.purchaseAmount");
        String remark = pro.getPro("editpurchase.remark");
        LogUtils.info("已经准备好了采购订单修改信息:从配置文件中提取到了需要修改项的各项信息");
        LogUtils.info("开始执行修改采购单操作");

        //修改采购单操作
        pma.editPurchace(orderNumber,arrivalTime,purchaseAmount,remark);

        //查看编辑后的采购订单详情,并截图保存订单详情信息
        pma.queryEditPurchaseOrderDetail(orderNumber);

        //断言编辑的actual截图和expected截图
        ImageUtil.imageCompare("purchaseEdit_expected","purchaseEdit_actual",true);

        //最终断言:判断商品添加逻辑是否正确,商品修改是否成功
        Assertion.assertFinally("采购订单编辑");

        //还原修改的记录
        //pma.restoreEditPurchase();

    }



    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin"})
    public void testExportPurchace() {
        String orderNumber = pro.getPro("export.orderNumber");
        LogUtils.info("从配置文件中提取到了需要导出的采购订单编号信息");
        //执行导出功能操作
        pma.exportPurchaseOrder(orderNumber);

        //验证导出功能
        pma.assertExportPurchase(orderNumber);
        //最终断言
        Assertion.assertFinally("采购单导出");
        //断言结束,恢复初始状态,即这里删除下载的导出记录(excel)

    }
}
