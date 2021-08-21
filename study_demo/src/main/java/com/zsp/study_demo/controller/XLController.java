package com.zsp.study_demo.controller;

import com.zsp.study_demo.feign.XLFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("who/is")
public class XLController {
    @Autowired
    XLFeign xlFeign;
    @GetMapping("/me")
    public String xLIsMe(){
        return xlFeign.xiaoLu();
    }

}
