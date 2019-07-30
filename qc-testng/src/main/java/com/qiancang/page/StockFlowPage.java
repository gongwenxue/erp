package com.qiancang.page;

import com.qiancang.basic.BasePage;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.util.ElementLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Description: 实时库存管理页面page类
 */
public class StockFlowPage extends BasePage {

    public StockFlowPage(DriverBase driver) {
        super(driver);
        this.pagecfgpath = Constants.CFG_REALTIMESTOCKMANAGEPAGE;
        this.locator = new ElementLocator(pagecfgpath);
    }



    //获得选择网点下拉输入框input
    public WebElement getSelectShopInput() {
       return getSelectInputs().get(0);
    }

    //获得选择商品下拉输入框input
    public WebElement getSelectGoodInput() {
        return getSelectInputs().get(1);
    }

    //获得商品关键字输入框input
    public WebElement getGoodKeyWordInput() {
        return getSelectInputs().get(2);
    }

    //获得库存修改类型输入框input
    public WebElement getStockTypeInput() {
        return getSelectInputs().get(3);
    }

    //获得库存修改单号输入框input
    public WebElement getStockNumberInput() {
        return getSelectInputs().get(4);
    }

    //获得制单时间开始输入框input
    public WebElement getStartInput() {
        return getSelectInputs().get(5);
    }

    //获得制单时间结束输入框input
    public WebElement getEndInput() {
        return getSelectInputs().get(6);
    }

    //获得查询按钮
    public WebElement getQueryBar() {
        return element(locator.getLocator("fl.queryBar"));
    }

    //获得查询区域的表单项input
    public List<WebElement> getSelectInputs() {
        return elements(locator.getLocator("fl.inputs"));
    }


    //根据网点参数,获得对应的li选项元素
    public WebElement getSelectShopLi(String shop) {
        return elements(By.xpath("//li/span[text()='"+shop+"']")).get(0);
    }

    //根据库存修改类型参数,获得对应的li选项元素
    public WebElement getStockTypeLi(String stype) {
        return elements(By.xpath("//li/span[text()='"+stype+"']")).get(1);
    }

    //获得选择网点下拉ul元素
    public WebElement getSelectShopUl() {
        return element(locator.getLocator("hp.shopul"));
    }

    //获得商品选择的li元素
    public WebElement getGoodSelectLi(String flag) {
        WebElement goodSelectInput = null;
        if ("1".equals(flag)){
            goodSelectInput = elements(locator.getLocator("fl.gcodeli")).get(1);
        }else {
            goodSelectInput = elements(locator.getLocator("fl.gnameli")).get(1);
        }
        return goodSelectInput;
    }


    //获得库存流水页面的tab标签
    public WebElement getFlowTabEle() {
        return element(locator.getLocator("fl.tab"));
    }
}
