package com.huayu.p.service;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.tool.DateTimeUtil;
import com.huayu.p.dao.ProjectPlanAccessoryDao;
import com.huayu.p.domain.ProjectPlanAccessory;
import com.huayu.p.util.FileUtil;
import com.huayu.p.web.tools.CustomException;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 进度上传附件
 * @author ZXL 2017-05-26 12:15
 **/
@Service
public class ProjectPlanAccessoryService {

    @Autowired
    private ProjectPlanAccessoryDao projectPlanAccessoryDao;

    /**
     * 获取唯一数据
     * @param projectId
     * @param compileId
     * @param id
     * @return
     */
    public ProjectPlanAccessory getOne(Long projectId, Long compileId, Long id){
        ProjectPlanAccessory projectPlanAccessory = new ProjectPlanAccessory();
        projectPlanAccessory.setId(id);
        projectPlanAccessory.setProjectId(projectId);
        projectPlanAccessory.setCompileId(compileId);
        return projectPlanAccessoryDao.getOne(projectPlanAccessory);
    }

    /**
     * 条件获取全部数据
     * @param projectId
     * @param compileId
     * @return
     */
    public List<ProjectPlanAccessory> getAll(Long projectId,Long compileId){
        ProjectPlanAccessory projectPlanAccessory = new ProjectPlanAccessory();
        projectPlanAccessory.setProjectId(projectId);
        projectPlanAccessory.setCompileId(compileId);
        return projectPlanAccessoryDao.getAll(projectPlanAccessory);
    }

    /**
     * 添加数据
     * @param projectId 项目档案ID
     * @param compileId 项目进度ID
     * @param title
     * @param url
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ProjectPlanAccessory post(Long projectId,Long compileId,String title,String url) throws Exception{
        User user = SpringSecurityUtil.getUser();
        ProjectPlanAccessory projectPlanAccessory = new ProjectPlanAccessory();
        projectPlanAccessory.setId(projectPlanAccessoryDao.getKey("project_plan_accessory",projectPlanAccessory));
        projectPlanAccessory.setStatus((short)2);
        projectPlanAccessory.setProjectId(projectId);
        projectPlanAccessory.setCompileId(compileId);
        projectPlanAccessory.setTitle(title);
        projectPlanAccessory.setUrl(url);
        projectPlanAccessory.setCreateDate(new Date());
        projectPlanAccessory.setCreateUserId(user.getUserId());
        projectPlanAccessory.setDeleteDate(DateTimeUtil.defaultVal());
        projectPlanAccessory.setDeteleUserId(0L);
        return projectPlanAccessoryDao.post(projectPlanAccessory)>0?projectPlanAccessory:null;
    }

    /**
     * 删除文件
     * @param projectId
     * @param compileId
     * @param id
     * @param path
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long projectId,Long compileId,Long id,String path) throws Exception{
        ProjectPlanAccessory projectPlanAccessory = this.getOne(projectId, compileId, id);
        if (null==projectPlanAccessory){
            throw new CustomException("删除失败，删除数据不存在");
        }
        User user = SpringSecurityUtil.getUser();
        projectPlanAccessory.setDeteleUserId(user.getUserId());
        projectPlanAccessory.setDeleteDate(new Date());
        int retNum = projectPlanAccessoryDao.delete(projectPlanAccessory);
        if (retNum>0){
            if (!FileUtil.deleteFile(path+projectPlanAccessory.getUrl())){
                throw new CustomException("删除失败，删除文件失败");
            }
        }
        return retNum;
    }

}
