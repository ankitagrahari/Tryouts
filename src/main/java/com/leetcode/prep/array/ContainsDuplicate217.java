package com.leetcode.prep.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct.
 *
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: true
 *
 * Example 2:
 * Input: nums = [1,2,3,4]
 * Output: false
 */
public class ContainsDuplicate217 {

    // Approach: Get the maximum element, and prepare a visited array of size of that max element
    // Once the num[i] is traversed, mark that index as 1 in visited array.
    // Drawback: Space: what if the input is only {1, 2000000000}.
    public boolean containsDuplicate(int[] nums){
        int max = 0;
        for(int i=0; i<nums.length; i++){
            if(max<nums[i])
                max = nums[i];
        }
        int[] visited = new int[max+1];
        for(int i=0; i<nums.length; i++){
            if(visited[nums[i]]==1)
                return true;
            else
                visited[nums[i]] = 1;
        }
        return false;
    }

    public boolean containsDuplicate1(int[] nums){
        Map<String, String> map = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            if (map.containsKey(String.valueOf(nums[i]))) {
//                map.put(String.valueOf(nums[i]), map.get(nums[i]));
                return true;
            } else {
                map.put(String.valueOf(nums[i]), "1");
            }
        }
        return false;
    }

//    Runtime: 18 ms, faster than 7.70% of Java online submissions for Contains Duplicate.
//    Memory Usage: 53 MB, less than 29.20% of Java online submissions for Contains Duplicate.
    public boolean containsDuplicate2(int[] nums){
        List<Integer> distNums = Arrays.stream(nums).boxed().distinct().collect(Collectors.toList());
        return distNums.size()==nums.length ? false : true;
    }

    public static void main(String[] args) {
        ContainsDuplicate217 obj = new ContainsDuplicate217();
        int[] in = {1,1,1,3,3,4,3,2,4,2};
        System.out.println(obj.containsDuplicate2(in));
    }
}
