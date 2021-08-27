package org.borg.data;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class Data {

    public static Integer[] generateRandomArray(int max){
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

    /**
     * 交换数组内两个元素
     * @param array
     * @param i
     * @param j
     */
    public static void swap(Integer[] array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void log(Integer[] array){
        log.info("Sorter array {}", Arrays.toString(array));
    }

    public static Boolean checkArgument(Integer[] array, int start, int end){
        return array.length >= 1 && start >= 0 && end < array.length && start <= end;
    }
}
