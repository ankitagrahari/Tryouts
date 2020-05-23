package com;

import com.threads.ProcessFiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class MainClass {

    private static final Integer THREADS = 10;
    public static ConcurrentHashMap<String, Integer> filesMap = new ConcurrentHashMap<>();

    public static void moveUsingMultiThread(){
        System.out.println("Running MultiThreaded Application to Move files from work to collect");
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS);

        for(int i=0; i<5; i++) {
            Runnable worker = new ProcessFiles();
            executorService.execute(worker);
        }
        executorService.shutdown();

        while (!executorService.isTerminated()) {
            // Wait until all threads are finished
        }
        System.out.println("Finished all threads");
    }

    public static void main(String[] args) {
        File workDir = new File("src\\main\\resources\\work");
        if(workDir.exists() && workDir.isDirectory()) {
            try {
                Stream<Path> paths = Files.walk(Paths.get("src\\main\\resources\\work"), 1);
                paths.forEach(path -> {
                    if(!path.toFile().getName().equalsIgnoreCase("work"))
                        filesMap.put(path.toFile().getName(), 0);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            File collectDir = new File("src\\main\\resources\\collect");
            if (collectDir.exists() && collectDir.isDirectory()) {
                MainClass.moveUsingMultiThread();
            }
        }
    }
}
