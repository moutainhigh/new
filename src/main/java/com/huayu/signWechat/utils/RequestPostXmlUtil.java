package com.huayu.signWechat.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 解析post请求发送XML参数工具类
 * @author yangjing
 *
 */
public class RequestPostXmlUtil {

	public static Map<String, String> getPostXmlMap(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		/*
		 * 该部分我们获取用户发送的信息，并且解析成<K,V>的形式进行显示
		 */
		// 解析用户发送过来的信息
    	InputStream is = null;
		try {
			is = request.getInputStream();
			// 拿取请求流
    		// 将解析结果存储在HashMap中
    		// 解析xml，将获取到的返回结果xml进行解析成我们习惯的文字信息
    		SAXReader reader = new SAXReader();// 第三方jar:dom4j
    		Document document = reader.read(is);
    		// 得到xml根元素
    		Element root = document.getRootElement();
    		// 得到根元素的所有子节点
    		List<Element> elementList = root.elements();
    		// 遍历所有子节点
    		for (Element e : elementList)
    		    map.put(e.getName(), e.getText());
    		
    		// 测试输出
    		Set<String> keySet = map.keySet();
    		// 测试输出解析后用户发过来的信息
    		System.out.println("解析用户发送过来的信息开始");
    		for (String key : keySet) {
    		    System.out.println(key + ":" + map.get(key));
    		}
		}catch (IOException e2) {
			e2.printStackTrace();
		}catch (DocumentException e1) {
		    e1.printStackTrace();
		}catch (Exception e) {    
            e.printStackTrace();    
        }finally {
			if(is!=null){
				try {
					is.close();
					return map;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
		
	}
}
