package com.llamamc.spit.networking.minecraft;

import java.util.List;

import com.llamamc.spit.networking.event.VarInts;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MinecraftFrameDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		in.markReaderIndex();
		if (!in.isReadable()) {
			return;
		}
		int length;
		try {
			length = VarInts.read(in);
		} catch (Exception e) {
			in.resetReaderIndex();
			return;
		}
		if (in.readableBytes() < length) {
			in.resetReaderIndex();
			return;
		}
		out.add(in.readBytes(length));
	}
}
