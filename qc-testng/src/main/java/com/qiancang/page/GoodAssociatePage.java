package com.qiancang.page;

import com.qiancang.basic.BasePage;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.util.ElementLocator;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Description: 供应商关联商品页面page类
 */
public class GoodAssociatePage extends BasePage {

    public GoodAssociatePage(DriverBase driver) {
        super(driver);
        this.pagecfgpath = Constants.CFG_SUPPLIERMANAGEPAGE;
        this.locator = new ElementLocator(pagecfgpath);
    }

    //获得新增关联商品按钮
    public WebElement getNewAssociateGoodBtn() {
        return element(locator.getLocator("setgood.addGood"));
    }

    //获得添加商品弹出框页面的筛选商品下拉输入框
    public WebElement getWindowGoodItemInput() {
        return getWindowSearchInputs().get(0);
    }

    //获得添加商品弹出框页面的商品检索关键字输入框
    public WebElement getWindowKewWordInput() {
        return getWindowSearchInputs().get(1);
    }

    //获得添加商品弹出框页面的商品查询栏表单元素
    public List<WebElement> getWindowSearchInputs() {
        return elements(locator.getLocator("addgood.searchInputs"));
    }
    //获得添加商品弹出框页面的商品查询按钮
    public WebElement getWindowQueryBar() {
        return element(locator.getLocator("addgood.queryBar"));
    }

    //根据参数的值,选择对应的下拉项li元素并返回
    public WebElement getGoodLiByFlag(String goodflag) {
        WebElement liEle = null;
        if ("1".equals(goodflag)){
            //选择筛选商品编码后四位li元素
            liEle = elements(locator.getLocator("addgood.gcode")).get(1);
        }else {
            //选择筛选商品编码后四位li元素
            liEle = elements(locator.getLocator("addgood.gname")).get(1);
        }

        return liEle;
    }

    //获得弹出框的全选按钮
    public WebElement getWinFullSelectBtn() {
        return element(locator.getLocator("addgood.fullSelect"));
    }

    //获得弹出框确定按钮
    public WebElement getSureAccociateBtn() {
        return element(locator.getLocator("addgood.surebtn"));
    }
}
