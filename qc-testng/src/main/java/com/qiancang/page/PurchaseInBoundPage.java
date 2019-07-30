package com.qiancang.page;

import com.qiancang.basic.BasePage;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.util.ElementLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Description: 采购入库page类
 */
public class PurchaseInBoundPage extends BasePage {

    public PurchaseInBoundPage(DriverBase driverBase) {
        super(driverBase);
        this.pagecfgpath = Constants.CFG_PURCHASEINBOUNDPAGE;
        this.locator = new ElementLocator(pagecfgpath);
    }

    //获得待入库tab标签
    public WebElement getCreatedTab() {
        return element(locator.getLocator("hp.createdTab"));
    }

    //选择第一条待入库的采购订单记录
    public WebElement getfrow() {
        return elements(locator.getLocator("hp.rowList")).get(0);
    }

    //根据参数row,获得对应行的入库按钮
    public WebElement getRowInBoundBtn(WebElement frow) {
        return nodeElement(frow, locator.getLocator("hp.inboundBtn"));
    }

    //获得入库单弹窗框页面的确定按钮
    public WebElement getSureBtn() {
        return element(locator.getLocator("win.sureBtn"));
    }

    //获得已完成tab标签
    public WebElement getCompleteTab() {
        return element(locator.getLocator("hp.completeTab"));
    }

    //获得当前页面所有的订单行
    public List<WebElement> getRowsList() {
        return elements(locator.getLocator("hp.rowList"));
    }

    //获得采购订单列的单元格字符串值
    public String getPurchaseOrderCodeCellValue(WebElement row) {

        WebElement OrderCodeCell = nodeElement(row, By.tagName("a"));
        return OrderCodeCell.getText();
    }

    //获得当前行的详情按钮
    public WebElement getDetailBtn(WebElement row) {
        return elements(row,By.tagName("button")).get(0);
    }

    //获得当前行的打印按钮
    public WebElement getPrintBtn(WebElement row) {
        return elements(row,By.tagName("button")).get(1);
    }

    //获得订单详情窗口div元素
    public WebElement getDetailWindow() {
        return element(locator.getLocator("win.detailWindow"));
    }
}
