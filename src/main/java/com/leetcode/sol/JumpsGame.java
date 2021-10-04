package com.leetcode.sol;

/**
 * Given an array of integers arr, you are initially positioned at the first index of the array.
 *
 * In one step you can jump from index i to index:
 *
 * i + 1 where: i + 1 < arr.length.
 * i - 1 where: i - 1 >= 0.
 * j where: arr[i] == arr[j] and i != j.
 * Return the minimum number of steps to reach the last index of the array.
 *
 * Notice that you can not jump outside of the array at any time.
 *
 * Example 1:
 *
 * Input: arr = [100,-23,-23,404,100,23,23,23,3,404]
 *              [1,2,3,4,5,6,7,8]
 *              [12, 1, 4, 1, 3, 12, 5 , 6, 7, 4]
 * Output: 3
 * Explanation: You need three jumps from index 0 --> 4 --> 3 --> 9. Note that index 9 is the last index of the array.
 */

/**
 * Logic:
 * Pick first node,
 * go to the next node of same value
 * if not present then goto next one
 *
 * Start a counter with forward approach:
 * Step 1:
 * If that element is present in the elements ahead:
 *      go to that index and increase the counter
 * Else, go to next element and increase the counter
 */
public class JumpsGame {
}
