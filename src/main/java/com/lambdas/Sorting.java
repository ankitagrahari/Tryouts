package com.lambdas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Sorting {

    public static void main(String[] args) {
        int[] a = {3, 4, 2, 15, 7, 2, 6, 34, 1, 23};

        ArrayList<Integer> st = (ArrayList<Integer>) Arrays.stream(a)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .collect(Collectors.toList());

        st.forEach(System.out::println);
    }
}
