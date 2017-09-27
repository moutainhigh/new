package com.huayu.projectcost.dao;

import com.huayu.projectcost.domain.ProjectCostBuildingType;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */
@Repository
public class ProjectCostBuildingTypeDao extends BasePageDao<ProjectCostBuildingType, Serializable> {

    public int postData(ProjectCostBuildingType projectCostBuildingType) {
        Long key = this.getKey("project_cost_buildingType", new ProjectCostBuildingType());
        projectCostBuildingType.setId(key.intValue());
        return super.post(projectCostBuildingType);
    }

    public List<ProjectCostBuildingType> getTypeByName(ProjectCostBuildingType projectCostBuildingType) {
        String sql = "select * from project_cost_buildingType where name = :name and status = 0";
        return super.get(sql, projectCostBuildingType);
    }

    public List<ProjectCostBuildingType> getAllType() {
        String sql = "select * from project_cost_buildingType where status = 0";
        return super.get(sql, new ProjectCostBuildingType());
    }

    public int updateType(ProjectCostBuildingType projectCostBuildingType) {
        String sql = "update project_cost_buildingType set name = :name where id = :id";
        return super.put(sql, projectCostBuildingType);
    }

    public int deleteType(ProjectCostBuildingType projectCostBuildingType) {
        String sql = "update project_cost_buildingType set status = 1 where id = :id";
        return super.put(sql, projectCostBuildingType);
    }
}
