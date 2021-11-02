package com.leetcode.dp;

import java.util.Arrays;

/**
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed.
 * All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one.
 * Meanwhile, adjacent houses have a security system connected, and it will automatically contact the police if two
 * adjacent houses were broken into on the same night.
 *
 * Given an integer array nums representing the amount of money of each house,
 * return the maximum amount of money you can rob tonight without alerting the police.
 *
 * Example 1:
 * Input: nums = [2,3,2]
 * Output: 3
 * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2), because they are adjacent houses.
 *
 * Example 2:
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 *
 * Example 3:
 * Input: nums = [1,2,3]
 * Output: 3
 */
public class HouseRobber213 {

    //Runtime: 0 ms, faster than 100.00% of Java online submissions for House Robber II.
    //Memory Usage: 36.7 MB, less than 52.62% of Java online submissions for House Robber II.
    public int robCases(int[] nums){

        if(nums.length==1)
            return nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for(int i=2; i<nums.length; i++){
            dp[i] = Math.max(dp[i-1], nums[i]+dp[i-2]);
        }
        System.out.println(Arrays.toString(dp));
        return dp[dp.length-1];
    }

    public int rob(int[] nums){
        if(nums.length==1)
            return nums[0];
        int[] includeFirst = new int[nums.length-1];
        int[] includeLast = new int[nums.length-1];
        for(int i=0; i<nums.length-1; i++){
            includeFirst[i] = nums[i];
        }
        System.out.println("First:"+ Arrays.toString(includeFirst));
        for(int i=1; i<nums.length; i++){
            includeLast[i-1] = nums[i];
        }
        System.out.println("Second:"+ Arrays.toString(includeLast));
        int withFirst = robCases(includeFirst);
        int withLast = robCases(includeLast);
        System.out.println(withFirst+"--"+withLast);
        return Math.max(withFirst, withLast);
    }

    public static void main(String[] args) {
        HouseRobber213 obj = new HouseRobber213();
        int[] nums = {1};
        System.out.println(Math.max(0, 0));
        System.out.println(obj.rob(nums));
    }
}
