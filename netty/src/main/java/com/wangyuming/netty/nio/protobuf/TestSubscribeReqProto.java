package com.wangyuming.netty.nio.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TestSubscribeReqProto {
    private static Logger logger = LogManager.getLogger();

    private static byte[] encode(SubscribeReqProto.SubscribeReq req){
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] bytes) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(bytes);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq(){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("WangYuMing");
        builder.setProductName("Netty Book");
        List<String> address = new ArrayList<>();
        address.add("ShenZhenWan");
        address.add("BaoAN Center");
        address.add("ShenZhen HongShuLin");
        builder.setAddress("ShenZhen HongShuLin");
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        logger.debug("Before encode : " + req.toString());
        SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
        logger.debug("After encode : " + req.toString());
        logger.debug("Assert equal : " + req.equals(req2));
    }
}
