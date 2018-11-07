package org.windwant.thrift.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.windwant.thrift.common.HelloService;
import org.windwant.thrift.server.impl.HelloServiceImpl;

/**
 * 继承 TNonblockingServer
 *
 * 半同步半异步  依赖TFramedTransport 一个单独的线程来处理网络I/O，一个独立的worker线程池来处理消息
 * Created by windwand on 2016/7/1.
 */
public class HSHAHelloServer {
    private static final int PORT = 8889;

    public void startServer(){
        TProcessor tProcessor = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());

        try {
            TNonblockingServerSocket tNonblockingServerSocket = new TNonblockingServerSocket(PORT);
            THsHaServer.Args Thhargs = new THsHaServer.Args(tNonblockingServerSocket);
            Thhargs.processor(tProcessor);
            Thhargs.transportFactory(new TFramedTransport.Factory());
            //二进制协议
            Thhargs.protocolFactory(new TBinaryProtocol.Factory());

            TServer tServer = new THsHaServer(Thhargs);
            tServer.serve();
            System.out.println("server start...");
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HSHAHelloServer().startServer();
    }
}
