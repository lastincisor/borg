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
    QS_DP(QuickSortDualPivotStrategy::new),
    QS_MK(QuickSortMultikeyStrategy::new),
    QS(QuickSortStrategy::new),
    RADIX(RadixSortStrategy::new),
    SELECTION(SelectionSortStrategy::new),
    SHELL(ShellSortStrategy::new);

    private final Supplier<SortStrategy> constructor;
}
