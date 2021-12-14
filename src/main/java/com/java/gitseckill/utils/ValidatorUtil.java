package com.java.gitseckill.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author javaercdy
 * @create 2021-12-01$-{TIME}
 */
public class ValidatorUtil {
    private static final Pattern mobile_pattern=Pattern.compile("^1([3]|[5]|[7]|[8])[0-9]{9}$");

    public static Boolean isMobile(String value){
        if (StringUtils.isBlank(value)){
            return false;
        }
        return  mobile_pattern.matcher(value).matches();
    }

//    public static void main(String[] args) {
//        Boolean mobile = isMobile("22334567891");
//        System.out.println(mobile);
//    }
 }
