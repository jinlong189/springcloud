package com.example.redis.other;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

public class TestTransaction {
    public static void main(String[] args) throws InterruptedException {
        boolean isSuccess = isSuccess("balance", "debt", 20l);
        System.out.println("main retVal-------"+isSuccess);
    }



    public static boolean isSuccess(String balance,String debt,Long delAmount) throws InterruptedException {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.watch(balance);
        Long amount = Long.parseLong(jedis.get(balance));
        Long debts = Long.parseLong(jedis.get(debt));
        if(amount<delAmount){
            jedis.unwatch();
            System.out.println("amount can't Less than delAmount !!!!");
            return false;
        } else {
            Transaction transaction = jedis.multi();
            transaction.decrBy(balance, delAmount);
            transaction.incrBy(debt,delAmount);
            //在这里模拟网络延迟时，我们通过redis命令窗口手动去修改balance值。
            Thread.sleep(3000);
            List<Object> exec = transaction.exec();
            //执行exec操作时发现balance值被修改，因此终止操作。
            if(exec.size()<=0){
                System.out.println("balance is upfated by other person,debt is fail!!!");
                return false;
            }
            System.out.println("After updated balance== "+Long.parseLong(jedis.get(balance)));
            System.out.println("After updated debt== "+Long.parseLong(jedis.get(debt)));
            return true;
        }
    }
}
