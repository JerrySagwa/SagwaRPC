package com.sagwa.RPC_V3.common.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sagwa.RPC_V3.common.RPCResponse;
import com.sagwa.RPC_V3.common.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Author Sagwa
 * @Date 2022/11/8 21:30
 * @ClassName TestSerializer
 */
public class TestSerializer {
    public static void main(String[] args) {
        RPCResponse response = RPCResponse.builder()
                .code(100)
                .message("hello")
                .data(User.builder().userName("Jim").id(10).gender(User.FEMALE).build())
                .dataType(User.class)
                .build();
        Object json = JSON.toJSON(response);
        System.out.println(json);
        System.out.println(json.getClass());
        RPCResponse rpcResponse = JSON.parseObject(json.toString(), RPCResponse.class);
        // 反序列化的 Data 并不是 传进去的 User！
        if (!rpcResponse.getDataType().isAssignableFrom(rpcResponse.getData().getClass())) {
            // 将 JSONObject --> JavaObject
            // 一开始反序列化后 Object Data 类型为 JSONObject
            // 通过类型参数转化！
            Object o = JSONObject.toJavaObject((JSONObject) rpcResponse.getData(), rpcResponse.getDataType());
            rpcResponse.setData(o);
        }
        System.out.println(rpcResponse);
        User data = (User) rpcResponse.getData();
        System.out.println("data = " + data);

        A a = new A(new B("Tom"), 1);
        Object o = JSON.toJSON(a);
        Object object = JSON.parseObject(o.toString(), A.class);
    }

    @NoArgsConstructor
    static class A {
        private B b;
        private int i;


        public A(B b, int i) {
            this.b = b;
            this.i = i;
        }
    }

    @NoArgsConstructor
    static class B {
        private String name;

        public B(String name) {
            this.name = name;
        }
    }
}
