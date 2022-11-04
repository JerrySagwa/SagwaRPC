package com.sagwa.RPC_V2.client;

import com.sagwa.RPC_V2.common.User;
import com.sagwa.RPC_V2.service.BlogService;
import com.sagwa.RPC_V2.service.UserService;

/**
 * @Author Sagwa
 * @Date 2022/11/3 19:38
 * @ClassName RPCClient
 */
public class RPCClient {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
        UserService userService = clientProxy.getProxy(UserService.class);
        BlogService blogService = clientProxy.getProxy(BlogService.class);
        System.out.println("user from server : " + userService.getUserById(10));
        User user = User.builder().userName("jim").id(10).gender(User.FEMALE).build();
        Integer id = userService.insertUserId(user);
        System.out.println("id = " + id);

        System.out.println("blog from server : " + blogService.getBlogById(10));
    }
}
