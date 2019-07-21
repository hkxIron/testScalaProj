package com.winwill.thrift.Impl;

/*
 * Created by IntelliJ IDEA.
 *
 * Author: hukexin
 * Email: hukexin@xiaomi.com
 * Date: 19-7-21
 * Time: 下午3:53
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;

import java.util.Date;

/**
 * @author qifuguang
 * @date 15/9/11 15:53
 */
public class HelloWordServiceImpl implements com.winwill.thrift.HelloWordService.Iface {
    // 实现这个方法完成具体的逻辑。
    public String doAction(com.winwill.thrift.Request request) throws com.winwill.thrift.RequestException, TException {
        System.out.println("Sever, Get client request: " + request);
        if (StringUtils.isBlank(request.getName()) || request.getType() == null) {
            throw new com.winwill.thrift.RequestException();
        }
        String result = "Hello, " + request.getName();
        if (request.getType() == com.winwill.thrift.RequestType.SAY_HELLO) {
            result += ", Welcome!";
        } else {
            result += ", Now is " + new Date().toLocaleString();
        }
        return result;
    }
}
