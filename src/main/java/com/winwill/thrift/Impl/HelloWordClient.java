package com.winwill.thrift.Impl;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author qifuguang
 * @date 15/9/11 16:13
 */
public class HelloWordClient {
    public static void main(String[] args) throws Exception {
        TTransport transport = new TSocket("localhost", 8888);
        TProtocol protocol = new TBinaryProtocol(transport);

        // 创建client
        com.winwill.thrift.HelloWordService.Client client = new com.winwill.thrift.HelloWordService.Client(protocol);

        transport.open();  // 建立连接

        // 第一种请求类型
        com.winwill.thrift.Request request = new com.winwill.thrift.Request()
                .setType(com.winwill.thrift.RequestType.SAY_HELLO).setName("winwill2012").setAge(24);
        System.out.println(client.doAction(request));

        // 第二种请求类型
        request.setType(com.winwill.thrift.RequestType.QUERY_TIME).setName("winwill2012");
        System.out.println(client.doAction(request));

        transport.close();  // 请求结束，断开连接
    }
}