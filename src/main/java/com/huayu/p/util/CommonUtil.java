package com.huayu.p.util;

import com.alibaba.fastjson.JSONObject;
import com.huayu.p.web.tools.CustomException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * ClassName: CommonUtil
 * 通用工具类
 * @author ZXL
 * 2016年7月28日
 */
public class CommonUtil {

	public static boolean isLong(Long l){
		return null!=l&&l>0;
	}

	public static boolean isInt(Integer i){
		return null!=i&&i>0;
	}

    public static boolean isShort(Short i){
        return null!=i&&i>0;
    }

    public static Long getUUID(){
        return UUID.randomUUID().getMostSignificantBits();
    }

    /**
     * 实例化接口数据
     * @return
     * Map
     */
    public static Map<String,Object> result(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("data",new JSONObject());
        map.put("msg","操作成功");
        return map;
    }

    /**
     * 两个实体类值复制
     * @param source 源实体类
     * @param targer 目标实体类
     * @return
     * Object
     * @throws Exception
     * e
     */
    public static Object copyValueForObject(Object source, Object targer) throws Exception {
        // 获取属性
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(),Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();
        BeanInfo destBean = Introspector.getBeanInfo(targer.getClass(),Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();
        try {
            for (int i = 0; i < sourceProperty.length; i++) {
                for (int j = 0; j < destProperty.length; j++) {
                    if (sourceProperty[i].getName().equals(destProperty[j].getName())) {
                        // 调用source的getter方法和dest的setter方法
                        destProperty[j].getWriteMethod().invoke(targer,sourceProperty[i].getReadMethod().invoke(source));
                        break;
                    }
                }
            }
            return targer;
        } catch (Exception e) {
            throw new CustomException("属性复制失败:");
        }
    }

    /**
     * 解析并组装HttpServletRequest参数
     * @param request HttpServletRequest
     * @return
     * String
     */
    public static String packageParameters(HttpServletRequest request){
        StringBuffer parameters = new StringBuffer();
        Map<String,String> map = CommonUtil.parseParams(request);
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            parameters.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        return StringUtils.isNotBlank(parameters)?parameters.substring(0,parameters.length()-1):"";
    }

    /**
     * 解析HttpServletRequest
     * @param request HttpServletRequest
     * @return
     * map
     */
    public static Map<String,String> parseParams(HttpServletRequest request) {
        Map<String,String> map = new HashMap<>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }

}
