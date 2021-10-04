package com.dmtryouts.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteJson {

    public JSONArray writeJson(List<Map<String, String>> dataset){
        JSONArray jarr = new JSONArray();

        for(Map<String, String> map: dataset) {
            JSONObject object = new JSONObject();
            for (String s : map.keySet()) {
                object.put(s, map.get(s));
            }
            jarr.put(object);
        }
        return jarr;
    }

    public void dumpToJSONFile(JSONArray jarr){
        try {
            FileWriter fw = new FileWriter("src/main/resources/test.json");
            fw.write(jarr.toString(2));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getJSONFileSize(JSONArray jarr) throws IOException {
        System.out.println("JSON Array String content:"+jarr.toString(2).length());
        System.out.println("JSON Array content:"+ jarr.length());
        Path filePath = Paths.get("src/main/resources/test.json");
        return Files.size(filePath);
    }

    public static void main(String[] args) {
        Map<String, String> data = new HashMap<>();
        data.put("entity_producer_instance_type", "SA");
        data.put("itemtype", "entity");
        data.put("datatablename", "RULE");
        data.put("operation", "UPDATE");
        data.put("timestamp_utc_s", "1604901192");
        data.put("entity_unique_id", "000000000000000000003622630aa531");
        data.put("rule_id", "000000000000000000003622630aa531");
        data.put("sa_rule_id", "18430001");
        data.put("rule_name", "updated_name10");
        data.put("rule_type_name", "CMDOUTPUT");
        data.put("description", "");
        data.put("created_date", "2020-09-10 04:01:29");
        data.put("modified_date", "2020-11-09 05:53:12.629");
        data.put("deleted_date", "");
        data.put("created_by", "opsware");
        data.put("modified_by", "opsware");
        data.put("entity_tenant_id", "");
        Map<String, String> data1 = new HashMap<>();
        data1.put("rule_name", "CPUS");
        data1.put("sa_rule_id", "20990001");
        data1.put("entity_producer_instance_type", "SA");
        data1.put("rule_type_name", "CPUS");
        data1.put("description", "");
        data1.put("entity_producer_instance_id", "862220");
        data1.put("modified_date", "2020-11-09 07,41,31.092");
        data1.put("created_by", "bala");
        data1.put("entity_tenant_id", "");
        data1.put("rule_id", "000000000000000000004e6b20eed431");
        data1.put("deleted_date", "");
        data1.put("itemtype", "entity");
        data1.put("datatablename", "RULE");
        data1.put("modified_by", "bala");
        data1.put("created_date", "2020-09-14 11,57,21");
        data1.put("operation", "UPDATE");
        data1.put("timestamp_utc_s", "1604907691");
        data1.put("entity_unique_id", "000000000000000000004e6b20eed431");
        Map<String, String> data2 = new HashMap<>();
        data2.put("A2", "1");
        data2.put("B2", "2");
        data2.put("C2", "3");
        data2.put("D2", "4");
        data2.put("E2", "5");
        List dataset = new ArrayList();
        dataset.add(data);
        dataset.add(data1);
        dataset.add(data2);

        WriteJson obj = new WriteJson();
        JSONArray jsonArray = obj.writeJson(dataset);
        obj.dumpToJSONFile(jsonArray);
        try {
            System.out.println(obj.getJSONFileSize(jsonArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int a = 1051738/1000000;
        System.out.println(a);
    }
}
