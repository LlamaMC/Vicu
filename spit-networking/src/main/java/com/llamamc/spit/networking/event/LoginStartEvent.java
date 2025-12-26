package com.llamamc.spit.networking.event;

import com.llamamc.spit.api.session.ISession;

public record LoginStartEvent(ISession session, String username) {
}
