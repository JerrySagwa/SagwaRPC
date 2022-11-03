package com.sagwa.RPC_V1.service;

import com.sagwa.RPC_V1.common.User;

import java.util.Random;
import java.util.UUID;

/**
 * @Author Sagwa
 * @Date 2022/11/3 19:19
 * @ClassName UserServiceImpl
 */

public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(int id) {
        System.out.println("客户端查询了"+id+"的用户");
        // 模拟从数据库中取用户的行为
        Random random = new Random();
        User user = User.builder().userName(UUID.randomUUID().toString())
                .id(id)
                .gender(random.nextBoolean() ? User.FEMALE : User.MALE).build();
        return user;
    }

    @Override
    public Integer insertUserId(User user) {
        return 1;
    }
}
