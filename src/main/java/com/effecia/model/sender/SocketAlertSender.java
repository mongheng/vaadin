package com.effecia.model.sender;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Queue;

import com.effecia.model.AlertCommand;
import com.effecia.model.AlertSender;
import com.effecia.netty.socket.client.ClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class SocketAlertSender implements AlertSender, Serializable, Runnable {

	private static final long serialVersionUID = 3150161991160252884L;

	private String host;
	private int port;
	private Queue<AlertCommand> commandQueue;
	
	public SocketAlertSender(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Queue<AlertCommand> getCommandQueue() {
		return commandQueue;
	}

	public void setCommandQueue(Queue<AlertCommand> commandQueue) {
		this.commandQueue = commandQueue;
	}

	public void feedAlert(AlertCommand alertCommand) {
		commandQueue = new ArrayDeque<AlertCommand>();
		commandQueue.add(alertCommand);
		
		new Thread(this).start();
	}

	private void sendCommand(final AlertCommand alertCommand) {
		try {
			EventLoopGroup group = new NioEventLoopGroup();
			Bootstrap client = new Bootstrap();
			client.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true);

			client.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					
					ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
					ch.pipeline().addLast(new ObjectEncoder());
					ch.pipeline().addLast(new ClientHandler(alertCommand));
				}
			});
			ChannelFuture channelFuture = client.connect(host, port).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		for (;;) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			AlertCommand command = commandQueue.poll();
			if (command != null) {
				sendCommand(command);
			}
		}
	}
	
}
