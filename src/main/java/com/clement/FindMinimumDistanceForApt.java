package com.clement;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Input
 * Requirements for you to have an apartment in the following blocks: Array of Integers
 * Blocks: List of amenities in this block. Array of Requirement Objects with boolean attributes
 * e.g.
 *  Req: [spa, school, gym]
 * [
 *  {spa:true, school:false, gym:false},
 *  {spa:true, school:false, gym:true},
 *  {spa:false, school:false, gym:true},
 *  {spa:true, school:true, gym:false},
 *  {spa:true, school:false, gym:true},
 *  {spa:true, school:true, gym:false}
 * ]
 *
 * In which block you will take the apartment such that all requirements are at minimum distance
 * For 4th and 5th are eligible as spa, school and gym are all close by.
 * In 4th Block, you have spa, school and gym is just one block away at either 3rd block or 5th block
 * In 5th Block, you have spa, gym and school is just one block away at either 4th block or 6th block
 */
public class FindMinimumDistanceForApt {

    public static int computeClosestToZero(int[] ts) {
        // Write your code here
        // To debug: System.err.println("Debug messages...");
        System.err.println("ts-:"+ Arrays.toString(ts));
        int minNeg = ts[0], minPos = ts[0];
        if(ts.length>0){
            for(int i=1; i<ts.length; i++){
                if(ts[i]>=0 && minPos>=ts[i]){
                    minPos = ts[i];
                }
                if(ts[i]<0 && minNeg<=ts[i]){
                    minNeg = ts[i];
                }
            }
            System.err.println("minPos:"+ minPos);
            System.err.println("minNeg:"+ minNeg);
            if(minPos<=minNeg)
                return minPos;
            else
                return minNeg;
        } else {
            return 0;
        }
    }

    public static int computeDayGains(int nbSeats, int[] payingGuests, int[] guestMovements) {
        // Write your code here
        // To debug: System.err.println("Debug messages...");
        System.out.println(nbSeats);
        System.out.println(Arrays.toString(payingGuests));
        System.out.println(Arrays.toString(guestMovements));

        int gains = 0;
        List<String> arrivedGuests = new ArrayList<>();
        for(int i=0; i<guestMovements.length; i++){
            if(arrivedGuests.contains(guestMovements[i])){
                arrivedGuests.remove(guestMovements[i]);
            } else {
                arrivedGuests.add(String.valueOf(guestMovements[i]));
                gains += payingGuests[guestMovements[i]];
            }
        }
        System.out.println(gains);
        return gains;
    }

    public static void main(String[] args) {
        int[] arr = {25, 10, 5, 30, 15};
        int[] arr1 = {4, 4, 3, 2, 3, 0, 0, 2};
        FindMinimumDistanceForApt.computeDayGains(100, arr, arr1);

//        int[] arr = {9, -2, -8, 4, 5};
//        System.out.println(FindMinimumDistanceForApt.computeClosestToZero(arr));
//        String str = "1:23am-1:08am";
//        String[] times = str.split("-");
//        int year = 2021; int month = 12; int day = 17;
//        int hour = Integer.parseInt(times[0].split(":")[0]);
//        int min = Integer.parseInt(times[0].split(":")[1].substring(0, 2));
//        LocalDateTime from = LocalDateTime.of(year, month, day, hour, min);
//
//        int hourTo = Integer.parseInt(times[1].split(":")[0]);
//        int minTo = Integer.parseInt(times[1].split(":")[1].substring(0, 2));
//        LocalDateTime to = LocalDateTime.of(year, month, day, hourTo, minTo);
//
//        Duration duration = Duration.between(from, to);
//        System.out.println(duration.toMinutes());

    }

}
