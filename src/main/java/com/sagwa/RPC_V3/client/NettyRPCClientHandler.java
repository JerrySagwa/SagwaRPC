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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCResponse rpcResponse) throws Exception {
        AttributeKey<RPCResponse> response = AttributeKey.valueOf("response");
        channelHandlerContext.channel().attr(response).set(rpcResponse);
        channelHandlerContext.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
