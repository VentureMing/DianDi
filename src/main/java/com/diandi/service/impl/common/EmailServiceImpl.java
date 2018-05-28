package com.diandi.service.impl.common;


import com.diandi.dao.author.AuthorAccountDao;
import com.diandi.entity.author.AuthorAccount;
import com.diandi.manager.MailManager;
import com.diandi.service.common.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.diandi.manager.properties.WebSiteProperties;

import javax.mail.internet.MimeMessage;

/**
 * Created on 2018/4/7.
 *
 * @author DuanJiaNing
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    protected WebSiteProperties websiteProperties;

    @Autowired
    private MailManager mailManager;

    @Autowired
    private AuthorAccountDao accountDao;

    @Override
    public boolean sendFeedback(int bloggerId, String subject, String content, String contact) {

        JavaMailSenderImpl mailSender = mailManager.getMailSender(
                websiteProperties.getMailSenderAddress(),
                websiteProperties.getMailSenderPassword());

        MimeMessage mailMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "utf-8");

            // 设置发件人
            helper.setFrom(websiteProperties.getMailSenderAddress());

            // 设置收件人
            helper.setTo(websiteProperties.getManageEmailAddress());

            // 设置主题
            helper.setSubject(subject);

            // 邮件体
            AuthorAccount account = bloggerId > 0 ? accountDao.getAccountById(bloggerId) : null;
            helper.setText(account != null ?
                    "from:\n\temail: " + account.getAuthorEmail() + "\n\tarticleId: " + account.getAuthorId() + "\n\n" + content + "\n\n\tcontact: " + contact :
                    content + "\n\n\tcontact: " + contact);

            mailSender.send(mailMessage);// 发送邮件
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

}
