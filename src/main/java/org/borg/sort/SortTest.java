
package org.borg.sort;

import org.borg.data.Data;
import org.borg.sort.strategy.BubbleSortStrategy;

public class SortTest {

    static Integer[] array = Data.generateRandomArray(100);

    static Sorter sorter = new Sorter(new BubbleSortStrategy());

    public static Integer[] run(SortStrategy sortStrategy,Boolean isLog){
        Integer[] sourceArray = Data.copy(array);
        sorter.changeStrategy(sortStrategy);
        Integer[] array = sorter.sortRun(sourceArray);
        if(isLog){
            Data.log(array);
        }
        return array;
    }

    public static void qsSort() {
        run(SortFactory.getSort(SortType.QS_ASC),false);
        run(SortFactory.getSort(SortType.QS_DESC),false);
        run(SortFactory.getSort(SortType.QS_2WAY),false);
        run(SortFactory.getSort(SortType.QS_3WAY),false);
    }

    public static void insertSort() {
        run(SortFactory.getSort(SortType.INSERT),false);
        run(SortFactory.getSort(SortType.INSERT_BIN),true);
    }

    public static void cocktailSort() {
        run(SortFactory.getSort(SortType.COCKTAIL),true);
        run(SortFactory.getSort(SortType.GNOME),true);
    }

    public static void combSort() {
        run(SortFactory.getSort(SortType.COMB),true);
        run(SortFactory.getSort(SortType.CYCLE),true);
    }


    public static void main(String[] args) {
        Integer[] sourceArray = Data.copy(array);
        sorter.sortRun(sourceArray);

        //qsSort();
        //insertSort();
        //cocktailSort();
        combSort();
    }
}
