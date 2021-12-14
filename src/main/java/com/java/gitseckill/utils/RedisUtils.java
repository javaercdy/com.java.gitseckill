package com.java.gitseckill.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author javaercdy
 * @create 2021-11-30$-{TIME}
 */
@Component
public class RedisUtils {
    @Autowired
    RedisTemplate redisTemplate;

    public void hset(String key, String field, Object value, long time, TimeUnit unit){
        try {
            redisTemplate.opsForHash().put(key,field,value);
            redisTemplate.expire(key,time,unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void set(String key,Object value,long time,TimeUnit unit){

        try {
            redisTemplate.opsForValue().set(key,value);
            redisTemplate.expire(key,time,unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Sadd(String key,Object value,long time,TimeUnit unit){
        try {
            redisTemplate.opsForSet().add(key,value);
            redisTemplate.expire(key,time,unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Long Sadd(String key,Object value){

        Long add = redisTemplate.opsForSet().add(key, value);
        return add;

    }


    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public Object hget(String key,String field){
       return redisTemplate.opsForHash().get(key, field);
    }

    public Object Spop(String key){
        return  redisTemplate.opsForSet().pop(key);
    }

    /**
     * 返回集合所有成员
     * @param key
     * @return
     */
    public Set Smget(String key){
        return redisTemplate.opsForSet().members(key);
    }
    /**
     * 判断set是否含有某个值
     */
    public Boolean hasKey(String key,Object value){
        return  redisTemplate.opsForSet().isMember(key,value);
    }
    public Long decrement(String key,String field){
        Long increment = redisTemplate.opsForHash().increment(key, field, -1l);
        return increment;
    }
    public Long decrement(String key){
        Long decrement = redisTemplate.opsForValue().decrement(key);
        return decrement;
    }
    public Long increment(String key,String field){
        Long increment = redisTemplate.opsForHash().increment(key, field,-1l);
        return increment;
    }
    public Long increment(String key){
        Long increment = redisTemplate.opsForValue().increment(key);
        return increment;
    }
}
