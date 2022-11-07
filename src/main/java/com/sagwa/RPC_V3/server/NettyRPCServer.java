package com.sagwa.RPC_V3.server;

import com.sagwa.RPC_V3.server.handler.NettyRPCServerHandler;
import com.sagwa.RPC_V3.service.ServiceProvider;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.AllArgsConstructor;

/**
 * @Author Sagwa
 * @Date 2022/11/6 23:55
 * @ClassName NettyRPCServer
 */
@AllArgsConstructor
public class NettyRPCServer implements RPCServer{
    private ServiceProvider serviceProvider;

    @Override
    public void start(int port) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        System.out.println("服务端启动");
        try {
            ChannelFuture channelFuture = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 消息格式 [长度][消息体], 解决粘包问题
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            // 计算当前待发送消息的长度，写入到前4个字节中
                            pipeline.addLast(new LengthFieldPrepender(4));

                            // 这里使用的还是java 序列化方式， netty的自带的解码编码支持传输这种结构
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(className -> Class.forName(className)));

                            pipeline.addLast(new NettyRPCServerHandler(serviceProvider));
                        }
                    })
                            .bind("localhost", port).sync();
                    // 等待关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    @Override
    public void stop() {

    }
}
