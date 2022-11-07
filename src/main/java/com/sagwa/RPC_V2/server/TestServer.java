package com.sagwa.RPC_V2.server;

import com.sagwa.RPC_V2 .service.UserServiceImpl;
import com.sagwa.RPC_V2.server.ServerImpl.ThreadPoolRPCServer;
import com.sagwa.RPC_V2.service.BlogServiceImpl;
import com.sagwa.RPC_V2.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author Sagwa
 * @Date 2022/11/4 19:55
 * @ClassName TestServer
 */
public class TestServer {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        BlogServiceImpl blogService = new BlogServiceImpl();
        // 暂时用 Map 来存服务
        Map<String, Object> serviceProvider = new HashMap<>();
        serviceProvider.put("com.sagwa.RPC_V2.service.UserService", userService);
        serviceProvider.put("com.sagwa.RPC_V2.service.BlogService", blogService);

        ThreadPoolRPCServer server = new ThreadPoolRPCServer(serviceProvider);
        server.start(8899);
    }
}
