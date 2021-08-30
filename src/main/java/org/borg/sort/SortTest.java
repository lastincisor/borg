
package org.borg.sort;

import org.borg.data.Data;
import org.borg.sort.strategy.BubbleSortStrategy;

public class SortTest {

    static Integer[] array = Data.generateRandomArray(100000);

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

    public static void main(String[] args) {
        Integer[] sourceArray = Data.copy(array);
        sorter.sortRun(sourceArray);
        run(SortFactory.getSort(SortType.QS_ASC),false);
        run(SortFactory.getSort(SortType.QS_DESC),false);
        run(SortFactory.getSort(SortType.QS_2WAY),false);
        run(SortFactory.getSort(SortType.QS_3WAY),false);
    }
}
