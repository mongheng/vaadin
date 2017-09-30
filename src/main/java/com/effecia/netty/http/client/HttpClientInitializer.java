package com.effecia.netty.http.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.ssl.SslContext;

public class HttpClientInitializer extends ChannelInitializer<SocketChannel> {

	private final SslContext sslCtx;
	
	public HttpClientInitializer(SslContext sslCtx) {
		super();
		this.sslCtx = sslCtx;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		
		          // Enable HTTPS if necessary.
		          if (sslCtx != null) {
		              p.addLast(sslCtx.newHandler(ch.alloc()));
		          }
		          //p.addLast(new HttpClientCodec());
		          p.addLast(new HttpResponseDecoder());
		          //p.addLast("aggregator",new HttpObjectAggregator(65536));
		          p.addLast(new HttpRequestEncoder());
		  
		          // Uncomment the following line if you don't want to handle HttpContents.
		          //p.addLast(new HttpObjectAggregator(1048576));
		  
		         // p.addLast(new HttpClientHandler());
	}
}
