package org.sort;

import org.sort.base.Data;
import org.sort.impl.*;

import java.util.Arrays;

public class SortTest {

    public static void run(SortFacade sort, Integer[] array){
        //sort.showArray(array);
        Long start = System.currentTimeMillis();
        Integer[] sortArray = sort.sort(array);
        Long end = System.currentTimeMillis();
        System.out.println(sort.name + " usetime :"+(end-start) + ", array size :"+array.length);
        //sort.showArray(sortArray);
    }

    private static Integer[] sourceArray(Integer[] array){
        Integer[] targetArray = new Integer[array.length];
        System.arraycopy(array,0,targetArray, 0,array.length);
        return targetArray;
    }

    public static void main(String[] args) {
        Integer[] array = Data.array(100000);

        Integer[] sourceArray = sourceArray(array);

        BubbleSort bubbleSort = new BubbleSort();
        run(bubbleSort,sourceArray);


        sourceArray = sourceArray(array);
        SelectionSort selectionSort = new SelectionSort();
        run(selectionSort,sourceArray);

        sourceArray = sourceArray(array);
        InsertionSort insertionSort = new InsertionSort();
        run(insertionSort,sourceArray);

        sourceArray = sourceArray(array);
        ShellSort shellSort = new ShellSort();
        run(shellSort,sourceArray);

        sourceArray = sourceArray(array);
        MergeSort mergeSort = new MergeSort();
        run(mergeSort,sourceArray);

        sourceArray = sourceArray(array);
        QuickSort quickSort = new QuickSort();
        run(quickSort,sourceArray);

        sourceArray = sourceArray(array);
        QuickSortDualPivot quickSortDualPivot = new QuickSortDualPivot();
        run(quickSortDualPivot,sourceArray);

        sourceArray = sourceArray(array);
        HeapSort heapSort = new HeapSort();
        run(heapSort,sourceArray);

        sourceArray = sourceArray(array);
        CountingSort countingSort = new CountingSort();
        run(countingSort,sourceArray);

        sourceArray = sourceArray(array);
        BucketSort bucketSort = new BucketSort();
        run(bucketSort,sourceArray);

        sourceArray = sourceArray(array);
        RadixSort radixSort = new RadixSort();
        run(radixSort,sourceArray);


        Arrays.sort(sourceArray);
    }


}
