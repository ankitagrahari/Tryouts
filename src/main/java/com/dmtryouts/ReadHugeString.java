package com.dmtryouts;

import java.io.IOException;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ReadHugeString {

    public void readString(String hugeStr){
        CharBuffer buffer = CharBuffer.allocate(97000);
        StringReader reader = new StringReader(hugeStr);
        int readOP = 0;
        try {
            while ((readOP = reader.read(buffer)) != -1) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subGivenStr(String hugeStr){

        System.out.println("Length:"+ hugeStr.length());

        Map<String, String> trackingColumnValueMap = new HashMap<>();
        trackingColumnValueMap.put("job_name", "Run Server Script (new_script1)");
        trackingColumnValueMap.put("sa_job_id", "16630001");
        trackingColumnValueMap.put("job_start_Date", "2021-06-22 17:10:49");
        trackingColumnValueMap.put("job_result_order", "0");
        trackingColumnValueMap.put("job_end_Date", "2021-06-22 17:10:52");
        trackingColumnValueMap.put("job_scheduled_date", "");
        trackingColumnValueMap.put("job_Status", "SUCCESSSUCCESSSUCCESS");
        trackingColumnValueMap.put("job_result_data", "abc");

        String temp = "job_result_data";
        String subHugeStr = "";
        int start = 0, end = hugeStr.length()>97000 ? 97000: hugeStr.length();
        while( start<end && end<=hugeStr.length() ){
            subHugeStr = hugeStr.substring(start, end);
            System.out.println("start:"+ start+" end:"+ end+" length:"+subHugeStr.length());
            start = end;
            end = (end + 97000) < hugeStr.length() ? (end+97000) : hugeStr.length();
        }
        trackingColumnValueMap.put("job_result_data", subHugeStr);
        trackingColumnValueMap.entrySet().stream()
                .filter(entry -> !entry.getKey().equalsIgnoreCase(temp))
                .forEach(entry -> System.out.println("Key:"+entry.getKey()+ " value:"+ entry.getValue()));
        trackingColumnValueMap.put("job_result_data", subHugeStr);

    }

    public static void main(String[] args) {
        try {
            String hugeStr = new String(Files.readAllBytes(Paths.get("src/main/resources/test.txt")));
            ReadHugeString obj = new ReadHugeString();
            obj.subGivenStr(hugeStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
