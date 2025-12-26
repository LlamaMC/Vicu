package com.llamamc.spit.api.event;

public interface IEventBus {
	<T> void subscribe(Class<T> eventClass, IEventListener<T> listener);
	void publish(Object event);
}
