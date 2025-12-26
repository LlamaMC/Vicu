package com.llamamc.spit.networking.event;

import com.llamamc.spit.api.session.ISession;

public record HandshakeEvent(ISession session, int protocolVersion, String serverAddress, int serverPort, int nextState) {
}
