package com.zsp.nacos_oepnfeign.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("xiaolu-study-nacos")
@Component
public interface XiaoLuOpenFeignService {
    @GetMapping("who/am/i")
    public String xiaoLu();
}
