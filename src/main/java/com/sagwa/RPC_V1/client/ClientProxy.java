package com.sagwa.RPC_V1.client;

import com.sagwa.RPC_V1.common.RPCRequest;
import com.sagwa.RPC_V1.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author Sagwa
 * @Date 2022/11/3 20:27
 * @ClassName ClientProxy
 */
@AllArgsConstructor
public class ClientProxy implements InvocationHandler {

    private String host;
    private int port;

    // jdk 动态代理， 每一次代理对象调用方法，会经过此方法增强（反射获取request对象，socket发送至客户端）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 调用过程 -- 向服务端发送请求，接收响应
        RPCRequest rpcRequest = RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramTypes(method.getParameterTypes()).build();
        RPCResponse response = IOClient.sendRequest(host, port, rpcRequest);
        return response.getData();
    }

    <T> T getProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T) o;
    }
}
