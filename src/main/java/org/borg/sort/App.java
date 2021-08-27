package org.borg.sort;

import org.borg.data.Data;
import org.borg.sort.strategy.*;

public class App {

    static Integer[] array = Data.array(100);

    static Sorter sorter = new Sorter(new BubbleSortStrategy());

    public static Integer[] run(SortStrategy sortStrategy){
        Integer[] sourceArray = Data.copy(array);
        sorter.changeStrategy(sortStrategy);
        return sorter.sortRun(sourceArray);
    }

    public static void main(String[] args) {
        Integer[] sourceArray = Data.copy(array);
        sorter.sortRun(sourceArray);

//        SortType[] sortTypes = SortType.values();
//        for(SortType sortType : sortTypes){
//            run(SortFactory.getSort(sortType));
//        }
       // Data.log(run(SortFactory.getSort(SortType.QS)));
        Data.log(run(SortFactory.getSort(SortType.QS_ASC)));
        Data.log(run(SortFactory.getSort(SortType.QS_DESC)));

    }
}
