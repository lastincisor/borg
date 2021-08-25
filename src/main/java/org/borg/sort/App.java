package org.borg.sort;

import org.borg.data.Data;
import org.borg.sort.strategy.*;

public class App {

    static Integer[] array = Data.array(100000);

    static Sorter sorter = new Sorter(new BubbleSortStrategy());

    public static void run(SortStrategy sortStrategy){
        Integer[] sourceArray = Data.copy(array);
        sorter.changeStrategy(sortStrategy);
        sorter.sortRun(sourceArray);
    }

    public static void main(String[] args) {
        Integer[] sourceArray = Data.copy(array);
        sorter.sortRun(sourceArray);

        run(new SelectionSortStrategy());
        run(new InsertionSortStrategy());
        run(new ShellSortStrategy());
        run(new MergeSortStrategy());
        run(new HeapSortStrategy());
        run(new CountingSortStrategy());
        run(new BucketSortStrategy());
        run(new RadixSortStrategy());

        /**
         * QuickSortStrategy
         *
         * 快速排序(逆序)、
         * 快速排序(有序)、
         * 快速排序(三分法逆序)、
         * 快速排序(三分法有序)、
         * 快速排序(双轴)、
         *
         * ***/
        run(new QuickSortStrategy());
        run(new QuickSortDualPivotStrategy());
        run(new QuickSortMultikeyStrategy());

    }
}
