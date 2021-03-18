package com.mediscreen.ui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="mediscreen-risk", url="http://localhost:9103")
public interface RiskProxy {

    @GetMapping("/assess/id")
    String getRiskForPatient(@RequestParam Integer patId);
}
