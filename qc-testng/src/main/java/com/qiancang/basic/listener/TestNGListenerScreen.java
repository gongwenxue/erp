package com.qiancang.basic.listener;

import com.qiancang.basic.CaseBase;
import com.qiancang.basic.log.LogUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.Arrays;


/**自定义testng测试监听器：
 * 作用：用于监听用例执行失败后执行自动截图功能,以及用例执行失败重跑功能的实现
 *
 */
public class TestNGListenerScreen extends TestListenerAdapter{

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        CaseBase b = (CaseBase) tr.getInstance();
        LogUtils.info(getTestClassAndMethodName(tr)+":该用例执行成功");
        //LogUtils.info("-------------------- success line ----------------------------");

    }

    
    /*主要重写测试失败的监听事件：
     *方法增强：当测试失败后，不仅调用父类方法执行测试失败的检测任务，并且增加截图功能
     * */
    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        LogUtils.info( getTestClassAndMethodName(tr)+":该用例执行失败,正在进行截图...");
        CaseBase b = (CaseBase) tr.getInstance();
        b.takeScreenShot();
        //LogUtils.info("-------------------- failed line -----------------------------");

    }
    /**
     * @Description 获得当前正在执行的测试用例类和方法名称
     **/
    public String getTestClassAndMethodName(ITestResult it){
        return it.getTestClass().getName()+"."+it.getMethod().getMethodName();
    }


    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        CaseBase b = (CaseBase) tr.getInstance();
        LogUtils.info(getTestClassAndMethodName(tr)+":该用例skipped");
        //LogUtils.info("-------------------- skiped line -----------------------------");


    }

    @Override
    public void onTestStart(ITestResult tr) {
        LogUtils.info(getTestClassAndMethodName(tr)+":该用例开始执行");
        super.onTestStart(tr);
    }

    @Override
    public void onStart(ITestContext testContext) {
        super.onStart(testContext);
    }

    /**
     * @Description 添加了重跑功能后会发现测试结果的邮件中用例的个数增加了，比如我只有一个用例，失败重跑了2次，一共运行3次，
     * 测试结果中显示的用例个数会是3个，那接下来就需要解决这个问题了。
     * 首先解决TestNg生成的index.html文件中个数不对的问题，这个问题只需要在Testng监听器的onFinish方法中，
     * 等所有用例运行完之后，检查用例，按照class+method+dataprodiver的名称生成hashcode获取唯一id，
     * 如果fail的用例中存在重复的则在fail的用例中剔除掉
     * 当前失败重跑也存在一些小问题：
     * 1、setup中出现的错误直接是skip的，不会重跑
     * 2、如果存在dataprodiver，则第二个参数以后的用例是不会重跑的
     * @Param * @param testContext
     * @return void
     **/
    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
//        ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
//        // collect all id's from passed test
//        Set<Integer> passedTestIds = new HashSet<Integer>();
//        for (ITestResult passedTest : testContext.getPassedTests()
//                .getAllResults()) {
//            //logger.info("PassedTests = " + passedTest.getName());
//            passedTestIds.add(getId(passedTest));
//        }
//
//        Set<Integer> failedTestIds = new HashSet<Integer>();
//        for (ITestResult failedTest : testContext.getFailedTests()
//                .getAllResults()) {
//            //logger.info("failedTest = " + failedTest.getName());
//            int failedTestId = getId(failedTest);
//
//            // if we saw this test as a failed test before we mark as to be
//            // deleted
//            // or delete this failed test if there is at least one passed
//            // version
//            if (failedTestIds.contains(failedTestId)
//                    || passedTestIds.contains(failedTestId)) {
//                testsToBeRemoved.add(failedTest);
//            } else {
//                failedTestIds.add(failedTestId);
//            }
//        }
//
//        // finally delete all tests that are marked
//        for (Iterator<ITestResult> iterator = testContext.getFailedTests()
//                .getAllResults().iterator(); iterator.hasNext();) {
//            ITestResult testResult = iterator.next();
//            if (testsToBeRemoved.contains(testResult)) {
//                //logger.info("Remove repeat Fail Test: " + testResult.getName());
//                iterator.remove();
//            }
//        }

    }

    private int getId(ITestResult result) {
        int id = result.getTestClass().getName().hashCode();
        id = id + result.getMethod().getMethodName().hashCode();
        id = id + (result.getParameters() != null ? Arrays.hashCode(result
                        .getParameters()) : 0);
        return id;
    }
}
















