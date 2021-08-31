package org.borg.sort.strategy;

import org.borg.data.Data;
import org.borg.sort.SortStrategy;

/**
 * 折半插入排序法，又称二分插入排序法，是直接插入排序法的改良版，也需要执行i-1趟插入，不同之处在于，
 * 第i趟插入，先找出第i+1个元素应该插入的的位置，假定前i个数据是已经处于有序状态。
 *
 * 平均： O(n2) O(n2) O(n^2)
 * 最坏： O(n2) O(n2) O(n^2)
 * 最好： O(nlog2n) O(nlog2n) O(nlog_2n)
 */
public class InsertionSortBinaryStrategy implements SortStrategy {


    @Override
    public Integer[] execute(Integer[] array) {
        if (array.length == 0){
            return array;
        }
        return sort(array);
    }

    public Integer[] sort(Integer[] array){
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                // 缓存i处的元素值
                int tmp = array[i];
                // 记录搜索范围的左边界
                int low = 0;
                // 记录搜索范围的右边界
                int high = i - 1;
                while (low <= high) {
                    // 记录中间位置
                    int mid = (low + high) / 2;
                    // 比较中间位置数据和i处数据大小，以缩小搜索范围
                    if (array[mid] < tmp) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
                //将low~i处数据整体向后移动1位
                for (int j = i; j > low; j--) {
                    array[j] = array[j - 1];
                }
                array[low] = tmp;
            }
        }
        return array;
    }
}
