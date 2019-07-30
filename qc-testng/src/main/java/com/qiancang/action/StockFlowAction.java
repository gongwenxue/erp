package com.qiancang.action;

import com.qiancang.basic.DriverBase;
import com.qiancang.basic.log.LogUtils;
import com.qiancang.basic.util.ImageUtil;
import com.qiancang.page.StockFlowPage;

/**
 * Description: 实时库存管理action类
 */
public class StockFlowAction {

    private DriverBase driverBase;
    private StockFlowPage sfp;

    public StockFlowAction(DriverBase driver) {
        this.driverBase = driver;
        this.sfp = new StockFlowPage(driver);
    }

    //查询库存流水功能
    public void searchStockFlow(String shop, String flag, String sKeyword, String stype, String scode, String startToEnd) {

        //进入到库存流水tab页面
        toFlowTabPage();

        //设置选择网点
        setChooseShop(shop);

        //设置筛选商品
        setSelectGood(flag);

        //设置商品查询关键词
        setGoodKeyword(sKeyword);

        //设置库存修改类型
        setStockType(stype);

        //设置库存修改单(号)
        setStockNumber(scode);

        //设置制单时间
        setStartToEnd(startToEnd);

        //点击查询按钮,开始查询
        clickQueryBar();

        //等待一会,让页面元素刷新稳定
        driverBase.sleepWait(300);
        driverBase.waitForPageLoad();



    }

    //进入到库存流水页面
    private void toFlowTabPage() {

        //点击库存流水tab标签
        sfp.click(sfp.getFlowTabEle());

        //等待一会,让页面元素稳定
        driverBase.sleepWait(1000);
        driverBase.waitForPageLoad();
    }

    //点击查询按钮,开始查询
    private void clickQueryBar() {
        sfp.click(sfp.getQueryBar());
    }

    //设置制单时间
    private void setStartToEnd(String startToEnd) {
        if (startToEnd == null || "".equals(startToEnd.trim())) return;

        String start = startToEnd.split(">")[0];
        String end = startToEnd.split(">")[1];

        sfp.sendkeys(sfp.getStartInput(),start);
        sfp.sendkeys(sfp.getEndInput(),end);
    }


    //设置库存修改单(号)
    private void setStockNumber(String scode) {
        sfp.sendkeys(sfp.getStockNumberInput(),scode);
    }

    //设置库存修改类型
    private void setStockType(String stype) {

        //选择库存修改类型下拉输入框
        sfp.click(sfp.getStockTypeInput());

        //点击相应的Li选项,选择指定的库存修改类型
        sfp.click(sfp.getSelectShopLi(stype));
    }

    //设置商品查询关键词
    private void setGoodKeyword(String sKeyword) {
        sfp.sendkeys(sfp.getGoodKeyWordInput(),sKeyword);
    }

    //设置筛选商品
    private void setSelectGood(String flag) {
        //如果flag没有值,说明这个检索条件不选择
        if (flag == null || "".equals(flag)) return;

        //点击商品选择的下拉输入框
        sfp.click(sfp.getSelectGoodInput());

        //给下来列表设置值,即选择相应的li
        sfp.click(sfp.getGoodSelectLi(flag));
    }

    //设置选择网点
    private void setChooseShop(String shop) {

        //选择网点下拉输入框
        sfp.click(sfp.getSelectShopInput());

        //点击相应的Li选项,选择指定的网点
        sfp.click(sfp.getStockTypeLi(shop));

    }


    //对库存流水进行断言
    public void assertSearchStockFlow() {

        //对查询结果进行截图
        ImageUtil.screenShotForElement(driverBase.getDriver(),null,"stockFlowSearch_actual");
        LogUtils.info("对库存流水的查询结果截图完成");

        //对比库存流水截图
        ImageUtil.imageCompare("stockFlowSearch_expected","stockFlowSearch_actual",true);
        LogUtils.info("对库存流水的查询结果截图完成");

    }


}
