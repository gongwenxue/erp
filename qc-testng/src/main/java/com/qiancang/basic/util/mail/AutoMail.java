package com.qiancang.basic.util.mail;

import com.qiancang.basic.Constants;
import com.qiancang.basic.util.ProUtil;

import javax.mail.internet.AddressException;
import java.io.FileNotFoundException;
import java.io.IOException;


public class AutoMail {
	public static void main(String[] args) throws AddressException, FileNotFoundException, IOException {
		MailInfo mailInfo = new MailInfo();
		ProUtil mailProp = new ProUtil(Constants.CFG_CONF);

        String[] mail_sendto = mailProp.getPro("mail_sendto").split(",");
        if(mail_sendto.length ==1 && "".equals(mail_sendto[0])){
//            LogUtils.error("邮件发送人的配置项mail_sendto值为空");
            mail_sendto = new String[]{};
        }
        String[] mail_cc = mailProp.getPro("mail_cc").split(",");
        if(mail_cc.length ==1 && "".equals(mail_cc[0])){
//            LogUtils.info("邮件抄送人的配置项mail_cc值为空");
            mail_cc = new String[]{};
        }
        String[] mail_attach = mailProp.getPro("mail_attach").split(",");

        mailInfo.setMailServerHost(mailProp.getPro("mail_host"));
		mailInfo.setMailServerPort(mailProp.getPro("mail_port"));
		mailInfo.setValidate(true);
		mailInfo.setUsername(mailProp.getPro("mail_username"));
		mailInfo.setPassword(mailProp.getPro("mail_password"));// 16位授权码, 注意不是登录密码!
		mailInfo.setFromAddress(mailProp.getPro("mail_from"));
		mailInfo.setSubject(mailProp.getPro("mail_subject"));
        mailInfo.setAttachFileNames(mail_attach);
		mailInfo.setToAddress(mail_sendto);
		mailInfo.setCcAddress(mail_cc);
				
		String mailcontent = ReadHtml.readString("./target/surefire-reports/html/overview.html");
		String cssvalue=ReadHtml.readString("./target/surefire-reports/html/reportng.css");
		String changestr="<style type=\"text/css\">h1 {display : inline}"+cssvalue+"</style>";

		mailcontent=mailcontent.replace("<link href=\"reportng.css\" rel=\"stylesheet\" type=\"text/css\" />", changestr);
		String logostr="<h1 style=\"color:red ; font-size:50px;font-family: '楷体','楷体_GB2312';\">ERP</h1><h1>自动化测试报告</h1>";
		mailcontent=mailcontent.replace("<h1>ERP UI自动化测试报告</h1>", logostr);
		mailInfo.setContent(mailcontent);
		SendMail.sendHtmlMail(mailInfo);// 发送html格式邮件
		
	}

}
