package com.dmtryouts;

public class TabCheck {

    public static void main(String[] args) {
        String str = "jdkscwj	";
        if(str.contains("\t")){
            System.out.println("tab exists");
        }
        
        String strTrim = str.trim();
        if(strTrim.contains("\t")){
            System.out.println("tab exists");
        }
    }
}

