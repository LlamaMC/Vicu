package com.llamamc.spit.networking.event;

import com.llamamc.spit.api.session.ISession;
import com.llamamc.spit.networking.util.MotdUtils;

public class StatusPingEvent {
	private final ISession session;
	private String motd;
	private int maxPlayers = 100;
	private int onlinePlayers = 0;

	public StatusPingEvent(ISession session, String defaultMotd) {
		this.session = session;
		this.motd = defaultMotd;
	}
	
	public ISession session() {
		return session;
	}
	
	public String motd() {
		return motd;
	}
	
	public void motd(String motd) {
        this.motd = MotdUtils.toJsonMotd(motd);
    }
	
	public int maxPlayers() {
		return maxPlayers;
	}
	
	public void maxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	
	public int onlinePlayers() {
		return onlinePlayers;
	}
	
	public void onlinePlayers(int onlinePlayers) {
		this.onlinePlayers = onlinePlayers;
	}
}
