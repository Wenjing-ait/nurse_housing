package com.jing.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jing.mapper.UserMapper;
import com.jing.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper UserMapper;

    public int insertUser(User user) {
        int insert = UserMapper.insert(user);
        return insert;
    }

    public List<User> selectList(User user) {
        List<User> users = UserMapper.selectList(null);
        return users;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("user_name", username);
        User user = UserMapper.selectOne(wrapper);
        if (user == null) {
            throw new UsernameNotFoundException("user is not exist");
        }
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                new BCryptPasswordEncoder().encode(user.getPassword()), auths);
    }
}
