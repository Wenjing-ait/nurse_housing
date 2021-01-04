package com.jing.service;

import com.jing.mapper.UserMapper;
import com.jing.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper UserMapper;

    public int insertUser(User user) {
        int insert = UserMapper.insert(user);
        return insert;
    }

    public List<User> selectList(User user){
        List<User> users = UserMapper.selectList(null);
        return users;
    }


}
