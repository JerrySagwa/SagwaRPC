package com.sagwa.RPC_V3.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 由于 Response 不可能只传一种对象
 * 将传输对象抽象为 Object
 * 引入状态码表示服务是否成功调用
 * @Author Sagwa
 * @Date 2022/11/3 19:39
 * @ClassName RPCResponse
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RPCResponse implements Serializable {
    // 状态信息
    private int code;
    private String message;
    // 具体数据
    private Object data;
    // 其他序列化方式得不到 data 的数据类型
    private Class<?> dataType;

    public static RPCResponse success(Object data) {
        return RPCResponse.builder().code(200).message("success").data(data).dataType(data.getClass()).build();
    }

    public static RPCResponse fail() {
        return RPCResponse.builder().code(500).message("服务器错误!").build();
    }

}
