package com.tony.rpc;

import com.proto.service.ServerMessageGrpc;
import com.proto.service.ServerServiceGrpc;
import com.proto.service.ServiceMessageIF;
import com.proto.service.ServiceServerIF;
import com.tony.rpc.util.pool.ChannelsPoolsUtil;
import com.tony.rpc.util.timer.ITimer;
import com.tony.rpc.util.timer.TimerUtil;
import io.grpc.ManagedChannel;

/**
 * Created by chnho02796 on 2017/10/17.
 */
public class RPCClient {
    private ChannelsPoolsUtil channelPools = new ChannelsPoolsUtil();
    private TimerUtil util = new TimerUtil();

    public void init(){
        channelPools.init();
    }

    public void sendMessage(){
        ManagedChannel channel = null;
        try {
            channel = channelPools.borrowObject();
            ServerMessageGrpc.ServerMessageBlockingStub stub = ServerMessageGrpc.newBlockingStub(channel);
            ServiceMessageIF.MessageReq req = ServiceMessageIF.MessageReq.newBuilder().setType(1).setMessage("").build();
            ServiceMessageIF.MessageRep rep = stub.message(req);
            System.out.println(rep.getMessage());
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            channelPools.returnObject(channel);
        }
        ServerServiceGrpc.ServerServiceBlockingStub HeartBeatStub = ServerServiceGrpc.newBlockingStub(channel);
        ServiceServerIF.Pin pin = ServiceServerIF.Pin.newBuilder().setServerAddress("127.0.0.1").setServerPort(8800).build();
        ServiceServerIF.Pon pon = HeartBeatStub.heartBeat(pin);
        System.out.println(pon.getMessage());
    }

    public void startHeartBeat(){
        util.scheduleAtFixedRate(new ITimer() {
            public void timeup(){
                ManagedChannel channel = null;
                try {
                    channel = channelPools.borrowObject();
                    ServerServiceGrpc.ServerServiceBlockingStub HeartBeatStub = ServerServiceGrpc.newBlockingStub(channel);
                    ServiceServerIF.Pin pin = ServiceServerIF.Pin.newBuilder().setServerAddress("127.0.0.1").setServerPort(8800).build();
                    ServiceServerIF.Pon pon = HeartBeatStub.heartBeat(pin);
                    System.out.println(pon.getMessage());
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    channelPools.returnObject(channel);
                }

            }
        },0,3*1000);
    }

    public void stop(){
        util.stop();
        channelPools.stop();
    }

    public static void main(String[] args) throws Exception{
        final ChannelsPoolsUtil pools = new ChannelsPoolsUtil();
        pools.init();
        TimerUtil util = new TimerUtil();
        util.scheduleAtFixedRate(new ITimer() {
            public void timeup(){
//                System.out.println("Run HeartBeat");
//                final ManagedChannel channel1 = ManagedChannelBuilder.forAddress("127.0.0.1",9900).usePlaintext(true).build();
//                ServerServiceGrpc.ServerServiceBlockingStub HeartBeatStub = ServerServiceGrpc.newBlockingStub(channel);
//                ServiceServerIF.Pin pin = ServiceServerIF.Pin.newBuilder().setServerAddress("127.0.0.1").setServerPort(8800).build();
//                ServiceServerIF.Pon pon = HeartBeatStub.heartBeat(pin);
//                System.out.println(pon.getMessage());
                ManagedChannel channel = null;
                try {
                    channel = pools.borrowObject();
                    ServerServiceGrpc.ServerServiceBlockingStub HeartBeatStub = ServerServiceGrpc.newBlockingStub(channel);
                    ServiceServerIF.Pin pin = ServiceServerIF.Pin.newBuilder().setServerAddress("127.0.0.1").setServerPort(9900).build();
                    ServiceServerIF.Pon pon = HeartBeatStub.heartBeat(pin);
                    System.out.println(pon.getMessage());
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    pools.returnObject(channel);
                }

            }
        },0,3*1000);
        System.out.println("Run Finish1");
//        channel.shutdown().awaitTermination(20, TimeUnit.SECONDS);
        Thread.sleep(90000);
        util.stop();
        System.out.println("Run Finish");
    }
}
