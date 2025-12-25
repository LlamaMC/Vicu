package com.llamamc.vicu.api.event.impl;

import com.llamamc.vicu.api.ISession;
import com.llamamc.vicu.api.packet.IPacket;
import com.llamamc.vicu.api.packet.PacketDirection;

public record PacketSendEvent(ISession session, IPacket packet, PacketDirection direction) {
}
