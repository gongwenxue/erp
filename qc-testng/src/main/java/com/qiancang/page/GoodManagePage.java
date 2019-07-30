package com.qiancang.page;

import com.qiancang.basic.BasePage;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.util.ElementLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Description: 商品管理page页面
 */
public class GoodManagePage extends BasePage {

    public GoodManagePage(DriverBase driver) {
        super(driver);
        this.pagecfgpath = Constants.CFG_GOODSMANAGEPAGE;
        this.locator = new ElementLocator(pagecfgpath);
    }


    /**
     * @Description 获得最后一页按钮元素
     **/
    public WebElement getLastPagenumberElement() {
        return element(locator.getLocator("lastpagenumber"));
    }

    /**
     * @Description  获得新增商品按钮
     **/
    public WebElement getNewAddButton() {
        return element(locator.getLocator("addgoods"));
    }

    /**
     * @Description  获得最后一页最后一行的详情按钮
     **/
    public WebElement getLastDetailButton() {
        return element(locator.getLocator("lastrownumberdetailbutton"));
    }
    /**
     * @Description  获得最后一页最后一行元素
     **/
    public WebElement getLastDetailRow() {
        return element(locator.getLocator("lastrownumber"));
    }


    //等待新增商品按钮出现
    public WebElement waitAddGoodsButton() {
        return waitforElement(locator.getLocator("addgoods"), 3);
    }

    //获得筛选商品按钮
    public WebElement getSelectGoodButton(){
        List<WebElement> goodsearchinputs = getSelectElements();
        WebElement searchgood = goodsearchinputs.get(0);
        return searchgood;
    }
    //获得商品模糊查询输入框
    public WebElement getGoodKeywordInput(){
        List<WebElement> goodsearchinputs = getSelectElements();
        WebElement goodKeyWord = goodsearchinputs.get(1);
        return goodKeyWord;
    }
    //获得商品分类下拉框
    public WebElement getSelectCatogoryEle(){
        List<WebElement> goodsearchinputs = getSelectElements();
        WebElement catogoryEle = goodsearchinputs.get(2);
        return catogoryEle;
    }

    //获得供应商下拉框
    public WebElement getSelectSuplierEle(){
        List<WebElement> goodsearchinputs = getSelectElements();
        WebElement surplierEle = goodsearchinputs.get(3);
        return surplierEle;
    }

    /**
     * @Description 获得商品分类输入框
     **/
    public WebElement getGoodCatageryElement() {
        return element(locator.getLocator("goodmanagecatagory"));
    }
    /**
     * @Description 获得商品分类li
     **/
    public WebElement getCatagoryLi(String childCatagoryName) {
        driver.sleepWait(50);
        //WebElement divul = waitforElement(locator.getLocator("catagoryuldiv"), 3);
        //return nodeElement(divul,By.xpath("//ul/li/span[text()='" + childCatagoryName.trim() + "']"));
        return element(By.xpath("//ul/li/span[text()='" + childCatagoryName.trim() + "']"));
    }


    //获得具体的供应商li选项
    public WebElement getSupplierLi(String supplier) {
        return element(By.xpath("//li/span[text()='" + supplier.trim() + "']"));
    }

    //获得查询按钮
    public WebElement getQueryBar(){
        return element(locator.getLocator("queryBar"));
    }


    //获得查询区域按钮集
    public List<WebElement>  getSelectElements(){
        return elements(locator.getLocator("goodsearchinputs"));
    }

    //获得四位编码选项li
    public WebElement getLast4CodeEle(){
        return element(locator.getLocator("codenumbersearch"));
    }
    //获得商品名称li
    public WebElement getGoodNameEle(){
        return element(locator.getLocator("codenamesearch"));
    }

    //获得查询列表的第一页的所有行
    public List<WebElement>  getFirstPageRows(){
        return elements(locator.getLocator("goodrows"));
    }


    //获得详情页弹窗元素
    public WebElement getDetailForm() {
        return element(locator.getLocator("detailForm"));

    }


}
