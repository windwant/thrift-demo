package org.thrifttest.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.thrifttest.server.impl.HelloServiceImpl;

/**
 * 非阻塞
 * Created by windwant on 2016/7/1.
 */
public class AsynHelloServer {
    private static final int PORT = 8889;

    public void startServer(){
        TProcessor tProcessor = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());

        try {
            TNonblockingServerSocket tNonblockingServerSocket = new TNonblockingServerSocket(PORT);
            TNonblockingServer.Args tnargs = new TNonblockingServer.Args(tNonblockingServerSocket);
            tnargs.processor(tProcessor);
            //最外层的transport必须使用TFramedTransport 带缓冲的Transport fully read message every time
            tnargs.transportFactory(new TFramedTransport.Factory());
            tnargs.protocolFactory(new TCompactProtocol.Factory());

            //非阻塞 单线程 公平锁
            TServer tServer = new TNonblockingServer(tnargs);
            tServer.serve();
            System.out.println("server start...");
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AsynHelloServer().startServer();
    }
}
