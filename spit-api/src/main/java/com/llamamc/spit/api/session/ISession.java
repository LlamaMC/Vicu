package com.llamamc.spit.api.session;

import java.net.SocketAddress;
import java.util.UUID;

import com.llamamc.spit.api.packet.IPacket;

public interface ISession {
	UUID id();
	SocketAddress clientAddress();
	SessionState state();
	String username();
	void state(SessionState state);
	void username(String username);
	void send(IPacket packet);
	void close();
}
