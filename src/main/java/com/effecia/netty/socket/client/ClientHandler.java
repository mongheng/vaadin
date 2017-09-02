package com.effecia.netty.socket.client;

import com.effecia.model.AlertCommand;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter{
	
	private AlertCommand alertCommand;

	public ClientHandler(AlertCommand alertCommand) {
		this.alertCommand = alertCommand;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		System.err.println(msg.toString());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(alertCommand);
		System.out.println("Data is sent.");
	}

}
