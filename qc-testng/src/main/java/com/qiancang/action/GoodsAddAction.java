package com.qiancang.action;

import com.qiancang.basic.DriverBase;
import com.qiancang.basic.log.LogUtils;
import com.qiancang.page.GoodsAddPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 商品管理action类
 */
public class GoodsAddAction {
    private DriverBase driverBase;
    private GoodsAddPage gap;

    public GoodsAddAction(DriverBase driver) {
        this.driverBase = driver;
        gap = new GoodsAddPage(driver);
    }

    /**
     * @Description 添加商品业务操作:填写商品相关信息,然后保存
     **/
    public void addOrEditGoods(String goodcode, String goodname, String goodcatagary, String period,
                         String saleperiod, String unit, String guige, String price,String state,Boolean isnewadd) {

        boolean wait = gap.waitConfirmButtonDisplay();
        if (wait){
            WebElement confirm =gap.getConfirmElement();
            if (isnewadd){
                jsClick(driverBase,confirm);
                LogUtils.info("第一次点击确定按钮,设置商品的信息");
                driverBase.sleepWait(100);
                //设置商品编码
                setGoodCode(goodcode);

                //设置商品名称
                setGoodName(goodname);

            }
            //设置商品分类
            setGoodCatogory(goodcatagary);

            //设置库存单位
            setGoodUnit(unit);

            //设置规格
            setGoodSpecification(guige);

            //设置采购参考价
            setGoodSuggestPrice(price);

            //设置销售状态
            selectSaleState(state);

            LogUtils.info("商品的必填信息填写完毕");
            jsClick(driverBase,confirm);
            if (isnewadd){
                LogUtils.info("第二次点击确定按钮,提交商品信息");
            }else{
                LogUtils.info("商品信息修改完毕,点击确定按钮,提交商品信息");
            }
            driverBase.sleepWait(5000);

        }

    }

    //设置采购参考价
    private void setGoodSuggestPrice(String price) {

        WebElement goodsuggestprice = gap.getGoodSuggestPriceElement();
        if (price != null && !price.trim().equals("") ){

            gap.sendkeys(goodsuggestprice,price);

        }
    }

    //设置规格
    private void setGoodSpecification(String guige) {

        WebElement goodguigeElement = gap.getGoodGuiGeElement();
        if (guige != null && !guige.trim().equals("") ){

            gap.sendkeys(goodguigeElement,guige);

        }
    }

    //设置库存单位
    private void setGoodUnit(String unit) {

        if (unit != null && !unit.trim().equals("")) {

            WebElement goodunit = gap.getGoodUnitElement();
            gap.jsClick(goodunit);

            WebElement goodunit_ul = gap.getUnitUlElement();
            WebElement goodunit_li = goodunit_ul.findElement(By.xpath("//*[text()='" + unit.trim() + "']"));
            gap.jsClick(goodunit_li);

        }
    }

    //设置商品分类
    private void setGoodCatogory(String goodcatagary) {

        if (goodcatagary == null || "".equals(goodcatagary.trim())){
            return ;
        }
        //点击商品分类下拉选择框
        gap.click( gap.getGoodCatageryElement());

        String[] catogoryList = goodcatagary.split(">");
        for (int i = 0; i < catogoryList.length; i++) {

            //获得选择的对应的分类li
            if (i == catogoryList.length-1){
                gap.moveToElementAndclick(gap.getCatagoryLi(catogoryList[i]));
                continue;
            }
            driverBase.sleepWait(100);
            gap.moveToElement(gap.getCatagoryLi(catogoryList[i]));

        }

    }


    //获得商品详情信息
    public  Map<String,String>  getGoodDetail() {
        Map<String,String> map = new HashMap<>();

        gap.implicitlyWait(3);
        //获得库存单位
        WebElement goodunit_ul = gap.getUnitUlElement();
        WebElement goodunit_li  = goodunit_ul.findElement(By.xpath("//li[contains(@class,'selected')]"));
        //String goodunit = null;
        //goodunit = goodunit_li.findElement(By.tagName("span")).getText();
        JavascriptExecutor js = (JavascriptExecutor) driverBase.getDriver();
        String goodunit= (String) js.executeScript("return arguments[0].innerHTML", goodunit_li.findElement(By.tagName("span")));

        //获得规格
        WebElement goodguigeElement = gap.getGoodGuiGeElement();
        String goodguige = getTypeValue(driverBase.getDriver(), goodguigeElement);



        //获得采购参考价
        WebElement goodsuggestprice = gap.getGoodSuggestPriceElement();
        String price = getTypeValue(driverBase.getDriver(), goodsuggestprice);


        //获得销售状态

        map.put("unit",goodunit);
        map.put("guige",goodguige);
        map.put("price",price);


        return map;


    }




    /**
     * @Description 通过js 直接给表单元素如input赋值,即给value属性设置值
     **/
    public  void setTypeValue(WebDriver driver,WebElement element, String values)  {
        LogUtils.info("给表单元素赋值,value属性值为:  " + values);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value=\""+values+"\"", element);
//        js.executeScript("alert(arguments[0].value);", element);
    }

    /**
     * @Description 通过js 直接给表单元素取值,即取value属性值
     **/
    public String  getTypeValue(WebDriver driver,WebElement element)  {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String value  = js.executeScript("return arguments[0].value", element).toString();
        LogUtils.info("取表单元素值,value属性值为:  " + value);
        return value;
    }

    /**
     * @Description 原因：用selenium模拟用户单击元素时，JS有一个操作鼠标悬浮的时候会对元素进行修改
     * 报错为://ElementNotVisibleException: element not interactable
     * 解决办法：用JS来操作元素(使用等待好像也可以解决)
     * @Param * driverBase
     * @param element
     * @return void
     **/
    public void jsClick(DriverBase driverBase ,WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driverBase.getDriver();
        js.executeScript("arguments[0].click();",element);
    }
    /**
     * @Description 输入商品编号
     **/
    public void setGoodCode(String code){
        driverBase.sleepWait(100);
        gap.sendkeys(gap.getGoodCodeElement(),code);
    }
    /**
     * @Description 输入商品名称
     **/
    public void setGoodName(String name){
        gap.sendkeys(gap.getGoodNameElement(),name);
    }
    /**
     * @Description 选择商品分类
     **/
    public void selectGoodCategory(String Category){
        gap.sendkeys(gap.getGoodNameElement(),Category);
    }
    /**
     * @Description 选择库存单位
     **/
    public void selectUnit(String unit){
        gap.sendkeys(gap.getGoodUnitElement(),unit);
    }
    /**
     * @Description 输入规格
     **/
    public void setguige(String guige){
        gap.sendkeys(gap.getGoodGuiGeElement(),guige);
    }
    /**
     * @Description 输入采购参考价
     **/
    public void setsuggestprice(String suggestprice){
        gap.sendkeys(gap.getGoodSuggestPriceElement(),suggestprice);
    }

    /**
     * @Description 选择销售状态
     **/
    public void selectSaleState(String state){
        //1:在售 2:停售
        if ("1".equals(state)){
            gap.click(gap.getGoodSaleStateElement().get(0));

        }else {
            gap.click(gap.getGoodSaleStateElement().get(1));
        }
    }
    /**
     * @Description 点击确定按钮
     **/
    public void clickConfirm(){
        gap.click(gap.getConfirmElement());
    }

    /**
     * @Description 点击取消按钮
     **/
    public void clickCancel(String suggestprice){
        gap.click(gap.getCancelElement());
    }



}
