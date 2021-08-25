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
        run(SortFactory.getSort(SortType.SELECTION));
        run(SortFactory.getSort(SortType.INSERTION));
        run(SortFactory.getSort(SortType.SHELL));
        run(SortFactory.getSort(SortType.MERGE));
        run(SortFactory.getSort(SortType.HEAP));
        run(SortFactory.getSort(SortType.COUNTING));
        run(SortFactory.getSort(SortType.BUCKET));
        run(SortFactory.getSort(SortType.RADIX));

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
        run(SortFactory.getSort(SortType.QS));
        run(SortFactory.getSort(SortType.QS_DP));
        run(SortFactory.getSort(SortType.QS_MK));

    }
}
