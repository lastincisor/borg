package org.borg.sort;

@FunctionalInterface
public interface SortStrategy {

    public Integer[] execute(Integer[] array);
}
