package com.huayu.p.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import com.huayu.p.domain.ProjectArea;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 项目进度管理区域
 *
 * @author ZXL
 * 2017-05-19 10:19
 **/
@Repository
public class ProjectAreaDao extends BasePageDao<ProjectArea,Serializable>{

    /**
     * 获取全部可用数据
     * @param projectArea ProjectArea
     * @return
     * list
     */
    public List<ProjectArea> get(ProjectArea projectArea){
        User user = SpringSecurityUtil.getUser();
        String sql = "SELECT * FROM project_area WHERE `status`=2 AND id REGEXP("+user.getDataAuthoritiesRegexp()+") ORDER BY sort ASC";
        return super.get(sql,projectArea);
    }


}
