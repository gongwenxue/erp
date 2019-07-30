package com.qiancang.action;

import com.qiancang.basic.DriverBase;
import com.qiancang.basic.log.LogUtils;
import com.qiancang.basic.util.Assertion;
import com.qiancang.basic.util.ImageUtil;
import com.qiancang.page.GoodManagePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description: 商品管理action类
 */
public class GoodsManageAction {
    private DriverBase driverBase;
    private GoodManagePage gmp;
    private GoodsAddAction gaa;

    public GoodsManageAction(DriverBase driver) {
        this.driverBase = driver;
        this.gmp = new GoodManagePage(driver);
        this.gaa = new GoodsAddAction(driver);
    }


    /**
     * Description: 根据条件,搜索符合条件的商品
     */
    public void searchGoods(String goodcode, String goodname, String goodcatagary, String supplier,String goodflag) {

        //等待一下,让页面请求下拉框数据并加载完成后操作

        //等待新增按钮出现
        WebElement addGoodsEle = gmp.waitAddGoodsButton();
        LogUtils.info("新增按钮存在,已经进入到商品管理页面");

        //设置筛选商品条件
        setGoodIdOrNameQuery(goodcode, goodname, goodflag);

        //设置商品分类筛选条件
        setCatogoryQuery(goodcatagary);

        //设置供应商筛选条件
        setSupplierQuery(supplier);

        //点击查询按钮
        clickQueryBar();

        //查询后等待页面刷新和加载完毕
        driverBase.waitForPageLoad();
    }

    //设置供应商筛选条件
    private void setSupplierQuery(String supplier) {
        if (supplier == null || "".equals(supplier.trim())){
            return ;
        }
        driverBase.sleepWait(30);
        //点击供应商下拉选择框
        WebElement suplierEle = gmp.getSelectSuplierEle();
        gmp.click(suplierEle);

        //点击选择的对应的供应商li
        gmp.moveToElementAndclick(gmp.getSupplierLi(supplier));

    }

    //设置商品分类筛选条件
    private void setCatogoryQuery(String goodcatagary) {

        if (goodcatagary == null || "".equals(goodcatagary.trim())){
            return ;
        }
        driverBase.sleepWait(30);
        //点击商品分类下拉选择框
        gmp.click( gmp.getGoodCatageryElement());

        String[] catogoryList = goodcatagary.split(">");
        for (int i = 0; i < catogoryList.length; i++) {
            driverBase.sleepWait(30);
            //获得选择的对应的分类li
            if (i == catogoryList.length-1){
                gmp.moveToElementAndclick(gmp.getCatagoryLi(catogoryList[i]));
                continue;
            }
            gmp.moveToElement(gmp.getCatagoryLi(catogoryList[i]));

        }
    }


    //点击查询按钮,进行查询操作
    public void clickQueryBar(){
        gmp.click(gmp.getQueryBar());
        LogUtils.info("点击查询按钮,开始进行查询");
    };
    //给商品id或name查询框赋值
    private void setGoodIdOrNameQuery(String goodcode, String goodname, String goodflag) {
        driverBase.sleepWait(30);
        gmp.jsClick(gmp.getSelectGoodButton());
        WebElement keyword = gmp.getGoodKeywordInput();
        if (goodflag != null && !goodflag.equals("")){
            if ("1".equals(goodflag)){//根据编码检索
                WebElement codelast4numbersearch = gmp.getLast4CodeEle();
                codelast4numbersearch.click();
                keyword.sendKeys(goodcode);
            }else {//根据商品名称检索
                WebElement codenamesearch = gmp.getGoodNameEle();
                gmp.jsClick(codenamesearch);
                keyword.sendKeys(goodname);
            }
        }
    }


    /**
     * Description: 添加商品业务操作:填写商品相关信息,然后保存
     */
    public void addGoods(String goodcode, String goodname, String goodcatagary, String period,
                         String saleperiod, String unit, String guige, String price,String state) {
        driverBase.waitForPageLoad();
        WebElement addgoods = gmp.waitAddGoodsButton();
        LogUtils.info("新增按钮存在,准备点击新增按钮");
        JavascriptExecutor js = (JavascriptExecutor) driverBase.getDriver();
        js.executeScript("arguments[0].click();",addgoods);
        gaa.addOrEditGoods(goodcode, goodname,goodcatagary,period,
                saleperiod,unit,guige,price,state,true);

    }


    /**
     * Description: 点击新增商品按钮
     */
    public void clickNewAddButton(){
        gmp.click(gmp.getNewAddButton());
    }
    /**
     * Description: 点击最后一页按钮
     */
    public void clickLastPagenumberButton(){
        gmp.click(gmp.getLastPagenumberElement());
    }

    /**
     * Description: 获得最后一行的各个单元格的值
     */
    public List<String> getCellsText(WebElement rowElement){
        ArrayList<String> cellvalues = new ArrayList<>();

        List<WebElement> cells = rowElement.findElements(By.tagName("td"));
        for (WebElement cell : cells) {
           cellvalues.add( cell.getText());
        }
        return cellvalues;
    }



    /**
     * @Description 填写编辑商品信息
     * @Param * @param period
     * @param saleperiod
     * @param unit
     * @param guige
     * @param price
     * @param state
     * @return void
     **/
    public void editGoods(String goodcatagary,String period, String saleperiod, String unit, String guige, String price, String state) {

        //获得操作按钮
        List<WebElement> buttons = getEditAndDetailButton();
        //点击编辑按钮,进入编辑页面
        this.jsClick(driverBase,buttons.get(1));
        LogUtils.info("点击编辑按钮,进入商品修改页面");

        //修改商品信息并保存
        gaa.addOrEditGoods(null,null,goodcatagary,period,  saleperiod,  unit,  guige,  price,  state,false);

        //修改完成自动跳转到商品管理主页面,获得第一行的按钮
//        BasePage.implicitlyWait(driverBase,2);
        buttons = getEditAndDetailButton();

        //点击详情按钮,跳转到详情页面
        this.jsClick(driverBase,buttons.get(0));

        //获得详情页面的对应商品的各个属性值
        Map<String,String> map = gaa.getGoodDetail();

        //断言设置:判断是否修改成功
        Assertion.verifyEquals(map.get("unit"),unit==""?map.get("unit"):unit,"库存单位和期望不一致");
//        Assertion.verifyEquals(map.get("guige"),guige==""?map.get("guige"):guige,"规格和期望不一致");
        Assertion.verifyEquals(map.get("price"),price==""?map.get("price"):price,"建议价格和期望不一致");


    }

    //编辑商品,默认修改的是第一页的第一行的那个商品信息
    public List<WebElement> getEditAndDetailButton() {

        LogUtils.info("已经进入到商品管理页面,且元素加载完毕");

        //定位到当前页列表table 的所有行
        List<WebElement> elements = driverBase.getDriver().findElements(By.className("el-table__body-wrapper"));
        List<WebElement> trlist = elements.get(0).findElements(By.tagName("tr"));

        //定位到第一行的所有列td
        List<WebElement> tds = trlist.get(0).findElements(By.tagName("td"));

        //获得操作按钮:详情和编辑
        List<WebElement> buttons = tds.get(tds.size() - 1).findElements(By.tagName("button"));

        return buttons;


    }


        public void jsClick(DriverBase driverBase, WebElement element){
            JavascriptExecutor js = (JavascriptExecutor) driverBase.getDriver();
            js.executeScript("arguments[0].click();", element);
        }

    //对查询结果进行校验:比对查询到的所有行的记录的code,name,catagory,supplier的值是否和查询条件一致
    //只校验了查询结果的第一页数据,即前10行数据
    public void checkSearchResults(String goodcode,String goodname,String goodcatagary,String supplier,String goodflag) {

        List<WebElement> goodrows = gmp.getFirstPageRows();
        for (WebElement goodrow : goodrows) {
            List<WebElement> cells = gmp.getCells(goodrow,"goodcells");

            //商品序号
            String rownumber = cells.get(0).getText();
            //商品分类
            String catagory = cells.get(1).getText();
            //商品编码
            String code = cells.get(2).getText();
            //商品名称
            String name = cells.get(3).getText();
            //商品供应商

            if (goodcatagary != null && !"".equals(goodcatagary)){
                //这里分类无法断言,原因:如果分类是一级的分类,它会查出子分类即2级3级分类,但是展示的名字是最后一级的分类名字
               // Assertion.verifyEquals(catagory,goodcatagary,"行序号为"+rownumber+"的行,商品分类和预期不一致");
            }
            if ("1".equals(goodflag)){
                if (goodcode != null && !"".equals(goodcode)){
                    //商品编号不区分大小写 商品编号后四位要一致,断言时候要模糊

                    Assertion.verifyEquals(code.substring(code.length()-4).toLowerCase(),
                            goodcode.substring(goodcode.length()-4).toLowerCase(),
                            "行序号为"+rownumber+"的行,商品编码和预期不一致");
                }
            }else {
                if (goodname != null && !"".equals(goodname)){

                    //名称是模糊查询,断言时候要模糊
                    Assertion.verifyTrue(name.contains(goodname),"行序号为"+rownumber+"的行,商品名称和预期不一致");
                }
            }



            //商品供应商 //TODO 供应商查询需要关联供应商页面:这个页面有商品和供应商关联的信息,可以比较总条数是否一致进行校验
            if (supplier != null && !"".equals(supplier)){
                //Assertion.verifyEquals(supplier,supplier,"供应商和预期不一致");
            }

        }
    }

    //断言添加功能是否正确
    public void assertAddGoods() {

        //等待页面加载完毕
        driverBase.waitForPageLoad();

        //点击列表页最后一页,进入到最后一页
        clickLastPageButton();

        //选择最后一行,点击最后一行的详情按钮
        selectLastRowAndClickDetailButton();

        //截图详情页
        takeScreenShotDetailPage();

        //比对截图
        ImageUtil.imageCompare("goodDetail_expected","goodDetail_actual",false);
    }

    //截图详情页
    private void takeScreenShotDetailPage() {

        //获得详情页表单元素
        WebElement detailForm = gmp.getDetailForm();
        ImageUtil.screenShotForElement(driverBase.getDriver(),detailForm,"goodDetail_actual.png");
    }

    //选择最后一行,点击最后一行的详情按钮
    private void selectLastRowAndClickDetailButton() {

        WebElement lastDetailButton = gmp.getLastDetailButton();
        gmp.click(lastDetailButton);
        driverBase.waitForPageLoad();
    }

    //点击列表页最后一页
    public void clickLastPageButton(){

        //获得最后一页按钮标签元素
        WebElement lastPagenumberElement = gmp.getLastPagenumberElement();

        //点击该标签,跳转到最后一页
        gmp.click(lastPagenumberElement);

        //等待一会,让页面元素加载完毕再继续操作
        driverBase.waitForPageLoad();
        driverBase.sleepWait(100);
    }

    //删除新增的记录
    public void deleteAddGoods() {
        //这里因为不知道表存储的结构,所以暂时未实现

    }
}
