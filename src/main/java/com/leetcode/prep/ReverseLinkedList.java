package com.leetcode.prep;

import com.leetcode.LC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReverseLinkedList{

    private ListNode prepareLL(int[] arr){

        ListNode head = new ListNode(arr[0]);
        ListNode temp = head;
        for(int i=1; i<arr.length; i++){
            temp.next = new ListNode(arr[i]);
            temp = temp.next;
        }
        return head;
    }

    /**        P   NC
     * Input : 1 -> 2 -> 3 -> 4 -> 5
     * Output: 5 -> 4 -> 3 -> 2 -> 1
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null, curr, next;
        curr = head;
        while(curr!=null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head = prev;
        return head;
    }

    public void implementation(int[] input) {
        ListNode head = prepareLL(input);
        reverseList(head);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};
        ReverseLinkedList obj = new ReverseLinkedList();
        obj.implementation(arr);
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
