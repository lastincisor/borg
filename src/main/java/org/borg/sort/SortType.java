package org.borg.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.borg.sort.strategy.*;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Getter
public enum SortType {

    /**
     * 冒泡排序
     * 鸡尾酒排序
     * 地精排序
     * **/
    BUBBLE(BubbleSortStrategy::new),
    COCKTAIL(CocktailSortStrategy::new),
    GNOME(GnomeSortStrategy::new),

    /**
     * 梳排序
     * **/
    COMB(CombSortStrategy::new),

    /**
     * 圈排序
     * **/
    CYCLE(CycleSortStrategy::new),

    /**
     * 煎饼排序
     * **/
    PANCAKE(PancakeSortStrategy::new),

    BUCKET(BucketSortStrategy::new),
    COUNTING(CountingSortStrategy::new),
    HEAP(HeapSortStrategy::new),
    /**
     * InsertionSortStrategy
     * 直接插入排序法
     * 折半插入排序
     * ***/
    INSERT(InsertionSortStrategy::new),
    INSERT_BIN(InsertionSortBinaryStrategy::new),
    MERGE(MergeSortStrategy::new),

    SELECTION(SelectionSortStrategy::new),
    SHELL(ShellSortStrategy::new),
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
    QS_DP(QuickSortDualPivotStrategy::new),
    QS_MK(QuickSortMultikeyStrategy::new),
    QS(QuickSortStrategy::new),
    QS_ASC(QuickSortAscStrategy::new),
    QS_DESC(QuickSortDescStrategy::new),
    QS_2WAY(QuickSort2WaysStrategy::new),
    QS_3WAY(QuickSort3WaysStrategy::new),

    MSD(RadixMsdSortStrategy::new),
    LSD(RadixSortStrategy::new);

    private final Supplier<SortStrategy> constructor;

}
