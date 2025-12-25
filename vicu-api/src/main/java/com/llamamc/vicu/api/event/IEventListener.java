package com.llamamc.vicu.api.event;

@FunctionalInterface
public interface IEventListener<T> {
	void handle(T event);
}
