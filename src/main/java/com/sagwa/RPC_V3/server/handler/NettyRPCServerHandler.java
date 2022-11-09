package com.sagwa.RPC_V3.server.handler;

import com.sagwa.RPC_V3.common.RPCRequest;
import com.sagwa.RPC_V3.common.RPCResponse;
import com.sagwa.RPC_V3.service.ServiceProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author Sagwa
 * @Date 2022/11/7 0:17
 * @ClassName NettyRPCServerHandler
 */
@AllArgsConstructor
public class NettyRPCServerHandler extends SimpleChannelInboundHandler<RPCRequest> {
    private ServiceProvider serviceProvider;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RPCRequest request) throws Exception {
        RPCResponse response = getResponse(request);
        System.out.println(response);
        ctx.writeAndFlush(response);
        ctx.close();
    }

    private RPCResponse getResponse(RPCRequest request) {
        String interfaceName = request.getInterfaceName();
        String methodName = request.getMethodName();
        Class<?>[] paramTypes = request.getParamTypes();
        Object[] params = request.getParams();
        Object service = serviceProvider.getService(interfaceName);
        try {
            Method method = service.getClass().getDeclaredMethod(methodName, paramTypes);
            Object invoke = method.invoke(service, params);
            return RPCResponse.success(invoke);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return RPCResponse.fail();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
