package com.sagwa.RPC_V1.client;

import com.sagwa.RPC_V1.common.User;
import com.sagwa.RPC_V1.service.UserService;

/**
 * @Author Sagwa
 * @Date 2022/11/3 19:38
 * @ClassName RPCClient
 */
public class RPCClient {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
        UserService proxy = clientProxy.getProxy(UserService.class);
        System.out.println("user from server : " + proxy.getUserById(10));
        User user = User.builder().userName("jim").id(10).gender(User.FEMALE).build();
        Integer id = proxy.insertUserId(user);
        System.out.println("id = " + id);
    }
}
