package com.sagwa.RPC_V3.service.impl;

import com.sagwa.RPC_V3.service.ServiceRegister;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author Sagwa
 * @Date 2022/11/10 10:36
 * @ClassName ZkServiceRegister
 */
public class ZkServiceRegister implements ServiceRegister {
    // curator 提供的 ZK 客户端
    private CuratorFramework client;
    // ZK 根节点
    private static final String ROOT_PATH = "SRPC";

    public ZkServiceRegister() {
        // 重试策略
        ExponentialBackoffRetry policy = new ExponentialBackoffRetry(1000, 3);
        /**
         * zookeeper 地址固定192.128.40.129:2181
         * sessionTimeoutMs 与 zoo.cfg中的tickTime 有关系
         * zk还会根据minSessionTimeout与maxSessionTimeout两个参数重新调整最后的超时值。默认分别为tickTime 的2倍和20倍
         * 使用心跳监听状态
         */
        client = CuratorFrameworkFactory.builder()
                .retryPolicy(policy)
                .sessionTimeoutMs(4000)
                .connectString("192.168.40.129:2181")
                .namespace(ROOT_PATH)
                .build();
        client.start();
        System.out.println("zookeeper connection built successfully!");
    }

    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        try {
            if (client.checkExists().forPath("/" + serviceName) == null) {
                // 以服务名创建永久节点
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                        .forPath("/" + serviceName);
            }

            String path = "/" + serviceName + "/" + getServiceAddress(serverAddress);
            // 创建临时节点，服务器下线就删除
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                    .forPath(path);
            System.out.println
                    ("service " + serviceName + " " + serverAddress.getHostName() + ":" + serverAddress.getPort() + " 注册成功！");
        } catch (Exception e) {
            System.out.println("服务已存在");
        }
    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            List<String> strings = client.getChildren().forPath("/" + serviceName);
            // TODO 默认第一个，后面再用负载均衡
            String s = strings.get(0);
            return parseAddress(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private InetSocketAddress parseAddress(String s) {
        String[] result = s.split(":");
        return new InetSocketAddress(result[0], Integer.parseInt(result[1]));
    }

    // 地址 -> XXX.XXX.XXX.XXX:port 字符串
    private String getServiceAddress(InetSocketAddress serverAddress) {
        return serverAddress.getHostName() +
                ":" +
                serverAddress.getPort();
    }
}
