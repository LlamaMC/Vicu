package com.llamamc.vicu;

import java.util.logging.Logger;

import com.llamamc.vicu.api.IVicu;
import com.llamamc.vicu.api.IVicuServer;
import com.llamamc.vicu.api.event.IEventBus;
import com.llamamc.vicu.networking.VicuServer;
import com.llamamc.vicu.networking.event.HandshakeEvent;
import com.llamamc.vicu.networking.event.PacketReceiveEvent;
import com.llamamc.vicu.networking.event.SessionConnectEvent;
import com.llamamc.vicu.networking.event.SessionDisconnectEvent;
import com.llamamc.vicu.networking.event.StatusPingEvent;
import com.llamamc.vicu.networking.event.VicuEventBus;

public final class Vicu implements IVicu {
	private final IVicuServer server;
	private final IEventBus eventBus;

	public Vicu(IVicuServer server, IEventBus eventBus) {
		this.server = server;
		this.eventBus = eventBus;
	}

	@Override
	public void registerEvents() {
		eventBus.subscribe(SessionConnectEvent.class, event -> System.out.println("Client connected: " + event.session().clientAddress()));
		eventBus.subscribe(SessionDisconnectEvent.class, event -> System.out.println("Client disconnected: " + event.session().clientAddress()));
		eventBus.subscribe(PacketReceiveEvent.class, event -> {
		    System.out.println(
		        "Packet id=0x" + Integer.toHexString(event.packet().id()) +
		        " size=" + event.packet().payload().readableBytes()
		    );
		});
		eventBus.subscribe(HandshakeEvent.class, event -> {
		    System.out.println(
		        "Handshake protocol=" + event.protocolVersion() +
		        " host=" + event.serverAddress() +
		        " port=" + event.serverPort() +
		        " nextState=" + event.nextState()
		    );
		});
		eventBus.subscribe(StatusPingEvent.class, event -> {
			System.out.println(event.motd());
			event.motd("<#ff9333-#fff333>Wir WIXEN auf LEBENMITTEL!");
			System.out.println(event.motd());
			event.onlinePlayers(server.getOnlinePlayerCount());
		});
	}

	@Override
	public IVicuServer server() {
		return this.server;
	}

	@Override
	public IEventBus eventBus() {
		return this.eventBus;
	}

	private static final Logger LOGGER = Logger.getLogger("Vicu");

	static void main() {
		IEventBus eventBus = new VicuEventBus();
		IVicuServer server = new VicuServer(eventBus);
		Vicu vicu = new Vicu(server, eventBus);
		vicu.registerEvents();
		Runtime.getRuntime().addShutdownHook(new Thread(vicu.server()::stop));
		vicu.server().start(25565);
		LOGGER.info("Vicu server started on port 25565");
	}
}
