package com.llamamc.vicu.networking.packet;

import java.nio.charset.StandardCharsets;

import com.llamamc.vicu.api.packet.IPacket;
import com.llamamc.vicu.networking.event.VarInts;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class LoginSuccessPacket implements IPacket {
    private final String uuid;
    private final String username;

    public LoginSuccessPacket(String uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    @Override
    public int id() {
    	return 0x02;
    }

    @Override
    public ByteBuf payload() {
        ByteBuf buf = Unpooled.buffer();
        writeString(buf, uuid);
        writeString(buf, username);
        return buf;
    }

    private void writeString(ByteBuf buf, String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        VarInts.write(buf, bytes.length);
        buf.writeBytes(bytes);
    }
}
