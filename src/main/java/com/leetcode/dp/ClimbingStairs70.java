package com.leetcode.dp;

import java.util.Map;

/**
 * You are climbing a staircase. It takes n steps to reach the top.
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 *
 * Example 1:
 * Input: n = 2
 * Output: 2
 * Explanation: There are two ways to climb to the top.
 * 1. 1 step + 1 step
 * 2. 2 steps
 *
 * Example 2:
 * Input: n = 3
 * Output: 3
 * Explanation: There are three ways to climb to the top.
 * 1. 1 step + 1 step + 1 step
 * 2. 1 step + 2 steps
 * 3. 2 steps + 1 step
 */
public class ClimbingStairs70 {

//    Runtime: 0 ms, faster than 100.00% of Java online submissions for Climbing Stairs.
//    Memory Usage: 37.7 MB, less than 30.69% of Java online submissions for Climbing Stairs.
    public int climbingStairs(int n){
        int[] dp = new int[n+1];
        dp[0]=0;
        if(n>0)
            dp[1]=1;
        if(n>1)
            dp[2]=2;
        for(int i=3; i<=n; i++){
            dp[i] = dp[i-2] + dp[i-1];
        }
        return dp[n];
    }

    public static void main(String[] args) {
        ClimbingStairs70 obj = new ClimbingStairs70();
        System.out.println(obj.climbingStairs(1));
    }
}
