package com.llamamc.vicu.networking;

import com.llamamc.vicu.api.event.IEventBus;
import com.llamamc.vicu.api.event.impl.PacketReceiveEvent;
import com.llamamc.vicu.api.event.impl.SessionConnectEvent;
import com.llamamc.vicu.api.event.impl.SessionDisconnectEvent;
import com.llamamc.vicu.api.packet.PacketDirection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public final class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final IEventBus eventBus;
    private VicuSession session;

    public ClientHandler(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        session = new VicuSession(ctx.channel());
        eventBus.publish(new SessionConnectEvent(session));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        eventBus.publish(new PacketReceiveEvent(
            session,
            new Packet(-1, msg.retain()),
            PacketDirection.CLIENT_TO_PROXY
        ));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        eventBus.publish(new SessionDisconnectEvent(session));
    }
}
