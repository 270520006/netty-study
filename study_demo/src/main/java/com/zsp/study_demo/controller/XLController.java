package com.zsp.study_demo.controller;

import com.zsp.study_demo.feign.XLFeign;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("who/is")
@RefreshScope
public class XLController {
    @Autowired
    XLFeign xlFeign;
    @Value("${xiaolu.name}")
    private String name;
    @GetMapping("/me")
    public String xLIsMe(){
        return xlFeign.xiaoLu()+name;
    }

}
