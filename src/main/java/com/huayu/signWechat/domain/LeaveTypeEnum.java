package com.huayu.signWechat.domain;

/**
 * Created by Administrator on 2017/7/18.
 */
public enum LeaveTypeEnum {
    COMPASSIONATE_LEAVE("-1135316255604717531", "事假"),
    MARITAL_LEAVE("8779689809439049634", "婚假"),
    MATERNITY_LEAVE("4699807474016863501", "产假"),
    SICK_LEAVE("5705361015223765326", "病假"),
    ANNUAL_LEAVE("3914859601377381015", "年假"),
    FUNERAL_LEAVE("-7949396643966726286", "丧假"),
    PATERNITY_LEAVE("9086439688191230057", "陪护假"),
    WORK_RELATED_INJURY_LEAVE("-2312192423498395678", "工伤假"),
    TRAVEL_ON_HOME_LEAVE("6611264508042233045", "探亲假"),
    ELSE_LEAVE("-3265967495408038335", "其他"),
    ;

    private String code;
    private String name;

    LeaveTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getName(String code) {
        for (LeaveTypeEnum l : LeaveTypeEnum.values()) {
            if (code.equals(l.getCode())) {
                return l.name;
            }
        }
        return null;
    }
}
