package com.qiancang.page;

import com.qiancang.basic.BasePage;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.util.ElementLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Description: 采购管理页面page层
 */
public class PurchaseAddPage extends BasePage {


    public PurchaseAddPage(DriverBase driverBase) {
        super(driverBase);
        this.pagecfgpath = Constants.CFG_PURCHASEMANAGEPAGE;
        this.locator = new ElementLocator(pagecfgpath);
    }

    /**
     * 获得选择网点下拉控件
     */
    public WebElement getSelectShopElement() {
        List<WebElement> list = this.getBasicInfoElements();
        return list.get(0);
    }

    /**
     * 获得选择网点下拉指定的选项元素li
     */
    public WebElement getSelectShopLiElement(String shop) {
        return element(By.xpath("//span[text()='"+shop+"']"));
    }
    /**
     * 获得供应商下拉控件
     */
    public WebElement getSelectSupplierElement() {
        List<WebElement> list = this.getBasicInfoElements();
        return list.get(1);
    }

    /**
     * 获得供应商下拉指定的选项元素li
     */
    public WebElement getSelectSupplierLiElement(String supplier) {
        return element(By.xpath("//span[text()='"+supplier+"']"));
    }

    /**
     * 获得送达日期时间控件
     */
    public WebElement getDateElement() {
        List<WebElement> list = this.getBasicInfoElements();
        return list.get(2);
    }

    /**
     * 获得添加商品按钮
     */
    public WebElement getAddGoodButton() {
        return element(locator.getLocator("addgoodrelation"));
    }

    /**
     * 获得基本信息栏操作元素集合
     */
    public List<WebElement> getBasicInfoElements() {
        return elements(locator.getLocator("searchinputs"));
    }

    /**
     * 获得添加商品弹出框页面的复选框
     */
    public WebElement getGoodCheckboxButton() {
        return element(locator.getLocator("goodadd.checkbox"));
    }

    /**
     * 获得添加商品弹出框页面的筛选商品输入框
     */
    public WebElement getGoodSelectInput()
    {
        return getWindowSearchInputs().get(0);
    }

    /**
     * 获得添加商品弹出框页面的商品关键字输入框
     */
    public WebElement getGoodKeyWordInput() {
        return getWindowSearchInputs().get(1);
    }

    /**
     * 获得添加商品弹出框页面的商品分类输入框
     */
    public WebElement getGoodCatagoryInput() {
        return getWindowSearchInputs().get(2);
    }

    /**
     * 获得添加商品弹出框页面的查询表单项inpt集合
     */
    public List<WebElement> getWindowSearchInputs() {
        return elements(locator.getLocator("goodadd.inputs"));
    }



    /**
     * 获得添加商品弹出框页面的确定按钮
     */
    public WebElement getGoodConfirmButton() {
        return element(locator.getLocator("goodadd.confirm"));
    }

    /**
     * 获得采购订单页面的确认按钮
     */
    public WebElement getPurchaseAddSureButton() {
        return element(locator.getLocator("purchageadd.confirm"));
    }
    /**
     * 获得已添加成功的商品列表行
     */
    public List<WebElement> getGoodListRows() {
        return elements(locator.getLocator("add.rows"));
    }


    //获得确定按钮
    public WebElement getAddSureButton() {
        return element(locator.getLocator("purchageadd.confirm"));
    }

    //获得编辑页面的确定按钮
    public WebElement getEditSureButton() {
        return element(locator.getLocator("edit.sureButton"));
    }

    //获得删除时的弹框的确定按钮:这个是添加的商品点删除时的弹窗的确定按钮
    public WebElement getDeleteSurebutton() {
        return element(locator.getLocator("deleteMessage"));
    }

    //获得商品筛选的li元素
    public WebElement getGoodSelectLi(String gflag) {
        WebElement goodLi = null;
        if ("1".equals(gflag)){
            goodLi = element(locator.getLocator("goodadd.goodcode"));
        }else if ("2".equals(gflag)){
            goodLi = element(locator.getLocator("goodadd.goodname"));
        }
        return goodLi;
    }

    //获得商品子分类
    public WebElement getCatagoryLi(String childCatagory) {
        driver.sleepWait(50);
        return element(By.xpath("//ul/li[text()='" + childCatagory.trim() + "']"));
    }

    //获得添加商品弹出框页面的查询按钮
    public WebElement getGoodQueryBar() {
        return element(locator.getLocator("goodadd.query"));
    }
}
