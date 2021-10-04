package com.dmtryouts;

public class HostnameExtraction {

    public static void getHostName(String a){

        String[] jdbcStringSplitted = a.split("=");
        String hostName=jdbcStringSplitted[7].substring(0,jdbcStringSplitted[7].lastIndexOf(")"));
        System.out.println(hostName);
    }

    public static void getPHostName(String a){

        String[] abc = a.split(":");
        String hostName= abc[0];
        System.out.println(hostName.substring(0, hostName.length()-1));
    }

    public static String escapeString(String content) {
        content = content.replaceAll("","");
        return content;
    }

    public static void main(String[] args) {
        //String str = "[BTPRD3PAR04_LUN000] BatmanAxisMachine-xx-004/BatmanAxisMachine-xx-004-000002.vmdk ";
//        String str = "<null>";
//        System.out.println(str.substring(str.indexOf('[')+1, str.indexOf(']')));

        String oracle = "(DESCRIPTION\\=(FAILOVER\\=ON)(BALANCE\\=ON)" +
                "(ADDRESS_LIST\\=(ADDRESS\\=(PROTOCOL\\=TCP)(HOST\\=dmasacore3.hpeswlab.net)(PORT\\=1521)))" +
                "(CONNECT_DATA\\=(SERVICE_NAME\\=truth)))\n";
        String postgres = "16.166.49.125\\:5432/truth";
        HostnameExtraction.getHostName(oracle);
        HostnameExtraction.getPHostName(postgres);
    }
}
