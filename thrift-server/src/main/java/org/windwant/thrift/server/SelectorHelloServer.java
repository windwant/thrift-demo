package org.windwant.thrift.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.windwant.thrift.common.HelloService;
import org.windwant.thrift.server.impl.HelloServiceImpl;

/**
 * TThreadedSelectorServer：
 * 半同步/半异步，
 * 它维护了两个线程池，一个用来处理网络I/O，另一个用来进行请求的处理
 *
 * 网络IO:
 * 使用单独的线程池处理来处理非阻塞网络I/O。
 * 连接请求由一个单一的线程处理
 * 连接请求的读写由可配置的多个selector线程处理
 *
 * 请求处理:
 * 同步worker线程池处理请求
 *
 * 对比 TNonblockingServer/THsHaServer 在多核环境下性能更好
 *
 * 另外：
 * 因为处理连接和读写和请求处理解耦，所以能更好的处理连接的压力
 * Created by windwant on 2016/7/1.
 */
public class SelectorHelloServer {
    private static final int PORT = 8889;

    public void startServer(){
        //processor is a generic object which operates upon an input stream and writes to some output stream.
        TProcessor tProcessor = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());

        try {
            //Server transport that can be operated in a nonblocking fashion
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
