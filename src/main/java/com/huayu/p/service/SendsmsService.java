package com.huayu.p.service;

import com.alibaba.fastjson.JSON;
import com.huayu.a.service.CommConfigService;
import com.huayu.a.service.CommLogService;
import com.huayu.p.util.FinalUtil;
import com.huayu.user.domain.Sms;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: SendsmsService
 * 发短信服务
 * @author zxl
 * 2016年8月2日
 */
@Service
public class SendsmsService {

	@Autowired
	private CommLogService commLogService;
	@Autowired
    private CommConfigService commConfigService;
	
	private static final Logger log = LoggerFactory.getLogger(SendsmsService.class);

    /**
     * 开始预警
     * @param uMobile 业主手机
     * @param inCode 订单ID
     * @param pickCode 提货码
     */
    public void smsStartWarn(String uMobile,String project,String task,String day){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("project", project);
        paramMap.put("task", task);
        paramMap.put("day", day);
        this.sendNormalSms(uMobile, JSON.toJSONString(paramMap), FinalUtil.SMS_TEMPLATE_START_CODE, FinalUtil.SMS_TEMPLATE_REGISTER_SIGN, "");
    }

    /**
     * 结束预警
     * @param uMobile 业主手机
     * @param inCode 订单ID
     * @param pickCode 提货码
     */
    public void smsEndWarn(String uMobile,String project,String task,String day){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("project", project);
        paramMap.put("task", task);
        paramMap.put("day", day);
        this.sendNormalSms(uMobile, JSON.toJSONString(paramMap), FinalUtil.SMS_TEMPLATE_END_CODE, FinalUtil.SMS_TEMPLATE_REGISTER_SIGN, "");
    }

	/**
	 * 发送普通短信
	 * @param mobile String
	 * @param content String
	 * @param smsTemplateCode String
	 * @param smsFreeSignName String
	 * @param extend String
	 */
	private void sendNormalSms(String mobile,String content,String smsTemplateCode,String smsFreeSignName,String extend){
		String ret = "";
		Sms sms = null;
		try {
            sms = this.getSms();
            sms.setMobile(mobile);
            sms.setContent(content);
            sms.setSmsTemplateCode(smsTemplateCode);
            sms.setSmsFreeSignName(smsFreeSignName);
            sms.setExtend(extend);
            ret = this.sendSMS(sms);
		} catch (Exception e) {
			log.error("发送短信"+e.getMessage());
			ret = "发送短信失败：" + e.getMessage();
		} finally{
			commLogService.smsLog(null!=sms?sms.getUrl():"", mobile, content, ret);
		}
	}
	
	/**
	 * 发送短信，多个电话逗号隔开18222，1833333；
	 * @param sms Sms
	 * @return
	 * String  
	 * @throws ApiException 
	 * @throws InterruptedException 
	 */
	private String sendSMS(Sms sms) throws ApiException, InterruptedException  {
			Thread.sleep(100);
			TaobaoClient client = new DefaultTaobaoClient(sms.getUrl(), sms.getUsername(),sms.getPassword());
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setExtend(sms.getExtend());
			req.setSmsType("normal");
			req.setSmsFreeSignName(sms.getSmsFreeSignName());
			req.setSmsParamString(sms.getContent());
			req.setRecNum(sms.getMobile());
			req.setSmsTemplateCode(sms.getSmsTemplateCode());
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
	        return rsp.getBody();
	}
	
	/**
	 * 获取发送短信基础对象
	 * @return
	 * Sms
	 */
	private Sms getSms(){
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext(); 
		Map<String, String> commConfig = (Map<String, String>) servletContext.getAttribute("commConfig");
		Sms sms = new Sms();
		sms.setUrl(StringUtils.trim(commConfig.get("sms_url")));
		sms.setUsername(StringUtils.trim(commConfig.get("sms_key")));
		sms.setPassword(StringUtils.trim(commConfig.get("sms_secret")));
		return sms;
	}
	
}


