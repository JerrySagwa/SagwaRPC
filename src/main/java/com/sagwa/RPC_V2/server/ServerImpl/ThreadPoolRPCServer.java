package com.sagwa.RPC_V2.server.ServerImpl;

import com.sagwa.RPC_V2.common.Task;
import com.sagwa.RPC_V2.server.RPCServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 改进方面：线程池解耦合
 * 使用serviceProvider 提供多个服务类
 * @Author Sagwa
 * @Date 2022/11/4 20:00
 * @ClassName ThreadPoolRPCServer
 */
public class ThreadPoolRPCServer implements RPCServer {

    private final ThreadPoolExecutor threadPool;
    private Map<String, Object> serviceProvider;

    public ThreadPoolRPCServer(int corePoolSize,
                               int maximumPoolSize,
                               TimeUnit timeUnit,
                               long keepAliveTime,
                               BlockingQueue<Runnable> workQueue,
                               Map<String, Object> serviceProvider) {
        this.threadPool = new ThreadPoolExecutor(corePoolSize,
                                                maximumPoolSize,
                                                keepAliveTime,
                                                timeUnit,
                                                workQueue);
        this.serviceProvider = serviceProvider;
    }

    public ThreadPoolRPCServer(Map<String, Object> serviceProvider) {
        this.serviceProvider = serviceProvider;
        this.threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                1000, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));;
    }

    @Override
    public void start(int port) {
        System.out.println("服务端启动 ~~");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new Task(serviceProvider, socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        }

    }

    @Override
    public void stop() {

    }
}
