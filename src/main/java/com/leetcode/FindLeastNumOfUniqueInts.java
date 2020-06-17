package com.leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class FindLeastNumOfUniqueInts {

    /**
     * Find the unique integers which occurred least number of times.
     * @param arr
     * @param k
     */
    private static int findLeastNumOfUniqueInts(int[] arr, int k){
        Map<Integer, Integer> countMap = new HashMap<>();
        for(int i: arr){
            countMap.put(i, countMap.getOrDefault(i, 0)+1);
        }

        Comparator<Map.Entry<Integer, Integer>> sortByCount = Comparator.comparing(Map.Entry::getValue);
        Map<Integer, Integer> linkedCountMap = countMap.entrySet()
                .stream()
                .sorted(sortByCount)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new)
                );
        System.out.println(linkedCountMap);

        Iterator<Map.Entry<Integer, Integer>> itr = linkedCountMap.entrySet().iterator();
        Map.Entry<Integer, Integer> nextEle;
        while(itr.hasNext() && k>0){
            nextEle = itr.next();
            if(nextEle.getValue()>k){
                linkedCountMap.put(nextEle.getKey(), nextEle.getValue()-k);
                break;
            }
            k = k - nextEle.getValue();
            itr.remove();
        }
        System.out.println(linkedCountMap);
        System.out.println(linkedCountMap.size());

        return linkedCountMap.size();
    }

    public static void main(String[] args) {
//        int[] arr = {4, 3, 1, 1, 3, 3, 2}; int k = 3;     //2
        int[] arr = {5, 5, 4}; int k=1;           //1
//        int[] arr = {1, 1, 2, 2, 3, 3}; int k=3;    //1
//        int[] arr = {2, 1, 1, 3, 3, 3}; int k=3;  //1
        findLeastNumOfUniqueInts(arr, k);
    }
}
