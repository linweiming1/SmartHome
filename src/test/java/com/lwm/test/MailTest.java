package com.lwm.test;

import com.lwm.MailUtil.EmailUtil;
import com.lwm.MailUtil.MailSenderInfo;
import com.lwm.smarthome.controller.VerifyCodeController;
import com.lwm.util.ToolUtils;
import com.lwm.util.VerifyCodeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MailTest {
  //  @Autowired
  //  MailSenderInfo mailInfo;

    private static Logger logger= LoggerFactory.getLogger(MailTest.class);
    @Test
    public void sendEmail() {
      //  EmailUtil.sendEmail(mailInfo, "linweimingfz@163.com", "1314");
     //   System.out.println(VerifyCodeUtils.generateVerifyCode(6,"123456789"));
        byte[] abc=ToolUtils.stringToByte("[1,2,3]");
        System.out.println(abc[0]);
    }
}
