package com.urlservice.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class Channelinit extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		UserDataHandler dataHandler = new UserDataHandler();
		ch.pipeline().addLast(dataHandler);
		
	}

}
