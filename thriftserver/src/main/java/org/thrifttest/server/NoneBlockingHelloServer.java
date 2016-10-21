package org.thrifttest.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.*;
import org.thrifttest.server.impl.HelloServiceImpl;

/**
 * NIO selector使用 使用TFramedTransport
 * Created by windwant on 2016/7/1.
 */
public class NoneBlockingHelloServer {
    private static final int PORT = 8889;

    public void startServer(){
        TProcessor tProcessor = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());

        try {
            TNonblockingServerSocket tNonblockingServerSocket = new TNonblockingServerSocket(PORT);
            TNonblockingServer.Args tnargs = new TNonblockingServer.Args(tNonblockingServerSocket);
            tnargs.processor(tProcessor);
            tnargs.transportFactory(new TFramedTransport.Factory());
            tnargs.protocolFactory(new TCompactProtocol.Factory());

            TServer tServer = new TNonblockingServer(tnargs);
            tServer.serve();
            System.out.println("server start...");
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NoneBlockingHelloServer().startServer();
    }
}
