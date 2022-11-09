package com.sagwa.RPC_V3.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Sagwa
 * @Date 2022/11/3 19:39
 * @ClassName RPCRequest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RPCRequest implements Serializable {
    // 服务类名，客户端只知道接口名，服务端通过接口名指向实现类
    private String interfaceName;
    // 服务方法名
    private String methodName;
    // 参数列表
    private Object[] params;
    // 参数类型
    private Class<?>[] paramTypes;


}
