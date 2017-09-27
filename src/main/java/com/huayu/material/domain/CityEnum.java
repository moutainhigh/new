package com.huayu.material.domain;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by DengPeng on 2017/5/17.
 */
public enum  CityEnum {
    CHONG_QING(1,"重庆"),
    CHEN_DU(2,"成都"),
    SU_ZHOU(3,"苏州"),
    HE_FEI(4,"合肥")
    ;

    private Integer cid;
    private String cname;

    CityEnum(Integer cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    public Integer getCid() {
        return cid;
    }

    public String getCname() {
        return cname;
    }

    public static String getCName(int cid) {
        for (CityEnum c : CityEnum.values()) {
            if (c.getCid() == cid) {
                return c.cname;
            }
        }
        return null;
    }

    public static List<CityEnum> getAvailableCities() {
        User user = SpringSecurityUtil.getUser();
        Set<String> dataAuthorities = user.getDataAuthorities();
        List<CityEnum> result = new ArrayList<>();
        for (CityEnum c : CityEnum.values()) {
            if (dataAuthorities.contains(String.valueOf(c.getCid()))) {
                result.add(c);
            }
        }
        return result;
    }
}
