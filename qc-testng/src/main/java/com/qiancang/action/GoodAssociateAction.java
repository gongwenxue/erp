package com.qiancang.action;

import com.qiancang.basic.DriverBase;
import com.qiancang.basic.log.LogUtils;
import com.qiancang.basic.util.ImageUtil;
import com.qiancang.page.GoodAssociatePage;
import org.openqa.selenium.WebElement;

/**
 * Description: 供应商关联商品action类
 */
public class GoodAssociateAction {
    private DriverBase driverBase;
    private SupplierManageAction sma;
    private GoodAssociatePage gap;

    public GoodAssociateAction(DriverBase driver) {
        this.driverBase = driver;
        this.sma = new SupplierManageAction(driver);
        this.gap = new GoodAssociatePage(driver);
    }

    //关联商品业务逻辑
    public void associateGood(String snumber, String goodflag, String goodname) {

        //关联前的准备工作
        beforeAssociateGood(snumber);

        //点击新增供应商商品按钮,进入到关联商品页面
        clickNewAssociateGood();

        //检索出该供应商需要关联的商品集合
        QueryAccociateGoodList(goodflag,goodname);

        //全部勾选关联商品集合
        selectAllAccociateGoodList();

        //点击确定按钮,提交关联保存
        clickSureAccociateBtn();
        LogUtils.info("已经勾选好了需要关联的商品,开始点击确定按钮,提交关联保存");

        //等待一会,让页面刷新列表完毕
        driverBase.sleepWait(3000);
        driverBase.waitForPageLoad();
    }

    //点击确定按钮,提交关联保存
    private void clickSureAccociateBtn() {
        gap.click(gap.getSureAccociateBtn());
    }

    //全部勾选关联商品集合
    private void selectAllAccociateGoodList() {

       gap.click(gap.getWinFullSelectBtn());
    }

    //检索出该供应商需要关联的商品集合
    private void QueryAccociateGoodList(String goodflag, String goodname) {

        //设置筛选商品值
        setGoodOption(goodflag);

        //设置查询关键字值
        setGoodKeyWord(goodname);

        //点击查询按钮,查询出商品集合
        clickWinQueryBar();

        //等待一会让页面稳定
        driverBase.sleepWait(100);

    }

    //点击查询按钮,查询出商品集合
    private void clickWinQueryBar() {
        gap.click(gap.getWindowQueryBar());
    }

    //设置查询关键字值
    private void setGoodKeyWord(String goodname) {
        gap.sendkeys(gap.getWindowKewWordInput(),goodname);
    }

    //设置筛选商品值
    private void setGoodOption(String goodflag) {
        //点击筛选商品下拉输入框
        gap.click(gap.getWindowGoodItemInput());
        //获得筛选商品li元素值
        WebElement liEle = gap.getGoodLiByFlag(goodflag);
        //选择该li元素
        gap.click(liEle);
    }

    //点击新增供应商商品按钮,进入到关联商品页面
    private void clickNewAssociateGood() {
        gap.click(gap.getNewAssociateGoodBtn());
    }


    /**
     * @Description 关联前的准备工作:主要是根据供应商名称,先检索出需要关联的这个供应商,然后进行关联动作
     * @Param * @param snumber 需要关联的供应商编号
     * @return void
     **/
    private void beforeAssociateGood(String snumber) {

        //根据供应商编号,查找指定供应商
        sma.searchSupplier("1",snumber);

        //定位到这个供应商,点击详情,进入到详情页面
        sma.clickFirstRowDetailBtn();
        LogUtils.info("已经定位到需要关联的供应商行,点击详情按钮,进入到详情页面");

        //等待一会,让页面元素加载完毕
        driverBase.sleepWait(100);
        driverBase.waitForPageLoad();

        //点击供应商品设定按钮,进入关联商品页面
        sma.clickPruductSettingBtn();

        //等待一会,让页面元素加载完毕
        driverBase.sleepWait(200);
        driverBase.waitForPageLoad();

    }

    //断言关联商品功能是否正确
    public void assertAssociateGood() {

        //对关联后的列表页面进行截图保存
        ImageUtil.screenShotForElement(driverBase.getDriver(),null,"associateGood_actual");
        LogUtils.info("对关联后的列表页面进行截图保存工作完成");

        //截图比对
        ImageUtil.imageCompare("associateGood_expected","associateGood_actual",true);
        LogUtils.info("商品关联截图对比工作完成");

    }
}
