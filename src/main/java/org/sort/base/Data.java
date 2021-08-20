package org.sort.base;

public class Data {

    public static Integer[] array(int max){
        Integer[] array = new Integer[max];
        for (int i=0;i<max;i++){
            array[i] = RandomUtil.getNumber(max);
        }
        return array;
    }
}
