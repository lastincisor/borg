package org.sort.impl;

import org.sort.base.Sort;

import java.util.Arrays;

public abstract class SortFacade implements Sort {

    public String name;

    public void showArray(int[] array){
        System.out.println(Arrays.toString(array));
    }
}
