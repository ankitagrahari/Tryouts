package com.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ValidateJson {

    public static boolean validateJson(File file){
        boolean isValid = false;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(file);
            isValid = true;
        } catch (Exception je) {
            isValid = false;
            moveToFailedDir(file);
            je.printStackTrace();
        }
        return isValid;
    }

    private static void moveToFailedDir(File file){
        File failedDir = null;
        try {
            if(null!=file && file.exists() && file.isFile()){
                failedDir = new File("src\\main\\resources\\source\\failed");
                if(!failedDir.exists()){
                    failedDir.mkdirs();
                }
                String failedFileName = "src\\main\\resources\\source\\failed"+ File.separator+ file.getName();
                Files.move(Paths.get(file.toURI()), Paths.get(failedFileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File json = new File("src\\main\\resources\\source\\test.json");
        System.out.println(ValidateJson.validateJson(json));
    }
}
