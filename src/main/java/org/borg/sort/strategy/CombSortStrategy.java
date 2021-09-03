package org.borg.sort.strategy;

import org.borg.sort.SortHelper;
import org.borg.sort.SortStrategy;

/**
 * 梳排序，跟梳子一样，齿和齿中间有间隙，这个间隙是多少呢，是1.3，
 * 有数组[6, 4, 5, 1, 8, 7, 2, 3]
 * 先来算算间隙，上面的数组长度为8，8/1.3=6，则间隙为6
 * 第一遍，取间隙6
 * 取下标0和6的数，分别是6和2，6比2大，进行互换，[2, 4, 5, 1, 8, 7, 6, 3]
 * 取下标1和7的数，分别是4和3，4比3大，进行互换，[2, 3, 5, 1, 8, 7, 6, 4]
 * 第二遍，取间隙6/1.3=4
 * 取下标0和4的数，分别是2和8，顺序正确，则不互换，[2, 3, 5, 1, 8, 7, 6, 4]
 * 取下标1和5的数，分别是3和7，顺序正确，则不互换，[2, 3, 5, 1, 8, 7, 6, 4]
 * 取下标2和6的数，分别是5和6，顺序正确，则不互换，[2, 3, 5, 1, 8, 7, 6, 4]
 * 取下标3和7的数，分别是1和4，顺序正确，则不互换，[2, 3, 5, 1, 8, 7, 6, 4]
 * 第三遍，取间隙4/1.3=3
 * 取下标0和3的数，分别是2和1，2比1大，进行互换，[1, 3, 5, 2, 8, 7, 6, 4]
 * 取下标1和4的数，分别是3和8，顺序正确，则不互换，[1, 3, 5, 2, 8, 7, 6, 4]
 * 取下标2和5的数，分别是5和7，顺序正确，则不互换，[1, 3, 5, 2, 8, 7, 6, 4]
 * 取下标3和6的数，分别是2和6，顺序正确，则不互换，[1, 3, 5, 2, 8, 7, 6, 4]
 * 取下标4和7的数，分别是8和4，8比4大，进行互换，[1, 3, 5, 2, 4, 7, 6, 8]
 * 第四遍，取间隙3/1.3=2
 * 取下标0和2的数，分别是1和5，顺序正确，则不互换，[1, 3, 5, 2, 4, 7, 6, 8]
 * 取下标1和3的数，分别是3和2，3比2大，进行互换，[1, 2, 5, 3, 4, 7, 6, 8]
 * 取下标2和4的数，分别是5和4，5比4大，进行互换，[1, 2, 4, 3, 5, 7, 6, 8]
 * 取下标3和5的数，分别是3和7，顺序正确，则不互换，[1, 2, 4, 3, 5, 7, 6, 8]
 * 取下标4和6的数，分别是5和6，顺序正确，则不互换，[1, 2, 4, 3, 5, 7, 6, 8]
 * 取下标5和7的数，分别是7和8，顺序正确，则不互换，[1, 2, 4, 3, 5, 7, 6, 8]
 * 第五遍，取间歇2/1.3=1
 * 取下标0和1的数，分别是1和3，顺序正确，则不互换，[1, 3, 5, 2, 4, 7, 6, 8]
 * 取下标1和2的数，分别是2和5，顺序正确，则不互换，[1, 2, 5, 3, 4, 7, 6, 8]
 * 取下标2和3的数，分别是4和3，4比3大，进行互换，[1, 2, 3, 4, 5, 7, 6, 8]
 * 取下标3和4的数，分别是3和7，顺序正确，则不互换，[1, 2, 3, 4, 5, 7, 6, 8]
 * 取下标4和5的数，分别是5和6，顺序正确，则不互换，[1, 2, 3, 4, 5, 7, 6, 8]
 * 取下标5和6的数，分别是7和8，顺序正确，则不互换，[1, 2, 3, 4, 5, 7, 6, 8]
 * 取下标6和7的数，分别是7和8，顺序正确，则不互换，[1, 2, 3, 4, 5, 7, 6, 8]
 * 因为1/1.3<1了，所以没法弄了，排序结束
 */
public class CombSortStrategy implements SortStrategy {

    @Override
    public Integer[] execute(Integer[] array) {
        int size  = array.length;

        // initialize gap
        int gap = size;

        // Initialize swapped as true to make sure that loop runs
        boolean swapped = true;

        // Keep running while gap is more than 1 and last iteration caused a swap
        while (gap != 1 || swapped) {
            // Find next gap
            gap = nextGap(gap);

            // Initialize swapped as false so that we can check if swap happened or not
            swapped = false;

            // Compare all elements with current gap
            for (int i = 0; i < size - gap ; i++) {
                if (SortHelper.less(array[i + gap], array[i])) {
                    // Swap arr[i] and arr[i+gap]
                    swapped = SortHelper.swap(array, i, i + gap);
                }
            }
        }
        return array;
    }

    private int nextGap(int gap) {
        // Shrink gap by Shrink factor
        gap = ( gap * 10 ) / 13;
        return Math.max(gap, 1);
    }
}
