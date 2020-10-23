package com.wangyuming.netty.nio.msgpack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LogManager.getLogger();
    private final int sendNumber;

    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] infos = UserInfo();
        for (UserInfo infoE: infos) {
            ctx.write(infoE);
        }
        ctx.flush();
    }

    private UserInfo[] UserInfo(){
        UserInfo[] userInfos = new UserInfo[sendNumber];
        UserInfo userInfo = null;

        for (int i = 0; i < userInfos.length; i++) {
            userInfo = new UserInfo();
            userInfo.setId(i + 1);
            userInfo.setUsername("user" + (i + 1));
            userInfo.setPassword("123456");
            userInfos[i] = userInfo;
        }

        return userInfos;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("Client receive the msgpack message: " + msg);
        ctx.write(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("error", cause);
        ctx.close();
    }
}
