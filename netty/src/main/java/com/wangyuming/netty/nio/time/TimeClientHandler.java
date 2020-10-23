package com.wangyuming.netty.nio.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LogManager.getLogger();

    private int conuter;

    private byte[] req;
    //private final ByteBuf firstMessage;

    public TimeClientHandler(){
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       // ctx.writeAndFlush(firstMessage);
        for (int i = 0; i < 100; i++) {
            ByteBuf firstMessage = Unpooled.buffer(req.length);
            firstMessage.writeBytes(req);
            ctx.writeAndFlush(firstMessage);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Unexpected exception form downstream: " + cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);*/
        String body = (String) msg;
        logger.debug("Now is : " + body + "; the counter is : " + ++conuter);
    }
}
