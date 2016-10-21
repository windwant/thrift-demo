package org.thrifttest.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.thrifttest.server.HelloService;

/**
 * Created by windwant on 2016/7/1.
 */
public class HelloClient {

    private static final String SERVER_HOST = "localhost";

    private static final int SERVER_PORT = 8889;

    private static final int TIME_OUT = 3000;

    private TTransport tTransport;

    private HelloService.Client client;

    HelloClient(){
        tTransport = new TSocket(SERVER_HOST, SERVER_PORT, TIME_OUT);
        TProtocol  tProtocol = new TBinaryProtocol(tTransport);
        client = new HelloService.Client(tProtocol);
    }

    public static void main(String[] args) {
        HelloClient helloClient = new HelloClient();
        try {
            helloClient.tTransport.open();
            System.out.println(helloClient.client.hello("lilei"));
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
