package com.tony.rpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

/**
 * Created by chnho02796 on 2017/10/17.
 */
public class RPCServer {
    private Server server;
    private int port;
    private ServerMessageImpl impl;

    public void init(int port) {
        init(port,null);
    }
    public void init(int port,RPCDefaultMsgAction action){
        this.port = port;
        impl = new ServerMessageImpl();
        if (null != action) {
            impl.init(action);
        } else {
            impl.init(new RPCDefaultMsgAction());
        }

    }

    public void start() throws IOException {
        try {
            server = ServerBuilder
                    .forPort(port)
                    .addService(new ServiceHelloImpl())
                    .addService(new ServerServiceImpl())
                    .addService(impl)
                    .build()
                    .start();
        } catch (IOException e) {

        }
        /* The port on which the RPCServer should run */
        System.out.println("Server Started!!!");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("*** shutting down gRPC RPCServer since JVM is shutting down");
                RPCServer.this.stop();
                System.out.println("*** RPCServer shut down");
            }
        });
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon
     * threads.
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception{
        final RPCServer server = new RPCServer();
        server.init(9900);
        server.start();
        server.blockUntilShutdown();
        //Thread.sleep(30*1000);
    }
}
