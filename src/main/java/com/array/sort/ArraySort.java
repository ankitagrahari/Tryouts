package com.array.sort;

import java.util.Arrays;
import java.util.List;

public class ArraySort {

    public static void main(String[] args) {

        // Uses Dual Pivot Quicksort method,
        // looks for some threshold which is 47, before this threshold, it will use insertion sort
        // else it will find five points which will decide the pivot points
        // and then run insertion sort on these.
        int[] arr = {1, 4, 2, 8, 3, 5, 1, 0, 6, 3, 1};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

//        List<Integer> list = Arrays.asList(1, 4, 2, 8, 3, 5, 1, 0, 6, 3, 1);
//        list.stream().sorted().forEach(System.out::println);
    }
}
