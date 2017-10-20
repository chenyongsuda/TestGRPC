package com.tony.rpc;

import com.proto.service.ServerServiceGrpc;
import com.proto.service.ServiceServerIF.Pon;
import com.proto.service.ServiceServerIF.Pon.Builder;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * Created by chnho02796 on 2017/10/18.
 */
public class ServerServiceImpl extends ServerServiceGrpc.ServerServiceImplBase{
    public void heartBeat(com.proto.service.ServiceServerIF.Pin request,
                          io.grpc.stub.StreamObserver<com.proto.service.ServiceServerIF.Pon> responseObserver) {
        Pon pon = Pon.newBuilder().setMessage("TRUE").build();
        responseObserver.onNext(pon);
        responseObserver.onCompleted();
    }
}
