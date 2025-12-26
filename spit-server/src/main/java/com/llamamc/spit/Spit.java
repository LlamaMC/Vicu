package com.llamamc.spit;

import java.util.logging.Logger;

import com.llamamc.spit.api.ISpit;
import com.llamamc.spit.api.ISpitServer;
import com.llamamc.spit.api.event.IEventBus;
import com.llamamc.spit.networking.SpitServer;
import com.llamamc.spit.networking.event.HandshakeEvent;
import com.llamamc.spit.networking.event.PacketReceiveEvent;
import com.llamamc.spit.networking.event.SessionConnectEvent;
import com.llamamc.spit.networking.event.SessionDisconnectEvent;
import com.llamamc.spit.networking.event.StatusPingEvent;
import com.llamamc.spit.networking.event.EventBus;

public final class Spit implements ISpit {
	private final ISpitServer server;
	private final IEventBus eventBus;

	public Spit(ISpitServer server, IEventBus eventBus) {
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
	public ISpitServer server() {
		return this.server;
	}

	@Override
	public IEventBus eventBus() {
		return this.eventBus;
	}

	private static final Logger LOGGER = Logger.getLogger("Vicu");

	static void main() {
		IEventBus eventBus = new EventBus();
		ISpitServer server = new SpitServer(eventBus);
		Spit vicu = new Spit(server, eventBus);
		vicu.registerEvents();
		Runtime.getRuntime().addShutdownHook(new Thread(vicu.server()::stop));
		vicu.server().start(25565);
		LOGGER.info("Vicu server started on port 25565");
	}
}
