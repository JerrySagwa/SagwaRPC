package com.sagwa.RPC_V3.client;

import com.sagwa.RPC_V3.service.BlogService;
import com.sagwa.RPC_V3.service.UserService;

/**
 * @Author Sagwa
 * @Date 2022/11/7 20:43
 * @ClassName TestNettRPC
 */
public class TestNettyRPCClient {
    public static void main(String[] args) {
//        NettyRPCClient client = new NettyRPCClient("localhost", 8080);
//        RPCClientProxy proxy = new RPCClientProxy(client);
//        UserService userService = proxy.getProxy(UserService.class);
//        System.out.println(userService.getUserById(10));
//        System.out.println("<===========>");
//        BlogService blogService = proxy.getProxy(BlogService.class);
//        System.out.println(blogService.getBlogById(10));
        RPCClientProxy proxy = new RPCClientProxy(new NettyRPCClient());
        UserService userService = proxy.getProxy(UserService.class);
        System.out.println(userService.getUserById(10));
    }
}
