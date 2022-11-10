package com.sagwa.RPC_V3.server;

import com.sagwa.RPC_V3.service.BlogService;
import com.sagwa.RPC_V3.service.ServiceProvider;
import com.sagwa.RPC_V3.service.UserService;
import com.sagwa.RPC_V3.service.impl.BlogServiceImpl;
import com.sagwa.RPC_V3.service.impl.UserServiceImpl;

/**
 * @Author Sagwa
 * @Date 2022/11/7 20:48
 * @ClassName TestServer
 */
public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

//        Map<String, Object> serviceProvide = new HashMap<>();
//        serviceProvide.put("com.ganghuan.myRPCVersion2.service.UserService",userService);
//        serviceProvide.put("com.ganghuan.myRPCVersion2.service.BlogService",blogService);
        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8080);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(8080);
    }
}
