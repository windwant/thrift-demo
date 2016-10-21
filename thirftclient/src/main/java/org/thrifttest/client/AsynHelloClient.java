package org.thrifttest.client;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.*;
import org.thrifttest.server.HelloService;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by windwant on 2016/7/1.
 */
public class AsynHelloClient{

    private static final String SERVER_HOST = "localhost";

    private static final int SERVER_PORT = 8889;

    private static final int TIME_OUT = 3000;

    private TNonblockingTransport tNonblockingTransport;

    private TProtocolFactory tProtocolFactory;

    private TAsyncClientManager tAsyncClientManager;

    private HelloService.AsyncClient asyncClient;

    AsynHelloClient(){
        try {
            tAsyncClientManager = new TAsyncClientManager();
            tNonblockingTransport = new TNonblockingSocket(SERVER_HOST, SERVER_PORT, TIME_OUT);
            tProtocolFactory = new TCompactProtocol.Factory();
            asyncClient = new HelloService.AsyncClient(tProtocolFactory, tAsyncClientManager, tNonblockingTransport);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            AsynHelloClient asynHelloClient = new AsynHelloClient();
            CountDownLatch latch = new CountDownLatch(1);
            asynHelloClient.asyncClient.hello("lilei", new ClientCallBack(latch));
            latch.await(3, TimeUnit.SECONDS);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ClientCallBack implements AsyncMethodCallback<HelloService.AsyncClient.hello_call>{

    CountDownLatch latch;

    ClientCallBack(CountDownLatch latch){
        this.latch = latch;
    }

    public void onComplete(HelloService.AsyncClient.hello_call response) {
        try {
            System.out.println(response.getResult().toString());
            latch.countDown();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public void onError(Exception exception) {
        latch.countDown();
    }
}
