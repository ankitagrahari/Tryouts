package com.threads;

import com.MainClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class ProcessFilesUsingListPartition implements Runnable{

    private List<File> files;

    public ProcessFilesUsingListPartition(List<File> files){
        this.files = files;
    }

    @Override
    public void run() {
        if(null!= files && files.size()>0){
            for (File file : files) {
                synchronized (this) {
                    System.out.println(Thread.currentThread().getName()+"---"+file.getName());
                    try {
                        if (null != file ) {
                            Files.move(Paths.get("src\\main\\resources\\source" +
                                            File.separator + file.getName()),
                                    Paths.get("src\\main\\resources\\target" +
                                            File.separator + file.getName()));
                        }
                    } catch (NoSuchFileException nsfe) {
                        if (!new File("src\\main\\resources\\target" +
                                File.separator + file.getName()).exists()) {
                            nsfe.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
