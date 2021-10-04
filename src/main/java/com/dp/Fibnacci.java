package com.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *           Index: 0 1 2 3 4 5 6 7  8  9  10 11 12
 * Fibnacci series: 0 1 1 2 3 5 8 13 21 34 55 89 144
 */
public class Fibnacci {

    public long naiveFibnacci(int limit){
        if(limit == 0)
            return 0L;
        else if(limit==1)
            return 1L;
        else
            return naiveFibnacci(limit-1) + naiveFibnacci(limit-2);
    }

    /**
     *     With this DP approach, we are keeping track of all the previous computation done for a number
     *     and using this cache for computation of other numbers
     *
     *     Advantage: Don't have to calculate the same computation mutliple times
     *     Disadvantage: With each growping series the space(MAP) it occupies will also grow
     */
    public Long dpFibnacci(int limit, Map<Integer, Long> map){

        long result =0;
        if(limit==0) return 0L;
        if(limit==1) return 1L;

        if(null==map) map = new HashMap<>();
        if(map.containsKey(limit)){
            return map.get(limit);
        } else {
            result = dpFibnacci(limit - 1, map) + dpFibnacci(limit - 2, map);
            map.put(limit, result);
        }
        return result;
    }

    public long dpFibnacciBottomUp(int limit){
        long a=0, b=1, temp = 0;
        for(int i=2; i<=limit; i++){
            temp = b;
            b = b+a;
            a = temp;
        }
        return b;
    }

    public void test(){
        List<String> sb = new ArrayList<>();
        sb.add("abc"); sb.add("bcd");
        System.out.println(String.join(",", sb.toArray(new String[0])));
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        Fibnacci obj = new Fibnacci();
//        int findFib = 50;
//        long start = System.currentTimeMillis();
//        System.out.println("Fibnacci of "+findFib+ " by recursion approach is "+ obj.naiveFibnacci(findFib));
//        long end = System.currentTimeMillis();
//        System.out.println("Time taken in seconds:"+(end-start)/1000);
//
//        start = System.currentTimeMillis();
//        System.out.println("Fibnacci of "+findFib+ " by Dynamic Programming(Memoization) is "+ obj.dpFibnacci(findFib, null));
//        end = System.currentTimeMillis();
//        System.out.println("Time taken in seconds:"+(end-start)/1000);
//
//        start = System.currentTimeMillis();
//        System.out.println("Fibnacci of "+findFib+ " by Dynamic Programming(Bottom-up approach) is "+ obj.dpFibnacciBottomUp(findFib));
//        end = System.currentTimeMillis();
//        System.out.println("Time taken in seconds:"+(end-start)/1000);

        obj.test();

    }
}
