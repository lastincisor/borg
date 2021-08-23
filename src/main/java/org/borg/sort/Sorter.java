package org.borg.sort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sorter {

    private SortStrategy strategy;

    public Sorter(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void changeStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public Integer[] sortRun(Integer[] array) {
        long start = System.currentTimeMillis();
        Integer[] sortArray = strategy.execute(array);
        long end = System.currentTimeMillis();
        log.info("Sorter {} run use time {}", strategy.getClass().getName(), end-start);
        return sortArray;
    }
}
