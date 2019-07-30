package com.qiancang.page;

import com.qiancang.basic.BasePage;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.util.ElementLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Description: 商品添加页面
 */
public class GoodsAddPage extends BasePage {

    public GoodsAddPage(DriverBase driver) {
        super(driver);
        this.pagecfgpath = Constants.CFG_GOODSMANAGEPAGE;
        this.locator = new ElementLocator(pagecfgpath);
    }


    /**
     * @Description 获得商品编码输入框
     **/
    public WebElement getGoodCodeElement() {
        return element(locator.getLocator("goodcode"));
    }
    /**
     * @Description 获得商品名称输入框
     **/
    public WebElement getGoodNameElement() {
        return element(locator.getLocator("goodname"));
    }
    /**
     * @Description 获得商品分类输入框
     **/
    public WebElement getGoodCatageryElement() {
        return element(locator.getLocator("catagery"));
    }
    /**
     * @Description 获得商品保质期输入框
     **/
    public WebElement getGoodPeriodElement() {
        return element(locator.getLocator("goodperiod"));
    }
    /**
     * @Description 获得可售天数输入框
     **/
    public WebElement getGoodSalePeriodElement() {
        return element(locator.getLocator("goodsaleperiod"));
    }
    /**
     * @Description 获得库存单位输入框
     **/
    public WebElement getGoodUnitElement() {
        return element(locator.getLocator("goodunit"));
    }
    /**
     * @Description 获得规格输入框
     **/
    public WebElement getGoodGuiGeElement() {
        return element(locator.getLocator("goodguige"));
    }

    /**
     * @Description 获得采购参考价输入框
     **/
    public WebElement getGoodSuggestPriceElement() {
        return element(locator.getLocator("goodsuggestprice"));
    }
    /**
     * @Description 获得销售状态元素
     **/
    public  List<WebElement> getGoodSaleStateElement() {
        List<WebElement> salestate = elements(locator.getLocator("salestate"));
        return salestate;
    }
    /**
     * @Description 获得取消按钮
     **/
    public WebElement getCancelElement() {
        return element(locator.getLocator("cancel"));
    }
    /**
     * @Description 获得确定按钮
     **/
    public WebElement getConfirmElement() {
        return element(locator.getLocator("confirm"));
    }

    /**
     * @Description 获得商品单位ul元素
     **/
    public WebElement getUnitUlElement() {
        return elements(locator.getLocator("goodunit_ul")).get(1);
    }

    /**
     * @Description 获得商品分类li
     **/
    public WebElement getCatagoryLi(String childCatagoryName) {
        return element(By.xpath("//*[text()='" + childCatagoryName.trim() + "']"));
    }
    public boolean waitConfirmButtonDisplay(){
        return waitEleDisplay(locator.getLocator("confirm"), 2);
    }

}
