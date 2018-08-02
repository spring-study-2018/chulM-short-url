package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.UUID;

import com.example.common.NettyClient;

import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.ErrorDataEncoderException;

//-Dio.netty.recycler.maxCapacity=0
//-Dio.netty.leakDetectionLevel=advanced
//-Dio.netty.noPreferDirect=true
//-Dio.netty.allocator.type=unpooled
//-Dio.netty.maxDirectMemory=0

public class RunnableNettyClient {
	public static void main(String[] args) {
		
//		String host = "localhost";
//		int port = 8080;
//		String url = "www.google.com";
		long startTime = System.currentTimeMillis();
		
		if(args.length < 5) {
			System.out.println("[usage args]: host port url clientCount requestCount");
			return;
		}
		
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		String url = args[2];
		int clientCount = Integer.parseInt(args[3]);
		int requestCount = Integer.parseInt(args[4]);
		
		
		NettyClient[] clientArr = new NettyClient[clientCount];
		
		try {
			for(int i=0; i<clientCount; i++) {
				clientArr[i]= new NettyClient();
				clientArr[i].connect(host, port);
			}
			
			for(int i=0; i<clientCount; i++) {
				for(int j=0; j<requestCount; j++) {
//						String uuid = (UUID.randomUUID().toString()).split("-")[0];
						String uuidUrl = url+"/"+i+"/"+j;
					clientArr[i].createRequest(host, port, uuidUrl);
				}
			}
			long endTime = System.currentTimeMillis();	
			Thread.sleep(5000);
			System.out.println("--------------");
			System.out.println("client :" + clientCount);
			System.out.println("requestCount per client:" + requestCount);
			System.out.println("processing time:" + (endTime-startTime));
			System.out.println("--------------");
//			for (int i = 0; i < clientArr.length; i++) {
//				clientArr[i].close();
//			}
		while(true) {
			
		}
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
