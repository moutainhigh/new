package com.huayu.hr.web.args;

import com.huayu.hr.domain.HrAtteAttDetail;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by DengPeng on 2017/4/14.
 */
public class HrAtteAttDetailArgs extends HrAtteAttDetail {

    private Integer company;

    private Integer department;

    private String empIdsStr;

    private List<Integer> empIds;

    private String badgenumbersStr;

    private List<Integer> badgenumbers;

    private Map<Integer,Integer> badgeEmpIdMap;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getBadgenumbersStr() {
        return badgenumbersStr;
    }

    public void setBadgenumbersStr(String badgenumbersStr) {
        this.badgenumbersStr = badgenumbersStr;
    }

    public Map<Integer, Integer> getBadgeEmpIdMap() {
        return badgeEmpIdMap;
    }

    public void setBadgeEmpIdMap(Map<Integer, Integer> badgeEmpIdMap) {
        this.badgeEmpIdMap = badgeEmpIdMap;
    }

    public List<Integer> getBadgenumbers() {
        return badgenumbers;
    }

    public void setBadgenumbers(List<Integer> badgenumbers) {
        this.badgenumbers = badgenumbers;
    }

    public String getEmpIdsStr() {
        return empIdsStr;
    }

    public void setEmpIdsStr(String empIdsStr) {
        this.empIdsStr = empIdsStr;
    }

    public List<Integer> getEmpIds() {
        return empIds;
    }

    public void setEmpIds(List<Integer> empIds) {
        this.empIds = empIds;
    }
}
