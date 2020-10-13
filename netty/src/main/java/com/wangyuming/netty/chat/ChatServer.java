package com.wangyuming.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class ChatServer {
    private int port;

    public ChatServer(int port){
        this.port = port;
    }

    public void start() throws Exception{
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();

            b.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChatServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("ChatServer 启动了");

            ChannelFuture future = b.bind().sync();
            future.channel().closeFuture().sync();
        }finally {
            boosGroup.shutdownGracefully().sync();
            workGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception{
        new ChatServer(8000).start();
    }
}
