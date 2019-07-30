package com.qiancang.action;

import com.qiancang.basic.DriverBase;
import com.qiancang.basic.log.LogUtils;
import com.qiancang.basic.util.ImageUtil;
import com.qiancang.page.RealTimeStockManagePage;
import org.openqa.selenium.WebElement;

/**
 * Description: 实时库存管理action类
 */
public class RealTimeStockManageAction {

    private DriverBase driverBase;
    private RealTimeStockManagePage rtsmp;

    public RealTimeStockManageAction(DriverBase driver) {
        this.driverBase = driver;
        this.rtsmp = new RealTimeStockManagePage(driver);
    }

    //开始进行实时库存查询
    public void searchRealTimeStock(String shop, String sKeyword, String flag) {

        //设置选择网点
        setChooseShop(shop);

        //设置商品下拉选检索选项值:根据名称或编码进行检索
        setGoodSelectValue(flag);

        //设置商品检索关键字
        setGoodKeyword(sKeyword);

        //等待一会
        driverBase.sleepWait(100);

        //点击查询按钮,开始查询
        clickQueryBar();

        //查询结束,等待页面刷新稳定
        driverBase.sleepWait(500);
        driverBase.waitForPageLoad();


    }

    //点击查询按钮,开始查询
    private void clickQueryBar() {
        rtsmp.click(rtsmp.getQueryBar());
    }

    //设置商品检索关键字
    private void setGoodKeyword(String sKeyword) {

        rtsmp.sendkeys(rtsmp.getGoodKeyWordInput(),sKeyword);
    }

    //设置商品下拉选检索选项值:根据名称或编码进行检索
    private void setGoodSelectValue( String flag) {

        //如果flag没有值,说明这个检索条件不选择
        if (flag == null || "".equals(flag)) return;

        //这里必须等待一下,让设置网点先设置完
        //driverBase.sleepWait(300);

        //点击商品选择的下拉输入框
        rtsmp.click(rtsmp.getSelectGoodInput());

        //给下来列表设置值,即选择相应的li
        rtsmp.click(rtsmp.getGoodSelectLi(flag));

    }

    //设置选择网点
    private void setChooseShop(String shop) {

        //点击选择网点下拉选
        rtsmp.click(rtsmp.getSelectShopInput());

        //点击相应的选项li,选择网点
        rtsmp.click(rtsmp.getSelectShopLi(shop));

    }

    //对实时库存查询功能断言
    public void assertSearchRealTimeStock() {

        //对查询结果进行截图
        ImageUtil.screenShotForElement(driverBase.getDriver(),null,"realTimeStockSearch_actual");
        LogUtils.info("对实时库存查询结果截图完成");

        //对比截图
        ImageUtil.imageCompare("realTimeStockSearch_expected","realTimeStockSearch_actual",true);
        LogUtils.info("实时库存截图对比结束");

    }

    //开始查询指定商品库存分布
    public void searchStockDistribute() {

        //定位到指定的商品行(第一行)
        WebElement frow = rtsmp.getGoodFristRow();

        //点击库存分布按钮,进入库存分布页面
        rtsmp.click(rtsmp.getDistributeBtn(frow));

        //等待一会,让页面元素稳定
        driverBase.sleepWait(200);
        driverBase.waitForPageLoad();
    }

    //对库存分布查询结果进行断言
    public void assertSearchStockDistribute() {

        //获得库存分布的window元素
        WebElement window = rtsmp.getStockDistributeWindow();

        //对查询结果进行截图
        ImageUtil.screenShotForElement(driverBase.getDriver(),window,"stockDistribute_actual");
        LogUtils.info("对库存分布的查询结果截图完成");

        //对比截图
        ImageUtil.imageCompare("stockDistribute_expected","stockDistribute_actual",false);
        LogUtils.info("库存分布的截图对比结束");

        //对比结束,关闭库存分布窗口
        closeStockDistributeWindow();
    }

    //关闭库存分布窗口
    private void closeStockDistributeWindow() {

        //点击库存分布弹窗关闭按钮,关闭弹窗
        rtsmp.click(rtsmp.getWindowCloseBtn());

        //等待一会
        driverBase.sleepWait(100);
        driverBase.waitForPageLoad();

    }

    //对库存流水开始查询
    public void searchStockFlow() {

        //定位到指定的商品行(第一行)
        WebElement frow = rtsmp.getGoodFristRow();

        //点击库存流水按钮,进入库存流水页面
        rtsmp.click(rtsmp.getStockFlowBtn(frow));
    }

    //对库存流水进行断言
    public void assertSearchStockFlow() {

        //对查询结果进行截图
        ImageUtil.screenShotForElement(driverBase.getDriver(),null,"stockFlow_actual");
        LogUtils.info("对库存流水的查询结果截图完成");

        //对比库存流水截图
        ImageUtil.imageCompare("stockFlow_expected","stockFlow_actual",true);
        LogUtils.info("对库存流水的查询结果截图完成");

    }


}
