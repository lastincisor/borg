package org.borg.sort;

import org.borg.sort.strategy.*;
import org.sort.base.Data;

public class App {

    private static Integer[] sourceArray(Integer[] array){
        Integer[] targetArray = new Integer[array.length];
        System.arraycopy(array,0,targetArray, 0,array.length);
        return targetArray;
    }

    public static void main(String[] args) {

        Integer[] array = Data.array(100000);
        Integer[] sourceArray = sourceArray(array);
        Sorter sorter = new Sorter(new BubbleSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = sourceArray(array);
        sorter.changeStrategy(new SelectionSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = sourceArray(array);
        sorter.changeStrategy(new InsertionSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = sourceArray(array);
        sorter.changeStrategy(new ShellSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = sourceArray(array);
        sorter.changeStrategy(new MergeSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = sourceArray(array);
        sorter.changeStrategy(new QuickSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = sourceArray(array);
        sorter.changeStrategy(new QuickSortDualPivotStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = sourceArray(array);
        sorter.changeStrategy(new HeapSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = sourceArray(array);
        sorter.changeStrategy(new CountingSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = sourceArray(array);
        sorter.changeStrategy(new BucketSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = sourceArray(array);
        sorter.changeStrategy(new RadixSortStrategy());
        sorter.sortRun(sourceArray);
    }
}
