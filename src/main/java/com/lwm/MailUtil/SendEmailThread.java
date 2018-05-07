package com.lwm.MailUtil;

/**
 * 发送邮件内部线程类
 */
public class SendEmailThread implements Runnable {
    private MailSenderInfo mailInfo;



    public SendEmailThread(MailSenderInfo mailInfo) {
        this.mailInfo = mailInfo;
    }



    @Override
    public void run() {
        try {
            if (this.mailInfo != null) {
                SimpleMailSender.sendHtmlMail(mailInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}