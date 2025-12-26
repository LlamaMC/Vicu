package com.llamamc.spit.networking.event;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.llamamc.spit.api.event.IEventBus;
import com.llamamc.spit.api.packet.IPacket;
import com.llamamc.spit.api.packet.PacketDirection;
import com.llamamc.spit.api.session.ISession;
import com.llamamc.spit.api.session.SessionState;
import com.llamamc.spit.networking.SpitSession;
import com.llamamc.spit.networking.packet.LoginSuccessPacket;
import com.llamamc.spit.networking.packet.SetCompressionPacket;
import com.llamamc.spit.networking.packet.StatusPacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public final class ClientHandler extends SimpleChannelInboundHandler<IPacket> {
    private final IEventBus eventBus;
    private ISession session;

    public ClientHandler(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        session = new SpitSession(ctx.channel());
        eventBus.publish(new SessionConnectEvent(session));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IPacket packet) {
        if (session.state() == SessionState.HANDSHAKE && packet.id() == 0x00) {
			ByteBuf buf = packet.payload();
			int protocolVersion = VarInts.read(buf);
		    String serverAddress = readString(buf);
		    int serverPort = buf.readUnsignedShort();
		    int nextState = VarInts.read(buf);
		    session.state(nextState == 1 ? SessionState.STATUS : SessionState.LOGIN);
			eventBus.publish(new HandshakeEvent(session, protocolVersion, serverAddress, serverPort, nextState));
			return;
		}
        if (session.state() == SessionState.LOGIN && packet.id() == 0x00) {
        	ByteBuf buf = packet.payload();
            String username = readString(buf);
            session.username(username);
            System.out.println("LoginStart: " + username);

            String uuid = UUID.randomUUID().toString();
            session.send(new LoginSuccessPacket(uuid, username));
            session.send(new SetCompressionPacket(256));
            session.state(SessionState.PLAY);
            eventBus.publish(new LoginStartEvent(session, username));
            return;
        }
        if (session.state() == SessionState.STATUS && packet.id() == 0x00) {
        	StatusPingEvent event = new StatusPingEvent(session, "§6Willkommen auf §aVicu!");
            eventBus.publish(event);
            String json = """
            {
              "version":{"name":"1.21.11","protocol":774},
              "players":{"max":100,"online":%d,"sample":[]},
              "description":%s
            }
            """.formatted(event.onlinePlayers(), event.motd());
            session.send(new StatusPacket(json));
            return;
		}
        eventBus.publish(new PacketReceiveEvent(session, packet, PacketDirection.CLIENT_TO_PROXY));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        eventBus.publish(new SessionDisconnectEvent(session));
    }
    
    private String readString(ByteBuf buf) {
        int len = VarInts.read(buf);
        byte[] data = new byte[len];
        buf.readBytes(data);
        return new String(data, StandardCharsets.UTF_8);
    }
}
