package com.lwm.smarthome.controller;

import com.lwm.smarthome.entity.User;
import com.lwm.smarthome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/frame")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/loginValide.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE
            + ";charset=UTF-8")
    public @ResponseBody String checkUser(@RequestBody User user) {
        System.out.println("登陆成功");
        String datas="linweiming";
        return datas;
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE
            + ";charset=UTF-8")
    public String saveUser(@RequestBody User user) {
        System.out.println("进来了~~~~");
        return userService.add(user);
    }

    @RequestMapping(value = "/saveUser/{account}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE
            + ";charset=UTF-8")
    public String add(@PathVariable String account) {
        return userService.addUser(account);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE
            + ";charset=UTF-8")
    public User add(@PathVariable Long id) {
        return userService.getOneUser(id);
    }
}


