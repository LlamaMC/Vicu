package com.llamamc.vicu.networking;

import java.net.SocketAddress;
import java.util.UUID;

import com.llamamc.vicu.api.packet.IPacket;
import com.llamamc.vicu.api.session.ISession;
import com.llamamc.vicu.api.session.SessionState;
import com.llamamc.vicu.networking.event.VarInts;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class VicuSession implements ISession {
	private final UUID id = UUID.randomUUID();
	private final Channel channel;
	private SessionState state = SessionState.HANDSHAKE;
	private String username;

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
	public SessionState state() {
		return state;
	}
	
	@Override
	public String username() {
		return username;
	}
	
	@Override
	public void state(SessionState state) {
		this.state = state;
	}
	
	@Override
	public void username(String username) {
		this.username = username;
	}

	@Override
	public void send(IPacket packet) {
	    ByteBuf payload = packet.payload();
	    int packetId = packet.id();
	    ByteBuf packetBuf = channel.alloc().buffer();
	    VarInts.write(packetBuf, packetId);
	    packetBuf.writeBytes(payload);
	    ByteBuf finalBuf = channel.alloc().buffer();
	    VarInts.write(finalBuf, packetBuf.readableBytes());
	    finalBuf.writeBytes(packetBuf);
	    channel.writeAndFlush(finalBuf);
	}

	@Override
	public void close() {
		channel.close();
	}
}
