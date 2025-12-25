package com.llamamc.vicu.api;

import com.llamamc.vicu.api.event.IEventBus;

public interface IVicu {
    IVicuServer server();
    IEventBus eventBus();
}
