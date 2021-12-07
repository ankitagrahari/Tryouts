package com.array;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinT {

    public static void main(String[] args) {
        ForkJoinPool pool = ForkJoinPool.commonPool();

        int[] arr = {1, 4, 2, 1, 2, 1, 23, 1, 5, 5, 2, 4, 9, 2, 8, 3, 5, 1, 0, 6, 3, 1};
        CountDuplicate task = new CountDuplicate(arr, 0, arr.length-1, 1);
        Integer count = (Integer) pool.invoke(task);
        System.out.println(count);

        Fibonacci task2 = new Fibonacci(25);
        Integer fib = (Integer) pool.invoke(task2);
        System.out.println(fib);
    }

}

class Fibonacci extends RecursiveTask<Integer>{

    private static final int THRESHOLD = 20;
    int to;

    Fibonacci(int to){
        this.to = to;
    }

    Integer computeDirectly(){
        int i=2;
        int a=0, b=1, curr;
        if(to<=1)
            return 1;

        while(i<=to){
            curr = a + b;
            a = b;
            b = curr;
            i++;
        }
        return b;
    }

    // 0 1 1 2 3 5 8 13 21 34 55 89 144 233 377 610...
    @Override
    protected Integer compute(){
        if(to < THRESHOLD){
            return computeDirectly();
        }

        Fibonacci task1 = new Fibonacci(to-1);
        Fibonacci task2 = new Fibonacci(to-2);

        task1.fork();
        return task2.compute() + task1.join();
    }
}


class CountDuplicate extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 47;
    int[] arr;
    int start, end, toFind;

    CountDuplicate(int[] arr, int start, int end, int toFind){
        this.arr = arr;
        this.start = start;
        this.end = end;
        this.toFind = toFind;
    }

    // Can only be assigned protected or public. Cannot assign weaker access specifier
    @Override
    protected Integer compute(){
        int count=0;

        if((end-start)<=THRESHOLD) {
            for (int i = start; i <= end; i++) {
                if (toFind == arr[i]) {
                    count++;
                }
            }
            System.out.println("count:"+ count + " from start:"+ start +" and end:"+ end);
            return count;
        } else {
            CountDuplicate task1 = new CountDuplicate(arr, 0, end/2, toFind);
            CountDuplicate task2 = new CountDuplicate(arr, (end/2)+1, arr.length-1, toFind);

            task1.fork();
            return task2.compute() + task1.join();
        }
    }
}
