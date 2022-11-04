package com.sagwa.RPC_V2.service;

import com.sagwa.RPC_V2.common.User;

/**
 * @Author Sagwa
 * @Date 2022/11/3 19:19
 * @ClassName UserService
 */
public interface UserService {
    User getUserById(int id);

    Integer insertUserId(User user);
}
