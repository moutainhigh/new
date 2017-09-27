package com.huayu.p.dao;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.user.domain.User;
import com.ly.dao.base.BasePageDao;
import com.huayu.p.domain.ProjectArchives;
import com.huayu.p.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 项目档案
 * @author ZXL
 * 2017-05-19 10:16
 **/
@Repository
public class ProjectArchivesDao extends BasePageDao<ProjectArchives,Serializable>{

    /**
     * 获取数据
     * @param projectArchives ProjectArchives
     * @return
     * ProjectArchives
     */
    public ProjectArchives getOne(ProjectArchives projectArchives){
        String sql = "SELECT s.*,s1.projectName parentName FROM project_archives s LEFT JOIN project_archives s1 ON s.parentId=s1.projectId WHERE s.projectId=:projectId";
        return super.getOne(sql,projectArchives);
    }

    /**
     * 获取数据
     * @param projectArchives ProjectArchives
     * @return
     * ProjectArchives
     */
    public ProjectArchives getOneByProjectName(ProjectArchives projectArchives){
        String sql = "SELECT * FROM project_archives WHERE projectName=:projectName AND `status`=2 AND isCompile=2 AND isComplete=1 LIMIT 0,1";
        return super.getOne(sql,projectArchives);
    }

    /**
     * 分页获取数据
     * @param projectArchives ProjectArchives
     * @param pageable Pageable
     * @return
     * page
     */
    public Page<ProjectArchives> get(ProjectArchives projectArchives, Pageable pageable){
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql = new StringBuilder("SELECT s.*,s1.projectName parentName,a.`name` areaName FROM project_archives s LEFT JOIN project_archives s1 ON s.parentId=s1.projectId LEFT JOIN project_area a ON s.areaId=a.id WHERE s.`status`=2 ");
        sql.append(" AND s.areaId REGEXP("+user.getDataAuthoritiesRegexp()+")");
        if (CommonUtil.isShort(projectArchives.getIsParent())) {
            sql.append(" AND s.isParent=2 ");
        }
        if (StringUtils.isNotBlank(projectArchives.getS1())) {
            sql.append(" AND INSTR(s.projectName,:s1)>0 ");
        }
        if (CommonUtil.isInt(projectArchives.getI1())) {
            sql.append(" AND s.areaId=:i1 ");
        }
        if (null!=projectArchives.getIsCompile()&&null!=projectArchives.getSufVersions()){
            sql.append(" AND s.isCompile=2 AND s.sufVersions>=2 ");
        }
        if (null!=projectArchives.getIsPlan()){
            sql.append(" AND s.isPlan=2 ");
        }
        sql.append(" ORDER BY s.sort ASC");
        return super.get(sql.toString(),projectArchives,pageable);
    }

    /**
     * 获取最大的编码
     * @param projectArchives ProjectArchives
     * @return
     * ProjectArchives
     */
    public ProjectArchives getMaxCode(ProjectArchives projectArchives){
        String sql = "SELECT s.projectId,s.projectCode parentCode,a.projectCode FROM project_archives s LEFT JOIN (SELECT MAX(projectCode) projectCode,parentId FROM project_archives WHERE parentId=:parentId) a ON s.projectId=a.parentId WHERE s.projectId=:parentId";
        return super.getOne(sql,projectArchives);
    }

    /**
     * 以父ID获取数据
     * @param projectArchives ProjectArchives
     * @return
     * List
     */
    public List<ProjectArchives> getAllByParentId(ProjectArchives projectArchives){
        String sql = "SELECT * FROM project_archives WHERE parentId=:parentId AND `status`=2";
        return super.get(sql,projectArchives);
    }

    /**
     * 修改数据
     * @param projectArchives ProjectArchives
     * @return
     * int
     */
    public int put(ProjectArchives projectArchives){
        String sql = "UPDATE project_archives SET projectName=:projectName,parentId=:parentId,areaId=:areaId,startDate=:startDate,endDate=:endDate,isPlan=:isPlan,remark=:remark,updateDate=:updateDate,updateUserId=:updateUserId,documentsDate=:documentsDate WHERE projectId=:projectId";
        return super.put(sql,projectArchives);
    }

    /**
     * 删除数据
     * @param projectArchives ProjectArchives
     * @return
     * int
     */
    public int delete(ProjectArchives projectArchives){
        String sql = "UPDATE project_archives SET `status`=:status,deleteDate=:deleteDate,deleteUserId=:deleteUserId WHERE projectId=:projectId";
        return super.put(sql,projectArchives);
    }

    /**
     * 修改是否编制项目 1否 2是
     * @param projectArchives ProjectArchives
     * @return
     * int
     */
    public int putIsCompile(ProjectArchives projectArchives){
        String sql = "UPDATE project_archives SET isCompile=:isCompile WHERE projectId=:projectId";
        return super.put(sql,projectArchives);
    }

    /**
     * 修改是否完成 1否2是
     * @param projectArchives ProjectArchives
     * @return
     * int
     */
    public int putIsComplete(ProjectArchives projectArchives){
        String sql = "UPDATE project_archives SET isComplete=2 WHERE projectId=:projectId AND isComplete=1";
        return super.put(sql,projectArchives);
    }

    /**
     * 生效
     * @param projectArchives ProjectArchives
     * @return
     * int
     */
    public int putIsOn(ProjectArchives projectArchives){
        StringBuilder sql = new StringBuilder("UPDATE project_archives SET isOn=:isOn,onDate=:onDate,onUserId=:onUserId,sufVersions=:sufVersions,versions=:versions WHERE projectId=:projectId");
        return super.put(sql.toString(),projectArchives);
    }

    /**
     * 生效
     * @param projectArchives ProjectArchives
     * @return
     * int
     */
    public int putNoOn(ProjectArchives projectArchives){
        String sql = "UPDATE project_archives SET isOn=:isOn WHERE projectId=:projectId";
        return super.put(sql,projectArchives);
    }

    /**
     * 增减关联次数
     * @param projectArchives
     * @return
     */
    public int addAssociatedNum(ProjectArchives projectArchives){
        String sql = "UPDATE project_archives SET associatedNum=associatedNum+:associatedNum WHERE projectId=:projectId";
        return super.put(sql,projectArchives);
    }

    /**
     * 修改完成节点和完成权重
     * @param projectArchives
     * @return
     */
    public int putNodeNumAndWeight(ProjectArchives projectArchives){
        String sql = "UPDATE project_archives SET successNodeNum=successNodeNum+:successNodeNum,successWeight=successWeight+:successWeight WHERE projectId=:projectId";
        return super.put(sql,projectArchives);
    }

    /**
     * 获取项目进度报表
     * @param projectArchives
     * @param pageable
     * @return
     */
    public Page<ProjectArchives> getToReport(ProjectArchives projectArchives, Pageable pageable){
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql = new StringBuilder("SELECT s.*,a.`name` areaName FROM project_archives s LEFT JOIN project_area a ON s.areaId=a.id WHERE s.status=2 AND s.isPlan=2 AND s.isCompile=2 AND s.areaId REGEXP("+user.getDataAuthoritiesRegexp()+") ");
        if (StringUtils.isNotBlank(projectArchives.getS1())) {
            sql.append(" AND INSTR(s.projectName,:s1)>0 ");
        }
        if (CommonUtil.isInt(projectArchives.getI1())) {
            sql.append(" AND s.areaId=:i1 ");
        }
        sql.append(" ORDER BY s.areaId ASC,s.sort ASC");
        return super.get(sql.toString(),projectArchives,pageable);
    }

    /**
     * 获取项目进度报表
     * @param projectArchives
     * @param pageable
     * @return
     */
    public List<ProjectArchives> getAllToReport(ProjectArchives projectArchives){
        User user = SpringSecurityUtil.getUser();
        StringBuilder sql = new StringBuilder("SELECT s.*,a.`name` areaName FROM project_archives s LEFT JOIN project_area a ON s.areaId=a.id WHERE s.status=2 AND s.isPlan=2 AND s.isCompile=2 AND s.isComplete=1 AND s.areaId REGEXP("+user.getDataAuthoritiesRegexp()+") ");
        if (StringUtils.isNotBlank(projectArchives.getS1())) {
            sql.append(" AND INSTR(s.projectName,:s1)>0 ");
        }
        if (CommonUtil.isInt(projectArchives.getI1())) {
            sql.append(" AND s.areaId=:i1 ");
        }
        sql.append(" ORDER BY s.areaId ASC,s.sort ASC");
        return super.get(sql.toString(),projectArchives);
    }

}
