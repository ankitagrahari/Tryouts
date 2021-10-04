package com.leetcode.prep;

import com.leetcode.LC;

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
        return new int[]{};
    }

    public void implementation(int[] input) {
        int[] bsp = new int[3];     //0: Buy, 1: Sell, 2: Profit(Sell-Buy)
        for(int i=0; i<input.length; i++){
            bsp[0] = input[0];
            bsp[1] = input[1];
            bsp[2] = bsp[2] + bsp[1] - bsp[0];
        }
    }
}
