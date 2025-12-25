package com.llamamc.vicu.api;

import java.util.Collection;

import com.llamamc.vicu.api.session.ISession;

public interface IVicuServer {
	void start(int port);
	void stop();
	int getOnlinePlayerCount();
	Collection<ISession> sessions();
}
