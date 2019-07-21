package com.winwill.thrift.Impl;

/*
 * Created by IntelliJ IDEA.
 *
 * Author: hukexin
 * Email: hukexin@xiaomi.com
 * Date: 19-7-21
 * Time: 下午3:54
 */
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

import java.net.ServerSocket;

/**
 * @author qifuguang
 * @date 15/9/11 16:07
 */
public class HelloWordServer {
    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(7912);
        TServerSocket serverTransport = new TServerSocket(socket);
        com.winwill.thrift.HelloWordService.Processor processor = new com.winwill.thrift.HelloWordService.Processor(new HelloWordServiceImpl());
        TServer server = new TSimpleServer(processor, serverTransport);
        System.out.println("Running server...");
        server.serve();
    }
}
