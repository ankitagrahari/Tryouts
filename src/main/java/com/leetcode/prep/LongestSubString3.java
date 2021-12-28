package com.leetcode.prep;

import java.util.*;

/**
 * Given a string s, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * Constraints:
 * 0 <= s.length <= 5 * 104
 * s consists of English letters, digits, symbols and spaces.
 */
public class LongestSubString3 {

    public int lengthOfFirstLongestSubstring(String s) {
        List<Integer> longestLength = new ArrayList<>();
        int[] ascii = new int[256];
        int start = 0, end = 0;
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(ascii[c]==0){
                ascii[c]++;
                end++;
            } else {
                longestLength.add(end-start);
                start++;
                i = start;
                end = i;
                i--;
                ascii = new int[256];
            }
        }

        longestLength.add(end-start);
        System.out.println(longestLength);
        longestLength.sort(Comparator.reverseOrder());
        return longestLength.size()>0?longestLength.get(0):0;
    }

    public int bestSolution(String s){
        int len = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for(int i=0, j=0; i<len; i++){
            if(map.containsKey(s.charAt(i)))
                j = Math.max(map.get(s.charAt(i)), j);
            ans = Math.max(i-j+1, ans);
            map.put(s.charAt(i), i+1);
        }
        return ans;
    }

    public static void main(String[] args) {
        String input = "pwwkewtrewiuy";
        LongestSubString3 obj = new LongestSubString3();
//        System.out.println(obj.lengthOfFirstLongestSubstring(input));
        System.out.println(obj.bestSolution(input));
    }

    // wqwqabcadd
}
