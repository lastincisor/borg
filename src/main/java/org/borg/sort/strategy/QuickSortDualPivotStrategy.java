package org.borg.sort.strategy;

import org.borg.sort.SortStrategy;
import org.sort.impl.SortFacade;

/**
 * 双轴快排，双轴和单轴的区别多一个轴，快排很多时候选最左侧元素以这个元素为轴将数据划分为两个区域，
 * 递归分治的去进行排序。但单轴很多时候可能会遇到较差的情况就是当前元素可能是最大的或者最小的，这
 * 样子元素就没有被划分区间，快排的递推T(n)=T(n-1)+O(n)从而为O(n2).
 *
 * 双轴就是选取两个主元素理想将区间划为3部分，这样不仅每次能够确定元素个数增多为2个，划分的区间由
 * 原来的两个变成三个，最坏最坏的情况就是左右同大小并且都是最大或者最小，但这样的概率相比一个最大
 * 或者最小还是低很多很多，所以双轴快排的优化力度还是挺大的。
 *
 *
 * 首先在初始的情况我们是选取待排序区间内最左侧、最右侧的两个数值作为pivot1和pivot2 .作为两个轴
 * 的存在。同时我们会提前处理数组最左侧和最右侧的数据会比较将最小的放在左侧。所以pivot1<pivot2.
 *
 * 而当前这一轮的最终目标是，比privot1小的在privot1左侧，比privot2大的在privot2右侧，
 * 在privot1和privot2之间的在中间。
 *
 * 这样进行一次后递归的进行下一次双轴快排，一直到结束，但是在这个执行过程应该去如何处理分析呢？
 * 需要几个参数呢？
 *
 * 假设知道排序区间[start，end]。数组为arr，pivot1=arr[start],pivot2=arr[end]
 * 还需要三个参数left，right和k。 l
 * left初始为start，[start,left]区域即为小于等于pivot1小的区域(第一个等于)。
 * right与left对应，初始为end，[right,end]为大于等于pivot2的区域(最后一个等于)。
 * k初始为start+1，是一个从左往右遍历的指针，遍历的数值与pivot1，pivot2比较进行适当交换，
 * 当k>=right即可停止。
 */
public class QuickSortDualPivotStrategy implements SortStrategy {

    @Override
    public Integer[] execute(Integer[] array) {
        return quickSortDualPivot(array, 0, array.length - 1);
    }

    public Integer[] quickSortDualPivot(Integer[] arr, int start, int end) {
        if(start>end){
            return arr;//参数不对直接返回
        }
        if(arr[start]>arr[end])
            swap(arr, start, end);
        int pivot1=arr[start],pivot2=arr[end];//储存最左侧和最右侧的值
        //(start，left]:左侧小于等于pivot1 [right,end)大于pivot2
        int left=start,right=end,k=left+1;
        while (k<right) {
            //和左侧交换
            if(arr[k]<=pivot1)
            {
                //需要交换
                swap(arr, ++left, k++);
            }
            else if (arr[k]<=pivot2) {//在中间的情况
                k++;
            }
            else {
                while (arr[right]>=pivot2) {//如果全部小于直接跳出外层循环

                    if(right--==k)
                        break ;
                }
                if(k>=right)break ;
                swap(arr, k, right);
            }
        }
        swap(arr, start, left);
        swap(arr, end, right);
        quickSortDualPivot(arr, start, left-1);
        quickSortDualPivot(arr, left+1, right-1);
        quickSortDualPivot(arr, right+1, end);
        return arr;
    }


    /**
     * 交换数组内两个元素
     * @param array
     * @param i
     * @param j
     */
    public void swap(Integer[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
