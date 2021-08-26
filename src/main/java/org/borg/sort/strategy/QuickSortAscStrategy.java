package org.borg.sort.strategy;

import org.borg.data.Data;
import org.borg.sort.SortStrategy;

/**
 * 先把第一个元素令为low下标,最后一个为high下标。并把第一个元素令为temp来作为标准元素。以标准元素来调整数组，
 * 使比标准元素小的都在标准元素前，比标准元素大的都在标准元素后。这样一次排序后，有两个好处：
 * 1.标准元素找到了它自己在该元素中的位置；
 * 2.把数组分成了以标准元素为分隔的两个子数组。
 * 然后分别对两个子数组采用相同的排序方法。
 *
 * 最佳情况：T(n) = O(nlogn)   最差情况：T(n) = O(n2)   平均情况：T(n) = O(nlogn)　
 */
public class QuickSortAscStrategy implements SortStrategy {

    @Override
    public Integer[] execute(Integer[] array) {
        quickSort(array, 0, array.length - 1);
        return array;
    }

    public void quickSort(Integer[] array, int start, int end) {
        if (!Data.checkArgument(array,  start,  end)) {
            return;
        }
        int i =start;
        int j =end;
        int temp= array[start];
        while (i< j) {
            //必须从右边开始扫描，否则从左边开始扫描遇到一个比它大的元素时
            // (左边不是有一个空位吗？但是放到左边肯定是不合适)应该放到右边，
            // 放到哪里呢，放到任何地方都会覆盖掉一个数据
            // 这样没有利用上这个缺口 严重错误
            while(i < j && array[j]>= temp) {
                j -- ;//放过
            }
            if(i<j) {
                array[i] = array[j] ;
                i ++ ; //放过，并开始左端循环
            }

            //注意 ：" < " 而不是<=这个细节说明即使和temp相等也要放到它的后面
            // 这样保证了-在数值相同时，保持原有顺序的原则！
            while(i< j && array[i]< temp) {
                i ++ ;
            }
            if(i< j) {
                array[j] = array[i] ;
                j -- ;
            }
        }
        array[i] =temp ;
        // 有可能i=low 如 :a[low]是最小的，一开始循环就一直j-- 一直减到了i ==j
        // 然后赋值(即它本来就应该在第一位),也有可能a[low]是最大的，一直i++到high。
        // 如果是这样就不用对那个子区间排序了，所以下面用if语句
        if(i> start){
            quickSort(array, start, i-1);
        }
        if(i< end){
            quickSort(array , i+1, end);
        }

    }

}
