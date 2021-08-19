package org.sort.base;

public class Data {

    public static int[] array(int max){
        int[] array = new int[max];
        for (int i=0;i<max;i++){
            array[i] = RandomUtil.getNumber(max);
        }
        return array;
    }
}
