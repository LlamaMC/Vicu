package com.llamamc.vicu.networking.packet;

import com.llamamc.vicu.api.packet.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;
import com.llamamc.vicu.networking.event.VarInts;

public class StatusPacket implements IPacket {
    private final String json;

    public StatusPacket(String json) {
        this.json = json;
    }

    @Override
    public int id() {
        return 0x00;
    }

    @Override
    public ByteBuf payload() {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        ByteBuf buf = Unpooled.buffer();
        VarInts.write(buf, bytes.length);
        buf.writeBytes(bytes);
        return buf;
    }
}
