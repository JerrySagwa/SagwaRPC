package com.sagwa.RPC_V2.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * User类是服务端和客户端共有的
 *      客户端：发送请求，解析响应数据，反序列化成 pojo
 *      服务端：调用方法，将 User 序列化为字节流
 * @Author Sagwa
 * @Date 2022/11/3 19:07
 * @ClassName User
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    public final static int MALE = 1;
    public final static int FEMALE = 0;
    private int id;
    private String userName;
    private int gender;
}
