package com.leetcode.dp;


/**
 * You are given an integer array nums. You are initially positioned at the array's first index, and each element
 * in the array represents your maximum jump length at that position.
 * Return true if you can reach the last index, or false otherwise.
 *
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: true
 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Example 2:
 * Input: nums = [3,2,1,0,4]
 * Output: false
 * Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.
 */
public class JumpGame55 {

    public boolean canJump(int[] nums){

        int nextIndex = 0;
        int startIndex = 0;
        int count = 0;
        int firstZeroEncountered = 0;
        boolean zeroesEncounteredAtLast = false;
        //If the elements from the last index is all zero, then pick index the first zero encountered
        for(int i=nums.length-1; i>0; i--){
            if(nums[i]==0){
                if (nums[i]==nums[i-1]){
                    firstZeroEncountered = i-1;
                    zeroesEncounteredAtLast = true;
                }
            }
            break;
        }
        System.out.println(firstZeroEncountered);
        while(nextIndex<nums.length-1 && count<nums.length){
            nextIndex = startIndex + nums[startIndex];
            startIndex = nextIndex;
            count++;
        }
        System.out.println(nextIndex);
        if(nextIndex>=nums.length-1 ||  (nextIndex==firstZeroEncountered && zeroesEncounteredAtLast))
            return true;
        return false;
    }

    public boolean canJumpCopied(int[] nums) {
        int n=0;
        for (int i = nums.length - 2; i >= 0; i--) {
            if(nums[i]>n) {
                n=0;
                continue;
            }
            else {
                if(i==0)
                    return false;
                else n++;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        JumpGame55 obj = new JumpGame55();
        int[] nums = {2, 1, 0, 0};
        System.out.println(obj.canJumpCopied(nums));
    }
}
