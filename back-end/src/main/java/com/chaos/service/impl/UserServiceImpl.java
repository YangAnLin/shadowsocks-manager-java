package com.chaos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chaos.config.ConfigValue;
import com.chaos.mapper.UserInfoMapper;
import com.chaos.po.UserInfo;
import com.chaos.service.IUserService;
import com.chaos.util.CommonUtil;
import com.chaos.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public User authUser(String name, String password) {
        User result=null;
        QueryWrapper<UserInfo> wrapper=new QueryWrapper<>();
        Map<String,String> param=new HashMap<>();
        param.put("name",name);
        param.put("password",password);
        wrapper.allEq(param,false);
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);
        if(userInfo==null){
            return null;
        }else{
            //db校验成功
            String userName=userInfo.getName();
            int role=userInfo.getRole();
            String token= CommonUtil.generateToken();

            result=new User();
            result.setUser(userName);
            result.setRole(role);
            result.setToken(token);
            result.setLoginTime(new Date());

            //token入缓存

            ConfigValue.tokenMap.put(token,result);
        }

        return result;
    }

    @Override
    public UserInfo getUser(String name) {

        QueryWrapper<UserInfo> wrapper=new QueryWrapper<>();
        wrapper.eq("name",name);
        UserInfo userInfo=userInfoMapper.selectOne(wrapper);
        return userInfo;
    }
}