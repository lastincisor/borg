package org.borg.sort.strategy;

import org.borg.sort.SortHelper;
import org.borg.sort.SortStrategy;

/**
 * 圈排序是一种比较排序算法，它强制将数组分解为圈数，其中每个圈可以旋转以生成排序数组。
 * 理论上它在理论上是最优的，它减少了对原始数组的写入次数。
 * 考虑一组n个不同的元素。 给出元素a，可以通过计算小于a的元素的数量来计算a的索引。
 *
 * 如果找到元素处于正确的位置，只需保持原样。否则，通过计算小于a的元素总数来找到a的正确位置。
 * 它必须出现在排序数组中。 被替换的另一个元素b将被移动到其正确的位置。
 * 这个过程一直持续到在a的原始位置得到一个元素。
 *
 *
 */
public class CycleSortStrategy implements SortStrategy {

    @Override
    public Integer[] execute(Integer[] array) {
        int n = array.length;

        // traverse array elements
        for (int j = 0; j <= n - 2; j++) {
            // initialize item as starting point
            Integer item = array[j];

            // Find position where we put the item.
            int pos = j;
            for (int i = j + 1; i < n; i++)
                if (SortHelper.less(array[i], item)) pos++;

            // If item is already in correct position
            if (pos == j)  continue;

            // ignore all duplicate elements
            while (item.compareTo(array[pos]) == 0)
                pos += 1;

            // put the item to it's right position
            if (pos != j) {
                item = replace(array, pos, item);
            }

            // Rotate rest of the cycle
            while (pos != j) {
                pos = j;

                // Find position where we put the element
                for (int i = j + 1; i < n; i++)
                    if (SortHelper.less(array[i], item)){
                        pos += 1;
                    }


                // ignore all duplicate elements
                while (item.compareTo(array[pos]) == 0)
                    pos += 1;

                // put the item to it's right position
                if (item != array[pos]) {
                    item = replace(array, pos, item);
                }
            }
        }
        return array;
    }

    private <T extends Comparable<T>> T replace(T[] arr, int pos, T item){
        T temp = item;
        item = arr[pos];
        arr[pos] = temp;
        return item;
    }
}
