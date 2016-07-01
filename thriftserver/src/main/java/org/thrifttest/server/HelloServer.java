package org.thrifttest.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.thrifttest.server.impl.HelloServiceImpl;

/**
 * Created by aayongche on 2016/7/1.
 */
public class HelloServer {
    private static final int PORT = 8888;

    public void startServer(){
        TProcessor tProcessor = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());

        try {
            TServerSocket tServerSocket = new TServerSocket(PORT);
            TServer.Args targs = new TServer.Args(tServerSocket);
            targs.processor(tProcessor);
            targs.protocolFactory(new TBinaryProtocol.Factory());

            TServer tServer = new TSimpleServer(targs);
            tServer.serve();
            System.out.println("server start...");
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HelloServer().startServer();
    }
}
