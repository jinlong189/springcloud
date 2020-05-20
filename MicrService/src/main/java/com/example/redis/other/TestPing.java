package com.example.redis.other;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestPing {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        System.out.println(jedis.ping());

        jedis.set("k44","v44");
        jedis.set("k45","v45");
        jedis.set("k46","v46");
        jedis.save();

        System.out.println(jedis.get("k44")+"=="+jedis.get("k45")+"=="+jedis.get("k46"));

        Map<String,String> map = new HashMap<String,String>();
        map.put("name","zhangsan");
        map.put("age","18");
        map.put("salary","10000");
        map.put("sex","man");
        jedis.hmset("hash2",map);
        List<String> result=jedis.hmget("hash2","name","age");
        for (String element: result){
            System.out.println("==="+element);
        }
        jedis.del("course");
        jedis.lpushx("course","oracle");
        jedis.lpush("course","redis1");
        jedis.lpush("course","mongodb2");
        jedis.lpush("course","mysql4");
        List<String> courses =jedis.lrange("course",0,10);
        for (int i = 0; i <courses.size() ; i++) {
            System.out.println("courses==="+courses.get(i));
        }
        System.out.println("lpop=="+jedis.lpop("course"));

        System.out.println("================================sadd============================================");
        jedis.del("student");
        jedis.sadd("student", "liwei");
        jedis.sadd("student", "wangwei");
        jedis.sadd("student", "zangwei");
        jedis.sadd("student", "chenwei");
        jedis.sadd("student", "chenwei");
        jedis.sadd("student", "chenwei");
//        String student1 = jedis.spop("student");
//        Long srem = jedis.srem("student", "liwei", "chenwei");
//        System.out.println("spop==="+student1);
//        System.out.println("srem==="+srem);
        Set<String> students = jedis.smembers("student");
        for (String student:students){
            System.out.println("student===="+student);
        }
        System.out.println("================================zadd============================================");
        jedis.del("teacher");
        jedis.zadd("teacher",1,"liwei");
        jedis.zadd("teacher",2,"wangwei");
        jedis.zadd("teacher",3,"zangwei");
        jedis.zadd("teacher",4,"chenwei");
        jedis.zadd("teacher",5,"chenwei");
        jedis.zadd("teacher",6,"chenwei");
        Set<String> teachers = jedis.zrange("teacher", 0, -1);
        for (String teacher:teachers){
            System.out.println("teachers===="+teachers);
        }

    }

}
