package com.llamamc.vicu.api;

import java.util.Collection;

public interface IVicuServer {
	void start(int port);
	void stop();
	
	Collection<ISession> sessions();
}
