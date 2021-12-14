package com.java.gitseckill.utils;

import ch.qos.logback.core.db.dialect.DBUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.gitseckill.entity.SysUser;
import com.java.gitseckill.vo.Result;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author javaercdy
 * @create 2021-12-04$-{TIME}
 */
public class UserUtils {

    private static void createUser(int count) throws Exception{
        List<SysUser> users = new ArrayList<SysUser>(count);
        //生成用户
        for(int i=0;i<count;i++) {
            SysUser user = new SysUser();
            user.setId(13000010000L+i);
            user.setLoginCount(1);
            user.setNickname("user"+i);
            user.setRegisterDate(LocalDateTime.now());
            user.setSalt("1a2b3c4d");
            user.setPassword(MD5Utils.inputPassToPass("123456", user.getSalt()));
            users.add(user);
        }
        System.out.println("create user");
//		//插入数据库
        Connection conn = getConn();
        String sql = "insert into sk_user(login_count, nickname, register_date, salt, password, id)values(?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for(int i=0;i<users.size();i++) {
            SysUser user = users.get(i);
            pstmt.setInt(1, user.getLoginCount());
            pstmt.setString(2, user.getNickname());
            ZoneId id=ZoneId.systemDefault();
            Instant instant = user.getRegisterDate().atZone(id).toInstant();
            Date date = new Date(instant.toEpochMilli());
            pstmt.setDate(3,date);
            pstmt.setString(4, user.getSalt());
            pstmt.setString(5, user.getPassword());
            pstmt.setLong(6, user.getId());
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        pstmt.close();
        conn.close();
        System.out.println("insert to db");
        //登录，生成token
        String urlString = "http://localhost:8081/login/login";
        File file = new File("D:\\yexu\\config.txt");
        if(file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for(int i=0;i<users.size();i++) {
            SysUser user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection)url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile="+user.getId()+"&password="+MD5Utils.inputPassToForm("123456")+"&key=bbb"+"&code=111";
            out.write(params.getBytes());
            out.flush();

            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0 ,len);
            }

            bout.close();

            String response = new String(bout.toByteArray());

            ObjectMapper objectMapper = new ObjectMapper();
            Result result = objectMapper.readValue(response, Result.class);
            System.out.println(result);
            String token = (String) result.getData();

            System.out.println("create token : " + user.getId());

            String row = user.getId()+","+token;

            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getId());
        }
        raf.close();

        System.out.println("over");
    }
    public static Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/seckill?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai", "root", "145364");
        return connection;
    }

    public static void main(String[] args) throws Exception {
        createUser(5000);
    }
}
