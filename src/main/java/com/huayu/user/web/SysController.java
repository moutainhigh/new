package com.huayu.user.web;

import com.alibaba.fastjson.JSON;
import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.a.service.tools.URLFilterInvocationSecurityMetadataSource;
import com.huayu.common.BaseResult;
import com.huayu.user.domain.*;
import com.huayu.user.service.SysService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by DengPeng on 2016/11/21.
 */
@Controller
@RequestMapping("/admin/sys")
public class SysController {

    @Autowired
    private SysService sysService;

    @Autowired
    private URLFilterInvocationSecurityMetadataSource securityMetadataSource;


    /**
     * 菜单管理
     * @param model
     * @return
     */
    @RequestMapping(value = "/menuEdit",method = RequestMethod.GET)
    public String menuEdit(Model model){

        return ".admin.sys.menuEdit";
    }

    @RequestMapping("/getAllMenus")
    @ResponseBody
    public BaseResult getAllMenus(){
        BaseResult result = BaseResult.initBaseResult();
        result.setRdata(sysService.getAllMenus(0));
        result.setSuccess();
        return result;
    }

    @RequestMapping(value = "/roleEdit",method = RequestMethod.GET)
    public String roleEdit(Model model){

        return ".admin.sys.roleEdit";
    }

    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addRole(UserQxRole role){
        BaseResult result = BaseResult.initBaseResult();
        try {
            sysService.addRole(role);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/updateRole",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateRole(UserQxRole role){
        BaseResult result = BaseResult.initBaseResult();
        try {
            sysService.updateRole(role);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/roleEdit/{system}/{roleId}",method = RequestMethod.GET)
    public String roleEdit(Model model,@PathVariable String system, @PathVariable Integer roleId, UserQxRole userQxRole){
        userQxRole.setRoleId(roleId);
        userQxRole.setRegSystem(system);
        model.addAttribute("entity",sysService.getRoleById(userQxRole));
        return ".admin.sys.roleEdit";
    }

    /**
     * 角色列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/roleList",method = RequestMethod.GET)
    public String roleList(Model model){
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("roleList",sysService.getAllRole(user.getRegSource()));
        return ".admin.sys.roleList";
    }


    /**
     * 角色关联权限
     * @param model
     * @return
     */
    @RequestMapping(value = "/roleAuthorityEdit/{roleId}",method = RequestMethod.GET)
    public String roleAuthorityEdit(Model model, @PathVariable Integer roleId, UserQxRole userQxRole){
        model.addAttribute("roleId", roleId);
        User user = SpringSecurityUtil.getUser();
        userQxRole.setRoleId(roleId);
        userQxRole.setRegSystem(user.getRegSource());
        model.addAttribute("role", sysService.getRoleById(userQxRole));
        return ".admin.sys.roleAuthorityEdit";
    }

    /**
     * 获取所有菜单并且设置对应的选中
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/getRoleMenus",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getRoleMenus(Integer roleId){
        BaseResult result = BaseResult.initBaseResult();
        if (null!=roleId){
            result.setRdata(sysService.getAllMenuWithRoleId(roleId));
            result.setSuccess();
        }
        return result;
    }

    @RequestMapping(value = "/saveRoleMenus",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult saveRoleMenus(String roleMenus){
        BaseResult result = BaseResult.initBaseResult();
        if (StringUtils.isNotBlank(roleMenus)){
            try {
                List<UserQxMenu> userQxMenus = JSON.parseArray(roleMenus, UserQxMenu.class);
                sysService.saveRoleMenus(userQxMenus);
                result.setSuccess();
            } catch (Exception e) {
                result.setRmsg(e.getMessage());
            }
        }else{
            result.setRmsg("参数错误");
        }
        return result;
    }

    /**
     * 添加 修改 菜单
     * @param menu
     * @param flag
     * @return
     */
    @RequestMapping(value = "/saveMenus",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult saveMenus(UserQxMenu menu, boolean flag){
        BaseResult result = BaseResult.initBaseResult();
        if (null != menu.getMenuId() && null != menu.getParentId()){
            try {
                if (flag){
                    sysService.addMenus(menu);
                }else{
                    sysService.updateMenus(menu);
                }
                result.setSuccess();
            } catch (Exception e) {
                result.setRmsg(e.getMessage());
            }
        }else{
            result.setRmsg("参数错误");
        }
        return result;
    }


    @RequestMapping(value = "/deleteMenus",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult deleteMenus(UserQxMenu menu){
        BaseResult result = BaseResult.initBaseResult();
        try {
            sysService.deleteMenus(menu);
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/resources",method = RequestMethod.GET)
    public String resources(Model model){
        User user = SpringSecurityUtil.getUser();
        model.addAttribute("resources",sysService.getAllResources(user.getRegSource()));
        return ".admin.sys.resources";
    }

    @RequestMapping(value = "/addResource",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addResource(UserQxResource resource){
        BaseResult result = BaseResult.initBaseResult();
        try {
            sysService.addResource(resource);
            securityMetadataSource.refreshResourceMap();
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/updateResource",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateResource(UserQxResource resource){
        BaseResult result = BaseResult.initBaseResult();
        try {
            if (null ==resource.getRid() || StringUtils.isBlank(resource.getRegSystem())){
                result.setRmsg("参数错误");
                return result;
            }
            sysService.updateResource(resource);
            securityMetadataSource.refreshResourceMap();
            result.setSuccess();
        } catch (Exception e) {
            result.setRmsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/resourceEdit",method = RequestMethod.GET)
    public String resourceEdit(HttpServletRequest request, Model model){
        request.getParameter("");
        return ".admin.sys.resourceEdit";
    }

    @RequestMapping(value = "/resourceEdit/{system}/{id}",method = RequestMethod.GET)
    public String resourceEdit(Model model,@PathVariable String system, @PathVariable Integer id, UserQxResource resource){
        resource.setRid(id);
        resource.setRegSystem(system);
        model.addAttribute("entity",sysService.getResourceById(resource));
        return ".admin.sys.resourceEdit";
    }

    /**
     * 根据角色获取 获取资源，包含未选中的
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/getResourceByRoleMenu",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getResourceByRoleMenu(Integer roleId,Integer menuId){
        BaseResult result = BaseResult.initBaseResult();
        if (null!=roleId){
            result.setRdata(sysService.getResourceByRoleMenu(roleId,menuId));
            result.setSuccess();
        }
        return result;
    }

    @RequestMapping(value = "/saveRoleResource",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult saveRoleResource(String roleResources){
        BaseResult result = BaseResult.initBaseResult();
        if (StringUtils.isNotBlank(roleResources)){
            try {
                List<UserQxRoleResource> list = JSON.parseArray(roleResources, UserQxRoleResource.class);
                sysService.saveRoleResource(list);
                result.setSuccess();
            } catch (Exception e) {
                result.setRmsg(e.getMessage());
            }
        }else{
            result.setRmsg("参数错误");
        }
        return result;
    }

}
