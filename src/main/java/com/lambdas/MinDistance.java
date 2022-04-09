package com.lambdas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class MinDistance {

    // 1, 2, 1, 2, 3, 5, 1, 4, 5, 7, 8, 5, 9   x=1, y=5
    int minDist(int a[], int n, int x, int y) {

        if(a.length!=n) return -1;
        boolean xPresent = false;
        boolean yPresent = false;
        int prev = -1; int m = Integer.MAX_VALUE;

        for(int i=0; i<n; i++){
            if(a[i]==x || a[i]==y) {
                if(prev!=-1 && a[prev]!=a[i])
                    m = Math.max(i-prev, m);
            }
            if(a[i]==y) {
                yPresent = true;
            }
        }

        if(!(xPresent && yPresent))
            return -1;

//
//        // Implement it as Stack, for each x entry, push x's index to the stack
//        // If y is encountered, calculate y's index - x's index and put it to a
//        // list, then pop that x.
////        List<Integer> stack = new ArrayList<>();
//        Stack<Integer> stack = new Stack<>();
//        List<Integer> dis = new ArrayList<>();
//        for(int i=0; i<n; i++){
////            try {
//            if (a[i] == x) {
//                stack.push(i);
//                System.out.println("pushed for index:"+i);
//            }
//            if (a[i] == y && !stack.empty()) {
//                int xIndex = stack.pop();
//                System.out.println("Popping "+ xIndex +" for index:"+i);
//                dis.add(i - xIndex);
//            }
////            } catch (EmptyStackException e){
////                int size = dis.size();
////                dis.add(i-size);
////            }
//        }
//        System.out.println(dis);
//        return dis.stream().min(Comparator.naturalOrder()).get();
    }

    public static void main(String[] args) {
        int[] a = {98,78,10,12,59,37,45,18,1,56,37,14,3,32,85,10,69,89,29,93,44,16,26,
                13,50,75,79,21,20,33,55,17,63,64,80,21,52,24,90,52,80,26,18,34,57,2,95,
                25,42,23,17,85,39,94,50,40,21,28,12,40,61,67,9,23,30,88,95,34,64,85,85,
                95,62,54,28,19,55,22,95,49,97,64,33};
        int x = 34, y = 56;
        MinDistance obj = new MinDistance();
        System.out.println(obj.minDist(a, a.length, x, y));
    }
}
