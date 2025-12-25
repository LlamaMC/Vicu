package com.llamamc.vicu.networking.packet;

import com.llamamc.vicu.api.packet.IPacket;

import io.netty.buffer.ByteBuf;

public record Packet(int id, ByteBuf payload) implements IPacket {
}
