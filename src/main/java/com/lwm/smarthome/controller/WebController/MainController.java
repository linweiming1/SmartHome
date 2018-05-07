package com.lwm.smarthome.controller.WebController;

import com.google.gson.Gson;
import com.lwm.common.Weather;
import com.lwm.smarthome.entity.Permission;
import com.lwm.smarthome.entity.Role;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.PermissionService;
import com.lwm.smarthome.service.RoleService;
import com.lwm.smarthome.service.SysUserService;
import com.lwm.smarthome.shiro.PermissionName;
import com.lwm.util.WeatherUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.relation.RoleResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/*
* web端主要的控制器
* */
@Controller
public class MainController {

    public static Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    SysUserService sysUserService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RoleService roleService;

    @RequestMapping("workbench")
    public String workbench(Model model, HttpSession session) {
        Weather weather = WeatherUtil.getWeather("福州");
        if (weather == null) {
            return "netfail";
        }
        model.addAttribute("entity", weather);
        return "workbench";
    }

    @RequestMapping("personalInfo")
    public String personalInfo(Model model, HttpSession session) {
        SysUser sysUser = (SysUser) session.getAttribute("current_user_Info");
        Set<Permission> permissionsSet = new HashSet<>();
        permissionsSet = permissionService.getPermissionNameBySysUser(sysUser);
        model.addAttribute("permissionName", permissionsSet);
        return "info";
    }

    @RequestMapping("/authInfo")
    @RequiresPermissions("admin:list")
    @PermissionName("用户列表")
    public String authInfo(HttpSession session, Model model) {
        SysUser sysUser = (SysUser) session.getAttribute("current_user_Info");
        String id = String.valueOf(sysUser.getId());
        List<SysUser> sysUserList = new LinkedList<>();
        sysUserList = sysUserService.findAllByAuthorizer(id);

        model.addAttribute("sysUserList", sysUserList);
        return "authInfo";
    }

    @RequestMapping("toPsw")
    @RequiresPermissions("user:updatePws")
    @PermissionName("用户修改密码")
    public String toPsw() {
        return "psw";
    }

    @RequestMapping("updatePsw")
    public String updatePsw(HttpSession httpSession, HttpServletRequest request, Model model) {

        // 从session　中取出来用户数据
        SysUser aUser = (SysUser) httpSession.getAttribute("current_user");
        String msg = null;
        // 旧密码比对
        String oldPassword = (String) request.getParameter("oldPassword");
        String newPassword = (String) request.getParameter("newPassword");
        if (aUser.getPassWord().equals(oldPassword)) {
            // 旧密码对了，可以更新
            aUser.setPassWord(newPassword);
            sysUserService.updateSysUser(aUser);
            msg = "修改成功！";
        } else {
            // 旧密码错误，不能更新
            msg = "旧密码错误！";
        }
        model.addAttribute("msg", msg);
        return "psw";
    }

    @RequestMapping("/toAddVisitor")
    public String toAddVisitor() {
        String returnMsg = null;
        return "addVisitor";


    }

    @ResponseBody
    @RequestMapping("/addVisitor")
    public String addVisitor(@RequestBody SysUser sysUser, HttpSession session) {
        String returnMsg = "ok";
        SysUser currUser = (SysUser) session.getAttribute("current_user_Info");
        sysUser.setAuthLevel("3");
        sysUser.setAuthorizer(String.valueOf(currUser.getId()));
        sysUser.setCreateTime(new Date());
        sysUser.setFamilyName(currUser.getFamilyName());
        Role role = new Role();
        role.setRoleName(sysUser.getUserName());
        Set<Role> roles = new HashSet();
        roleService.save(roles);
        sysUser.setRoles(roles);
        sysUserService.saveSysUser(sysUser);
        return returnMsg;
    }

    @ResponseBody
    @RequestMapping("/deleteVisitor")
    public String deleteVisitor(@RequestParam(value = "id") String id) {
        String returnMsg = "ok";
        sysUserService.deleteVisitor(id);
        return returnMsg;
    }

    @RequestMapping("/showVisitorPermission")
    public String showVisitorPermission(@RequestParam(value = "id") String id, Model model) {
        SysUser sysUser = sysUserService.findById(id);
        Set<Permission> permissionSet = permissionService.getPermissionNameBySysUser(sysUser);
        model.addAttribute("visitorUser", sysUser);
        model.addAttribute("permissionSet", permissionSet);
        return "visitorPer";
    }

    @RequestMapping("/toAuthSelect")
    public String toAuthSelect(@RequestParam(value = "id") String id, Model model) {
        SysUser sysUser = sysUserService.findById(id);
        Set<String> permissionSet = permissionService.getPermissionBySysUser(sysUser);
        Set<String> permissionSetNew = new HashSet<>();
        Iterator iterator = permissionSet.iterator();
        while (iterator.hasNext()) {
            String permissionName = (String) iterator.next();
            String permissionNameNew = permissionName.replace(":", "");
            permissionSetNew.add(permissionNameNew);
        }
        Gson gson = new Gson();
        String permissionSetJson = gson.toJson(permissionSetNew).toString();

        model.addAttribute("permissionSet", permissionSetJson);
        model.addAttribute("visitor", sysUser);
        return "authSelect";
    }

    @ResponseBody
    @RequestMapping("/savePermission")
    public String savePermission(@RequestParam(value = "id") String id, @RequestBody String[] permissionList) {
        String returnMsg = null;
        SysUser sysUser = sysUserService.findById(id);
        Set roleSet = sysUser.getRoles();
        Iterator iterator = roleSet.iterator();
        Role role = (Role) iterator.next();

        roleService.savePermissions(role, permissionList);

        returnMsg = "ok";
        return returnMsg;
    }

}
