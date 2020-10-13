package com.wangyuming.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {
    private int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        int port = 8090;
        new EchoServer(port).start();
    }

    public void start() throws  Exception{
        NioEventLoopGroup group = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();

            //指定使用 NIO 的传输 Channel,设置 socket 地址使用所选的端口,添加 EchoServerHandler 到 Channel 的 ChannelPipeline
            bootstrap.group(group).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>(){
                    @Override
                    public void initChannel(SocketChannel socketChannel){
                        socketChannel.pipeline().addLast(new EchoServerHandler());
                    }
                });

            ChannelFuture future = bootstrap.bind().sync(); //绑定的服务器;sync 等待服务器关闭
            System.out.println(EchoServer.class.getName() + " started and listen on " + future.channel().localAddress());
            future.channel().closeFuture().sync();  //关闭 channel和块，直到它被关闭

        }finally {
            group.shutdownGracefully().sync(); //关机的 EventLoopGroup，释放所有资源
        }
    }
}
