package com.qiancang.action;

import com.qiancang.basic.DriverBase;
import com.qiancang.basic.util.ImageUtil;
import com.qiancang.page.PurchaseInBoundPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

/**
 * Description: 采购入库action类
 */
public class PurchaseInBoundAction {
    private DriverBase driver;
    private PurchaseInBoundPage pibp;

    public PurchaseInBoundAction(DriverBase driver) {
        this.driver = driver;
        this.pibp = new PurchaseInBoundPage(driver);


    }

    //进行入库操作(采购订单的商品一次性全部入库)
    public void startPurhcaseInBound() {

        //进入到待入库tab页面
        clickCreatedTab();

        //选择第一条待入库的采购订单记录(默认这一条是刚才前置条件新建的,如果不是,断言报错,这里有不严谨,以后再优化)
        WebElement frow = SelectFirstRow();

        //点击入库按钮,进入到资料填写页面
        clickRowInBoundBtn(frow);

        //进行入库资料填写
        setInBoundInfo();

        //资料填写完毕,点击确定按钮,提交入库
        clickSureBtn();

        //入库完毕,让页面刷新完毕
        driver.sleepWait(2000);
        driver.waitForPageLoad();

    }

    //点击确定按钮,提交入库
    private void clickSureBtn() {
        pibp.click(pibp.getSureBtn());
    }

    //进行入库资料的编辑
    private void setInBoundInfo() {

        //这里不填写,全部使用默认值

    }

    //点击入库按钮,进行入库资料填写
    private void clickRowInBoundBtn(WebElement frow) {
        pibp.click(pibp.getRowInBoundBtn(frow));
    }

    //选择第一条待入库的采购订单记录
    private WebElement SelectFirstRow() {
        return pibp.getfrow();
    }

    //进入到待入库tab页面
    private void clickCreatedTab() {
        pibp.click(pibp.getCreatedTab());
    }

    //断言采购订单的一次性入库操作
    public void assertStartPurchaseInBound(String onumber) {

        //进入到已完成订单页面
        clickCompleteTab();

        //在已完成订单页面查找有订单号为onumber的记录行,找不到,断言失败,找到继续
        WebElement onumberRow = assertONumberRowExist(onumber);

        //点击详情按钮,进入到入库单详情页面
        pibp.click(pibp.getDetailBtn(onumberRow));

        //等待页面稳定,另外保存到数据库和再次查询也需要时间,如果不等待就查,很可能查不到这条记录,因为是异步操作.
        driver.sleepWait(300);
        driver.waitForPageLoad();

        //对入库结果进行截图
        ImageUtil.screenShotForElement(driver.getDriver(),pibp.getDetailWindow(),"PurchaseInBound_actual");

        //对比截图
        ImageUtil.imageCompare("PurchaseInBound_expected","PurchaseInBound_actual",false,98);


    }

    //在已完成订单页面查找有订单号为onumber的记录行,找不到,断言失败,找到继续
    private WebElement assertONumberRowExist(String onumber) {
        WebElement expOrderRow = null;

        //获得当前页面所有的已完成行
        List<WebElement> rows = getCompleteOrderRows();

        //遍历行,看是否有采购单号列值和onumber一致,有就返回,无返回null
        for (WebElement row : rows) {
            String actoNumber = pibp.getPurchaseOrderCodeCellValue(row);
            if (actoNumber.equals(onumber)){
                expOrderRow = row;
                break;
            }
        }

        //断言onumber的记录是否存在,没有抛出异常(功能有误)
        Assert.assertTrue(expOrderRow != null,
                "新增了一个采购订单,但是却在入库功能中未找到对应的采购入库单[已完成采购入库单列表中应该有一条记录的采购采购单号为:"+onumber+"]");

        return expOrderRow;
    }

    //获得当前页面所有的已完成行
    private List<WebElement> getCompleteOrderRows() {
        return pibp.getRowsList();
    }

    //点击已完成tab标签
    private void clickCompleteTab() {
       pibp.click( pibp.getCompleteTab());
    }
}
