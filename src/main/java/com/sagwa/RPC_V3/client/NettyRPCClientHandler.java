package com.sagwa.RPC_V3.client;

import com.sagwa.RPC_V3.common.RPCResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/**
 * @Author Sagwa
 * @Date 2022/11/7 19:39
 * @ClassName RPCClientHandler
 */
public class NettyRPCClientHandler extends SimpleChannelInboundHandler<RPCResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCResponse response) throws Exception {
        System.out.println("response = " + response);
        AttributeKey<RPCResponse> key = AttributeKey.valueOf("response");
        channelHandlerContext.channel().attr(key).set(response);
        channelHandlerContext.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
