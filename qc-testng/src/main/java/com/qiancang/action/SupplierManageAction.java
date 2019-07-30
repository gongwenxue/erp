package com.qiancang.action;

import com.qiancang.basic.DriverBase;
import com.qiancang.basic.log.LogUtils;
import com.qiancang.basic.util.ImageUtil;
import com.qiancang.page.SupplierManagePage;
import org.openqa.selenium.WebElement;

/**
 * Description: 供应商管理的action类
 */
public class SupplierManageAction {
    private DriverBase driverBase;
    private SupplierManagePage smp;

    public SupplierManageAction(DriverBase driver) {
        this.driverBase = driver;
        this.smp = new SupplierManagePage(driver);
    }

    //根据指定条件查询供应商
    public void searchSupplier(String flag, String sKeyword) {

        //给供应商下拉选设置值
        setSupplierSelect(flag);

        //因为查询关键字是在供应商下拉选设置值后才能使用,所以需要给个等待时间
        driverBase.sleepWait(100);

        //设置查询关键词
        setKeyWordInput(sKeyword);

        //点击查询按钮,开始查询
        clickQueryBar();

        //查询结束,等待页面刷新稳定
        driverBase.sleepWait(500);
        driverBase.waitForPageLoad();


    }

    //点击查询按钮,开始查询
    private void clickQueryBar() {
        smp.click(smp.getQueryBar());
    }

    //设置查询关键词
    private void setKeyWordInput(String sKeyword) {
        smp.sendkeys(smp.getKeyWordInput(),sKeyword);
    }

    //给供应商下拉选设置值
    private void setSupplierSelect(String flag) {
        if (flag == null || "".equals(flag)) {
            return;
        }

        //点击下拉列表
        smp.click(smp.getSupplierSelect());

        //给下拉列表设置值,即选择相应的li
        smp.click(smp.getSupplierSelectLi(flag));
    }

    //新增供应商
    public void addSupplier(String snumber, String sname, String site, String manager, String email, String phoneNumber, String accountbank, String accName, String account, String taxNumber, String remark) {

        //点击新增供应商按钮,进入添加页面(弹窗)
        clickAddSupplierButton();

        //设置供应商编号
        setSupplierNumber(snumber);

        //设置供应商名称
        setSupplierName(sname);

        //设置地址
        setSite(site);

        //设置负责人名称
        setManager(manager);

        //设置email
        setEmail(email);

        //设置联系方式
        setPhoneNumber(phoneNumber);

        //设置开户行
        setBank(accountbank);

        //设置账号名称
        setAccname(accName);

        //设置账号名
        setAccount(account);

        //设置税号
        setTaxNumber(taxNumber);

        //设置备注
        setRemark(remark);
        LogUtils.info("供应商信息填写完毕");

        //点击新增按钮,保存
        clickSavebtn();
        LogUtils.info("点击保存按钮,正在保存供应商");

        //新增完成后等待一会,让页面刷新完毕
        driverBase.sleepWait(500);
        driverBase.waitForPageLoad();



    }

    //点击新增按钮,保存
    private void clickSavebtn() {
        smp.click(smp.getSaveAddBtn());
    }

    //设置备注
    private void setRemark(String remark) {
        smp.sendkeys(smp.getRemarkInput(),remark);
    }


    //设置税号
    private void setTaxNumber(String taxNumber) {
        smp.sendkeys(smp.getTaxNumberInput(),taxNumber);
    }

    //设置账号名
    private void setAccount(String account) {
        smp.sendkeys(smp.getAccountInput(),account);
    }

    //设置账号名称
    private void setAccname(String accName) {
        smp.sendkeys(smp.getAccNameInput(),accName);
    }

    //设置开户行
    private void setBank(String accountbank) {
        smp.sendkeys(smp.getBankInput(),accountbank);
    }

    //设置联系方式
    private void setPhoneNumber(String phoneNumber) {
        smp.sendkeys(smp.getPhonenumberInput(),phoneNumber);
    }

    //设置email
    private void setEmail(String email) {
        smp.sendkeys(smp.getEmailInput(),email);
    }

    //设置负责人名称
    private void setManager(String manager) {
        smp.sendkeys(smp.getManagerInput(),manager);
    }

    //设置地址
    private void setSite(String site) {
        smp.sendkeys(smp.getSiteInput(),site);
    }

    //设置供应商名称
    private void setSupplierName(String sname) {
        smp.sendkeys(smp.getSupplierNameInput(),sname);
    }

    //设置供应商编号
    private void setSupplierNumber(String snumber) {
        //获得供应商编号输入框
        WebElement snumberInput= smp.getSupplierNumberInput();

        //给输入框输入编号值
        smp.sendkeys(snumberInput,snumber);
    }

    //点击新增供应商按钮,进入添加页面(弹窗)
    private void clickAddSupplierButton() {
        //获得新增供应商按钮元素
        WebElement newAddBtn = smp.getAddSupplierButton();

        //点击新增按钮,跳转到新增页面
        smp.click(newAddBtn);
    }


    //断言查询结果
    public void assertSearchSupplier() {

        //对查询结果进行截图保存
        ImageUtil.screenShotForElement(driverBase.getDriver(),null,"supplierSearch_actual");

        //截图对比
        ImageUtil.imageCompare("supplierSearch_expected","supplierSearch_actual",true);
    }

    //断言供应商添加功能
    public void assertAddSupplier(String snumber) {
        //根据sname查询出这条新增记录
        searchSupplier("1",snumber);

        //选择这条记录,点击详情按钮,进入到详情页面
        clickFirstRowDetailBtn();

        LogUtils.info("选择刚才新增的这条记录,点击详情按钮,进入到详情页面");

        //对详情页面进行截图
        WebElement DetailForm = smp.getDetailForm();
        ImageUtil.screenShotForElement(driverBase.getDriver(),DetailForm,"supplierAdd_actual");
        LogUtils.info("已经成功完成对商品添加后的详情页面的截图");

        //对比截图
        ImageUtil.imageCompare("supplierAdd_expected","supplierAdd_actual",true);
        LogUtils.info("供应商新增功能的截图对比工作已经结束");

    }

    //选择查询出的第一行点击详情按钮,进入到这个供应商详情页面
    public  void clickFirstRowDetailBtn() {
        WebElement fRow = smp.getFirstSupplierRow();
        WebElement fRowBtn = smp.getRowDetailBtn(fRow);
        smp.click(fRowBtn);
    }

    //选择供应商品设定按钮,进入到这个供应商商品关联页面
    public  void clickPruductSettingBtn() {

        smp.click(smp.getPruductSettingBtn());
    }


}
