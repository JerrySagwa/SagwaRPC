package com.sagwa.RPC_V3.client;

import com.sagwa.RPC_V3.common.RPCRequest;
import com.sagwa.RPC_V3.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author Sagwa
 * @Date 2022/11/6 22:56
 * @ClassName RPCClientProxy
 */
@AllArgsConstructor
public class RPCClientProxy implements InvocationHandler {

    private RPCClient rpcClient;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RPCResponse rpcResponse = null;
        try {
            RPCRequest request = RPCRequest.builder()
                    .interfaceName(method.getDeclaringClass().getName())
                    .methodName(method.getName())
                    .paramTypes(method.getParameterTypes())
                    .params(args).build();
//        System.out.println("proxy = " + proxy);
            rpcResponse = rpcClient.sendRequest(request);
            return rpcResponse.getData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public <T> T getProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}
