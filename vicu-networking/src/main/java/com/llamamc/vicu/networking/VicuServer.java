package com.llamamc.vicu.networking;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.llamamc.vicu.api.IVicuServer;
import com.llamamc.vicu.api.event.IEventBus;
import com.llamamc.vicu.api.session.ISession;
import com.llamamc.vicu.api.session.SessionState;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class VicuServer implements IVicuServer {
	private final Set<ISession> sessions = ConcurrentHashMap.newKeySet();
	private final IEventBus eventBus;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	
	public VicuServer(IEventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void start(int port) {
		bossGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
		workerGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
		new ServerBootstrap()
		.group(bossGroup, workerGroup)
		.channel(NioServerSocketChannel.class)
		.childHandler(new VicuServerInitializer(eventBus))
		.bind(port);
	}

	@Override
	public void stop() {
		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}
	}
	
	public void addSession(ISession session) {
	    sessions.add(session);
	}

	public void removeSession(ISession session) {
	    sessions.remove(session);
	}

	@Override
	public int getOnlinePlayerCount() {
	    return (int) sessions.stream()
	            .filter(s -> s.state() == SessionState.LOGIN || s.state() == SessionState.PLAY)
	            .count();
	}

	@Override
	public Collection<ISession> sessions() {
		return sessions;
	}
}
