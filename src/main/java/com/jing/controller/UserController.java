package com.jing.controller;

import com.jing.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private com.jing.service.UserService UserService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        UserService.loadUserByUsername(username);
        System.out.println("login....");
        return "login success";
    }

    @RequestMapping("/register")
    public String register(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(user.getPassword().trim());
        user.setPassword(password);
        user.setUserType("normal");
        UserService.insertUser(user);
        System.out.println("register....");
        return"/registerSuccess";
    }

    @Secured({"ROLE_admin","ROLE_normal"})
    @RequestMapping("/selectUser")
    public String selectUser(Model model) {
        List<User> users = UserService.selectList(null);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        model.addAttribute("users",users);
        System.out.println("selectUsers....");
        return "pages/userList";
    }

}
