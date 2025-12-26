package com.llamamc.spit.networking;

import com.llamamc.spit.api.event.IEventBus;
import com.llamamc.spit.networking.event.ClientHandler;
import com.llamamc.spit.networking.minecraft.MinecraftFrameDecoder;
import com.llamamc.spit.networking.minecraft.MinecraftPacketDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public final class SpitServerInitializer extends ChannelInitializer<SocketChannel> {
	private final IEventBus eventBus;

	public SpitServerInitializer(IEventBus eventBus) {
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
