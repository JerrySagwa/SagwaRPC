package com.sagwa.RPC_V3.service;

import java.net.InetSocketAddress;

/**
 * @Author Sagwa
 * @Date 2022/11/10 10:34
 * @ClassName ServiceRegister
 */
public interface ServiceRegister {
    /**
     * 服务注册
     * @param serviceName
     * @param serverAddress
     */
    void register(String serviceName, InetSocketAddress serverAddress);

    /**
     * 服务发现
     * @param serviceName
     * @return
     */
    InetSocketAddress serviceDiscovery(String serviceName);
}
