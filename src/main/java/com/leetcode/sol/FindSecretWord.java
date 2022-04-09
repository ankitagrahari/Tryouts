package com.leetcode.sol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

interface Master{
    int guess(String word);
}

class Solution implements Master{

    String secret;
    String[] wordList;

    public void findSecretWord(String[] wordlist, Master master) {
        this.wordList = wordlist;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
            master.guess(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int guess(String word) {
        if(!Arrays.asList(this.wordList).contains(word)){
            return -1;
        }

        for (String w : wordList){

        }
        return 0;
    }
}

public class FindSecretWord{

    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] wordList = {"acckzz","ccbazz","eiowzz","abcczz"};
        String secret = "acckzz";
        Master master = new Solution();


        solution.findSecretWord(wordList, master);
    }
}
