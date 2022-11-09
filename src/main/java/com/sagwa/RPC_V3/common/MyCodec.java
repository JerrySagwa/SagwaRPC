package com.sagwa.RPC_V3.common;

import com.sagwa.RPC_V3.common.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @Author Sagwa
 * @Date 2022/11/8 22:36
 * @ClassName MyCodec
 */
@AllArgsConstructor
public class MyCodec extends ByteToMessageCodec {

    private Serializer serializer;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf out) throws Exception {
        System.out.println(o.getClass());
        if (o instanceof RPCRequest) {
            out.writeShort(MessageType.REQUEST);
        } else if (o instanceof RPCResponse) {
            out.writeShort(MessageType.RESPONSE);
        }

        out.writeShort(serializer.getType());
        byte[] bytes = serializer.serialize(o);
        int len = bytes.length;
        out.writeInt(len);
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List out) throws Exception {
        short messageType = in.readShort();
        if (messageType != MessageType.REQUEST && messageType != MessageType.RESPONSE) {
            System.out.println("暂不支持的消息类型!");
            return;
        }

        short serializeType = in.readShort();
        Serializer s = Serializer.getSerializerByCode(serializeType);
        int len = in.readInt();
        byte[] bytes = new byte[len];
        in.readBytes(bytes);
        Object deserialize = s.deserialize(bytes, messageType);
        out.add(deserialize);
    }
}
