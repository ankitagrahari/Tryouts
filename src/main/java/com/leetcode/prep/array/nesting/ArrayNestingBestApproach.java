package com.leetcode.prep.array.nesting;

public class ArrayNestingBestApproach {

    /**
     * On Condition that array includes only values from 0 to 20000
     * It will be o(1) in space and o(n) in time
     * @param nums
     * @return
     */
    private int arrayNesting(int[] nums){
        int result = 0;
        for(int i=0; i<nums.length; i++){
            if(nums[i]!=Integer.MAX_VALUE){
                int start = nums[i];
                int count = 0;
                while(nums[start]!=Integer.MAX_VALUE){
                    int temp = start;
                    start = nums[start];
                    count++;
                    nums[temp] = Integer.MAX_VALUE;
                }
                result = Math.max(result, count);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayNestingBestApproach obj = new ArrayNestingBestApproach();
        System.out.println(obj.arrayNesting(new int[]{0, 2, 3, 4, 5, 1}));
    }
}
