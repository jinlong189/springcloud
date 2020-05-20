package com.example.provider.service.impl;

import com.example.provider.service.StockService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private Redisson redisson;
    private Object object = new Object();

//    @Override
//    public String decStock() {
//        ValueOperations<String, String> ops = template.opsForValue();
//        Integer stock = 0;
//        synchronized (object){
//            stock = Integer.parseInt(ops.get("stock"));
//            if (stock>0){
//                stock--;
//                ops.set("stock",String.valueOf(stock));
//                System.out.println("扣减成功，剩余库存："+stock);
//                return "扣减成功，剩余库存："+stock;
//            }else{
//                System.out.println("扣减失败，库存不足了！！！");
//                return "扣减失败，库存不足了！！！";
//            }
//        }
//    }

    @Override
    public String decStock() {
        RLock redisonLock = redisson.getLock("lock");
        try {
            redisonLock.lock();
            int stock = Integer.parseInt(template.opsForValue().get("stock"));
            if (stock>0){
                stock--;
                template.opsForValue().set("stock",String.valueOf(stock));
                System.out.println("扣减成功，剩余库存："+stock);
                return "扣减成功，剩余库存："+stock;
            }else{
                System.out.println("扣减失败，库存不足了！！！");
                return "扣减失败，库存不足了！！！";
            }

        }finally {
            redisonLock.unlock();
        }

    }

    /**
     * 这种处理方式基本可以解决大多数分布式锁的问题，但是还是存在一些问题。如果线程一在执行的过程中花费的时间超过了过期时间，
     * 那么锁会自动删除，但是线程一还没有执行完操作仍然在执行，由于锁已经删除了这时候线程2将会进入去执行。一段代码有多个线程去执
     * 行，这样就出现了锁失效问题。
     * 如何解决锁失效问题？
     * 可以通过redisson去解决。redisso相当于开启了一个守护线程去守护这个锁的时间，一旦锁时间快过期的时候而业务还没有执行完毕，
     * 这时候会对锁的有效期自动进行续期，直到当前线程执行完毕释放锁，其他线程才可以去执行。
     * @return
     */
    @Override
    public String decStockForRedis() {
        String lock="lock_key";
        String lockVal = UUID.randomUUID().toString();
        Boolean lock_key = template.opsForValue().setIfAbsent(lock, lockVal,10, TimeUnit.SECONDS);
        if(!lock_key){
            return "fail";
        }
        try {
            int stock = Integer.parseInt(template.opsForValue().get("stock"));
            if(stock>0){
                stock--;
                template.opsForValue().set("stock",String.valueOf(stock));
                System.out.println("扣减成功，剩余库存："+stock);
                return "扣减成功，剩余库存："+stock;
            }else{
                System.out.println("扣减失败，库存不足了！！！");
                return "扣减失败，库存不足了！！！";
            }
        }finally {
            if (lockVal.equals(template.opsForValue().get(lock))){
                template.delete(lock);
            }
        }
    }
}
