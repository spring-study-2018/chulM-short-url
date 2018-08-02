package com.example.common;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.compression.FastLzFrameDecoder;

public class MessageDecoder extends FastLzFrameDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
//		System.err.println(in);
		out.add(in.readBytes(in.readableBytes()));
//		System.err.println(count);
	}
	
}
