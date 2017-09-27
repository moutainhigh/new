package com.huayu.inventory.dao;

import com.huayu.common.tool.NumberUtil;
import com.huayu.inventory.domain.PrProject;
import com.huayu.inventory.web.args.PrProjectArgs;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DengPeng on 2017/6/12.
 */
@Repository
public class PrProjectDao extends BasePageDao<PrProject,Integer>{

    public List<PrProject> getAll(){
        String sql = "select * from pr_project";
        return super.get(sql,new PrProject());
    }

    public int addPrProject(PrProject prProject ){
        Long key = super.getKey("pr_project", prProject);
        prProject.setPcode(NumberUtil.buildNum(key.intValue(),5));
        prProject.setId(key.intValue());
        return super.post(prProject);
    }


    public List<PrProject> getAllProjects(PrProjectArgs prProject) {
        String sql  = "select p.* from pr_project p where p.pcode REGEXP "+prProject.getAuthoritiesRegexp();
        return super.get(sql,prProject);
    }
}
