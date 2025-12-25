package com.llamamc.vicu.networking.event;

import com.llamamc.vicu.api.session.ISession;

public record HandshakeEvent(ISession session, int protocolVersion, String serverAddress, int serverPort, int nextState) {
}
