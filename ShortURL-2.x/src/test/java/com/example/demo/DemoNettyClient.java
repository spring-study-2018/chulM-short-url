package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import com.example.common.NettyClient;

import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.ErrorDataEncoderException;

public class DemoNettyClient {
	
	public static void main(String args[]) {
		String host = "localhost";
		int port = 8080;
		String url = "www.naver.com";

		try {
			NettyClient client = new NettyClient();
			client.connect(host, port);
			client.createRequest(host, port, url);
			Thread.sleep(1000);
			client.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorDataEncoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
