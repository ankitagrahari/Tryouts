package com.leetcode.sol;

import java.util.Arrays;

public class MinCostTicket {

    public static int minCostTicket(int[] days, int[] cost){

        int lastDayForTravel = days[days.length-1];
        boolean[] travelDays = new boolean[lastDayForTravel+1];
        int[] costOfEachDay = new int[lastDayForTravel+1];

        for(int day: days){
            travelDays[day] = true;
        }
        System.out.println(Arrays.toString(travelDays));

        for(int i=1; i<costOfEachDay.length; i++){
            if(!travelDays[i]){
                costOfEachDay[i] = costOfEachDay[i-1];
                continue;
            } else {
                int boughtSingleDay = costOfEachDay[i-1] + cost[0];
                int boughtSevenDays = costOfEachDay[Math.max(i-7, 0)] + cost[1];
                int boughtThirtyDays = costOfEachDay[Math.max(i-30, 0)] + cost[2];
                int temp = Math.min(boughtSingleDay, boughtSevenDays);
                costOfEachDay[i] = Math.min(temp, boughtThirtyDays);
            }
        }

        return costOfEachDay[lastDayForTravel];
    }

    public static void main(String[] args) {
        int[] days = {1,4,6,7,8,20};
        int[] costs = {2, 7, 15};
        System.out.println(MinCostTicket.minCostTicket(days, costs));
    }
}
