package com.urlservice.netty;

import java.net.InetSocketAddress;

import com.urlservice.RedisPool.RedisConnection;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {

	private ServerBootstrap sb;
	private EventLoopGroup parentGroup;
	private EventLoopGroup childGroup;
	private Channelinit initChannel;
	public void init() {
		    parentGroup = new NioEventLoopGroup(1);
	         childGroup = new NioEventLoopGroup();
	        try{
	        	
	        	initChannel = new Channelinit();
	        	
	            sb = new ServerBootstrap();
	        
	            sb.group(parentGroup, childGroup)
	            .channel(NioServerSocketChannel.class)
	            .option(ChannelOption.SO_BACKLOG, 100)
	            .option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT)
	            .handler(new LoggingHandler(LogLevel.INFO))
	            .childHandler(initChannel);
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	    }
	
	public void start() {
		InetSocketAddress address = new InetSocketAddress("192.168.25.34",8080);
		try {
			ChannelFuture cf = sb.bind(address).sync();
			 cf.channel().closeFuture().sync();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			RedisConnection.close();
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
	}
}
