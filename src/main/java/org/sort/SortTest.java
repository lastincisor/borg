package org.sort;

import org.sort.base.Data;
import org.sort.impl.*;

public class SortTest {

    public static void run(SortFacade sort, int[] array){
        //sort.showArray(array);
        Long start = System.currentTimeMillis();
        int[] sortArray = sort.sort(array);
        Long end = System.currentTimeMillis();
        System.out.println(sort.name + " usetime :"+(end-start) + ", array size :"+array.length);
        //sort.showArray(sortArray);
    }

    public static void main(String[] args) {
        int[] array = Data.array(100000);


        BubbleSort bubbleSort = new BubbleSort();
        run(bubbleSort,array);

        SelectionSort selectionSort = new SelectionSort();
        run(selectionSort,array);

        InsertionSort insertionSort = new InsertionSort();
        run(insertionSort,array);

        ShellSort shellSort = new ShellSort();
        run(shellSort,array);

        MergeSort mergeSort = new MergeSort();
        run(mergeSort,array);

        QuickSort quickSort = new QuickSort();
        run(quickSort,array);
    }


}
