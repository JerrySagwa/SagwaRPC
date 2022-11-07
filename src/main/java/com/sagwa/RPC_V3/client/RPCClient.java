package com.sagwa.RPC_V3.client;

import com.sagwa.RPC_V3.common.RPCRequest;
import com.sagwa.RPC_V3.common.RPCResponse;

/**
 * 抽取共性 NettyRPCClient SocketRPCClient
 * RPCClient:不同的网络连接，网络传输方式的客户端分别实现这个接口
 *
 * XXXRPCClient:具体实现类
 *
 * RPCClientProxy:动态代理Service类，封装不同的Service请求为Request对象，
 *                并且持有一个RPCClient对象，负责与服务端的通信，
 * @Author Sagwa
 * @Date 2022/11/6 22:46
 * @ClassName RPCClient
 */
public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
