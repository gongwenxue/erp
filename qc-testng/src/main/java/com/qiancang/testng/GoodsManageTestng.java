package com.qiancang.testng;

import com.qiancang.action.GoodsManageAction;
import com.qiancang.basic.CaseBase;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.listener.TestNGListenerScreen;
import com.qiancang.basic.log.LogUtils;
import com.qiancang.basic.util.Assertion;
import com.qiancang.basic.util.HandleCookie;
import com.qiancang.basic.util.ProUtil;
import com.qiancang.basic.util.RandomUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Description: 商品管理测试用例类
 */
@Listeners({TestNGListenerScreen.class})
public class GoodsManageTestng extends CaseBase {

    private DriverBase driver;
    private GoodsManageAction gma ;
    private ProUtil pro;
    private HandleCookie handcookie;


    @BeforeMethod
    public void beforeMethod() {
        this.driver = CaseBase.driver;
        pro = new ProUtil(Constants.CFG_GOODSMANAGETESTNG);
        gma = new GoodsManageAction(driver);
        driver.sleepWait(500);
        driver.waitForPageLoad();
        driver.get(pro.getPro("goodmanage.url"));
    }

    @BeforeSuite
    @Override
    public void BeforeSuite() {
        super.BeforeSuite();

    }

    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin"})
    public void testSearchGoods() {

        String goodcode = pro.getPro("goodmanage.goodcode");
        String goodname = pro.getPro("goodmanage.goodname");
        String goodcatagary = pro.getPro("goodmanage.goodcatagary");
        String supplier = pro.getPro("goodmanage.supplier");
        String goodflag = pro.getPro("goodmanage.goodflag");

        LogUtils.info("已经从配置文件中提取到了商品的搜索条件");
        LogUtils.info("开始执行商品筛选操作");
        gma.searchGoods(goodcode,goodname,goodcatagary,supplier,goodflag);

        //对查询结果进行校验:判断查询结果是否符合预期
        gma.checkSearchResults(goodcode,goodname,goodcatagary,supplier,goodflag);
        Assertion.assertFinally("商品查询");



    }

    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin"})
    public void testAddGoods() {


        String goodcode = pro.getPro("goodadd.goodcode");
        goodcode = goodcode+RandomUtil.getRandomString(5);

        String goodname = pro.getPro("goodadd.goodname");
        String goodcatagary = pro.getPro("goodadd.goodcatagary");
        String period = pro.getPro("goodadd.period");
        String saleperiod = pro.getPro("goodadd.saleperiod");
        String unit = pro.getPro("goodadd.unit");

        String guige = pro.getPro("goodadd.guige");
        String price = pro.getPro("goodadd.suggestprice");
        String state = pro.getPro("goodadd.state");
        LogUtils.info("已经准备好了商品信息:从配置文件中提取到了商品的各项信息");
        LogUtils.info("开始执行添加商品操作");
        gma.addGoods(goodcode,goodname,goodcatagary,period,saleperiod,unit,
                guige,price,state);
        gma.assertAddGoods();
        //最终断言:判断商品添加逻辑是否正确,商品修改是否成功
        //添加成功后断言goodcode的这条记录是否存在,存在则pass
        Assertion.assertFinally("商品添加");

        //恢复环境(即删除新增的这条记录)
        gma.deleteAddGoods();
    }


    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin"})
    public void testEditGoods() {
        String goodcatagary = pro.getPro("goodEdit.goodcatagary");
        String period = pro.getPro("goodEdit.period");
        String saleperiod = pro.getPro("goodEdit.saleperiod");
        String unit = pro.getPro("goodEdit.unit");

        String guige = pro.getPro("goodEdit.guige");
        String price = pro.getPro("goodEdit.suggestprice");
        String state = pro.getPro("goodEdit.state");
        LogUtils.info("已经准备好了商品信息:从配置文件中提取到了商品的编辑信息");
        LogUtils.info("开始执行编辑商品信息操作");
        gma.editGoods(goodcatagary,period,saleperiod,unit,guige,price,state);

        //最终断言:判断商品修改逻辑是否正确,商品修改是否成功
        Assertion.assertFinally("商品修改");


    }
}
