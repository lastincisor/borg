package org.borg.sort.strategy;

import org.borg.sort.SortStrategy;

import java.util.ArrayList;

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
        sort(array, array.length);
        return array;
    }

    public Integer[] sort(Integer[] array, int n){
        int max = array[0];
        for(int i = 1 ;i < n;i++){
            if(max < array[i])
                max = array[i];
        }
        int maxL = String.valueOf(max).length();  //获取数组中最长元素长度

        int k = new Double(Math.pow(10, maxL - 1)).intValue();
        int[][] t = new int[10][n];  //桶
        int[] num = new int[n];      //记录每个桶中存入数的个数

        for(int a : array){              //按最高位入桶
            int m = (a / k) % 10;
            t[m][num[m]] = a;
            num[m]++;
        }
        int c = 0;
        for(int i = 0; i < n; i++){
            if(num[i] == 1){        //如果桶中只有一个数则直接取出
                array[c++] = t[i][0];
            }else if(num[i] > 1){   //如果桶中不止一个数，则另存如数组B递归
                Integer[] B = new Integer[num[i]];
                for(int j = 0;j < num[i];j++){
                    B[j] = t[i][j];
                    sort(B,num[i]);   //递归方法
                }
            }
        }
        return array;
    }

}
