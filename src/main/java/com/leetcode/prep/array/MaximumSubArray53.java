package com.leetcode.prep.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an integer array nums, find the contiguous subarray (containing at least one number)
 * which has the largest sum and return its sum.
 * A subarray is a contiguous part of an array.
 *
 * Example 1:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: [4,-1,2,1] has the largest sum = 6.
 *
 * Example 2:
 * Input: nums = [1]
 * Output: 1
 */
public class MaximumSubArray53 {

    //Kadance Algorithm: To find max sum, hold the subarray if the sum is greater than 0.
    public int maxSubarraySum(int[] arr){
        List<Integer> dp = new ArrayList<>();
        int globalSum = arr[0];
        dp.add(arr[0]);
        for(int i=1; i<arr.length; i++){
            dp.add(i, Math.max(dp.get(i-1)+arr[i], arr[i]));
            if(dp.get(i)>globalSum)
                globalSum = dp.get(i);
        }

        return globalSum;
    }

    public static void main(String[] args) {
//        int[] a = {-2,1,-3,4,-1,2,1,-5,4};
        int[] a = {-2,0,-1};
        MaximumSubArray53 obj = new MaximumSubArray53();
        System.out.println(obj.maxSubarraySum(a));
    }
}
