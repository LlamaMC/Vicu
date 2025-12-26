package com.llamamc.spit.networking.packet;

import com.llamamc.spit.api.packet.IPacket;

import io.netty.buffer.ByteBuf;

public record Packet(int id, ByteBuf payload) implements IPacket {
}
