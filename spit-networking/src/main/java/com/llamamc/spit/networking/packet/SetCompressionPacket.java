package com.llamamc.spit.networking.packet;

import com.llamamc.spit.api.packet.IPacket;
import com.llamamc.spit.networking.event.VarInts;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class SetCompressionPacket implements IPacket {
    private final int threshold;

    public SetCompressionPacket(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public int id() {
    	return 0x03;
    }

    @Override
    public ByteBuf payload() {
        ByteBuf buf = Unpooled.buffer();
        VarInts.write(buf, threshold);
        return buf;
    }
}
