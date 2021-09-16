package org.borg.sort.strategy;

import org.borg.sort.SortStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 基数排序(Radix Sorting)是桶排序的扩展。
 *
 * 它的基本思想是：将整数（十进制）按位数切割成不同的数字，然后按每个位数分别进行比较。
 *
 * 具体做法是：找出所有待比较的整数中的最大值，求出最大的数位长度，将所有待比较的整数统一成同样的数位长度，数位较短的数前面补零，然后按每个位数分别进行比较。
 * 按照位数比较顺序的不同，将其分为LSD（Least significant digital）和MSD（Most significant digital）两类。
 *
 * 最低位优先法(LSD)
 * 从最低位（个位）开始，依次进行一次排序，直到到达最高位。这样从最低位排序，一直到最高位排序完成以后, 数列就变成一个有序序列。
 *
 * 最高位优先法(MSD)
 * 从最高位开始，依次进行一次排序，直到到达最低位（个位）。
 *
 * MSD与LSD在分配收集过程中的不同
 * MSD：当待排序序列按照位数不同进行分配后，相当于进入了多个子序列， 后续的排序工作分别在各个子序列中进行。
 * LSD：每次分配和收集都是相对于整个序列而言的。
 */
public class RadixMsdSortStrategy implements SortStrategy {

    @Override
    public Integer[] execute(Integer[] array) {
        if (array == null || array.length < 2){
            return array;
        }
        msdSort(array);
        return array;
    }

    public Integer[] sort(Integer[] array){
        /** step1:确定排序的趟数*/
        int max=array[0];
        for(int i=1;i<array.length;i++) {
            if(array[i]>max) {
                max=array[i];
            }

        }
        /** step2:判断位数*/
        int digit = 0;
        while(max > 0) {
            max/=10;
            digit++;
        }
        /**初始化一个二维数组，相当于二维数组，可以把重复的存进去*/
        List<List<Integer>> temp = new ArrayList<>();
        for(int i = 0;i < 10;i++) {
            temp.add(new ArrayList<Integer>());
        }
        /**开始合并收集*/
        for(int i = 0; i < digit; i++) {
            /** 对每一位进行排序 */
            for(int j = 0; j < array.length; j++) {
                /**求每一个数的第i位的数，然后存到相对应的数组中*/
                int digitInx = array[j]%(int)Math.pow(10, i + 1)/(int)Math.pow(10, i);
                List<Integer> tempInside = temp.get(digitInx);
                tempInside.add(array[j]);
                temp.set(digitInx,tempInside );
            }
            int count = array.length - 1;
            for(int k = 0;k < 10;k++) {
                for(;temp.get(k).size()>0;count--) {
                    List<Integer> temp2 = temp.get(k);
                    array[count] = temp2.get(0);
                    temp2.remove(0);
                }
            }
        }

        return array;
    }

    public Integer[] msdSort(Integer[] array){
        int len = array.length;
        // 取数组中最大值
        int max = array[0];
        for (int i = 1; i < len; i++) {
            if (array[i] != null && max < array[i]) {
                max = array[i];
            }
        }

        int maxL = String.valueOf(max).length();//获取素组中最长元素的长度
        int bit = new Double(Math.pow(10, maxL - 1)).intValue();
        int[][] t = new int[10][len];   //准备10个用于存放0-9的桶，每个桶最多存放数组长度len个元素
        int[] num = new int[len];   // 记录每个桶中存入元素的个数

        for (int a : array) { //按最高位入桶
            int m = (a / bit) % 10;
            t[m][num[m]] = a;
            num[m]++;
        }

        int index = 0;
        for (int i = 0; i < len; i++) {
            if (num[i] == 1) {  // 如果桶中只有一个数则直接取出
                array[index++] = t[i][0];
            } else if (num[i] > i) { //如果桶中不止一个数，则另存入数组arr2，递归
                Integer[] arr2 = new Integer[num[i]];
                for (int j = 0; j < num[i]; j++) {
                    arr2[j] = t[i][j];
                    msdSort(arr2); // 递归方法
                }
            }
        }
        return array;
    }

}
