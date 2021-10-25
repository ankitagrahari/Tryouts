package com.leetcode.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * The Fibonacci numbers, commonly denoted F(n) form a sequence, called the Fibonacci sequence, 
 * such that each number is the sum of the two preceding ones, starting from 0 and 1. That is,
 *
 * F(0) = 0, F(1) = 1
 * F(n) = F(n - 1) + F(n - 2), for n > 1.
 * Given n, calculate F(n).
 *
 * Example 1:
 * Input: n = 2
 * Output: 1
 * Explanation: F(2) = F(1) + F(0) = 1 + 0 = 1.
 */
public class Fibonacci509 {

    //Using Memoization
    public int fib(int n, Map<Integer, Integer> fib){
        int result = 0;
        if(n<=1){
            return n;
        }
        if(fib.containsKey(n))
            result = fib.get(n);
        else
            result = fib(n-1, fib) + fib(n-2, fib);
            fib.put(n, result);

        return result;
    }

    public static void main(String[] args) {
        Fibonacci509 ovj = new Fibonacci509();
        Map<Integer, Integer> map = new HashMap<>();
        System.out.println(ovj.fib(6, map));
    }
}
