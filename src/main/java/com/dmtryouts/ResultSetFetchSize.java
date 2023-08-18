package com.dmtryouts;

import java.sql.*;
import java.util.Properties;

public class ResultSetFetchSize {

    Connection getConnection(){
        String oracleDriver = "oracle.jdbc.driver.OracleDriver";
        String psqlDriver = "org.postgresql.Driver";
        String connectionURL = "jdbc:postgresql://16.166.49.125:5432/truth";
        Properties dbArgs = new Properties();
        dbArgs.put("user", "");
        dbArgs.put("password", "");
        dbArgs.put("database", "");
        try {
            Class.forName(psqlDriver);
            Connection connection = DriverManager.getConnection(connectionURL, dbArgs);
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    void queryDB(Connection connection){
        try {
            Statement stmt = connection.createStatement();
            stmt.setFetchSize(15);
            ResultSet rs = stmt.executeQuery("SELECT aiops.TYPE_ID AS INPUT0, aiops.TYPE_ID AS INPUT1, rc_app_configops.ROLE_CLASS_FULL_NAME AS INPUT2,  " +
                                                     "cast ('(?<=Application Configurations:).*' as varchar(1500))  AS INPUT3, aiops.ATTACHED_DVC_ID AS INPUT4, " +
                                                     "aiops.ATTACHED_DVC_ID AS INPUT5, dvcops.SYSTEM_NAME AS INPUT6,  cast (NULL as varchar(1500))  AS INPUT7,  " +
                                                     "cast (NULL as varchar(1500))  AS INPUT8,  cast (NULL as varchar(1500))  AS INPUT9,  " +
                                                     "cast (NULL as varchar(1500))  AS INPUT10,  cast (NULL as varchar(1500))  AS INPUT11,  " +
                                                     "cast (NULL as varchar(1500))  AS INPUT12 FROM (SELECT * FROM truth.APPLICATION_INSTALLATIONS aiopsQWE " +
                                                     "WHERE ((ATTACHED_DVC_ID IS NOT NULL ))) aiops  INNER JOIN truth.role_classes rc_app_configops " +
                                                     "on aiops.TYPE_ID = rc_app_configops.ROLE_CLASS_ID INNER JOIN truth.DEVICES dvcops on aiops.ATTACHED_DVC_ID = dvcops.DVC_ID");
            
            while(rs.next()){
                System.out.println(rs.getString(1)+"--"+ rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ResultSetFetchSize obj = new ResultSetFetchSize();
        Connection connection = obj.getConnection();
        obj.queryDB(connection);
    }
}
