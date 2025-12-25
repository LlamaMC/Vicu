package com.llamamc.vicu.networking;

import com.llamamc.vicu.api.event.IEventBus;
import com.llamamc.vicu.networking.event.ClientHandler;
import com.llamamc.vicu.networking.minecraft.MinecraftFrameDecoder;
import com.llamamc.vicu.networking.minecraft.MinecraftPacketDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public final class VicuServerInitializer extends ChannelInitializer<SocketChannel> {
	private final IEventBus eventBus;

	public VicuServerInitializer(IEventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	protected void initChannel(SocketChannel ch) {
		ch.pipeline().addLast(
			new MinecraftFrameDecoder(),
			new MinecraftPacketDecoder(),
			new ClientHandler(eventBus)
		);
	}
}
