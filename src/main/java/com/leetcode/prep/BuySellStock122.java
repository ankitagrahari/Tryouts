package com.leetcode.prep;

import java.util.Arrays;

/**
 * You are given an integer array prices where prices[i] is the price of a given stock on the ith day.
 *
 * On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time. However, you can buy it then immediately sell it on the same day.
 *
 * Find and return the maximum profit you can achieve.
 *
 *
 *
 * Example 1:
 *
 * Input: prices = [7,1,5,3,6,4]
 * Output: 7
 * Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
 * Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
 * Total profit is 4 + 3 = 7.
 * Example 2:
 *
 * Input: prices = [1,2,3,4,5]
 * Output: 4
 * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
 * Total profit is 4.
 */
public class BuySellStock122 {

    public int[] getInputs() {
        return new int[]{1,2,3,4,5};
    }

    //Runtime: 2 ms, faster than 18.27% of Java online submissions for Best Time to Buy and Sell Stock II.
    //Memory Usage: 41.6 MB, less than 8.47% of Java online submissions for Best Time to Buy and Sell Stock II.
    public int implementation(int[] input) {
        System.out.println("Input:"+ Arrays.toString(input));
        int buyIndex = 0;
        int sellIndex = 1;
        int maxProfit = 0;
        for(int i=0; i<=input.length; i++){
            int profit = 0;

            if(buyIndex>=input.length || buyIndex==sellIndex || sellIndex>=input.length)
                break;

            if(input[buyIndex] < input[sellIndex]) {
                profit = input[sellIndex] - input[buyIndex];
                buyIndex = sellIndex;
            } else {
                buyIndex++;
            }
            sellIndex++;
            maxProfit += profit;
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        BuySellStock122 obj = new BuySellStock122();
        int[] in = obj.getInputs();
        System.out.println(obj.implementation(in));
    }
}
