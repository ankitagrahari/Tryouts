package com.threads;

import com.MainClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Map;

public class ProcessFiles implements Runnable{

    @Override
    public void run() {
        if(null!= MainClass.filesMap && MainClass.filesMap.size()>0){

            for (Map.Entry<String, Integer> entry : MainClass.filesMap.entrySet()) {
                synchronized (this) {
                    System.out.println(Thread.currentThread().getName()+"---"+entry.getKey() +
                            "---" + MainClass.filesMap.get(entry.getKey()));
                    try {
                        //Multiple checks for multi threads
                        if (null != MainClass.filesMap &&
                                null!=MainClass.filesMap.get(entry.getKey()) &&
                                MainClass.filesMap.get(entry.getKey()) == 0) {

                            Files.move(Paths.get("src\\main\\resources\\work" +
                                            File.separator + entry.getKey()),
                                    Paths.get("src\\main\\resources\\collect" +
                                            File.separator + entry.getKey()));
                            MainClass.filesMap.remove(entry.getKey());
                        }
                    } catch (NoSuchFileException nsfe) {
                        if (!new File("src\\main\\resources\\collect" +
                                File.separator + entry.getKey()).exists()) {
                            nsfe.printStackTrace();
                        }
                    } catch (NullPointerException npe){
                        //There might be a case when some threads will try to access get on fileMap,
                        // which will be empty at that time. Hence, on NPE if the map is not empty, then
                        // throw error, else skip
                        if(null!=MainClass.filesMap
                                && MainClass.filesMap.size()>0){
                            System.out.println("FileName:"+entry.getKey()+" Value:"+entry.getValue());
                            npe.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
