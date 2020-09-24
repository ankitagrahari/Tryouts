package com.leetcode;

import java.util.Arrays;

public class ShortestSubArrayKLength {

    public int getIndex(int[] a, int k){
        Arrays.sort(a);
        for(int i=0; i<a.length; i++){
            if(a[i]>k){
                return i;
            }
        }
        return -1;
    }

    public int shortestSubarray(int[] A, int K) {
        int indexTo = getIndex(A, K);

        int groupCount = K-1;
        for (int j=0; j<indexTo; j++){
            int temp = groupCount;
            while(groupCount>0){
                
            }
        }

        return 0;
    }

    public static void main(String[] args) {

    }
}
