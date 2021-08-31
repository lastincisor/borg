package org.borg.sort.strategy;

import org.borg.sort.SortStrategy;

/**
 * 鸡尾酒排序是冒泡排序的一种，又称为来回排序。它比冒泡排序要高级点，
 *
 * 鸡尾酒排序等于是冒泡排序的轻微变形。不同的地方在于从低到高然后从高到低，
 * 而冒泡排序则仅从低到高去比较序列里的每个元素。他可以得到比冒泡排序稍微好一点的效能，
 * 原因是冒泡排序只从一个方向进行比对(由低到高)，每次循环只移动一个项目。
 * 为何这么说呢
 * 冒泡排序是先找最大，然后找第二大，然后一直找完
 * 鸡尾酒排序是这样的，先找最大，再找最小，然后找第二大，再找第二小，依次类推，直至找不到了为止
 *
 * Cmin=n-1;Mmin=0;
 * 所以，冒泡排序最好的时间复杂度为O(n)。
 */
public class CocktailSortStrategy implements SortStrategy {


    @Override
    public Integer[] execute(Integer[] array) {
        if (array.length == 0){
            return array;
        }
        return sort(array);
    }

    public Integer[] sort(Integer[] array){
        while(true){
            boolean flag = false;
            for(int i=0; i<array.length-1; i++) {
                if(array[i] > array[i+1]) {
                    int tmp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = tmp;
                    flag = true;
                }
            }
            for(int j=array.length-1; j>0; j--) {
                if(array[j-1] > array[j]) {
                    int tmp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = tmp;
                    flag = true;
                }
            }
            if(!flag) {
                break;
            }
        }
        return array;
    }

}
