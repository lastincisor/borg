package org.borg.sort.strategy;

import org.borg.data.Data;
import org.borg.sort.SortStrategy;

/**
 * 双路快速排序算法是随机化快速排序的改进版本，partition 过程使用两个索引值（i、j）用来遍历数组，
 * 将 <v 的元素放在索引i所指向位置的左边，而将 >v 的元素放在索引j所指向位置的右边，v 代表标定值。
 *
 * 时间和空间复杂度同随机化快速排序。 对于有大量重复元素的数组，如果使用上一节随机化快速排序效率是
 * 非常低的，导致 partition 后大于基点或者小于基点数据的子数组长度会极度不平衡，甚至会退化成 O(n*2)
 * 时间复杂度的算法，对这种情况可以使用双路快速排序算法。
 *
 * 使用两个索引值（i、j）用来遍历我们的序列，将 <=v 的元素放在索引 i 所指向位置的左边，而将 >=v
 * 的元素放在索引 j 所指向位置的右边，平衡左右两边子数组。
 *
 */
public class QuickSort2WaysStrategy implements SortStrategy {

    @Override
    public Integer[] execute(Integer[] array) {
        sort(array, 0, array.length - 1);
        return array;
    }

    private void sort(Integer[] arr, int l, int r){
        if (l >= r) {
            return;
        }
        int p = partition(arr, l, r);
        sort(arr, l, p-1 );
        sort(arr, p+1, r);
    }

    public static void main(String[] args) {
        int r = 1000000-1;
        int l = 1;
        int pivot = (int)(Math.random()*(r-l+1))+l;
        System.out.println(pivot);
    }

    //核心代码---开始
    private int partition(Integer[] arr, int l, int r){
        // 随机在arr[l...r]的范围中, 选择一个数值作为标定点pivot
        Data.swap( arr, l , (int)(Math.random()*(r-l+1))+l );
        Integer v = arr[l];
        // arr[l+1...i) <= v; arr(j...r] >= v
        int i = l+1, j = r;
        while( true ){
            while( i <= r && arr[i].compareTo(v) < 0 ){
                i ++;
            }
            while( j >= l+1 && arr[j].compareTo(v) > 0 ){
                j --;
            }
            if( i > j ){
                break;
            }
            Data.swap( arr, i, j );
            i ++;
            j --;
        }
        Data.swap(arr, l, j);
        return j;
    }

}
