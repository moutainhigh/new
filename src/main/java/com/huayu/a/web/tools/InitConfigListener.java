package com.huayu.a.web.tools;

import com.huayu.a.service.CommConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * 描述：读取数据库的配置初始化
 * 
 * @author 刘咏
 * @version 1.1
 * 
 * @日期：2014-10-27
 */
@Service
public class InitConfigListener implements ServletContextAware {

	private static final Logger log = LoggerFactory
			.getLogger(InitConfigListener.class);

	@Autowired
	private CommConfigService commConfigService;

	@Override
	public void setServletContext(ServletContext servletContext) {
		Map<String, String> commConfig = commConfigService.get();
		log.info(commConfig.get("admin_site_copyright"));
		try {
			if (commConfig != null) {
				servletContext.setAttribute("commConfig", commConfig);
			}
		} catch (RuntimeException re) {
			log.error("comm_config表,获取全局配置信息失败!");
			throw re;
		}
	}

}
