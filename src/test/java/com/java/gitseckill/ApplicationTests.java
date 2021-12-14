package com.java.gitseckill;

import com.java.gitseckill.entity.SysUser;
import com.java.gitseckill.service.impl.UserServiceImpl;
import com.java.gitseckill.utils.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.sql.Date;
import java.time.*;
import java.util.Set;

@SpringBootTest
class ApplicationTests {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
//        redisTemplate.opsForHash().put("kk","k1",10);
//        Object k1 = redisTemplate.opsForHash().get("kk","k1");
//        System.out.println(k1);
//        Long k11 = redisTemplate.opsForHash().increment("kk","k1",-1);
//        System.out.println("返回值为:"+k11);
        Object user18181818181 = redisTemplate.opsForHash().get("User18181818181", "aa639556-0523-4a60-b733-17005940073f");
        System.out.println(user18181818181);
        SysUser user=(SysUser)user18181818181;
        System.out.println(user);

    }
    @Test
    void test1(){
        SysUser user = new SysUser();
        user.setNickname("hahah");
        user.setPassword(MD5Utils.inputPassToPass("123456","1a2b3c4d"));
        user.setRegisterDate(LocalDateTime.now());
        user.setSalt("1a2b3c4d");
        System.out.println(LocalDateTime.now());
        boolean save = userService.save(user);
    }
    @Test
    void test2(){
        ZoneId id=ZoneId.systemDefault();
        System.out.println("时区:"+id);
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(id);
        Instant instant1 = zonedDateTime.toInstant();


        System.out.println("instant"+instant1);
        System.out.println("instant的milli"+instant1.toEpochMilli());

        Date date= new Date(instant1.toEpochMilli());
        java.util.Date from = Date.from(instant1);

        System.out.println("utils的from"+from);

        System.out.println("sql的"+date);
    }
    @Test
    void test3(){

    }

}
