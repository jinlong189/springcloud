package com.example.ribbonconsumer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ConsumerDao {
    @Autowired
    private RestTemplate restTemplate;

    public String getProvider(String name) {
        return restTemplate.getForEntity("http://hello-service/hello?name="+name, String.class).getBody();
    }

    public String decStock(){
        return restTemplate.getForEntity("http://hello-service/stockNum", String.class).getBody();
    }
}
