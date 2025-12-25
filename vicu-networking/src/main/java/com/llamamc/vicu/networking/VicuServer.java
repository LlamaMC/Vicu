package com.llamamc.vicu.networking;

import java.util.Collection;

import com.llamamc.vicu.api.ISession;
import com.llamamc.vicu.api.IVicuServer;
import com.llamamc.vicu.api.event.IEventBus;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class VicuServer implements IVicuServer {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<ISession> sessions() {
		// TODO Auto-generated method stub
		return null;
	}

}
