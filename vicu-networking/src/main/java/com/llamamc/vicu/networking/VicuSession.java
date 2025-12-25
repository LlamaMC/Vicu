package com.llamamc.vicu.networking;

import java.net.SocketAddress;
import java.util.UUID;

import com.llamamc.vicu.api.ISession;
import com.llamamc.vicu.api.packet.IPacket;

import io.netty.channel.Channel;

public class VicuSession implements ISession {
	private final UUID id = UUID.randomUUID();
	private final Channel channel;

	public VicuSession(Channel channel) {
		this.channel = channel;
	}

	@Override
	public UUID id() {
		return id;
	}

	@Override
	public SocketAddress clientAddress() {
		return channel.remoteAddress();
	}

	@Override
	public void send(IPacket packet) {
		channel.writeAndFlush(packet);
	}

	@Override
	public void close() {
		channel.close();
	}
}
