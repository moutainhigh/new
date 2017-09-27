package com.huayu.user.web;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.BusinessException;
import com.huayu.common.Constants;
import com.huayu.common.PageArgs;
import com.huayu.user.domain.User;
import com.huayu.user.service.SysService;
import com.huayu.user.service.UserService;
import com.huayu.user.web.args.UserArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by DengPeng on 2016/11/21.
 */
@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SysService sysService;


    @RequestMapping(value = "/user/toUpdatePassword",method = RequestMethod.GET)
    public String toUpdatePassword(Model model){
        model.addAttribute("entity", SpringSecurityUtil.getUser());
        return ".admin.user.toUpdatePassword";
    }

    @RequestMapping(value = "/user/updatePassword",method = RequestMethod.POST)
    @ResponseBody
    public String updatePassword(String oldPassword, String password){
        try {
            userService.updatePassword(oldPassword,password);
        } catch (BusinessException e) {
            return e.getMessage();
        }
        return "success";
    }


    @RequestMapping(value = "/user/list",method = RequestMethod.GET)
    public String userList(Model model, User user){
        user.setRegSource(Constants.ADMIN);
        model.addAttribute("entity",user);
        return ".admin.user.userList";
    }

    @RequestMapping(value = "/user/listData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> userListData(@RequestParam Map<String,Object> param, User user, UserArgs userArgs, PageArgs pageArgs){
        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength(), Sort.Direction.DESC,"createDate");
        Page<User> page = userService.get(userArgs,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }


    @RequestMapping(value = "/user/edit",method = RequestMethod.GET)
    public String edit(Model model){
        model.addAttribute("roles", sysService.getAllRoleWithGroup(Constants.ADMIN));
        return ".admin.user.edit";
    }

    @RequestMapping(value = "/user/edit/{id}/{reg}",method = RequestMethod.GET)
    public String edit(Model model, @PathVariable Long id, @PathVariable String reg){
        model.addAttribute("entity",userService.getUserOne(id, Constants.ADMIN));
        model.addAttribute("roles", sysService.getAllRoleWithGroup(Constants.ADMIN));
        return ".admin.user.edit";
    }

    @RequestMapping(value = "/user/userRregistration",method = RequestMethod.POST)
    @ResponseBody
    public int userRregistration(User user){
        return userService.userRregistration(user);
    }

    @RequestMapping(value = "/user/userUpdate",method = RequestMethod.POST)
    @ResponseBody
    public int userUpdate(User user){
        if (null==user.getUserId()|| StringUtils.isEmpty(user.getUsername())){
            return 0;
        }
        return userService.userUpdate(user);
    }

    @RequestMapping(value = "/user/delUser",method = RequestMethod.POST)
    @ResponseBody
    public int delUser(Long id ,String reg){
        if (null == id||!StringUtils.hasText(reg)){
            return 2;
        }
        return userService.delUser(id,reg);
    }

}
