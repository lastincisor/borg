package org.borg.data;

public class Data {

    public static Integer[] array(int max){
        Integer[] array = new Integer[max];
        for (int i=0;i<max;i++){
            array[i] = RandomUtil.getNumber(max);
        }
        return array;
    }

    public static Integer[] copy(Integer[] array){
        Integer[] targetArray = new Integer[array.length];
        System.arraycopy(array,0,targetArray, 0,array.length);
        return targetArray;
    }
}
