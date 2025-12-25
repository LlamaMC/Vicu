package com.llamamc.vicu.api;

import java.net.SocketAddress;
import java.util.UUID;

import com.llamamc.vicu.api.packet.IPacket;

public interface ISession {
	UUID id();
	SocketAddress clientAddress();
	void send(IPacket packet);
	void close();
}
