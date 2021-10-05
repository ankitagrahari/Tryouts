package com.leetcode.prep;

import com.leetcode.LC;

import java.util.Arrays;
import java.util.List;

/**
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 *
 * Find the maximum profit you can achieve. You may complete as many transactions as you like
 * (i.e., buy one and sell one share of the stock multiple times) with the following restrictions:
 * After you sell your stock, you cannot buy stock on the next day (i.e., cooldown one day).
 *
 * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
 *
 * Example 1:
 * Input: prices = [1,2,3,0,2]
 * Output: 3
 * Explanation: transactions = [buy, sell, cooldown, buy, sell]
 *
 * Example 2:
 * Input: prices = [1]
 * Output: 0
 */
public class BuySellStock309 {

    public int[] getInputs() {
        return new int[]{1,2,3,0,2};
    }

    public int implementation(int[] input) {
        System.out.println("Input:"+ Arrays.toString(input));
        int buyIndex = 0;
        int sellIndex = 1;
        int maxProfit = 0;
        for(int i=0; i<=input.length; i++){
            int profit = 0;

            if(buyIndex>=input.length || sellIndex>=input.length)
                break;

            if(input[buyIndex] < input[sellIndex]) {
                profit = input[sellIndex] - input[buyIndex];
                buyIndex += 2;
                sellIndex = buyIndex+1;
            } else {
                buyIndex++;
                sellIndex++;
            }
            maxProfit += profit;
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        BuySellStock309 obj = new BuySellStock309();
        int[] in = obj.getInputs();
        System.out.println(obj.implementation(in));
    }
}
