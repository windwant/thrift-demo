package org.windwant.thrift.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.windwant.thrift.common.HelloService;
import org.windwant.thrift.server.impl.HelloServiceImpl;

/**
 * 非阻塞
 * NIO selector使用 使用TFramedTransport
 * Created by windwant on 2016/7/1.
 */
public class AsynHelloServer {
    private static final int PORT = 8889;

    public void startServer(){
        TProcessor tProcessor = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());

        try {
            //继承TNonblockingServerTransport 非阻塞Socket
            TNonblockingServerSocket tNonblockingServerSocket = new TNonblockingServerSocket(PORT);
            TNonblockingServer.Args tnargs = new TNonblockingServer.Args(tNonblockingServerSocket);
            tnargs.processor(tProcessor);
            //带缓冲的传输 Transport   fully read message every time
            tnargs.transportFactory(new TFramedTransport.Factory());
            //带压缩的传输协议
            tnargs.protocolFactory(new TCompactProtocol.Factory());

            //非阻塞 单线程 公平锁  TNonblockingServer 要求最外层的transport必须使用TFramedTransport
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
