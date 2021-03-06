package com.huayu.a.service.tools;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Properties;

/**
 * desc：验证码Service
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：11:46
 */
@Service
public class KaptchaService {

	/***
	 * 产生验证图片工具
	 * 
	 * @return
	 */
	public Producer createImage() {
		Properties props = new Properties();
		props.put("kaptcha.border", "no");
		props.put("kaptcha.noise.color", "white");
		props.put("kaptcha.image.width", "120");
		props.put("kaptcha.image.height", "42");
		props.put("kaptcha.textproducer.font.color", Color.cyan);
		props.put("kaptcha.textproducer.font.names", "微软雅黑");
		props.put("kaptcha.textproducer.font.size", "30");
		props.put("kaptcha.textproducer.char.space", "10");
		props.put("kaptcha.textproducer.char.length", "4");
		props.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
		props.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
		Config config = new Config(props);
		return config.getProducerImpl();
	}

	/***
	 * kaptcha.border 是否有边框 默认为true 我们可以自己设置yes，no kaptcha.border.color 边框颜色
	 * 默认为Color.BLACK kaptcha.border.thickness 边框粗细度 默认为1 kaptcha.producer.impl
	 * 验证码生成器 默认为DefaultKaptcha kaptcha.textproducer.impl 验证码文本生成器
	 * 默认为DefaultTextCreator kaptcha.textproducer.char.string 验证码文本字符内容范围
	 * 默认为abcde2345678gfynmnpwx kaptcha.textproducer.char.length 验证码文本字符长度 默认为5
	 * kaptcha.textproducer.font.names 验证码文本字体样式 默认为new Font("Arial", 1,
	 * fontSize), new Font("Courier", 1, fontSize)
	 * kaptcha.textproducer.font.size 验证码文本字符大小 默认为40
	 * kaptcha.textproducer.font.color 验证码文本字符颜色 默认为Color.BLACK
	 * kaptcha.textprodkaptcha.noise.colorucer.char.space 验证码文本字符间距 默认为2
	 * kaptcha.noise.impl 验证码噪点生成对象 默认为DefaultNoise kaptcha.noise.color 验证码噪点颜色
	 * 默认为Color.BLACK kaptcha.obscurificator.impl 验证码样式引擎 默认为WaterRipple
	 * kaptcha.word.impl 验证码文本字符渲染 默认为DefaultWordRenderer
	 * kaptcha.background.impl 验证码背景生成器 默认为DefaultBackground
	 * kaptcha.background.clear.from 验证码背景颜色渐进 默认为Color.LIGHT_GRAY
	 * kaptcha.background.clear.to 验证码背景颜色渐进 默认为Color.WHITE kaptcha.image.width
	 * 验证码图片宽度 默认为200 kaptcha.image.height 验证码图片高度 默认为50
	 */

}
