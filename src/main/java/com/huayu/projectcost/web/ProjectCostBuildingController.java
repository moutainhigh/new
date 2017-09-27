package com.huayu.projectcost.web;

import com.huayu.projectcost.domain.ProjectCostProject;
import com.huayu.projectcost.service.ProjectCostProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/9/15.
 */
@Controller
@RequestMapping("/admin/projectcost/project")
public class ProjectCostBuildingController {

    @Autowired
    private ProjectCostProjectService projectCostProjectService;

    @RequestMapping("/toProjectPage")
    public String toProjectPage(Model model) {
        List<ProjectCostProject> list = projectCostProjectService.getprojectList();
        model.addAttribute("list",list);
        return ".projectcost.project.projectList";
    }
}
