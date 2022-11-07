package com.sagwa.RPC_V3.client;

import com.sagwa.RPC_V3.common.RPCRequest;
import com.sagwa.RPC_V3.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 使用 Socket 实现 RPC 客户端
 * @Author Sagwa
 * @Date 2022/11/6 22:52
 * @ClassName SimpleRPCClient
 */
@AllArgsConstructor
public class SimpleRPCClient implements RPCClient{
    private String host;
    private int port;
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("send request : " + request);
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            return ((RPCResponse) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}
