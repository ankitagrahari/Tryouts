package com.leetcode.prep.array.nesting;

import com.leetcode.LC;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * You are given an integer array nums of length n where nums is a permutation of the numbers in the range [0, n - 1].
 *
 * You should build a set s[k] = {nums[k], nums[nums[k]], nums[nums[nums[k]]], ... } subjected to the following rule:
 *
 * The first element in s[k] starts with the selection of the element nums[k] of index = k.
 * The next element in s[k] should be nums[nums[k]], and then nums[nums[nums[k]]], and so on.
 * We stop adding right before a duplicate element occurs in s[k].
 * Return the longest length of a set s[k].
 */
public class ArrayNestingWithOnlyArr{

    private int prepareList(int[] input, int k, boolean[] visitedArr){
        List<Integer> result = new ArrayList<>();
        while(!isPresent(result, input[k]) && k<input.length && !visitedArr[input[k]]){
            result.add(input[k]);
            visitedArr[input[k]] = true;
            k = input[k];
        }
        return result.size();
    }

    private boolean isPresent( List<Integer> arr, int ele){
        return arr.contains(ele);
    }

    public void arrayNesting(int[] nums) {
        int maxSize = 0;

        long start = System.currentTimeMillis();
        boolean[] visitedArr = new boolean[nums.length];
        for(int k=0; k<nums.length; k++) {
            if(!visitedArr[k]) {
                int kLength = prepareList(nums, k, visitedArr);
                maxSize = maxSize < kLength ? kLength : maxSize;
            }
        }
        System.out.println(maxSize);
        long end = System.currentTimeMillis();
        System.out.println("Time Taken:"+(end-start));
    }

    public static void main(String[] args) {
        ArrayNestingWithOnlyArr obj = new ArrayNestingWithOnlyArr();
//        int[] nums = {5,4,0,3,1,6,2};


//        try {
//            InputStream inputStream = new FileInputStream("src/main/java/com/leetcode/prep/input");
//            String[] ac = new String(inputStream.readAllBytes()).split(", ");
//            Integer[] nums = Arrays.stream(ac).map(a -> Integer.parseInt(a)).toArray(Integer[]::new);
//            System.out.println(nums.length);
//
//            obj.arrayNesting(Arrays.stream(nums).mapToInt(Integer::intValue).toArray());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
