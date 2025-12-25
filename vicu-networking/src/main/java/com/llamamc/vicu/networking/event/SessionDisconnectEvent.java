package com.llamamc.vicu.networking.event;

import com.llamamc.vicu.api.session.ISession;

public record SessionDisconnectEvent(ISession session) {
}
