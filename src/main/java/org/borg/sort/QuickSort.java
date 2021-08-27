package org.borg.sort;

import lombok.extern.slf4j.Slf4j;
import org.borg.data.Data;

import java.util.Arrays;
import java.util.Random;

@Slf4j
public class QuickSort {

    private QuickSort() {
    }

    public static <E extends Comparable<E>> void sort(E[] arr) {

        Random random = new Random();
        sort(arr, 0, arr.length - 1,random);
    }

    private static <E extends Comparable<E>> void sort(E[] arr, int l, int r,Random random) {

        if (l >= r)
            return;

        int p = partition(arr, l, r,random);
        sort(arr, l, p - 1,random);
        sort(arr, p + 1, r,random);
    }

    private static <E extends Comparable<E>> int partition(E[] arr, int l, int r,Random random) {

        //生成[l,r]之间的随机索引
        int p = random.nextInt(r - l + 1) + l;
        swap(arr,l,p);

        // 使得arr[l+1,…,j]<v;arr[j+1,…,r]>v;
        int j = l;
        for (int i = l + 1; i <= r; i++) {
            if (arr[i].compareTo(arr[l]) < 0) {
                j++;
                swap(arr, i, j);
            }
        }
        swap(arr, l, j);
        return j;
    }

    public static <E extends Comparable<E>> void sort2ways(E[] arr) {

        Random random = new Random();
        sort2ways(arr, 0, arr.length - 1,random);
    }

    private static <E extends Comparable<E>> void sort2ways(E[] arr, int l, int r,Random random) {

        if (l >= r)
            return;

        int p = partition2ways(arr, l, r,random);
        sort2ways(arr, l, p - 1,random);
        sort2ways(arr, p + 1, r,random);
    }

    private static <E extends Comparable<E>> int partition2ways(E[] arr, int l, int r,Random random) {

        //生成[l,r]之间的随机索引
        int p = random.nextInt(r - l + 1) + l;
        swap(arr,l,p);

        // 使得arr[l+1,…,j]<=v;arr[j+1,…,r]>=v;
        int i = l+1;
        int j = r;

        while(i <= j){
            while (arr[i].compareTo(arr[l]) < 0)
                i++;
            while (arr[j].compareTo(arr[l]) > 0)
                j--;
            swap(arr,i,j);
            i++;
            j--;

        }
        swap(arr,l,j);

        return j;
    }


    public static <E extends Comparable<E>> void sort3ways(E[] arr) {

        Random random = new Random();
        sort3ways(arr, 0, arr.length - 1,random);
    }

    private static <E extends Comparable<E>> void sort3ways(E[] arr, int l, int r,Random random) {

        if (l >= r)
            return;

        // 生成[l,r]之间的随机索引
        int p = l + random.nextInt(r - l + 1);
        swap(arr,l,p);

        // arr[l+1 , lt] < v , arr[lt+1 , i-1] == v ,arr[i , r] > v
        int lt = l,i = l + 1, gt = r + 1; // 先将数组初始化为空

        while (i < gt){
            if (arr[i].compareTo(arr[l]) < 0){
                lt++; // 因为lt刚开始指向的是l，所以要先++
                swap(arr,lt,i);
                i++;
            }else if (arr[i].compareTo(arr[l]) > 0){
                gt--;
                swap(arr,i,gt);
                // i 不用++ 因为交换后的数还没有判断其大小
            }else {
                i++;
            }
        }
        swap(arr,l,lt);
        // arr[l , lt-1] < v , arr[lt , gt-1] == v ,arr[gt , r] > v

        sort3ways(arr,l,lt-1,random);
        sort3ways(arr,gt,r,random);

    }



    private static <E extends Comparable<E>> void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] arr = Data.generateRandomArray(1000000);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
        Integer[] arr3 = Arrays.copyOf(arr, arr.length);

        Long start = System.currentTimeMillis();
        sort(arr);
        Long end = System.currentTimeMillis();
        log.info("sort {} ", end - start);
        sort2ways(arr2);
        Long end2 = System.currentTimeMillis();
        log.info("sort2ways {}", end2 - end);
        sort3ways(arr3);
        Long end3 = System.currentTimeMillis();
        log.info("sort3ways {}", end3 - end2);
        //Data.log(arr);
        //Data.log(arr2);
        //Data.log(arr3);

    }

}
