package com.lambdas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class MinDistance {

    // 1, 2, 1, 2, 3, 5, 1, 4, 5, 7, 8, 5, 9   x=1, y=5
    int minDist(int a[], int n, int x, int y) {
        if(a.length!=n)
            return -1;

        int min = Integer.MAX_VALUE;
        int prev = -1;
        for(int i=0; i<n; i++){
            if(a[i]==x || a[i]==y){
                if(prev!=-1 && a[i]!=a[prev]) {
                    min = Math.min(min, i - prev);
                }
                prev = i;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        int[] a = {98,78,10,12,59,37,45,18,1,56,37,14,3,32,85,10,69,89,29,93,44,16,26,
                13,50,75,79,21,20,33,55,17,63,64,80,21,52,24,90,52,80,26,18,34,57,2,95,
                25,42,23,17,85,39,94,50,40,21,28,12,40,61,67,9,23,30,88,95,34,64,85,85,
                95,62,54,28,19,55,22,95,49,97,64,33};
        int x = 34, y = 56;
        MinDistance obj = new MinDistance();
        System.out.println(obj.minDist(a, a.length, x, y));
    }
}
