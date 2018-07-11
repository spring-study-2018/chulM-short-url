package com.urlservice.main;

import com.urlservice.RedisPool.RedisConnection;
import com.urlservice.netty.NettyServer;

public class Main {
	//메인 서버 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String host = "127.0.0.1";
		int port = 6379;
		int timeOut = 3000;
		int dbNum = 2;
		RedisConnection.init(host, port, timeOut, dbNum);
		
		NettyServer server = new NettyServer();
		server.init();
		server.start();
	}

}
