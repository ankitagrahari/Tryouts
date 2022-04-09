package com.dmtryouts.EncryptDecrypt;

import java.util.Locale;
import java.util.StringTokenizer;
import javax.crypto.SecretKey;

public class ReplaceUtils {

    public static void main(String[] args) {
        String jdbcConn = "user=opsware_admin,password=8clBtcxENlsgoRE3miP+vyCrCU/mWy08zSwCsO79O2g=,database="
                + "(DESCRIPTION=(FAILOVER=ON)(BALANCE=ON)(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=16"
                + ".166.49.108)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=truth)))";
        StringTokenizer st = new StringTokenizer(jdbcConn, ",;");
        StringBuilder jdbcConnPropStr = new StringBuilder("");
        String key;
        while (st.hasMoreTokens()) {
            String configStr = st.nextToken();
            int equalToken = configStr.indexOf("=");

            String value = "";

            if (equalToken > -1) {
                key = configStr.substring(0, equalToken);
                if (equalToken <= configStr.length()) {
                    value = configStr.substring(equalToken + 1);
                }
            } else {
                key = configStr;
            }

            if (key.toUpperCase(Locale.getDefault()).contains("PASSWORD")) {
                System.out.println("pASS:"+ value);
                value = "newEncrypted";
            }
            jdbcConnPropStr.append(key).append("=").append(value);
        }
        System.out.println(jdbcConnPropStr);
    }
}
