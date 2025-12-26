package com.llamamc.spit.api;

import java.util.Collection;

import com.llamamc.spit.api.session.ISession;

public interface ISpitServer {
	void start(int port);
	void stop();
	int getOnlinePlayerCount();
	Collection<ISession> sessions();
}
