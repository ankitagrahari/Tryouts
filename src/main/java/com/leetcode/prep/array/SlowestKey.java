package com.leetcode.prep.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SlowestKey {

    private static char slowestKey(int[] releaseTimes, String keysPressed){
        char result = 0;
        if(keysPressed.length() == releaseTimes.length){
            List<Character> indexForMaxTime = new ArrayList<>();
            int maxTime = releaseTimes[0];
            for(int i=1; i<keysPressed.length(); i++){
                int temp = releaseTimes[i]-releaseTimes[i-1];
                if(maxTime<=temp){
                    maxTime = temp;
                    indexForMaxTime.add(keysPressed.charAt(i));
                }
            }
            if(indexForMaxTime.size()>0)
                indexForMaxTime = indexForMaxTime.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            else
                return keysPressed.charAt(0);
            System.out.println(indexForMaxTime);
            return keysPressed.charAt(indexForMaxTime.get(0));
        }

        return result;
    }

    public static void main(String[] args) {

        System.out.println(SlowestKey.slowestKey(new int[]{1,2}, "ba"));
    }
}
