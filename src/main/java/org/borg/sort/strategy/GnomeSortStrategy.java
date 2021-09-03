package org.borg.sort.strategy;

import org.borg.sort.SortHelper;
import org.borg.sort.SortStrategy;

/**
 * 地精排序,传说有一个地精在排列一排花盘。他从前至后的排列，如果相邻的两个花盘顺序正确，他向前一步；
 * 如果花盘顺序错误，他后退一步，直到所有的花盘的顺序都排列好。
 * (地精排序思路与插入排序和冒泡排序很像, 主要其对代码进行了极简化)
 *
 * O(n2
 */
public class GnomeSortStrategy implements SortStrategy {

    @Override
    public Integer[] execute(Integer[] array) {
        int i=1;
        while(i<array.length) {
            if(i==0||array[i]>=array[i-1]) {
                 i++;
            }else {
                SortHelper.swap(array,i,i-1);
                i--;
            }
         }
        return array;
    }
}
