package com.zsp.nacos_oepnfeign.controller;

import com.zsp.nacos_oepnfeign.feign.XiaoLuOpenFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xiaolu")
@RefreshScope
public class WhoAmIcontroller {
    @Value("${zhuzhu.age}")
    private int age;
    @Autowired
    XiaoLuOpenFeignService xiaoLuOpenFeignService;
    @GetMapping("/oepnfeign")
    public String xiaolulu(){
        return xiaoLuOpenFeignService.xiaoLu();
    }
    @GetMapping("/you")
    public String xiaopeng(){
        return "I am ShaoPeng";
    }
    @GetMapping("/age")
    public int xiaozhuzhu(){
        return age;
    }

}
