package com.wangyuming.netty.nio.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.Date;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LogManager.getLogger();
    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*ByteBuf buffer = (ByteBuf) msg;
        byte[] req = new byte[buffer.readableBytes()];
        buffer.readBytes(req);*/

        String body = (String) msg;//new String(req, "UTF-8");
        logger.debug("receive body: " + body + ", counter is : " + ++counter);

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf writeBuffer = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(writeBuffer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
