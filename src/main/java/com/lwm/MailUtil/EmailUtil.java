package com.lwm.MailUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
* 这是邮件工具类
* */
public class EmailUtil {
    /**
     * 发送邮件方法
     *
     * @param mailInfo
     * @param account
     * @param code
     */
    private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    public static Boolean sendEmail(MailSenderInfo mailInfo, String account, String code) {

        mailInfo.setValidate(Boolean.TRUE);
        mailInfo.setToAddress(account);
        mailInfo.setSubject("验证码邮件");
        mailInfo.setContent(
                "<p>您好!</p><p>感谢您注册成为物联网智慧家居用户！</p><p>您的验证码是：" + code + "。祝您使用愉快！</p><p>点击www.baidu.com了解更多产品。</p>");
        SendEmailThread d = new SendEmailThread(mailInfo);
        Boolean result = SimpleMailSender.sendHtmlMail(mailInfo);
        if (result) {
            logger.info("发送邮件成功！");
        }
        return result;
    }
}
