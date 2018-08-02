package com.example.demo;


import org.junit.Test;

import com.example.common.NettyClient;


public class NettyClientTest {
	
	NettyClient client = new NettyClient();
	
	@Test
	public void connectTest() {
		String host = "localhost";
		int port =8080;
		
		client.connect(host, port);
	}
}
