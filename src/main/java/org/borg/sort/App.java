package org.borg.sort;

import org.borg.data.Data;
import org.borg.sort.strategy.*;

public class App {



    public static void main(String[] args) {

        Integer[] array = Data.array(100000);
        Integer[] sourceArray = Data.copy(array);
        Sorter sorter = new Sorter(new BubbleSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = Data.copy(array);
        sorter.changeStrategy(new SelectionSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = Data.copy(array);
        sorter.changeStrategy(new InsertionSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = Data.copy(array);
        sorter.changeStrategy(new ShellSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = Data.copy(array);
        sorter.changeStrategy(new MergeSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = Data.copy(array);
        sorter.changeStrategy(new QuickSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = Data.copy(array);
        sorter.changeStrategy(new QuickSortDualPivotStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = Data.copy(array);
        sorter.changeStrategy(new HeapSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = Data.copy(array);
        sorter.changeStrategy(new CountingSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = Data.copy(array);
        sorter.changeStrategy(new BucketSortStrategy());
        sorter.sortRun(sourceArray);

        sourceArray = Data.copy(array);
        sorter.changeStrategy(new RadixSortStrategy());
        sorter.sortRun(sourceArray);
    }
}
