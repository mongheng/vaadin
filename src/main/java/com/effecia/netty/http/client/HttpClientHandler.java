package com.effecia.netty.http.client;

import java.net.URI;

import com.effecia.model.AlertCommand;
import com.effecia.utility.UtilClient;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class HttpClientHandler extends SimpleChannelInboundHandler<Object> {

	private String url;
	private AlertCommand alertCommand;

	public HttpClientHandler(String url, AlertCommand alertCommand) {
		this.url = url;
		this.alertCommand = alertCommand;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		URI uri = new URI(url);
		
		String msg = UtilClient.convertObjectToJsonString(alertCommand);

		FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath(),
				Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
		request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
		request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

		ctx.channel().writeAndFlush(request);
		System.err.println("Data sent");
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
		if (msg instanceof HttpResponse) {
			HttpResponse response = (HttpResponse) msg;

			System.err.println("STATUS: " + response.status());
			System.err.println("VERSION: " + response.protocolVersion());
			System.err.println();

			if (!response.headers().isEmpty()) {

				for (CharSequence name : response.headers().names()) {
					for (CharSequence value : response.headers().getAll(name)) {
						System.err.println("HEADER: " + name + " = " + value);
					}
				}
				System.err.println();
			}

			if (HttpUtil.isTransferEncodingChunked(response)) {
				System.err.println("CHUNKED CONTENT {");
			} else {
				System.err.println("CONTENT {");
			}
		}
		if (msg instanceof HttpContent) {
			HttpContent content = (HttpContent) msg;

			System.err.print(content.content().toString(CharsetUtil.UTF_8));
			System.err.flush();

			if (content instanceof LastHttpContent) {
				System.err.println("} END OF CONTENT");
				ctx.close();
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		UtilClient.playSound();
		System.err.println("The connection to the remote target has been lost. Please reconnect again.");
		cause.printStackTrace();
		ctx.close();
	}
	
	
}
