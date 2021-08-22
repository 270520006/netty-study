package com.xiaolu.nacos_study.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("nacos-zsp")
@Component
public interface NacosFeignServer {
    @GetMapping("/xiaolu/you")
    public String xiaopengpeng();
}
