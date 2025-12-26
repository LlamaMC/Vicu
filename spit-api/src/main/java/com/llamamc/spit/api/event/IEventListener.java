package com.llamamc.spit.api.event;

@FunctionalInterface
public interface IEventListener<T> {
	void handle(T event);
}
