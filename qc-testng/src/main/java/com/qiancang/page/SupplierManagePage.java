package com.qiancang.page;

import com.qiancang.basic.BasePage;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.util.ElementLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Description: 供应商管理的page类
 */
public class SupplierManagePage extends BasePage {
    public SupplierManagePage(DriverBase driver) {
        super(driver);
        this.pagecfgpath = Constants.CFG_SUPPLIERMANAGEPAGE;
        this.locator = new ElementLocator(pagecfgpath);
    }

    //获得新增供应商按钮元素
    public WebElement getAddSupplierButton() {
        return element(locator.getLocator("hp.addSupplier"));
    }

    //获得供应商编号输入框
    public WebElement getSupplierNumberInput() {
        List<WebElement> addInputs = getAddInputs();
        return addInputs.get(0);
    }
    //获得供应商名称输入框
    public WebElement getSupplierNameInput() {
        List<WebElement> addInputs = getAddInputs();
        return addInputs.get(1);
    }
    //获得地址输入框
    public WebElement getSiteInput() {
        List<WebElement> addInputs = getAddInputs();
        return addInputs.get(2);
    }
    //获得负责人输入框
    public WebElement getManagerInput() {
        List<WebElement> addInputs = getAddInputs();
        return addInputs.get(3);
    }
    //获得email输入框
    public WebElement getEmailInput() {
        List<WebElement> addInputs = getAddInputs();
        return addInputs.get(4);
    }
    //获得联系方式输入框
    public WebElement getPhonenumberInput() {
        List<WebElement> addInputs = getAddInputs();
        return addInputs.get(5);
    }
    //获得开户行输入框
    public WebElement getBankInput() {
        List<WebElement> addInputs = getAddInputs();
        return addInputs.get(6);
    }
    //获得账户名称输入框
    public WebElement getAccNameInput() {
        List<WebElement> addInputs = getAddInputs();
        return addInputs.get(7);
    }
    //获得账户名输入框
    public WebElement getAccountInput() {
        List<WebElement> addInputs = getAddInputs();
        return addInputs.get(8);
    }

    //获得税号输入框
    public WebElement getTaxNumberInput() {
        List<WebElement> addInputs = getAddInputs();
        return addInputs.get(9);
    }

    //获得备注输入框
    public WebElement getRemarkInput() {
        List<WebElement> addInputs = getAddInputs();
        return addInputs.get(10);
    }

    //获得保存按钮
    public WebElement getSaveAddBtn() {
        return element(locator.getLocator("add.addButton"));
    }

    //获得新增表单下的所有的输入框集合
    public List<WebElement> getAddInputs() {

        WebElement addForm = getAddForm();
        this.waitEleDisplay(addForm,2);
        return elements(addForm, By.tagName("input"));
    }

    //获得新增表单
    public WebElement getAddForm() {
        return element(locator.getLocator("add.addForm"));
    }

    //获得选择供应商下拉列表元素
    public WebElement getSupplierSelect() {
        return element(locator.getLocator("hp.supSelect"));
    }

    //获得关键字输入框元素
    public WebElement getKeyWordInput() {
        return element(locator.getLocator("hp.keyword"));
    }

    //获得查询按钮元素
    public WebElement getQueryBar() {
        return element(locator.getLocator("hp.queryBar"));
    }

    //获得供应商筛选的li元素
    public WebElement getSupplierSelectLi(String flag) {
        WebElement liEle= null;
        if ("1".equals(flag)){
            liEle = element(locator.getLocator("hp.supcode"));
        }else {
            liEle = element(locator.getLocator("hp.supname"));
        }
        return  liEle;
    }

    //获得当前选择行的供应商详情按钮
    public WebElement getRowDetailBtn(WebElement newRow) {
        return nodeElement(newRow,locator.getLocator("hp.detailbtn"));
    }
    //获得当前选择行的供应商编辑按钮
    public WebElement getRowEditBtn(WebElement newRow) {
        return nodeElement(newRow,locator.getLocator("hp.editbtn"));
    }
    //获得当前选择行的供应商删除按钮
    public WebElement getRowDeleteBtn(WebElement newRow) {
        return nodeElement(newRow,locator.getLocator("hp.deletebtn"));
    }

    //获得新增的供应商记录行
    public WebElement getFirstSupplierRow() {
        return getSupplierList().get(0);
    }

    //获得供应商列表页面当前页的所有记录行
    public  List<WebElement> getSupplierList(){
        return elements(locator.getLocator("hp.supplierList"));
    }


    //获得详情页面的详情表单元素
    public WebElement getDetailForm() {
        return element(locator.getLocator("detail.form"));
    }

    //获得详情页面的供应商商品设定按钮
    public WebElement getPruductSettingBtn() {
        return element(locator.getLocator("detail.sgRelationBtn"));
    }

    //获得详情页面的信息编辑按钮
    public WebElement getInfoEditBtn() {
        return element(locator.getLocator("detail.infoEditBtn"));
    }

    //获得详情页面的删除按钮
    public WebElement getDeleteBtn() {
        return element(locator.getLocator("detail.deleteBtn"));
    }
}
