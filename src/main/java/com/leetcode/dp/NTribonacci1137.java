package com.leetcode.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * The Tribonacci sequence Tn is defined as follows:
 * T0 = 0, T1 = 1, T2 = 1, and Tn+3 = Tn + Tn+1 + Tn+2 for n >= 0.
 * Given n, return the value of Tn.
 *
 * Example 1:
 * Input: n = 4
 * Output: 4
 * Explanation:
 * T_3 = 0 + 1 + 1 = 2
 * T_4 = 1 + 1 + 2 = 4
 *
 * Example 2:
 * Input: n = 25
 * Output: 1389537
 */
public class NTribonacci1137 {

    //Using Memoization
//    Runtime: 1 ms, faster than 100.00% of Java online submissions for N-th Tribonacci Number.
//    Memory Usage: 37.9 MB, less than 6.45% of Java online submissions for N-th Tribonacci Number.
    public int fib(int n, Map<Integer, Integer> fibM){
        int result = 0;
        if(n<=1){
            return n;
        }
        if(n==2)
            return 1;

        if(fibM.containsKey(n))
            result = fibM.get(n);
        else
            result = fib(n-1, fibM) + fib(n-2, fibM) + fib(n-3, fibM);
        fibM.put(n, result);

        return result;
    }

    public static void main(String[] args) {
        NTribonacci1137 ovj = new NTribonacci1137();
        Map<Integer, Integer> map = new HashMap<>();
        System.out.println(ovj.fib(25, map));
    }
}
