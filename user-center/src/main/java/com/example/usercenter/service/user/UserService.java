package com.example.usercenter.service.user;

import com.example.usercenter.dao.user.UserMapper;
import com.example.usercenter.domain.entity.user.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    public User findById(Integer id){
       return userMapper.selectByPrimaryKey(id);
    }
}
