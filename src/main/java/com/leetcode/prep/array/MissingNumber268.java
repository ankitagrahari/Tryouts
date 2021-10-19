package com.leetcode.prep.array;

/**
 * Given an array nums containing n distinct numbers in the range [0, n],
 * return the only number in the range that is missing from the array
 */
public class MissingNumber268 {

    /**
     * Runtime: 1 ms, faster than 63.55% of Java online submissions for Missing Number.
     * Memory Usage: 48.7 MB, less than 5.86% of Java online submissions for Missing Number.
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums){
        int max = nums.length;
        int[] visited = new int[nums.length+1];
        for(int i=0; i<max; i++){
            visited[nums[i]] = 1;
        }

        for(int i=0; i<visited.length; i++){
            if(visited[i]!=1)
                return i;
        }
        return 0;
    }

    public static void main(String[] args) {
        MissingNumber268 obj = new MissingNumber268();
        int[] nums = {0};
        System.out.println(obj.missingNumber(nums));
    }
}
