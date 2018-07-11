package com.urlservice.netty;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.urlservice.RedisPool.RedisConnection;
import com.urlservice.utils.UrlManager;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import redis.clients.jedis.Jedis;

public class UserDataHandler extends ChannelInboundHandlerAdapter{
	
	StringBuilder serverDomain;
	UrlManager manager ;
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// todo auto-generated method stub
		manager = new UrlManager();
		InetSocketAddress myAddress = (InetSocketAddress)ctx.channel().localAddress();


		serverDomain = new StringBuilder();
		serverDomain.append(myAddress.getHostName()).append(":").append(myAddress.getPort()).append("/");
	}
	

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		super.channelRead(ctx, msg);
		String user = "user00";
		String originalurl = "www.naver.com";
		makeURL(user, originalurl);
	}


	public void makeURL(String user,String originalURL) {
		System.out.println(user);
		System.out.println(originalURL);
		
		String shortURL = manager.generateURL(user,originalURL,serverDomain.toString());
		System.out.println(shortURL);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}
}
