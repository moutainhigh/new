package com.huayu.p.web;

import com.alibaba.fastjson.JSON;
import com.huayu.p.domain.ProjectPlanAccessory;
import com.huayu.p.service.ProjectPlanAccessoryService;
import com.huayu.p.util.FileUtil;
import com.huayu.p.web.tools.CustomException;
import com.ly.service.base.DateUtil;
import com.ly.service.base.UmImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 项目上传数据
 *
 * @author ZXL 2017-05-25 13:56
 **/
@Controller
@RequestMapping("/project/image")
public class ProjectImagesController {

    @Autowired
    private ProjectPlanAccessoryService projectPlanAccessoryService;

    /***
     * uri:/project/image/saveplanimgs
     * 存储多张商品图片,针对dropzone
     * @param file MultipartFile
     * @return String
     */
    @RequestMapping("/saveplanimgs")
    public void saveplanimgs(MultipartFile file, HttpServletRequest request, Long cid, Long pid, HttpServletResponse response) throws Exception{
        UmImage umImage = new UmImage();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            try {
                Map<String, String> commConfig = (Map<String, String>) request.getServletContext().getAttribute("commConfig");
                String newImageName = FileUtil.cutSaveFile(file,commConfig.get("img_projectplan_path"), DateUtil.getYmd());
                if (newImageName.equals("")) {
                    umImage.setState("ERROR");
                } else {
                    ProjectPlanAccessory projectPlanAccessory = this.postAccessory(pid, cid, file.getOriginalFilename(), newImageName, commConfig.get("img_projectplan_path"));
                    if (null!=projectPlanAccessory){
                        umImage.setName(newImageName);
                        umImage.setState("SUCCESS");
                        umImage.setUrl(commConfig.get("img_projectplan_url") + newImageName);
                        umImage.setType(projectPlanAccessory.getId()+"");
                    } else {
                        umImage.setState("ERROR");
                    }
                }
            } catch (Exception e){
                umImage.setState("ERROR");
            }
            out.print(JSON.toJSONString(umImage));
        }finally {
            out.flush();
            out.close();
        }
    }

    /***
     * uri:/project/image/delplanimgs
     * 存储多张商品图片,针对dropzone
     * @param file MultipartFile
     * @return String
     */
    @RequestMapping("/delplanimgs")
    public @ResponseBody
    String delplanimgs(HttpServletRequest request,Long cid,Long pid,Long id) throws Exception{
        UmImage umImage = new UmImage();
        try {
            Map<String, String> commConfig = (Map<String, String>) request.getServletContext().getAttribute("commConfig");
            if (projectPlanAccessoryService.delete(pid,cid,id,commConfig.get("img_projectplan_path"))>0) {
                umImage.setState("SUCCESS");
                umImage.setName("删除成功");
            } else {
                umImage.setState("ERROR");
                umImage.setName("删除失败");
            }
        } catch (CustomException e){
            umImage.setState("ERROR");
            umImage.setName(e.getMessage());
        } catch (Exception e){
            umImage.setState("ERROR");
            umImage.setName("删除失败");
        }
        return JSON.toJSONString(umImage);
    }

    private ProjectPlanAccessory postAccessory(Long projectId,Long compileId,String title,String url,String path){
        try {
            ProjectPlanAccessory projectPlanAccessory = projectPlanAccessoryService.post(projectId, compileId, title, url);
            if (null!=projectPlanAccessory){
                return projectPlanAccessory;
            } else {
                FileUtil.deleteFile(path+url);
                return null;
            }
        }catch (Exception e){
            FileUtil.deleteFile(path+url);
            return null;
        }
    }

}
