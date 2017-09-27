package com.huayu.bill.web;

import com.huayu.a.service.tools.SpringSecurityUtil;
import com.huayu.common.PageArgs;
import com.huayu.user.service.SysService;
import com.huayu.user.service.UserService;
import com.huayu.user.web.args.UserArgs;
import com.huayu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by DengPeng on 2017/5/16.
 */
@Controller
@RequestMapping("/admin/bill")
public class BillUserController {

    private final static String  regSource = "bill";

    @Autowired
    private UserService userService;

    @Autowired
    private SysService sysService;

    @RequestMapping(value = "/user/list",method = RequestMethod.GET)
    public String userList(Model model, User user){
        user.setRegSource(regSource);
        model.addAttribute("entity",user);
        return ".admin.bill.userList";
    }

    @RequestMapping(value = "/user/listData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> userListData(@RequestParam Map<String,Object> param, UserArgs user, PageArgs pageArgs){

        user.setRegSource(regSource);
        Pageable pageable = new PageRequest(pageArgs.getPageIndex(), pageArgs.getLength());
        Page<User> page = userService.get(user,pageable);
        param.put("data",page.getContent());
        param.put("recordsTotal",page.getTotalElements());
        param.put("recordsFiltered",page.getTotalElements());
        return param;
    }

    @RequestMapping(value = "/user/edit",method = RequestMethod.GET)
    public String edit(Model model){
        model.addAttribute("user", SpringSecurityUtil.getUser());
        model.addAttribute("roles", sysService.getAllRoleWithGroup(regSource));
        return ".admin.bill.userEdit";
    }

    @RequestMapping(value = "/user/edit/{id}/{reg}",method = RequestMethod.GET)
    public String edit(Model model, @PathVariable Long id,@PathVariable String reg){
        model.addAttribute("user",SpringSecurityUtil.getUser());
        model.addAttribute("entity",userService.getUserOne(id, regSource));
        model.addAttribute("roles", sysService.getAllRoleWithGroup(regSource));
        return ".admin.bill.userEdit";
    }

    @RequestMapping(value = "/user/registration",method = RequestMethod.POST)
    @ResponseBody
    public int userRregistration(User user){
        return userService.userRregistration(user);
    }

    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
    @ResponseBody
    public int userUpdate(User user){
        if (null==user.getUserId()|| StringUtils.isEmpty(user.getUsername())){
            return 0;
        }
        return userService.userUpdate(user);
    }

    @RequestMapping(value = "/user/delete",method = RequestMethod.POST)
    @ResponseBody
    public int delUser(Long id ,String reg){
        if (null == id||!StringUtils.hasText(reg)){
            return 2;
        }
        return userService.delUser(id,reg);
    }
}
