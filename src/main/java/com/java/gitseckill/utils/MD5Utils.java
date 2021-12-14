package com.java.gitseckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.security.MD5Encoder;

import java.security.DigestException;

/**
 * @author javaercdy
 * @create 2021-11-30$-{TIME}
 */
public class MD5Utils {
    private static String salt="1a2b3c4dcdy";

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassToForm(String input){
        String password=salt.charAt(0)+salt.charAt(9)+salt.charAt(2)+input+salt.charAt(4)+salt.charAt(6)+salt.charAt(8);
        return md5(password);
    }

    public static String formPassToDbPass(String form,String salt){
        String password=salt.charAt(0)+salt.charAt(3)+salt.charAt(2)+form+salt.charAt(4)+salt.charAt(6)+salt.charAt(7);
        return md5(password);
    }
    public static String inputPassToPass(String input,String salt){
        String s = inputPassToForm(input);
        String pass = formPassToDbPass(s, salt);
        return pass;
    }

    public static void main(String[] args) {
        String pass = formPassToDbPass("f910a7a7937cc31fc2ed74cc4d120769","1a2b3c4d");
        System.out.println(pass);
    }

}
