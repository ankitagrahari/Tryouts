package com;

import com.threads.ProcessFiles;
import com.threads.ProcessFilesUsingListPartition;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class MainClassUsingListPartition {

    private static final Integer THREADS = 10;

    public static void moveUsingMultiThread(List<File> fileList){
        System.out.println("Running MultiThreaded Application to Move files from work to collect");
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS);

        List<List<File>> partitionedLists = partitionListOfFiles(fileList, 3);
        System.out.println(partitionedLists.size());
        for(List<File> files : partitionedLists) {
            Runnable worker = new ProcessFilesUsingListPartition(files);
            executorService.submit(worker);
        }
        executorService.shutdown();

        while (!executorService.isTerminated()) {
            // Wait until all threads are finished
        }
        System.out.println("Finished all threads");
    }

    private static List<List<File>> partitionListOfFiles(List<File> files, final int breakInto) {
        List<List<File>> fileParts = new ArrayList<>();
        final int totalFiles = files.size();
        for (int i = 0; i < files.size(); i += breakInto) {
            fileParts.add(
                    new ArrayList<>(files.subList(i, Math.min(totalFiles, i + breakInto)))
            );
        }
        return fileParts;
    }

    public static void main(String[] args) {
        File workDir = new File("src\\main\\resources\\source");
        List<File> files = new ArrayList<>();
        if(workDir.exists() && workDir.isDirectory()) {
            try {
                Stream<Path> paths = Files.walk(Paths.get("src\\main\\resources\\source"), 1);
                paths.forEach(path -> {
                    if(!path.toFile().getName().equalsIgnoreCase("source"))
                        files.add(path.toFile());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            File collectDir = new File("src\\main\\resources\\target");
            if (collectDir.exists() && collectDir.isDirectory()) {
                MainClassUsingListPartition.moveUsingMultiThread(files);
            }
        }
    }
}
