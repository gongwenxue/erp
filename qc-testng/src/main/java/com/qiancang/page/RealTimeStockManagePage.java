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
public class RealTimeStockManagePage extends BasePage {

    public RealTimeStockManagePage(DriverBase driver) {
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

    //获得查询按钮
    public WebElement getQueryBar() {
        return element(locator.getLocator("hp.queryBar"));
    }

    //获得查询区域的表单项input
    public List<WebElement> getSelectInputs() {
        return elements(locator.getLocator("hp.searchInputs"));
    }


    //根据网点参数,获得对应的li选项元素
    public WebElement getSelectShopLi(String shop) {
        //WebElement shopul = getSelectShopUl();
        //return elements(shopul,By.xpath("//li/span[text()='"+shop+"']")).get(1);
        return elements(By.xpath("//li/span[text()='"+shop+"']")).get(1);
    }

    //获得选择网点下拉ul元素
    public WebElement getSelectShopUl() {
        return element(locator.getLocator("hp.shopul"));
    }

    //获得商品选择的li元素
    public WebElement getGoodSelectLi(String flag) {
        WebElement goodSelectInput = null;
        if ("1".equals(flag)){
            goodSelectInput = elements(locator.getLocator("hp.gcode")).get(1);
        }else {
            goodSelectInput = elements(locator.getLocator("hp.gname")).get(1);
        }
        return goodSelectInput;
    }

    //定位到指定的商品行(第一行)
    public WebElement getGoodFristRow() {
        return elements(locator.getLocator("hp.goodRow")).get(0);
    }

    //根据指定的行,获得对应行的库存分布按钮元素
    public WebElement getDistributeBtn(WebElement frow) {
        return nodeElement(frow,locator.getLocator("hp.distribute"));
    }

    //获得库存分布的window元素
    public WebElement getStockDistributeWindow() {

        return element(locator.getLocator("hp.dialog"));
    }

    //获得库存分布弹窗关闭按钮
    public WebElement getWindowCloseBtn() {
        return elements(locator.getLocator("hp.closeBtn")).get(0);
    }

    //根据指定的行,获得对应行的库存流水按钮
    public WebElement getStockFlowBtn(WebElement frow) {
        return nodeElement(frow,locator.getLocator("hp.flow"));
    }
}
