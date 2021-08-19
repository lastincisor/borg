package org.sort.base;

import java.util.Random;

public class RandomUtil {

    public static Integer getNumber(int max,int min){
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

    public static Integer getNumber(int max){
        Random random = new Random();
        return random.nextInt(max)%(max+1);
    }

    public static Integer getNumber(){
        return getNumber(1200,600);
    }
}
