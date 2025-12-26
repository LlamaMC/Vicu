package com.llamamc.spit.networking.event;

import com.llamamc.spit.api.packet.IPacket;
import com.llamamc.spit.api.packet.PacketDirection;
import com.llamamc.spit.api.session.ISession;

public record PacketReceiveEvent(ISession session, IPacket packet, PacketDirection direction) {
}
