package com.leetcode.prep;

import java.util.Arrays;

/**
 * Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
 * The overall run time complexity should be O(log (m+n)).
 *
 * Example 1:
 * Input: nums1 = [1,3], nums2 = [2]
 * Output: 2.00000
 * Explanation: merged array = [1,2,3] and median is 2.
 *
 * Example 2:
 * Input: nums1 = [1,2], nums2 = [3,4]
 * Output: 2.50000
 * Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
 *
 *
 * Example 3: 1.5 +
 *  * Input: nums1 = [1,2], nums2 = [3,4,5]
 *  * Output: 3
 *  * Explanation: merged array = [1,2,3,4,5] and median is 3.
 *
 * Constraints:
 * nums1.length == m
 * nums2.length == n
 * 0 <= m <= 1000
 * 0 <= n <= 1000
 * 1 <= m + n <= 2000
 * -106 <= nums1[i], nums2[i] <= 106
 */
public class MedianOfTwoSortedArray4 {

    /**
     * In order to find the median in O(log(m+n)), we need to find the value which divides the arrays such that
     * the last element in that first part of the first array is smaller than the first element of the next part of the second array and
     * last element of
     *
     * a = []
     * a
     *
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArraysApproach2(int[] nums1, int[] nums2) {
        return 0.0;
    }

    //Time complexity will be O(m+n)
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len3 = nums1.length + nums2.length;
        int[] mergedArr = new int[len3];
        for(int i=0; i<nums1.length; i++){
            mergedArr[i] = nums1[i];
        }

        for(int i=0; i<nums2.length; i++){
            mergedArr[i+nums1.length] = nums2[i];
        }

        Arrays.sort(mergedArr);

        double median;
        int temp = len3/2;
        if(mergedArr.length%2==0){
            median = mergedArr[temp-1] + mergedArr[temp]/2.0;
        } else {
            median = mergedArr[temp];
        }
        return median;
    }

    public static void main(String[] args) {
        MedianOfTwoSortedArray4 obj = new MedianOfTwoSortedArray4();
        int[] i1 = {1,2,6};
        int[] i2 = {3,4};
        System.out.println(obj.findMedianSortedArrays(i1, i2));
    }
}
