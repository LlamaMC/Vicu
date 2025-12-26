package com.llamamc.spit.api;

import com.llamamc.spit.api.event.IEventBus;

public interface ISpit {
    ISpitServer server();
    IEventBus eventBus();
    void registerEvents();
}
