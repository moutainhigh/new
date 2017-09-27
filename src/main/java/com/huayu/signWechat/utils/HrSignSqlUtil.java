package com.huayu.signWechat.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by Administrator on 2017/9/26.
 */
public class HrSignSqlUtil {

    public static void setCodeDepartmentId(StringBuilder sql, String codes, String departmentIds, String c, String d) {
        if (StringUtils.isNotBlank(codes)) {
            String[] split1 = codes.split(",");
            if (null != split1 && split1.length>0) {
                Set set = new HashSet(Arrays.asList(split1));
                List list = new ArrayList();
                list.addAll(set);
                String[] strings = (String[]) list.toArray(new String[list.size()]);
                if (null != strings && strings.length > 0) {
                    for (int i = 0;i<strings.length;i++) {
                        String s = (String) strings[i];
                        if (StringUtils.isNotBlank(s)) {
                            if (strings.length == 1) {
                                sql.append(" and "+c+".code REGEXP "+s);
                            }else {
                                if (i==0) {
                                    sql.append(" and ("+c+".code REGEXP "+s);
                                } else if (i>0 && i<strings.length-1){
                                    sql.append(" or "+c+".code REGEXP "+s);
                                } else if (i == strings.length - 1) {
                                    sql.append(" or "+c+".code REGEXP "+s+")");
                                }
                            }
                        }
                    }
                }
            }
        }

        if (StringUtils.isNotBlank(departmentIds)) {
            String[] split = departmentIds.split(",");
            if (null != split && split.length>0) {
                for (int i = 0;i<split.length;i++) {
                    if (split.length == 1) {
                        sql.append(" and "+d+".id = "+split[0]);
                    }else {
                        String s = split[i];
                        if (i == 0) {
                            sql.append(" and ("+d+".id = "+s);
                        } else if (i>0 && i<split.length-1){
                            sql.append(" or "+d+".id = "+s);
                        } else if (i == split.length-1) {
                            sql.append(" or "+d+".id = "+s+")");
                        }
                    }
                }
            }
        }
    }
}
