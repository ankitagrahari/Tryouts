package com.leetcode.prep.array;

public class FindMissingNumFromArr448 {

//    Runtime: 7 ms, faster than 56.27% of Java online submissions for Find All Numbers Disappeared in an Array.
//    Memory Usage: 64 MB, less than 16.49% of Java online submissions for Find All Numbers Disappeared in an Array.
    public int missingNumber(int[] nums){
        int max = nums.length;
        int[] visited = new int[nums.length+1];
        for(int i=0; i<max; i++){
            visited[nums[i]] = 1;
        }

        for(int i=1; i<visited.length; i++){
            if(visited[i]!=1)
                return i;
        }
        return 0;
    }

    public static void main(String[] args) {
        FindMissingNumFromArr448 obj = new FindMissingNumFromArr448();
        int[] nums = {1,2,5};
        System.out.println(obj.missingNumber(nums));
    }
}
