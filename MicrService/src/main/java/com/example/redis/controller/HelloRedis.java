package com.example.redis.controller;

import com.example.redis.dao.RedisDao;
import com.example.redis.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRedis {
    @Autowired
    RedisDao redisDao;
    @Autowired
    StockService stockService;

    @RequestMapping("/helloredis")
    @ResponseBody
    public String hello(String name, String age) {
        redisDao.setKey("name", name);
        redisDao.setKey("age", age);

        System.out.println("name=" + name + " * " + "age=" + age);

        String retName = redisDao.getValue("name");
        String retAge = redisDao.getValue("age");
        return retName + " * " + retAge;
    }

    @RequestMapping("/stockNum")
    @ResponseBody
    public int stockNum() {
        int stockNum = stockService.decStock();
        return stockNum;
    }
}
