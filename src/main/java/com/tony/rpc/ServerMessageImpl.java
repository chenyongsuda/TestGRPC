package com.tony.rpc;

import com.proto.service.ServerMessageGrpc;
import com.proto.service.ServiceMessageIF;
import com.proto.service.ServiceMessageIF.MessageRep;

/**
 * Created by chnho02796 on 2017/10/19.
 */
public class ServerMessageImpl extends ServerMessageGrpc.ServerMessageImplBase{

    private RPCDefaultMsgAction action;

    public void init(RPCDefaultMsgAction action){
        this.action = action;
    }

    public void message(com.proto.service.ServiceMessageIF.MessageReq request,
                        io.grpc.stub.StreamObserver<com.proto.service.ServiceMessageIF.MessageRep> responseObserver) {
        int type = request.getType();
        String message = request.getMessage();
        MessageRep resp = action.message(request);
        ServiceMessageIF.MessageRep rep = ServiceMessageIF.MessageRep.newBuilder().setStatus(resp.getStatus()).setMessage(resp.getMessage()).build();
        responseObserver.onNext(rep);
        responseObserver.onCompleted();
    }
}
