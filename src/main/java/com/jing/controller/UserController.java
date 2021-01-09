package com.jing.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jing.pojo.Role;
import com.jing.pojo.User;
import com.jing.pojo.UserRole;
import com.jing.service.RoleService;
import com.jing.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private com.jing.service.UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        userService.loadUserByUsername(username);
        System.out.println("login....");
        return "login success";
    }

    @RequestMapping("/register")
    public String register(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(user.getPassword().trim());
        user.setPassword(password);
        user.setUserType("normal");
        userService.insertUser(user);

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setUserName(user.getUserName());
        userRole.setRoleId(2);
        userRoleService.insertUserRole(userRole);

        System.out.println("register....");
        return "/registerSuccess";
    }

    @Secured({"ROLE_Admin", "ROLE_Normal","ROLE_VIP"})
    @RequestMapping("/selectUser")
    public String selectUser(Model model) {
        List<User> users = userService.selectList(null);
        sendUserInfoToPage(model);

        model.addAttribute("users", users);
        System.out.println("selectUsers....");
        return "pages/userList";
    }

    private void sendUserInfoToPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("userDetails", userDetails);
        model.addAttribute("authorities", authorities);
    }

    @RequestMapping("/home")
    public String home(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        System.out.println("selectUsers....");
        return "pages/home";
    }

    @RequestMapping("/pay")
    public String pay() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.selectOne(username);
        Long userId = user.getId();

        UpdateWrapper<UserRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId);
        UserRole userRole = new UserRole();
        userRole.setRoleId(3);
        userRoleService.updateUserRole(userRole, updateWrapper);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
        List<Role> roles = roleService.selectRoleByUsername(username);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < roles.size(); i++) {
            Role role = roles.get(i);
            stringBuffer.append("ROLE_"+role.getName());
            if (i < roles.size() - 1) {
                stringBuffer.append(",");
            }
        }
        SimpleGrantedAuthority role_new_role = new SimpleGrantedAuthority(stringBuffer.toString());
        updatedAuthorities.add(role_new_role);//add your role here [e.g., new SimpleGrantedAuthority("ROLE_NEW_ROLE")]
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return "redirect:/selectUser";
    }

}
