package com.sagwa.RPC_V2.server;



/**
 * @Author Sagwa
 * @Date 2022/11/3 19:38
 * @ClassName RPCServer
 */
public interface RPCServer {
    void start(int port);
    void stop();
}
