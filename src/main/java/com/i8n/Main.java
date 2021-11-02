package com.i8n;

import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Locale locale = Locale.CHINA;
        String a = "CONVERT THIS";
        System.out.println(locale.getDisplayLanguage(locale));
    }
}
