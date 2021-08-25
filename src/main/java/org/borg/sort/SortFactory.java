package org.borg.sort;

public class SortFactory {

    public static SortStrategy getSort(SortType type) {
        return type.getConstructor().get();
    }
}
