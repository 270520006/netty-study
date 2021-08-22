package com.xiaolu.nacos_study.controller;

import com.xiaolu.nacos_study.feign.NacosFeignServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/who/am")
@RefreshScope
public class WhoAmIController {
    @Autowired
    NacosFeignServer nacosFeignServer;
    @Value("${xiaolu.name}")
    private String xiaolu;
    @GetMapping("/i")
    public String xiaoLu(){
        return "I am XiaoLu";
    }
    @GetMapping("/you")
    public String xiaopengpeng(){
        return nacosFeignServer.xiaopengpeng();
    }

    @GetMapping("/xiaolulu")
    public String xiaoLULu(){
        return xiaolu;
    }

}
