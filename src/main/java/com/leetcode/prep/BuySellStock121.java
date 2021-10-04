package com.leetcode.prep;

import java.util.Arrays;

/**
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day
 * in the future to sell that stock.
 *
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 *
 * Example 1:
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 * Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
 *
 * Example 2:
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transactions are done and the max profit = 0.
 */
public class BuySellStock121 {

    public int[] getInputs() {
        return new int[]{1,2,4,2,5,7,2,4,9,0,9};
    }

    //Runtime: 3 ms, faster than 34.63% of Java online submissions for Best Time to Buy and Sell Stock.
    //Memory Usage: 105.4 MB, less than 19.96% of Java online submissions for Best Time to Buy and Sell Stock.
    public int implementation(int[] input) {
        System.out.println("Input:"+ Arrays.toString(input));
        int buyIndex = 0;
        int sellIndex = 1;
        int profit = 0;
        for(int i=0; i<=input.length; i++){
            if(buyIndex>=input.length || buyIndex==sellIndex || sellIndex>=input.length)
                break;

            if(input[buyIndex] < input[sellIndex]){
                if(profit < (input[sellIndex]-input[buyIndex])){
                    profit = input[sellIndex]-input[buyIndex];
                }
            } else {
                buyIndex = sellIndex;
            }
            sellIndex++;
        }
        return profit;
    }

    public static void main(String[] args) {
        BuySellStock121 obj = new BuySellStock121();
        int[] in = obj.getInputs();
        System.out.println(obj.implementation(in));
    }
}
