package com.sagwa.RPC_V3.client;


import com.sagwa.RPC_V3.common.MyCodec;
import com.sagwa.RPC_V3.common.RPCRequest;
import com.sagwa.RPC_V3.common.RPCResponse;
import com.sagwa.RPC_V3.common.serialize.impl.JsonSerializer;
import com.sagwa.RPC_V3.common.serialize.impl.ObjectSerializer;
import com.sagwa.RPC_V3.service.ServiceRegister;
import com.sagwa.RPC_V3.service.impl.ZkServiceRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * @Author Sagwa
 * @Date 2022/11/7 19:33
 * @ClassName NettyRPCClient
 */
public class NettyRPCClient implements RPCClient {

    private final static Bootstrap bootstrap;
    private final static NioEventLoopGroup eventLoopGroup;
    private ServiceRegister serviceRegister;

    private String host;
    private int port;

    public NettyRPCClient() {
        serviceRegister = new ZkServiceRegister();
    }

    static {
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }

    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try {
            String interfaceName = request.getInterfaceName();
            InetSocketAddress address = serviceRegister.serviceDiscovery(interfaceName);
            host = address.getHostName();
            port = address.getPort();
            System.out.println(host + ":" + port);
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(request);
            channel.closeFuture().sync();
            AttributeKey<RPCResponse> key = AttributeKey.valueOf("response");
            RPCResponse response = channel.attr(key).get();
            System.out.println(response);
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
