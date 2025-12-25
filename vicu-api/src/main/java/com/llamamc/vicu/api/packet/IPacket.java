package com.llamamc.vicu.api.packet;

import io.netty.buffer.ByteBuf;

public interface IPacket {
	int id();
	ByteBuf payload();
}
