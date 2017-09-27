package com.huayu.a.service.tools;

import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

/**
 * desc：发送邮件服务
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：11:47
 */
@Service
public class SendMailService {

	@Autowired
	private JavaMailSenderImpl sendMailDao;

	@Autowired
	private FreeMarkerConfigurer freeMarker;

	private static final Logger log = LoggerFactory.getLogger(SendMailService.class);

	/**
	 * 生成html模板字符串
	 * 
	 * @param root
	 *            存储动态数据的map
	 * @return
	 */
	private String getMailText(Map<String, String> root, String templateName) {
		String htmlText = "";
		try {
			// 通过指定模板名获取FreeMarker模板实例
			Template tpl = freeMarker.getConfiguration().getTemplate(
					templateName);
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,
					root);
		} catch (Exception e) {
			log.error("生成html邮件模板字符串错误:" + e.getMessage());
		}
		return htmlText;
	}

	/**
	 * 发送邮件
	 * 
	 * @param root
	 *            存储动态数据的map
	 * @param toEmail
	 *            邮件地址
	 * @param subject
	 *            邮件主题
	 * @return
	 */
	@Async
	public void sendTemplateMail(Map<String, String> map, String toEmail,
			String subject, String templateName) {
		try {
			MimeMessage msg = sendMailDao.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, false,
					"utf-8");// 由于是html邮件，不是mulitpart类型
			helper.setFrom("y.liu@862.com.cn");
			helper.setSubject(subject);
			String htmlText = getMailText(map, templateName);// 使用模板生成html邮件内容
			helper.setText(htmlText, true);

			helper.setTo(toEmail);
			sendMailDao.send(msg);
			return;
		} catch (MailException e) {
			log.error("发送模板邮件失败:" + e.getStackTrace());
			return;
		} catch (MessagingException e) {
			log.error("发送模板邮件失败:" + e.getStackTrace());
			return;
		}
	}

	/**
	 * 批量发送邮件
	 * 
	 * @param root
	 *            存储动态数据的map
	 * @param toEmail
	 *            邮件地址
	 * @param subject
	 *            邮件主题
	 * @return
	 */
	public void sendTemplateMail(Map<String, String> map, List<String> toEmail,
			String subject, String templateName) {

		String htmlText = getMailText(map, templateName);
		MimeMessage msg = sendMailDao.createMimeMessage();
		MimeMessageHelper helper;
		for (String mail : toEmail) {
			try {
				helper = new MimeMessageHelper(msg, false, "utf-8");
				helper.setFrom("y.liu@862.com.cn");
				helper.setSubject(subject);
				// 使用模板生成html邮件内容
				helper.setText(htmlText, true);
				helper.setTo(mail);
				sendMailDao.send(msg);
			} catch (MailException e) {
				log.error("发送模板邮件失败:" + e.getMessage());
			} catch (MessagingException e) {
				log.error("发送模板邮件失败:" + e.getMessage());
			}
		}
	}
}
