package com.tony.rpc.com.tony.test;

import com.tony.rpc.RPCClient;
import com.tony.rpc.RPCServer;

import java.io.IOException;

/**
 * Created by chnho02796 on 2017/10/20.
 */
public class Server {
    private RPCServer server;
    private RPCClient client;

    public void init(){
        server = new RPCServer();
        server.init(8000);
        client = new RPCClient();
    }

    public void start() throws IOException, InterruptedException {
        server.start();
    }

    public void stop(){
        server.stop();
    }

    public static void main(String[] args) throws Exception{
        Server ser = new Server();
        ser.init();
        ser.start();
        Thread.sleep(200 * 1000);
        ser.stop();
    }
}
