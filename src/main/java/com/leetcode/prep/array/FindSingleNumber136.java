package com.leetcode.prep.array;

import java.util.*;

/**
 * Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.
 * You must implement a solution with a linear runtime complexity and use only constant extra space.
 *
 */
public class FindSingleNumber136 {

    /**
     * Runtime: 2 ms, faster than 57.19% of Java online submissions for Single Number.
     * Memory Usage: 47.7 MB, less than 21.75% of Java online submissions for Single Number.
     * @param nums
     * @return
     */
    public int singleNumber1(int[] nums){
        int res = nums[0];
        for(int i=1; i<nums.length; i++){
            res ^= nums[i];
        }
        return res;
    }

    /**
     * Runtime: 22 ms, faster than 11.44% of Java online submissions for Single Number.
     * Memory Usage: 51.7 MB, less than 9.93% of Java online submissions for Single Number.
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums){
        Map<Integer, Integer> memory = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            if (memory.containsKey(nums[i])) {
                memory.put(nums[i], memory.get(nums[i]) + 1);
            } else {
                memory.put(nums[i], 1);
            }
        }
        for(Map.Entry entry : memory.entrySet()){
            if((int)entry.getValue()==1){
                return (int)entry.getKey();
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        FindSingleNumber136 obj = new FindSingleNumber136();
        int[] nums = {2, 2, 1};
//        System.out.println(obj.singleNumber(nums));
        System.out.println(obj.singleNumber1(nums));
    }
}
