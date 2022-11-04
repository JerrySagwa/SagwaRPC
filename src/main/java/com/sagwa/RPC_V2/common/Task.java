package com.sagwa.RPC_V2.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @Author Sagwa
 * @Date 2022/11/4 20:08
 * @ClassName Task
 */
public class Task implements Runnable{
    private final Map<String, Object> serviceProvider;
    private Socket socket;

    public Task(Map<String, Object> serviceProvider, Socket socket) {
        this.serviceProvider = serviceProvider;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            RPCRequest request = (RPCRequest) ois.readObject();
            RPCResponse response = getResponse(request);
            oos.writeObject(response);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("IO读取错误");
        }

    }

    private RPCResponse getResponse(RPCRequest request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String serviceName = request.getInterfaceName();
        Object o = serviceProvider.get(serviceName);
        Method method = o.getClass().getDeclaredMethod(request.getMethodName(), request.getParamTypes());
        Object result = method.invoke(o, request.getParams());
        RPCResponse response = RPCResponse.success(result);
        return response;
    }
}
