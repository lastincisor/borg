package org.borg.sort.strategy;

import org.borg.data.Data;
import org.borg.sort.SortStrategy;

/**
 * 快速排序的基本思想：通过一趟排序将待排记录分隔成独立的两部分，
 * 其中一部分记录的关键字均比另一部分的关键字小，则可分别对这两
 * 部分记录继续进行排序，以达到整个序列有序。
 *
 * 快速排序使用分治法来把一个串（list）分为两个子串（sub-lists）。具体算法描述如下：
 *
 * 从数列中挑出一个元素，称为 “基准”（pivot）；
 * 重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面
 * （相同的数可以到任一边）。在这个分区退出之后，该基准就处于数列的中间位置。
 * 这个称为分区（partition）操作；
 * 递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
 *
 * 最佳情况：T(n) = O(nlogn)   最差情况：T(n) = O(n2)   平均情况：T(n) = O(nlogn)　
 */
public class QuickSortStrategy implements SortStrategy {

    @Override
    public Integer[] execute(Integer[] array) {
        return quickSort(array, 0, array.length - 1);
    }

    public Integer[] quickSort(Integer[] array, int start, int end) {
        if (array.length < 1 || start < 0 || end >= array.length || start > end) return null;
        int smallIndex = partition(array, start, end);
        if (smallIndex > start){
            quickSort(array, start, smallIndex - 1);
        }
        if (smallIndex < end){
            quickSort(array, smallIndex + 1, end);
        }
        return array;
    }

    /**
     * 快速排序算法——partition
     * @param array
     * @param start
     * @param end
     * @return
     */
    public int partition(Integer[] array, int start, int end) {
        int pivot = (int) (start + Math.random() * (end - start + 1));
        int smallIndex = start - 1;
        Data.swap(array, pivot, end);
        for (int i = start; i <= end; i++){
            if (array[i] <= array[end]) {
                smallIndex++;
                if (i > smallIndex){
                    Data.swap(array, i, smallIndex);
                }
            }
        }
        return smallIndex;
    }

}
