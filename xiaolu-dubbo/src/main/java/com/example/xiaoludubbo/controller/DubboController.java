package com.example.xiaoludubbo.controller;

import com.example.xiaoludubbo.server.DubboServer;
import com.example.xiaoludubbo.server.impl.DubboserverImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xialu/dubbo")
public class DubboController {

    @Autowired
    DubboServer dubboserver;
    @GetMapping("/test")
    public String testDubbo() {
        return dubboserver.testServer();
    }
}
