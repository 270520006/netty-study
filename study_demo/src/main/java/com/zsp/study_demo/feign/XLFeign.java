package com.zsp.study_demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(value = "xiaoluFeign")//这里要填上服务注册的名字
public interface XLFeign {
    @GetMapping("xiaolu/is/me")
    public String xiaoLu();
}
