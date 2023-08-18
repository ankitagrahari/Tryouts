package com.dmtryouts.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class ValidatorJSON {

    public boolean validateJson(File file){
        boolean isValid = false;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(file);
            isValid = true;
        } catch (Exception je) {
            isValid = false;
            je.printStackTrace();
//            moveToFailedDir(file);
        }
        return isValid;
    }

    public static void main(String[] args) {
        ValidatorJSON obj = new ValidatorJSON();
        File file = new File("src/main/resources/PACKAGE.json");
        System.out.println(obj.validateJson(file));
    }
}
