package com.leetcode.prep;

import java.util.ArrayList;
import java.util.List;

/**
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order, and each of their nodes contains a single digit.
 * Add the two numbers and return the sum as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Example 1:
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807.
 *
 * Example 2:
 * Input: l1 = [0], l2 = [0]
 * Output: [0]
 *
 * Example 3:
 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * Output: [8,9,9,9,0,0,0,1]
 *
 * Constraints:
 * The number of nodes in each linked list is in the range [1, 100].
 * 0 <= Node.val <= 9
 * It is guaranteed that the list represents a number that does not have leading zeros.
 */
public class AddTwoNumber2 {

    public ListNode2 addTwoNumbers(ListNode2 l1, ListNode2 l2) {
        int carry = 0;
        List<Integer> sb = new ArrayList<>();
        ListNode2 curr, prev = null;
        boolean firstNodeFlag = true;
        ListNode2 firstNode = null;
        do{
            int sum = (null!=l1?l1.val:0) + (null!=l2?l2.val:0) + carry;

            if(sum > 9){
                carry = sum/10;
                sum = sum%10;
            }

            if(l1==null && l2==null && carry!=0)
                curr = new ListNode2(carry);
            else
                curr = new ListNode2(sum);

            if(firstNodeFlag){
                prev = curr;
                firstNodeFlag = false;
                firstNode = curr;
            } else {
                prev.next = curr;
                prev = curr;
            }
            System.out.println(String.format("l1:%s, l2:%s, carry:%s, sum:%s",
                    l1!=null?l1.val:0, l2!=null?l2.val:0, carry, sum));

            if(null!=l1)
                l1 = l1.next;

            if(null!=l2)
                l2 = l2.next;

//            sb.add(sum);
        } while(l1!=null || l2!=null || (carry!=0 && (l1!=null || l2!=null)));

        return firstNode;
//        System.out.println("--"+sb);
//        return prepareListNode(sb.toArray(new Integer[0]));
    }

    public ListNode2 addTwoNumber(ListNode2 l1, ListNode2 l2) {
        int carry = 0;
        List<Integer> sb = new ArrayList<>();
        do{
            int sum = (null!=l1?l1.val:0) + (null!=l2?l2.val:0) + carry;
            if(null!=l1)
                l1 = l1.next;

            if(null!=l2)
                l2 = l2.next;

            if(sum > 9){
                carry = sum/10;
                sum = sum%10;
            } else{
                carry = 0;
            }
            sb.add(sum);
        } while( l1!=null || l2!=null || carry!=0 );

        System.out.println("--"+sb);
        return prepareListNode(sb.toArray(new Integer[0]));
    }

    private ListNode2 prepareListNode(Integer[] arr){
        ListNode2 last = new ListNode2(arr[arr.length-1], null);
        ListNode2 obj = null;
        if(arr.length>1) {
            for (int i = arr.length - 2; i >= 0; i--) {
                obj = new ListNode2(arr[i], last);
                last = obj;
            }
        } else {
            obj = last;
        }
//        traverseListNode(obj);
        return obj;
    }

    protected void traverseListNode(ListNode2 node){
        do {
            System.out.print(node.val+"-->");
            node = node.next;
        } while(node.next!=null);
    }


    public static void main(String[] args) {
        AddTwoNumber2 obj = new AddTwoNumber2();
//        Integer[] ar = {9,9,9,9,9,9,9}; Integer[] ar1 = {9,9,9,9};
        Integer[] ar = {0}; Integer[] ar1 = {0};
        System.out.println(obj.addTwoNumber(obj.prepareListNode(ar), obj.prepareListNode(ar1)));
    }
}

// Definition for singly-linked list.
class ListNode2 {
    int val;
    ListNode2 next;
    ListNode2() {}
    ListNode2(int val) { this.val = val; }
    ListNode2(int val, ListNode2 next) {
        this.val = val;
        this.next = next;
    }
}
