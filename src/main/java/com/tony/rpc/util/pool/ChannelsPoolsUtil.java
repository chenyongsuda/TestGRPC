package com.tony.rpc.util.pool;

import io.grpc.ManagedChannel;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Created by chnho02796 on 2017/10/19.
 */
public class ChannelsPoolsUtil{
    private GenericObjectPool<ManagedChannel> objectPool;

    public void init(){
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(3);
        config.setMaxTotal(8);
        config.setMinIdle(0);
        config.setBlockWhenExhausted(true);
        config.setFairness(true);
        config.setMaxWaitMillis(10 * 1000);
        config.setMinEvictableIdleTimeMillis(60 *1000);
        config.setNumTestsPerEvictionRun(-1);
        config.setTimeBetweenEvictionRunsMillis(3 * 1000);
        config.setTestOnBorrow(false);
        config.setTestOnCreate(false);
        config.setTestOnReturn(false);
        config.setTestWhileIdle(true);
        objectPool = new GenericObjectPool(new ChannelsFactory(),config);
    }
    public ChannelsPoolsUtil(){

    }

    public ManagedChannel borrowObject() throws Exception {
        return objectPool.borrowObject();
    }

    public void returnObject(ManagedChannel obj){
        objectPool.returnObject(obj);

    }

    public void stop(){
        objectPool.close();
    }

    public void log(){
        System.out.println("["+objectPool.getNumActive()+"]"+"["+objectPool.getNumIdle()+"]"+"["+objectPool.getNumWaiters()+"]");
    }

    public static void main(String[] args) throws Exception {
        ChannelsPoolsUtil clientChannel = new ChannelsPoolsUtil();
        ManagedChannel channel = clientChannel.borrowObject();
        clientChannel.returnObject(channel);
    }
}
