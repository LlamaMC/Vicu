package com.llamamc.vicu;

import java.util.logging.Logger;

import com.llamamc.vicu.api.IVicu;
import com.llamamc.vicu.api.IVicuServer;
import com.llamamc.vicu.api.event.IEventBus;
import com.llamamc.vicu.api.event.impl.PacketReceiveEvent;
import com.llamamc.vicu.api.event.impl.SessionConnectEvent;
import com.llamamc.vicu.api.event.impl.SessionDisconnectEvent;
import com.llamamc.vicu.networking.VicuEventBus;
import com.llamamc.vicu.networking.VicuServer;

public final class Vicu implements IVicu {
	private final IVicuServer server;
	private final IEventBus eventBus;

	public Vicu(IVicuServer server, IEventBus eventBus) {
		this.server = server;
		this.eventBus = eventBus;
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
		registerEvents(eventBus);
		IVicuServer server = new VicuServer(eventBus);
		Vicu vicu = new Vicu(server, eventBus);
		Runtime.getRuntime().addShutdownHook(new Thread(vicu.server()::stop));
		vicu.server().start(25565);
		LOGGER.info("Vicu server started on port 25565");
	}

	private static void registerEvents(IEventBus eventBus) {
		eventBus.subscribe(SessionConnectEvent.class,
				e -> System.out.println("Client connected: " + e.session().clientAddress()));
		eventBus.subscribe(SessionDisconnectEvent.class,
				e -> System.out.println("Client disconnected: " + e.session().clientAddress()));
		eventBus.subscribe(PacketReceiveEvent.class, e -> System.out.println("Packet received from "
				+ e.session().clientAddress() + ", size: " + e.packet().payload().readableBytes()));
	}
}
