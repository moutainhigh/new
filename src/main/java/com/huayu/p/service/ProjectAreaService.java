package com.huayu.p.service;

import com.huayu.p.dao.ProjectAreaDao;
import com.huayu.p.domain.ProjectArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目进度管理区域
 *
 * @author ZXL 2017-05-19 10:24
 **/
@Service
public class ProjectAreaService {

    @Autowired
    private ProjectAreaDao projectAreaDao;

    /**
     * 获取全部可用数据
     * @return
     * list
     */
    public List<ProjectArea> get(){
        return projectAreaDao.get(new ProjectArea());
    }


}
