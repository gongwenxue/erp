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
public class PurchaseManagePage extends BasePage {


    public PurchaseManagePage(DriverBase driverBase) {
        super(driverBase);
        this.pagecfgpath = Constants.CFG_PURCHASEMANAGEPAGE;
        this.locator = new ElementLocator(pagecfgpath);

    }



    //获得待处理标签页元素
    public WebElement getToDealOrderTabElement(){
        return element(locator.getLocator("hp.checkedtab"));
    }
    //获得部分入库标签页元素
    public WebElement getPatialOrderTabElement(){
        return element(locator.getLocator("hp.partialtab"));
    }
    //获得已完成签页元素
    public WebElement getFinishedOrderTabElement(){
        return element(locator.getLocator("hp.completedtab"));
    }
    //获得已关闭标签页元素
    public WebElement getClosedOrderTabElement(){
        return element(locator.getLocator("hp.closedtab"));
    }


    //获得待处理页面的订单列表
    public List<WebElement> getPurchaseOrderList(){
        return elements(locator.getLocator("hp.purchaseList"));
    }


    //获得新增的订单
    public WebElement getPurchaseOrderRow(){
        List<WebElement> list = getPurchaseOrderList();
        return list.get(0);
    }
    //获得订单详情按钮
    public WebElement getPurchaseOrderDetailButton(WebElement orderRow){
        WebElement detailButton = nodeElement(orderRow, locator.getLocator("hp.purchaseOrderDetail"));
        return detailButton;
    }

    //获得订单导出按钮
    public WebElement getExportOrderButton(WebElement orderRow){
        WebElement exportOrderButton = nodeElement(orderRow, locator.getLocator("hp.purchaseOrderExport"));
        return exportOrderButton;
    }

    //获得订单编辑按钮
    public WebElement getOrderEditButton(WebElement orderRow){
        WebElement detailButton = nodeElement(orderRow, locator.getLocator("hp.purchaseOrderEdit"));
        return detailButton;
    }

    //获得新增的订单关闭按钮
    public WebElement getPurchaseOrderCloseButton(WebElement row){

        WebElement closeButton = nodeElement(row, locator.getLocator("hp.purchaseOrderClose"));
        return closeButton;
    }
    //获得采购单详情window元素
    public WebElement getDetailWindow(){
        return  element(locator.getLocator("hp.detailWindow"));
    }

    //等待新增采购订单按钮出现
    public WebElement waitAddPurchaseButton() {
        return waitforElement(locator.getLocator("hp.addpurcharce"), 5);
    }


    //获得查询按钮
    public WebElement getQueryBar(){
        return  element(locator.getLocator("hp.searchbutton"));
    }


    //获得选择网点元素
    public WebElement getSelectShopInput(){
        return  getSelectAreaInputs().get(0);
    }

    //获得筛选商品输入key元素
    public WebElement getSelectGoodByIdOrNameInput(){
        return  getSelectAreaInputs().get(1);
    }
    //获得筛选商品输入关键词元素
    public WebElement getGoodKeyWordInput(){
        return  getSelectAreaInputs().get(2);
    }
    //获得采购单选择元素
    public WebElement getSelectGoodByOrderNumberInput(){
        return  getSelectAreaInputs().get(3);
    }
    //获得选择选择供应商元素
    public WebElement getSelectGoodBySupplierInput(){
        return  getSelectAreaInputs().get(4);
    }
    //获得选择采购员元素
    public WebElement getSelectGoodByPurchaseManInput(){
        return  getSelectAreaInputs().get(5);
    }
    //获得选择查询时间元素
    public WebElement getSelectGoodByTimeInput(){
        return  getSelectAreaInputs().get(6);
    }
    //获得开始时间输入框元素
    public WebElement getStartTimeInput(){
        return  getSelectGoodByTimeInput().findElements(By.tagName("input")).get(0);
    }
    //获得结束时间输入框元素
    public WebElement getEndTimeInput(){
        return  getSelectGoodByTimeInput().findElements(By.tagName("input")).get(1);
    }
    //获得清除时间按钮元素
    public WebElement getClearTimeButton(){
        return  getSelectGoodByTimeInput().findElements(By.tagName("i")).get(1);
    }

    //获得选择网点li元素
    public WebElement getSelectShopLiEle(String shop){
        return  element(By.xpath("//li/span[text()='" + shop.trim() + "']"));
    }
    //获得选择供应商li元素
    public WebElement getSelectSupplierLiEle(String supplier){
        return  element(By.xpath("//li/span[text()='" + supplier.trim() + "']"));
    }
    //获得筛选商品li元素
    public WebElement getSelectGoodLiEle(String flag){
        WebElement goodLiEle= null;
        if ("1".equals(flag)){
            goodLiEle = element(locator.getLocator("goodlast4codesearch"));
        }else {
            goodLiEle = element(locator.getLocator("goodnamesearch"));
        }
        return  goodLiEle;
    }
    //获得采购员li元素
    public WebElement getSelectPurchaseManLiEle(String purchaseman){
        return  element(By.xpath("//li/span[text()='" + purchaseman.trim() + "']"));
    }

    //获得查询区域的输入框集合
    public List<WebElement> getSelectAreaInputs(){
        driver.sleepWait(300);
        return  elements(locator.getLocator("hp.searchinputs"));
    }


    //获得查询列表分页栏的总计元素:index 表示页签的索引
    public WebElement getTotalElement(int index) {
        return elements(locator.getLocator("hp.total")).get(index);
    }

    //获得查询结果表格div元素
    public WebElement getResultTableDiv() {
        return element(locator.getLocator("hp.tablediv"));

    }

    //获得预计送达时间的输入框
    public WebElement getArrivalTime() {
        return elements(locator.getLocator("edit.arrivalTime")).get(0);
    }

    //获得订单编号单元格
    public WebElement getOrderNuberCell(WebElement row) {
        return nodeElement(row,By.tagName("a"));
    }
}
