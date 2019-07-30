package com.qiancang.basic.util;

import com.qiancang.basic.log.LogUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * Description: 图片处理工具类
 */
public class ImageUtils {

    public static void imageCompare(String expectedImgPath, String actualImgPath,Boolean strictMacth,int... expPercent){

        //实际相似度
        float similiar = 0;
        //预期相似度,默认值95%
        float ep = 0.95f;
        if (expPercent != null && expPercent.length>0){
            ep = (expPercent[0])/100f;
        }

        String currentPath = System.getProperty("user.dir");
        expectedImgPath =currentPath+"\\file\\screenshot\\expected\\"+expectedImgPath;
        actualImgPath =currentPath+"\\file\\screenshot\\actual\\"+actualImgPath;
        if (!StringUtils.endsWithIgnoreCase(expectedImgPath, ".png")) {
            expectedImgPath += ".png";
        }
        if (!StringUtils.endsWithIgnoreCase(actualImgPath, ".png")) {
            actualImgPath += ".png";
        }

        //生成了两个文件对象，一个是期望的图片，一个是实际测试过程中产生的图片
        Boolean matchFlag = true;;
        try {
            File fileInput = new File(expectedImgPath);
            File fileOutPut = new File(actualImgPath);
            /*
             以下部分为两个文件进行像素比对的算法实现，获取文件的像素个数大小，然后使用循环的方式将两张图片的
             所有项目进行一一对比，如有任何一个像素不相同，则退出循环，将matchFlag变量的值设定为false，
             最后使用断言语句判断matchFlag是否为true。如果为true表示两张图片完全一致，如果为false
             表示两张图片并不是完全匹配
             */

            //读取expected img
            BufferedImage bufileInput = ImageIO.read(fileInput);
            DataBuffer dafileInput = bufileInput.getData().getDataBuffer();
            int sizefileInput = dafileInput.getSize();

            //读取actual img
            BufferedImage bufileOutPut = ImageIO.read(fileOutPut);
            DataBuffer dafileOutPut = bufileOutPut.getData().getDataBuffer();
            int sizefileOutPut = dafileOutPut.getSize();

            int count=0;

            if(sizefileInput == sizefileOutPut){
                for(int j = 0; j<sizefileInput; j ++){
                    if(dafileInput.getElem(j) != dafileOutPut.getElem(j)) {
                        count++;
                    }
                }

                //如果匹配度高于期望ep,默认是截图通过的.
                matchFlag = ((sizefileInput-count)/(float)sizefileInput>ep)? true:false;
                similiar = (sizefileInput-count)/(float)sizefileInput;
            }
            else{
                matchFlag = false;
                similiar = 0;
            }


        } catch (IOException e) {
            matchFlag = false;
            similiar = 0;
            LogUtils.error("图片比对过程中出现异常,因为异常,重置相似度similiar为0%",e);
        }

        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(1);//这个1的意思是保存结果到小数点后几位，但是特别声明：这个结果已经先*100了。
        //实际相似度
        String actualSimiliar = nf.format(similiar);
        //预期相似度
        String expectedSimiliar = nf.format(ep);

        if(!strictMacth){
            if (matchFlag){
                LogUtils.info("采用非严格匹配截图,图片匹配度为:"+actualSimiliar+",高于预定义"+expectedSimiliar+",匹配通过pass");
            }else {
                Assert.assertTrue( matchFlag ,"采用非严格匹配截图,图片匹配度为:"+actualSimiliar+",低于预定义"+expectedSimiliar+",匹配不通过failed");
            }
        }else {
            if (matchFlag){
                LogUtils.info("采用严格匹配截图,图片匹配度为:"+actualSimiliar+",预期相似度100%,匹配通过pass");
            }else {
                Assert.assertTrue( matchFlag ,"采用严格匹配截图,图片匹配度为:"+actualSimiliar+",预期相似度100%,,匹配通过pass");
            }
        }



    }


    /**
     * @Description 为页面元素截图保存
     * @param element 截图的元素,如果element为空则是全屏幕截图方式
     * @param path 截图的图片名称
     **/
    public static void screenShotForElement(WebDriver driver, WebElement element, String path){

        //截图前让页面元素先稳定
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        String currentPath = System.getProperty("user.dir");
        path =currentPath+"\\file\\screenshot\\actual\\"+path;
        if (!StringUtils.endsWithIgnoreCase(path, ".png")) {
            path += ".png";
        }

        //截取整个页面的图片
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
        //获取元素在所处frame中位置对象
        Point p = element.getLocation();
        //获取元素的宽与高
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        //矩形图像对象
        Rectangle rect = new Rectangle(width,height);
        BufferedImage img = ImageIO.read(scrFile);
        //x、y表示加上当前frame的左边距,上边距
        BufferedImage dest = img.getSubimage(p.getX(), p.getY(),rect.width, rect.height);
        ImageIO.write(dest, "png", scrFile);
        FileUtils.copyFile(scrFile, new File(path));
        Thread.sleep(1000);
        LogUtils.info(String.format("为元素【%s】截图成功，保存位置: %s", element.getTagName(), path));
        } catch (Exception e) {
            //用于截图元素是否有异常,如果抛出异常,则改为全屏截图,而不是元素截图
            scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            if (element != null){
                LogUtils.info(String.format("为元素【%s】截图出现异常，现在改为全屏幕截图",element.getTagName()));
            }
            try {
                FileUtils.copyFile(scrFile, new File(path));
                Thread.sleep(1000);
                LogUtils.info(String.format("全屏幕截图成功，保存位置: %s",path));
            } catch (Exception e1) {
                LogUtils.error("全屏幕截图也出现异常",e1);
            }

        }


    }

}
