package com.example.redis.service.impl;

import com.example.redis.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StringRedisTemplate template;
    private Object object = new Object();
    @Override
    public int decStock() {
        ValueOperations<String, String> ops = template.opsForValue();
        int stock = 0;
        synchronized (object){
            stock = Integer.parseInt(ops.get("stock"));
            if (stock>0){
                stock--;
                ops.set("stock",String.valueOf(stock));
                System.out.println("扣减成功，剩余库存："+stock);
            }else{
                System.out.println("扣减失败，库存不足了！！！");
            }
        }
        return stock;
    }

}
