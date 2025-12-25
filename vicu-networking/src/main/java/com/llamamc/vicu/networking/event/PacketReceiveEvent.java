package com.llamamc.vicu.networking.event;

import com.llamamc.vicu.api.packet.IPacket;
import com.llamamc.vicu.api.packet.PacketDirection;
import com.llamamc.vicu.api.session.ISession;

public record PacketReceiveEvent(ISession session, IPacket packet, PacketDirection direction) {
}
