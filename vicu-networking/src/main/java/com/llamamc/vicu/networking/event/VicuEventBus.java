package com.llamamc.vicu.networking.event;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.llamamc.vicu.api.event.IEventBus;
import com.llamamc.vicu.api.event.IEventListener;

public class VicuEventBus implements IEventBus {
	private final Map<Class<?>, List<IEventListener<?>>> listeners = new ConcurrentHashMap<>();

    @Override
    public <T> void subscribe(Class<T> event, IEventListener<T> listener) {
        listeners.computeIfAbsent(event, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void publish(Object event) {
        var list = listeners.get(event.getClass());
        if (list == null) {
            return;
        }
        for (IEventListener listener : list) {
            listener.handle(event);
        }
    }
}
