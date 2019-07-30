package com.qiancang.testng;

import com.qiancang.action.GoodAssociateAction;
import com.qiancang.action.SupplierManageAction;
import com.qiancang.basic.CaseBase;
import com.qiancang.basic.Constants;
import com.qiancang.basic.DriverBase;
import com.qiancang.basic.listener.TestNGListenerScreen;
import com.qiancang.basic.log.LogUtils;
import com.qiancang.basic.util.Assertion;
import com.qiancang.basic.util.ProUtil;
import com.qiancang.basic.util.RandomUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Description: 采购管理类测试用例集
 */

@Listeners({TestNGListenerScreen.class})
public class SupplierManageTestng extends CaseBase {

    private DriverBase driver;
    private ProUtil pro;
    private SupplierManageAction sma ;
    private GoodAssociateAction gaa ;


    @BeforeMethod
    public void beforeMethod() {
        this.driver = CaseBase.driver;
        pro = new ProUtil(Constants.CFG_SUPPLIERMANAGETESTNG);
        sma = new SupplierManageAction(driver);
        gaa = new GoodAssociateAction(driver);

        driver.sleepWait(300);
        driver.waitForPageLoad();
        driver.get(pro.getPro("supplierManage.url"));
        driver.waitForPageLoad();

    }

    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin"})
    public void testsearchSupplier() {

        //读取查询参数
        String sKeyword = null;

        String flag = this.pro.getPro("search.flag");
        String snumber = this.pro.getPro("search.snumber");
        String sname = this.pro.getPro("search.sname");

        if ("1".equals(flag)){
            sKeyword = snumber;
        }else {
            sKeyword = sname;
        }

        //开始查询
        sma.searchSupplier(flag,sKeyword);

        //断言查询结果
        sma.assertSearchSupplier();

        //最终断言
        Assertion.assertFinally("供应商查询");
    }



    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin"})
    public void testAddSupplier() {

        //读取参数
        String snumber = this.pro.getPro("supplierAdd.snumber");
        snumber = snumber + RandomUtil.getRandomString(5);
        String sname = this.pro.getPro("supplierAdd.sname");
        String site = this.pro.getPro("supplierAdd.site");

        String manager = this.pro.getPro("supplierAdd.manager");
        String email = this.pro.getPro("supplierAdd.email");
        String phoneNumber = this.pro.getPro("supplierAdd.phoneNumber");

        String accountbank = this.pro.getPro("supplierAdd.accountbank");
        String accName = this.pro.getPro("supplierAdd.accName");
        String account = this.pro.getPro("supplierAdd.account");

        String taxNumber = this.pro.getPro("supplierAdd.taxNumber");
        String remark = this.pro.getPro("supplierAdd.remark");


        //添加供应商
        sma.addSupplier(snumber,sname,site,manager,email,
                       phoneNumber,accountbank,accName,account,taxNumber,
                       remark);

        //断言添加功能是否正确
        sma.assertAddSupplier(snumber);

        //最终断言
        Assertion.assertFinally("供应商新增");


        //初始化环境,删除这条新增的供应商
        //ToDo

    }


    @Test(dependsOnMethods={"com.qiancang.testng.LoginTestng.testCorrectLogin"})
    public void testAssociateGood() {

        //准备工作,读取配置信息

        String snumber = this.pro.getPro("sg.snumber");
        String goodflag = this.pro.getPro("sg.goodflag");
        String goodname = this.pro.getPro("sg.goodname");
        LogUtils.info("准备工作,读取配置信息完成");

        //进行关联操作
        LogUtils.info("开始进行商品关联操作->>");
        gaa.associateGood(snumber,goodflag,goodname);
        LogUtils.info("<<-商品关联操作完成");

        //对关联功能进行断言
        LogUtils.info("开始进行商品关联功能断言->>");
        gaa.assertAssociateGood();
        LogUtils.info("<<-商品关联功能断言结束");

        //最终断言
        Assertion.assertFinally("供应商关联商品");


    }



}
