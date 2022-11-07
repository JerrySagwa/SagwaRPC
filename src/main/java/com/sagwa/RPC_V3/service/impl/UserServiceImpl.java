package com.sagwa.RPC_V3.service.impl;

import com.sagwa.RPC_V3.common.User;
import com.sagwa.RPC_V3.service.UserService;

public class UserServiceImpl implements UserService {


    @Override
    public User getUserById(int id) {
        // 模拟从数据库中取用户的行为
        User user = User.builder().id(id).userName("he2121").gender(User.FEMALE).build();
        System.out.println("客户端查询了"+id+"用户");
        return user;
    }

    @Override
    public Integer insertUserId(User user) {
        System.out.println("插入数据成功："+user);
        return 1;
    }
}