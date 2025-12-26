package com.llamamc.spit.networking.minecraft;

import java.util.List;

import com.llamamc.spit.networking.event.VarInts;
import com.llamamc.spit.networking.packet.Packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class MinecraftPacketDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
		buf.readerIndex();
	    int packetId = VarInts.read(buf);
	    int payloadStart = buf.readerIndex();
	    int payloadLength = buf.readableBytes();
	    ByteBuf payload = buf.slice(payloadStart, payloadLength).retain();
	    out.add(new Packet(packetId, payload));
	}
}
