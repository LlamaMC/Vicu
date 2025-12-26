package com.llamamc.spit.api.packet;

import io.netty.buffer.ByteBuf;

public interface IPacket {
    int id();
    ByteBuf payload();
}
