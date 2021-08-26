package org.borg.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.borg.sort.strategy.*;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Getter
public enum SortType {

    BUBBLE(BubbleSortStrategy::new),
    BUCKET(BucketSortStrategy::new),
    COUNTING(CountingSortStrategy::new),
    HEAP(HeapSortStrategy::new),
    INSERTION(InsertionSortStrategy::new),
    MERGE(MergeSortStrategy::new),
    RADIX(RadixSortStrategy::new),
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
    QS_ASC(QuickSortAscStrategy::new);

    private final Supplier<SortStrategy> constructor;

}
