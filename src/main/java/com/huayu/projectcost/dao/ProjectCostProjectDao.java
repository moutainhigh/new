package com.huayu.projectcost.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.Authority;
import com.huayu.projectcost.domain.ProjectCostProject;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */
@Repository
public class ProjectCostProjectDao extends BasePageDao<ProjectCostProject, Serializable> {

    public List<ProjectCostProject> getprojectList() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM project_cost_project p INNER JOIN hr_company c ON c.id = p.pcCompanyId  WHERE p.`status` = 1");
        User user = SpringSecurityUtil.getUser();
        Authority authority = user.getDataAuthorityMap();
        sql.append(" and c.code REGEXP").append(Authority.getAuthority(authority,user.getCurrCompanyCode()).authoritiesRegexp());
        return super.get(sql.toString(), new ProjectCostProject());
    }
}
