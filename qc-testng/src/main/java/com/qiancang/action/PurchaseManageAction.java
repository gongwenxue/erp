package com.qiancang.action;

import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.dataprovider.ExcelDataProvider;
import com.qiancang.basic.log.LogUtils;
import com.qiancang.basic.util.Assertion;
import com.qiancang.basic.util.FileFilterUtil;
import com.qiancang.basic.util.ImageUtil;
import com.qiancang.page.PurchaseAddPage;
import com.qiancang.page.PurchaseManagePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 采购订单管理action
 */

public class PurchaseManageAction {
    private DriverBase driver;
    private PurchaseManagePage pmp;
    private PurchaseAddPage pap;

    public PurchaseManageAction(DriverBase driver) {
        this.driver = driver;
        this.pmp = new PurchaseManagePage(driver);
        this.pap = new PurchaseAddPage(driver);

    }


    /**
     * Description: 新增采购订单
     */
    public  void addPurchace(String shop, String surplier, String datetime, Map<String,String> gMap) {

        //等待新增采购订单按钮出现
        WebElement addPurchaseEle = pmp.waitAddPurchaseButton();
        LogUtils.info("新增采购订单按钮存在,准备点击按钮新增");
        pmp.click(addPurchaseEle);

        //设置采购网点
        setValueForSelectShop(shop);

        //添加采购供应商
        setValueForSelectSuplier(surplier);

        //添加采购时间
        setValueForSelectDatetime(datetime);

        LogUtils.info("采购订单基本信息填写完毕,现在开始关联商品");

        //点击添加商品按钮,弹出商品添加窗口: 让采购订单关联采购的商品
        clickGoodAddButton();

        //等待一会,让页面元素恢复稳定
        driver.sleepWait(100);

        //检索出需要关联的商品
        QuerySupplierGoods(gMap);

        //点击勾选复选框全选按钮
        clickFullChooseCheckbox();

        //点击确定按钮,回到订单添加页面
        clickGoodConfirmButton();

        //获得所有的商品列表行元素
        //List<WebElement> rows =pap.getGoodListRows();

        //删除第一行
        //deleteGoodRelation(rows,new int[]{0});

        //等待一会,让页面元素恢复稳定
        driver.sleepWait(100);

        //填写商品的采购数量:默认全都为1,备注为空不填写
        setPurchaseAmountForEveryGood("1","","");


        //点击确定按钮,提交商品订单
        pmp.click(pap.getAddSureButton());
        LogUtils.info("点击确定按钮,提交商品订单");

    }

    //检索出需要关联的商品
    private void QuerySupplierGoods(Map<String, String> gMap) {

        //遍历map取出查询条件
        String gflag = gMap.get("gflag");
        String gkeyword = gMap.get("gkeyword");
        String catagory = gMap.get("catagory");

        //给添加商品弹框的筛选商品下拉输入框设置值
        setWindowSelectGood(gflag);

        //给商品关键字输入框设置值
        setWindowKeyword(gkeyword);

        //给商品分类下拉输入框设置值
        setWindowCatagory(catagory);

        //点击查询按钮,进行商品检索
        clickGoodQueryBar();

        //等待检索页面刷新和稳定后再操作
        driver.sleepWait(100);
        driver.waitForPageLoad();



    }

    //点击查询按钮,进行商品检索
    private void clickGoodQueryBar() {

        pap.click(pap.getGoodQueryBar());
    }

    //给商品分类下拉输入框设置值
    private void setWindowCatagory(String catagory) {
        if (catagory == null || "".equals(catagory.trim())) return;

        String[] caList = catagory.split(">");

        for (int i = 0; i < caList.length; i++) {
            driver.sleepWait(30);
            //获得选择的对应的分类li
            if (i == caList.length-1){
                pap.moveToElementAndclick(pap.getCatagoryLi(caList[i]));
                continue;
            }
            pap.moveToElement(pap.getCatagoryLi(caList[i]));

        }
    }

    //给商品关键字输入框设置值
    private void setWindowKeyword(String gkeyword) {
        pap.sendkeys(pap.getGoodKeyWordInput(),gkeyword);
    }

    //给添加商品弹框的筛选商品下拉输入框设置值
    private void setWindowSelectGood(String gflag) {

        if (gflag == null || "".equals(gflag.trim())){
            return;
        }

        //点击筛选商品输入框
        pap.click(pap.getGoodSelectInput());

        //选择对应的li选项点击,完成赋值
        pap.click(pap.getGoodSelectLi(gflag));

    }

    //设置商品的采购数量
    private void setPurchaseAmountForEveryGood(String amount,String price,String remark) {
        //重新获取页面稳定后的商品列表行
        List<WebElement> rows =pap.getGoodListRows();

        if (rows.size()==0){
            String disabled = pap.getPurchaseAddSureButton().getAttribute("disabled");
            Assert.assertEquals(disabled,"disabled","关联商品时," +
                    "如果没有一个商品被关联上,则确定按钮应该是disabled");
            LogUtils.info("关联商品时,必须至少关联一个商品");
        }

        //填写采购数量,采购数量必须>0
        for (WebElement row : rows) {
            driver.sleepWait(300);
            List<WebElement> cells = pap.getCells(row,"add.cells");

            //填写采购数量
            WebElement purchaseAmount = cells.get(6).findElement(By.tagName("input"));
            pmp.sendkeys( purchaseAmount,amount);

            //填写采购的单价
            WebElement purchasePrice = cells.get(7).findElement(By.tagName("input"));
            pmp.sendkeys(purchasePrice,price );

            //填写采购的备注
            WebElement remarkEle = cells.get(9).findElement(By.tagName("input"));
            pmp.sendkeys(remarkEle,remark);

        }
        LogUtils.info("采购订单关联商品:商品的采购数量信息填写完成");
    }

    //查看订单详情信息
    public void queryAddPurchaseOrderDetail() {

        //获得新增的订单行
        WebElement newRow = getNewAddPurchaseOrder();

        //点击详情按钮,查看订单详情
        clickOrderDetailButton(newRow);

        LogUtils.info("选择刚才新增的订单,查看订单详情");

        //稍等一下
        driver.sleepWait(300);

    }

    //获得根据行,获得该行的订单号
    public String getPurchaseOrderNumber(){

        //新增的采购记录订单
        WebElement newRow = getNewAddPurchaseOrder();
        //获得订单编号单元格
        WebElement orderNumberCell = pmp.getOrderNuberCell(newRow);
        return orderNumberCell.getText();
    }
    //获得新增的订单行
    public WebElement getNewAddPurchaseOrder() {

        LogUtils.info("点击待入库tab标签,切换到待入库订单列表页面");

        //点击待入库tab标签,切换到待入库订单列表页面
        clickToDealTab();
        return pmp.getPurchaseOrderRow();
    }

    //弹出订单详情窗口,对这条订单详情截图保存到指定目录位置
    public void takeScreenPurchaseOrderDetail(String path){
        WebElement detailWindow = pmp.getDetailWindow();
        ImageUtil.screenShotForElement(driver.getDriver(),detailWindow,path);
        LogUtils.info("指定指定编号的订单详情截图保存成功");
    }

    //点击待入库tab标签,切换到待入库订单列表页面
    public void clickToDealTab(){
       pmp.click( pmp.getToDealOrderTabElement());
    }

    //选择刚才新增的订单,点击详情按钮,查看订单详情
    public void clickOrderDetailButton(WebElement orderRow){
        pmp.click(pmp.getPurchaseOrderDetailButton(orderRow));
    }

    //给选择网点赋值
    public void setValueForSelectShop(String shop){
        WebElement shopElement = pap.getSelectShopElement();
        pmp.click(shopElement);
        driver.sleepWait(100);
        WebElement selectShopLiElement = pap.getSelectShopLiElement(shop);
//        pmp.waitEleDisplay(selectShopLiElement,5);
        pmp.click(selectShopLiElement);
    }

    //给供应商赋值
    public void setValueForSelectSuplier(String suplier){
        pmp.click(pap.getSelectSupplierElement());
        pmp.click(pap.getSelectSupplierLiElement(suplier));
    }

    //给预计送达日期赋值
    public void setValueForSelectDatetime(String datetime){
        pmp.sendkeys(pap.getDateElement(),datetime);

    }

    //点击添加商品按钮
    public void clickGoodAddButton(){
        WebElement addgoodrelation = pap.getAddGoodButton();
        pap.click(addgoodrelation);

    }
    //勾选全选按钮
    public void clickFullChooseCheckbox(){
        pap.click(pap.getGoodCheckboxButton());
    }
    //点击商品添加弹出框页面的确定按钮
    public void clickGoodConfirmButton(){
        pmp.click(pap.getGoodConfirmButton());
    }

    //根据商品行记录,删除指定索引处的商品
    public void deleteGoodRelation(List<WebElement> rows,int[] index){

        if (rows.size()>0){

            //删除第一条关联的商品,检验删除功能
            for (int i = 0; i < index.length; i++) {
                int index1 = index[i];
                List<WebElement> cells = pap.getCells(rows.get(index1),"add.cells");
               WebElement deleteButton =  cells.get(cells.size()-1).findElement(By.tagName("button"));
               pmp.click(deleteButton);
               WebElement deleteSureButton = pap.getDeleteSurebutton();
               pmp.click(deleteSureButton);
            }
            LogUtils.info("关联商品时,删除商品功能正常");
        }
    }


    //根据查询条件进行检索操作
    public void searchPurchace(String shopsite, String flag, String goodIdOrName, String orderNumber, String surplier, String man, String start, String end) {

        //设置选择网点
        setSelectShopQuery(shopsite);

        //设置筛选商品
        setSelectGoodByCodeOrName(flag,goodIdOrName);

        //设置采购订单编号
        setOrderNumberQuery(orderNumber);

        //设置供应商
        setSupplierQuery(surplier);

        //设置采购员
        setPurchaseManQuery(man);

        //设置查询日期

        setTimePeriodQuery(start,end);

        //点击查询按钮,开始查询
        clickQueryBar();

        //查询后等待页面加载完毕
        driver.waitForPageLoad();

        //查询结束就进行截图
        takeScreenForQueryResult();
    }

    //弹出订单详情窗口,对这条订单详情截图保存到指定目录位置
    public void takeScreenForQueryResult(){
        WebElement resultTableDiv = pmp.getResultTableDiv();
        ImageUtil.screenShotForElement(driver.getDriver(),resultTableDiv,"purchaseOrderTable_actual");
    }

    //点击查询按钮,开始查询
    private void clickQueryBar() {
        pmp.click(pmp.getQueryBar());
        LogUtils.info("商品查询条件设置完毕,点击查询按钮,正在进行查询");
    }

    //设置查询日期
    private void setTimePeriodQuery(String start, String end) {

        //清除默认时间设置
        pmp.click(pmp.getClearTimeButton());
        if (start == null || "".equals(start.trim()) ||
                end == null || "".equals(end.trim())){
            return ;
        }

        //设置查询开始日期
        pmp.sendkeys(pmp.getStartTimeInput(),start);

        //设置查询结束日期
        pmp.sendkeys(pmp.getEndTimeInput(),end);
    }

    //设置采购员
    private void setPurchaseManQuery(String man) {
        if (man == null || "".equals(man.trim())){
            return ;
        }
        WebElement manInput = pmp.getSelectGoodByPurchaseManInput();
        pmp.click(manInput);
        //TODO 这里无法检索,所以暂时屏蔽掉
        //pmp.click(pmp.getSelectPurchaseManLiEle(man));
    }

    //设置供应商
    private void setSupplierQuery(String supplier) {
        if (supplier == null || "".equals(supplier.trim())){
            return ;
        }
        driver.sleepWait(50);
        //点击供应商下拉选择框
        WebElement suplierEle = pmp.getSelectGoodBySupplierInput();
        pmp.click(suplierEle);
        driver.sleepWait(500);

        //点击选择的对应的供应商li
        pmp.moveToElementAndclick(pmp.getSelectSupplierLiEle(supplier));
    }

    //设置采购订单编号
    private void setOrderNumberQuery(String orderNumber) {
        driver.sleepWait(100);
       pmp.sendkeys( pmp.getSelectGoodByOrderNumberInput(),orderNumber);

    }

    //设置筛选商品
    private void setSelectGoodByCodeOrName(String flag, String keyword) {

        //如果flag为空或空串,表示筛选条件没有此项(不用此项进行检索)
        if (flag != null && !flag.equals("")) {

            //点击筛选商品输入框
            pmp.jsClick(pmp.getSelectGoodByIdOrNameInput());

            //选择按名称或商品编码后四位li,并点击
            WebElement GoodLiEle = pmp.getSelectGoodLiEle(flag);
            if (GoodLiEle != null){
                pmp.click(GoodLiEle);

                //输入查询关键字
                WebElement keywordInput = pmp.getGoodKeyWordInput();
                pmp.sendkeys(keywordInput,keyword);
            }
        }

    }

    //设置选择网点
    private void setSelectShopQuery(String shopsite) {
        if (shopsite == null || "".equals(shopsite.trim())){
            return ;
        }
        WebElement shopInput = pmp.getSelectShopInput();
        pmp.click(shopInput);
       pmp.click( pmp.getSelectShopLiEle(shopsite));
    }

    //校验查询功能
    public void assertSearchPurchace() {
        //校验查询结果

        //1.检查查询结果截图比对

        ImageUtil.imageCompare("purchaseOrderTable_expected",
                "purchaseOrderTable_actual" ,true);

        //2.校验全部=待入库+部分入库+已完成+已关闭

        //获得全部的total
        WebElement totalEle = pmp.getTotalElement(0);
        String totalText = totalEle.getText();
        int alltotal = getMatchStr(totalText, "[^0-9]");

        //获得待入库total
        pmp.click(pmp.getToDealOrderTabElement());
        int toDealTotal = getMatchStr(pmp.getTotalElement(1).getText(),"[^0-9]");

        //获得部分入库total
        pmp.click(pmp.getPatialOrderTabElement());
        int patialTotal = getMatchStr(pmp.getTotalElement(2).getText(),"[^0-9]");

        //获得已完成total
        pmp.click(pmp.getFinishedOrderTabElement());
        int finishTotal = getMatchStr(pmp.getTotalElement(3).getText(),"[^0-9]");

        //获得已关闭total
        pmp.click(pmp.getClosedOrderTabElement());
        int closedTotal = getMatchStr(pmp.getTotalElement(4).getText(),"[^0-9]");

        Assertion.verifyTrue(alltotal==(toDealTotal+patialTotal+finishTotal+closedTotal),"校验全部=待入库+部分入库+已完成+已关闭失败");

    }

    public int getMatchStr(String inputstr,String regEx){
//        String strInput = "3a7s10@5d2a6s17s56;33";

        //Pattern是一个正则表达式经编译后的表现模式
        Pattern p = Pattern.compile(regEx);

        // 一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。
        Matcher m = p.matcher(inputstr);

        //将输入的字符串中非数字部分用空格取代并存入一个字符串
        String string = m.replaceAll(" ").trim();

        //以空格为分割符在讲数字存入一个字符串数组中
        String[] strArr = string.split(" ");

        return Integer.parseInt(strArr[0]);
    }

    //这里因为不知道表存储的结构,所以暂时使用关闭新增的采购订单的方式实现
    public void deleteAddPurchase() {

        //关闭采购订单

        //点击待入库tab标签,切换到待入库订单列表页面
        LogUtils.info("点击待入库tab标签,切换到待入库订单列表页面");
        clickToDealTab();
        //选择刚才新增的订单,点击关闭按钮,关闭订单
        clickOrderCloseButton();

    }

    //选择订单,点击关闭按钮,关闭订单
    public void clickOrderCloseButton(){
        pmp.click(pmp.getPurchaseOrderCloseButton(pmp.getPurchaseOrderRow()));
    }

    //修改采购单操作
    public void editPurchace(String orderNumber, String arrivalTime, String purchaseAmount, String remark) {

        //查询出指定订单编号的采购单
        WebElement row = searchOrderByordercode(orderNumber);

        WebElement orderEditButton = pmp.getOrderEditButton(row);
        pmp.click(orderEditButton);

        driver.sleepWait(500);

        //编辑预计送达时间
        pmp.sendkeys(pmp.getArrivalTime(),arrivalTime);

        //编辑采购数量,采购价格,采购备注等
        setPurchaseAmountForEveryGood(purchaseAmount,"10",remark);

        //点击确认按钮,保存信息
        pmp.click(pap.getEditSureButton());
        LogUtils.info("点击确定按钮,提交修改后的商品订单");


    }



    //根据编号查询出指定订单
    private WebElement searchOrderByordercode(String orderNumber) {
        //设置采购订单编号
        setOrderNumberQuery(orderNumber);

        //清除默认的查询时间
        setTimePeriodQuery("","");

        //点击查询按钮,开始查询
        clickQueryBar();

        //等待一会,让页面稳定
        driver.sleepWait(500);

        //选择这个采购单,点击编辑按钮,进入到编辑页面
        WebElement row = pmp.getPurchaseOrderList().get(0);

        return row;
    }

    //查询修改后的采购单详情,并截图保存详情页面
    public void queryEditPurchaseOrderDetail(String orderNumber) {
        //编辑成功后,等待页面稳定
        driver.sleepWait(2000);
        //查询出指定订单编号的采购单
        WebElement row = searchOrderByordercode(orderNumber);

        //选择这个采购单,点击详情按钮,弹出详情弹出框
        clickOrderDetailButton(row);

        //截图详情弹出框并保存
        takeScreenPurchaseOrderDetail("purchaseEdit_actual");


    }

    //采购单导出功能
    public void exportPurchaseOrder(String orderNumber) {

        //等待一会让页面稳定
        driver.sleepWait(2000);
        //查询出指定订单编号的采购单
        WebElement row = searchOrderByordercode(orderNumber);

        //点击导出按钮前,先清理一遍下载目录中之前的下载记录文件(旧文件)
       clearOldfiles(orderNumber);

        //点击相应的采购订单,点击导出按钮,导出相应订单
        WebElement exportOrderButton = pmp.getExportOrderButton(row);
        pmp.click(exportOrderButton);
        LogUtils.info("正在执行采购单导出");

        //点击导出后等3s ,预计下载完毕后再操作
        driver.sleepWait(2000);

        //检查点击导出按钮后是否有指定的文件下载完毕,
        // 如果不抛出指定异常,则文件一定下载成功(且只有一份,不含重名的文件)
        String[] files = FileFilterUtil.listFileName(Constants.DOWNLOADDIR, orderNumber);
        if (files == null || files.length == 0){
            int i = 0;
            while (i<=180){
                //没有下载完毕,则等待一会
                driver.sleepWait(100);
                files = FileFilterUtil.listFileName(Constants.DOWNLOADDIR, orderNumber);
                if (files.length>0) break;
                i++;
            }
            if (i > 180){
                LogUtils.error("文件导出超时(20s),请重新尝试导出");
                throw  new RuntimeException("自定义文件导出超时异常");
            }
        }

    }

    //清理下载目录下指定的文件
    private void clearOldfiles(String orderNumber) {
        File[] files = FileFilterUtil.listFile(Constants.DOWNLOADDIR, orderNumber);
        if(files != null && files.length>0){
            for (File file : files) {
                file.delete();
            }
        }
    }

    //验证导出功能正确
    public void assertExportPurchase(String orderNumber) {

        File[] files = FileFilterUtil.listFile(Constants.DOWNLOADDIR, orderNumber);
        if (files == null || files.length == 0){
            Assertion.verifyTrue(false,"断言文件导出功能失败,原因是未找到指定导出的订单excel文件");
        }
        //获得实际的sheet的值
        String[][] actualData = ExcelDataProvider.getSheetData(Constants.DOWNLOADDIR+files[0].getName());
        LogUtils.info("从导出的excel文件中读取到了实际的订单数据");
        //获得预期的sheet值
        String[][] expectData = ExcelDataProvider.getSheetData(Constants.XLS_EXPECTEDDIR+"供应商-00001号供应商(2019049420553130128).xlsx");
        LogUtils.info("从期望的excel文件中读取到了期望的订单数据");
        //检验值是否和预期一致

        /**
         * 1、deepEquals用于判定两个指定数组彼此是否深层相等，此方法适用于任意深度的嵌套数组。
         * 2、equals用于判定两个数组是否相等，如果两个数组以相同顺序包含相同元素，则返回true，否则返回false。
         **/
        //boolean equals = Arrays.equals(actualData, expectData);
        boolean equals = Arrays.deepEquals(actualData, expectData);

        Assertion.verifyTrue(equals,"导出的excel表格数据和预期不一致");
        LogUtils.info("实际的订单数据和期望的订单数据匹配");

    }


    //断言新增的采购订单
    public void assertAddPurchase() {

        //查看新增后的采购订单详情
        queryAddPurchaseOrderDetail();
        LogUtils.info("查看新增后的采购订单详情");

        //截图保存订单详情信息
        LogUtils.info("开始截图保存订单详情信息");

        //弹出订单详情窗口,对这条订单详情截图保存到指定目录位置
        takeScreenPurchaseOrderDetail("purchaseOrderDetail_actual");
        LogUtils.info("截图保存订单详情信息完毕");

        //稍等一下
        driver.sleepWait(100);

        //断言actual截图和expected截图
        LogUtils.info("开始比对订单详情截图");
        ImageUtil.imageCompare("purchaseOrderDetail_expected","purchaseOrderDetail_actual",false);
        LogUtils.info("订单详情截图比对结束");
    }
}
