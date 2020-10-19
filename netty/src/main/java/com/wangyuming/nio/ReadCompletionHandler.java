package com.wangyuming.nio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
    private Logger logger = LogManager.getLogger();
    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel){
        if(this.channel == null){
            this.channel = channel;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try{
            String req = new String(body, "UTF-8");
            logger.debug("The time server receive over: " + req);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
            doWrite(currentTime);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void doWrite(String currentTime) {
        if(currentTime != null && currentTime.length() > 0){
            byte[] bytes = currentTime.getBytes();
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(bytes);
            buffer.flip();
            channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if(buffer.hasRemaining()){
                        channel.write(buffer, buffer, this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try{
                        channel.close();
                    }catch (Exception e){

                    }
                }
            });
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            channel.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
