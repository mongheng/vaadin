package com.effecia.model.sender;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Queue;

import com.effecia.model.AlertCommand;
import com.effecia.model.AlertSender;
import com.effecia.netty.http.client.HttpClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

public class HttpAlertSender implements AlertSender, Serializable, Runnable {

	private static final long serialVersionUID = -3305300886166397739L;

	private String host;
	private int port;
	private Queue<AlertCommand> commandQueue;

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

	public void setCommandsQueue(Queue<AlertCommand> commandQueue) {
		this.commandQueue = commandQueue;
	}
	
	public void feedAlert(AlertCommand alertCommand) {
		commandQueue = new ArrayDeque<AlertCommand>();
		commandQueue.add(alertCommand);
		
		new Thread(this).start();
	}

	public void run() {
		for (;;) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			AlertCommand command = commandQueue.poll();
			if (command != null) {
				sendCommand(command);
			}
		}

	}

	private void sendCommand(final AlertCommand command) {
		
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap client = new Bootstrap();
			client.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true);
			final String URL = System.getProperty("url", "http://" + host + ":" + port + "/");

			client.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new HttpResponseDecoder());
					ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
					ch.pipeline().addLast(new HttpRequestEncoder());
					ch.pipeline().addLast(new HttpClientHandler(URL, command));
				}

			});
			Channel channel = client.connect(host, port).sync().channel();
			channel.closeFuture().sync();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			group.shutdownGracefully();
		}
	}

}
