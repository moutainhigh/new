package com.huayu.projectcost.service;

import com.huayu.projectcost.dao.ProjectCostProjectDao;
import com.huayu.projectcost.domain.ProjectCostProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/9/15.
 */
@Service
public class ProjectCostProjectService {

    @Autowired
    private ProjectCostProjectDao projectCostProjectDao;

    public List<ProjectCostProject> getprojectList() {
        List<ProjectCostProject> projectCostProjects = projectCostProjectDao.getprojectList();
        Map<String,String> map = new HashMap<>();
        for (ProjectCostProject p :projectCostProjects) {
            if (!map.containsKey(p.getPcConmpanyName())) {
                map.put(p.getPcConmpanyName(),"");
            }
        }
        Set<String> keySet = map.keySet();
        for (String s : keySet) {
            ProjectCostProject projectCostProject = new ProjectCostProject();
            projectCostProject.setParentId(-1);
            projectCostProject.setName(s);
            projectCostProject.setBriefName(s);
            projectCostProject.setIsParent(1);
            projectCostProjects.add(projectCostProject);
        }
        return projectCostProjects;
    }
}
