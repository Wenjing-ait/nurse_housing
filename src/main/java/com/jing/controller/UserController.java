package com.jing.controller;

import com.jing.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private com.jing.service.UserService UserService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login() {
        System.out.println("login....");
        return "login success";
    }

    @RequestMapping("/register")
    public String register(User user) {
        user.setUserType("common");
        UserService.insertUser(user);
        System.out.println("register....");
        return"redirect:http:localhost:8080/registerSuccess.html";
    }

    @RequestMapping("/selectUser")
    public String selectUser(Model model) {
        List<User> users = UserService.selectList(null);
        model.addAttribute("users",users);
        System.out.println("register....");
        return "pages/userList";
    }


}
