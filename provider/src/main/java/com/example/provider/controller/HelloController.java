package com.example.provider.controller;

import com.example.provider.service.StockService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.sun.istack.internal.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {
    private final Logger logger = Logger.getLogger(getClass());
    @Autowired
    StockService stockService;

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index(@RequestParam String name) {
        return "hi "+name+",i am from port:" +port;
    }

    @RequestMapping("/stockNum")
    @ResponseBody
    public String stockNum() {
        String stockNum = stockService.decStockForRedis();
        return "i am from port:"+port+",剩余库存:"+stockNum;
    }
}
