package org.borg.sort.strategy;

import org.borg.sort.SortStrategy;

/**
 * Pancake sort煎饼排序
 * 将最大的元素进行煎饼翻转，移动到序列的最前面，然后再使用煎饼翻转操作将它移动到序列的最后面，
 * 这样最大的元素已经移动到正确的位置上了。
 * 依次类推，对第二大、第三大的元素都可以如此，先煎饼翻转到最前端，在煎饼翻转到它应该在的位置。
 * 我重复这个过程直到序列被排好序为止。每一步，我们需要花费两次煎饼翻转操作。
 */
public class PancakeSortStrategy implements SortStrategy {

    @Override
    public Integer[] execute(Integer[] array) {
        if(array == null || array.length == 0){
            return array;
        }
        int largest = array.length;
        int n = array.length;
        for(int i = 0;i < n;i++){
            int max = Integer.MIN_VALUE;
            int maxIndex = -1;
            for(int j = 0; j < largest;j++){
                if(array[j] > max){
                    max = array[j];
                    maxIndex = j;
                }
            }
            flip(array,maxIndex);
            flip(array,largest-1);
            largest--;
        }
        return array;
    }

    private void flip(Integer[] array,int k){
        int i = 0, j = k;
        while(i < j){
            int temp = array[i];
            array[i++] =array[j];
            array[j--] = temp;
        }
    }
}
