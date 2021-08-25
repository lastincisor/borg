package org.borg.sort.strategy;

import org.borg.sort.SortStrategy;

/**
 * 三路基数快排
 * （Three-way Radix Quicksort，也称作Multikey Quicksort、Multi-key Quicksort）：
 *  结合了基数排序（radix sort，如一般的字符串比较排序就是基数排序）和快排的特点，是字符串
 *  排序中比较高效的算法。该算法被排序数组的元素具有一个特点，即multikey，如一个字符串，每个
 *  字母可以看作是一个key。算法每次在被排序数组中任意选择一个元素作为关键数据，首先仅考虑这个
 *  元素的第一个key（字母），然后把其他元素通过key的比较分成小于、等于、大于关键数据的三个部分。
 *  然后递归地基于这一个key位置对“小于”和“大于”部分进行排序，基于下一个key对“等于”部分进行排序。
 * 　
 */
public class QuickSortMultikeyStrategy implements SortStrategy {

    @Override
    public Integer[] execute(Integer[] array) {
        return quickSort(array);
    }

    public  int getMiddle(Integer[] data, int low, int high){
        int tmp = data[low];

        while (low < high) {
            while (high > low && data[high] >= tmp) {
                high--;
            }

            data[low] = data[high];

            while (low < high && data[low] <= tmp) {
                low++;
            }

            data[high] = data[low];
        }

        data[low] = tmp;

        return low;
    }

    public  void quickSort(Integer[] data, int low, int high){
        if (low < high) {
            int middle = getMiddle(data, low, high);
            quickSort(data, low, middle - 1);
            quickSort(data, middle + 1, high);
        }
    }

    public Integer[] quickSort(Integer[] data){
        int low = 0;
        int high = data.length - 1;
        quickSort(data, low, high);
        return data;
    }
}

/**
 *
 * 变种优化
 * 随机化快排
 * 快速排序的最坏情况基于每次划分对主元的选择。基本的快速排序选取第一个元素作为主元。这样在数组已经有序的情况下，
 * 每次划分将得到最坏的结果。一种比较常见的优化方法是随机化算法，即随机选取一个元素作为主元。这种情况下虽然最坏
 * 情况仍然是O(n^2)，但最坏情况不再依赖于输入数据，而是由于随机函数取值不佳。实际上，随机化快速排序得到理论最坏
 * 情况的可能性仅为1/(2^n)。所以随机化快速排序可以对于绝大多数输入数据达到O(nlogn)的期望时间复杂度。一位前辈
 * 做出了一个精辟的总结：“随机化快速排序可以满足一个人一辈子的人品需求。”
 * 随机化快速排序的唯一缺点在于，一旦输入数据中有很多的相同数据，随机化的效果将直接减弱。对于极限情况，即对于n个
 * 相同的数排序，随机化快速排序的时间复杂度将毫无疑问的降低到O(n^2)。解决方法是用一种方法进行扫描，使没有交换的
 * 情况下主元保留在原位置。
 *
 * 平衡快排
 * 每次尽可能地选择一个能够代表中值的元素作为关键数据，然后遵循普通快排的原则进行比较、替换和递归。通常来说，选择
 * 这个数据的方法是取开头、结尾、中间3个数据，通过比较选出其中的中值。取这3个值的好处是在实际问题中，出现近似顺序
 * 数据或逆序数据的概率较大，此时中间数据必然成为中值，而也是事实上的近似中值。万一遇到正好中间大两边小（或反之）
 * 的数据，取的值都接近最值，那么由于至少能将两部分分开，实际效率也会有2倍左右的增加，而且利于将数据略微打乱，破坏
 * 退化的结构。
 *
 * 外部快排
 * 与普通快排不同的是，关键数据是一段buffer，首先将之前和之后的M/2个元素读入buffer并对该buffer中的这些元素进行
 * 排序，然后从被排序数组的开头（或者结尾）读入下一个元素，假如这个元素小于buffer中最小的元素，把它写到最开头的空
 * 位上；假如这个元素大于buffer中最大的元素，则写到最后的空位上；否则把buffer中最大或者最小的元素写入数组，并把
 * 这个元素放在buffer里。保持最大值低于这些关键数据，最小值高于这些关键数据，从而避免对已经有序的中间的数据进行
 * 重排。完成后，数组的中间空位必然空出，把这个buffer写入数组中间空位。然后递归地对外部更小的部分，循环地对其他
 * 部分进行排序。
 *
 * 三路基数快排
 * （Three-way Radix Quicksort，也称作Multikey Quicksort、Multi-key Quicksort）：结合了基数排序
 * （radix sort，如一般的字符串比较排序就是基数排序）和快排的特点，是字符串排序中比较高效的算法。该算法被排序
 * 数组的元素具有一个特点，即multikey，如一个字符串，每个字母可以看作是一个key。算法每次在被排序数组中任意选
 * 择一个元素作为关键数据，首先仅考虑这个元素的第一个key（字母），然后把其他元素通过key的比较分成小于、等于、
 * 大于关键数据的三个部分。然后递归地基于这一个key位置对“小于”和“大于”部分进行排序，基于下一个key对“等于”
 * 部分进行排序。
 *
 *
 * ***/
