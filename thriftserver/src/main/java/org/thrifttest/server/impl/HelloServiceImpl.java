package org.thrifttest.server.impl;

import org.apache.thrift.TException;
import org.thrifttest.server.HelloService;

/**
 * Created by aayongche on 2016/7/1.
 */
public class HelloServiceImpl implements HelloService.Iface {

    public HelloServiceImpl(){};

    public String hello(String name) throws TException {
        System.out.println("name: " + name);
        return String.format("%s say hello!", name);
    }
}
