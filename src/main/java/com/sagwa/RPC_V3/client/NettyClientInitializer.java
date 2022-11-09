package com.sagwa.RPC_V3.client;

import com.sagwa.RPC_V3.common.MyCodec;
import com.sagwa.RPC_V3.common.serialize.impl.JsonSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author Sagwa
 * @Date 2022/11/9 22:23
 * @ClassName NettyClientInitializer
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyCodec(new JsonSerializer()));
        pipeline.addLast(new NettyRPCClientHandler());
    }
}
