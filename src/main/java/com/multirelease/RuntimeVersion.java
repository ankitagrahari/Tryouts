package com.multirelease;

public class RuntimeVersion {

    private static int getVersion() {
        String version = System.getProperty("java.version");
        System.out.println(version);
        if(version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if(dot != -1) { version = version.substring(0, dot); }
        } return Integer.parseInt(version);
    }

    public static void main(String[] args) {
        System.out.println(RuntimeVersion.getVersion());
    }
}
