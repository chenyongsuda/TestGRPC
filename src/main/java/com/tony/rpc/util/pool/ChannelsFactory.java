package com.tony.rpc.util.pool;

import com.proto.service.ServerServiceGrpc;
import com.proto.service.ServiceServerIF;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Created by chnho02796 on 2017/10/19.
 */
public class ChannelsFactory extends BasePooledObjectFactory<ManagedChannel> {
    @Override
    public ManagedChannel create() throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1",9900).usePlaintext(true).build();
        return channel;
    }

    @Override
    public PooledObject<ManagedChannel> wrap(ManagedChannel obj) {
        return new DefaultPooledObject<ManagedChannel>(obj);
    }

    @Override
    public PooledObject<ManagedChannel> makeObject() throws Exception {
        return super.makeObject();
    }

    @Override
    public void destroyObject(PooledObject<ManagedChannel> obj) throws Exception {
        ManagedChannel channel = obj.getObject();
        channel.shutdown();
    }

    @Override
    public boolean validateObject(PooledObject<ManagedChannel> obj) {
        System.out.println("Check Active");
        ManagedChannel channel = obj.getObject();
        try {
            ServerServiceGrpc.ServerServiceBlockingStub HeartBeatStub = ServerServiceGrpc.newBlockingStub(channel);
            ServiceServerIF.Pin pin = ServiceServerIF.Pin.newBuilder().setServerAddress("127.0.0.1").setServerPort(8800).build();
            ServiceServerIF.Pon pon = HeartBeatStub.heartBeat(pin);
            if ("TRUE".equals(pon.getMessage())){
                return true;
            }
        }catch (Exception e){
            System.out.println("Channel Unactive");
            return false;
        }
        return false;
    }

    @Override
    public void activateObject(PooledObject<ManagedChannel> obj) throws Exception {
        super.activateObject(obj);
    }

    @Override
    public void passivateObject(PooledObject p) throws Exception {
        super.passivateObject(p);
    }

}
