package com.sagwa.RPC_V1.server;

import com.sagwa.RPC_V1.common.RPCRequest;
import com.sagwa.RPC_V1.common.RPCResponse;
import com.sagwa.RPC_V1.service.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Sagwa
 * @Date 2022/11/3 19:38
 * @ClassName RPCServer
 */
public class RPCServer {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        ExecutorService pool = Executors.newFixedThreadPool(2);

        try {
            ServerSocket serverSocket = new ServerSocket(8899);
            System.out.println("服务端启动");
            // BIO
            while (true) {
                Socket socket = serverSocket.accept();
                pool.submit(() -> {
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        // 读取客户端传过来的request
                        RPCRequest request = (RPCRequest) ois.readObject();
                        // 反射调用对应方法
                        Method method = userService.getClass().getMethod(request.getMethodName(), request.getParamTypes());
                        Object invoke = method.invoke(userService, request.getParams());
                        System.out.println("invoke = " + invoke);
                        // 封装，写入response对象
                        oos.writeObject(RPCResponse.success(invoke));
                        oos.flush();
                    }catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                            InvocationTargetException e){
                        e.printStackTrace();
                        System.out.println("从IO中读取数据错误");
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            pool.shutdown();
        }
    }
}
