package com.zarker.util;

import java.util.Random;

/**
 * @author xuzhi
 * @createTime 2018-03-04 20:28:15
 * @description
 */
public class IpUtils {

    public static String getRandomIp(){
        Random random=new Random();
        return random.nextInt(255)+1+"."+random.nextInt(255)+1+"."+random.nextInt(255)+1+"."+random.nextInt(255)+1;
    }
}
