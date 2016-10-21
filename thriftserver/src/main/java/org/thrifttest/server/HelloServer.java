package org.thrifttest.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.thrifttest.server.impl.HelloServiceImpl;

/**
 * Created by windwant on 2016/7/1.
 */
public class HelloServer {
    private static final int PORT = 8889;

    public void startServer(){
        TProcessor tProcessor = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());

        try {
            TServerSocket tServerSocket = new TServerSocket(PORT); //ServerSocket包装类
//            TServerTransport tServerTransport = new TServerSocket(PORT);
//            TServer.Args targs = new TServer.Args(tServerSocket);
            TThreadPoolServer.Args targs = new TThreadPoolServer.Args(tServerSocket); //使用java内置线程池管理产生线程池
            targs.processor(tProcessor);  //读入，写出
            targs.protocolFactory(new TBinaryProtocol.Factory()); //二进制传输协议

            TServer tServer = new TSimpleServer(targs); // 简单的单线程服务模型，一般用于测试
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
