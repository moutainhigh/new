package com.huayu.p.dao;

import com.huayu.p.domain.ProjectPlanAccessory;
import com.ly.dao.base.BasePageDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 进度上传附件
 *
 * @author ZXL 2017-05-26 11:55
 **/
@Repository
public class ProjectPlanAccessoryDao extends BasePageDao<ProjectPlanAccessory,Serializable> {

    /**
     * 条件获取全部数据
     * @param projectPlanAccessory
     * @return
     */
    public List<ProjectPlanAccessory> getAll(ProjectPlanAccessory projectPlanAccessory){
        String sql = "SELECT * FROM project_plan_accessory WHERE projectId=:projectId AND compileId=:compileId AND `status`=2";
        return super.get(sql,projectPlanAccessory);
    }

    /**
     *
     * @param projectPlanAccessory
     * @return
     */
    public ProjectPlanAccessory getOne(ProjectPlanAccessory projectPlanAccessory){
        String sql = "SELECT * FROM project_plan_accessory WHERE id=:id AND projectId=:projectId AND compileId=:compileId";
        return super.getOne(sql,projectPlanAccessory);
    }

    /**
     * 删除数据
     * @param projectPlanAccessory ProjectPlanAccessory
     * @return
     */
    public int delete(ProjectPlanAccessory projectPlanAccessory){
        String sql = "UPDATE project_plan_accessory SET `status`=1,deleteDate=:deleteDate,deteleUserId=:deteleUserId WHERE id=:id AND projectId=:projectId AND compileId=:compileId AND `status`=2";
        return super.delete(sql, projectPlanAccessory);
    }

}
