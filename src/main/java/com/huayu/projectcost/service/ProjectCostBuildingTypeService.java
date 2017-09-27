package com.huayu.projectcost.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.projectcost.dao.ProjectCostBuildingTypeDao;
import com.huayu.projectcost.domain.ProjectCostBuildingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */
@Service
public class ProjectCostBuildingTypeService {

    @Autowired
    private ProjectCostBuildingTypeDao projectCostBuildingTypeDao;

    public void insertProjectType(ProjectCostBuildingType projectCostBuildingType) {
        projectCostBuildingType.setCreateUser(SpringSecurityUtil.getUser().getUserId().intValue());
        projectCostBuildingType.setCreateTime(new Date());
        projectCostBuildingType.setStatus(0);
        if (1 != projectCostBuildingTypeDao.postData(projectCostBuildingType)) {
            throw new BusinessException("新增业态失败");
        }
    }

    public List<ProjectCostBuildingType> getTypeByName(ProjectCostBuildingType projectCostBuildingType) {
        return projectCostBuildingTypeDao.getTypeByName(projectCostBuildingType);
    }

    public List<ProjectCostBuildingType> getAllType() {
        return projectCostBuildingTypeDao.getAllType();
    }

    public void updateType(ProjectCostBuildingType projectCostBuildingType) {
        if (1 != projectCostBuildingTypeDao.updateType(projectCostBuildingType)) {
            throw new BusinessException("更新业态失败!");
        }
    }

    public void deleteType(ProjectCostBuildingType projectCostBuildingType) {
        if (1 != projectCostBuildingTypeDao.deleteType(projectCostBuildingType)) {
            throw new BusinessException("删除业态失败!");
        }
    }
}
