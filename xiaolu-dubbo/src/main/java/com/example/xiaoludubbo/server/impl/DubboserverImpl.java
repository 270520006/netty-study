package com.example.xiaoludubbo.server.impl;

import com.example.xiaoludubbo.server.DubboServer;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService(timeout = 10000,group = "xiaolu")
public class DubboserverImpl implements DubboServer {

    @Override
    public String testServer() {
        return "lulu-dubbo";
    }
}
