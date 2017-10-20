package com.tony.rpc;


import com.proto.service.GreeterGrpc;

import io.grpc.stub.StreamObserver;
import com.proto.base.ServiceBaseInfo.HelloReply;
import com.proto.base.ServiceBaseInfo.HelloRequest;

/**
 * Created by chnho02796 on 2017/10/17.
 */
public class ServiceHelloImpl extends GreeterGrpc.GreeterImplBase{
    public void sayHello(HelloRequest request,
                         StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage(("Hello "+request.getName())).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
