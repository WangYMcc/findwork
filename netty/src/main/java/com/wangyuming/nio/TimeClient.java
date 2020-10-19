package com.wangyuming.nio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeClient {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        int port = 8080;
        if(args != null && args.length > 0){
            try{
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                logger.error("parse port fault");
                port = 8080;
            }
        }

        AsyncTimeClientHandler timeServer = new AsyncTimeClientHandler(null, port);
        new Thread(timeServer).start();

    }
}
