package com.leetcode.dp;

import com.leetcode.LC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a string s, return the longest palindromic substring in s.
 *
 * Example 1:
 * Input: s = "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 *
 * Example 2:
 * Input: s = "cbbd"
 * Output: "bb"
 *
 * Example 3:
 * Input: s = "a"
 * Output: "a"
 *
 * Example 4:
 * Input: s = "ac"
 * Output: "a"
 *
 * Constraints:
 * 1 <= s.length <= 1000
 * s consist of only digits and English letters (lower-case and/or upper-case),
 */
public class LongestPalindromicSubString implements LC {

    public List<String> addLPToList(String s, int start, int end, List<String> list){
        System.out.println("start:"+start+"--end:"+end);
        if(start==end || start<0 || end>s.length())
            return list;
        if(start<end) {
            if (s.charAt(start) == s.charAt(end)) {
                list.add(s.substring(start, end >= s.length() ? s.length() : end));
                this.addLPToList(s, --start, ++end, list);
                System.out.println("IF start:"+start+"--end:"+end);
            } else {
                System.out.println("ELSE start:"+start+"--end:"+end);
                addLPToList(s, start <= 0 ? 0 : start - 1, start + 1, list);
                addLPToList(s, end - 1, end >= s.length() ? s.length() : end + 1, list);
            }
        }
        return list;
    }

    public String longestPalindrome(String s){
        String subStr = null;
        List<String> strList = new ArrayList<>();
        int start, end = 0;
        if(s.length()%2==0){
            start = (s.length()/2) -1;
            end = (s.length()/2);
        } else {
            start = Math.floorDiv(s.length(), 2);
            end = Math.floorDiv(-s.length(), 2) * -1;
        }
        System.out.println("Initial-start:"+start+"--end:"+end);
        strList = this.addLPToList(s, start, end, strList);
        System.out.println(strList);
        return subStr;
    }

    @Override
    public List<String> getInputs() {
        List<String> inputs = new ArrayList<>();
        inputs.add("aabba");
        inputs.add("abbaaaa");
        return inputs;
    }

    @Override
    public void implementation(List input) {
        input.stream().forEach(ele -> this.longestPalindrome((String) ele));
    }

    public static void main(String[] args) {
        LongestPalindromicSubString obj = new LongestPalindromicSubString();
        List<String> inputs = obj.getInputs();
        obj.implementation(inputs);
    }
}
