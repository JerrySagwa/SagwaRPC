package com.sagwa.RPC_V3.server;

/**
 * @Author Sagwa
 * @Date 2022/11/7 0:10
 * @ClassName RPCServer
 */
public interface RPCServer {
    void start(int port);
    void stop();
}
