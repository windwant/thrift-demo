package org.thrifttest.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.thrifttest.server.impl.HelloServiceImpl;

/**
 * Created by aayongche on 2016/7/1.
 */
public class SelectorHelloServer {
    private static final int PORT = 8889;

    public void startServer(){
        TProcessor tProcessor = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());

        try {
            TNonblockingServerTransport tNonblockingServerTransport = new TNonblockingServerSocket(PORT);
            TThreadedSelectorServer.Args tsargs = new TThreadedSelectorServer.Args(tNonblockingServerTransport);
            tsargs.transportFactory(new TFramedTransport.Factory());
            tsargs.protocolFactory(new TCompactProtocol.Factory());
            tsargs.processor(tProcessor);
            TThreadedSelectorServer tThreadedSelectorServer = new TThreadedSelectorServer(tsargs);
            tThreadedSelectorServer.serve();
            System.out.println("server start...");
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SelectorHelloServer().startServer();
    }
}
