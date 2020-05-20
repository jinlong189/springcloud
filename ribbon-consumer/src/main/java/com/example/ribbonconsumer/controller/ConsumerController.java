package com.example.ribbonconsumer.controller;

import com.example.ribbonconsumer.dao.ConsumerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
    @Autowired
    ConsumerDao consumerDao;


    @RequestMapping(value = "/ribbon-consumer",method = RequestMethod.GET)
    public String helloController(@RequestParam String name) {
        return consumerDao.getProvider(name);
    }

    @RequestMapping(value = "/ribbon-stock",method = RequestMethod.GET)
    public String decStock(){
        return consumerDao.decStock();
    }

}
