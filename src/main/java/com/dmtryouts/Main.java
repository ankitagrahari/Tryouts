package com.dmtryouts;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.sql.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("Note : This program only supports on oracle database");
        String serverFile = "../resources/server.xml";
        String URL = "";
        String username = "";
        String file = "../resources/server.txt";
        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet rs = null;
        Connection con = null;
        BufferedWriter out = null;

        try {

            // get JDBC URL and DB username from server.xml

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document docServer = docBuilder.parse(serverFile);
            docServer.getDocumentElement().normalize();
            // Get context element
            NodeList nNodeList = docServer.getElementsByTagName("Context");
            if (nNodeList.getLength() > 0) {
                // fetch jdbc url and username
                //NodeList nListContext = docServer.getElementsByTagName(CONTEXT_ELEMENT);
                NodeList resources = docServer.getElementsByTagName("Resource");
                for (int i = 0; i < resources.getLength(); i++) {
                    Element el = (Element) resources.item(i);
                    if (el.hasAttribute("url")) {
                        URL = el.getAttribute("url");
                        System.out.println("DB connection URL is: " + URL);
                    }
                    if (el.hasAttribute("username")) {
                        username = el.getAttribute("username");
                        System.out.println("DB username is: " + username);
                    }
                }
            }

            String password = PasswordField.readPassword();
            System.out.println("The password entered is: " + password);
            //load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");

            //create  the connection object
            con = DriverManager.getConnection(URL, username, password);
            if (con != null) {
                System.out.println("Database Connection Successful");
                //create the statement object
                stmt = con.createStatement();

                //execute query
                rs = stmt.executeQuery("select name from dma_server where deleted is not null");

                System.out.println("************************************************************************************");
                System.out.println("List of servers marked for deletion can be viewed in the file " + file);
                System.out.println("************************************************************************************");

                out = new BufferedWriter(new FileWriter(file));
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                    out.newLine();
                    out.write(rs.getString(1));
                }
                System.out.print("Do you want to proceed for purging deleted servers data from DMA database? Enter 'yes' or 'no' : ");
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String response = in.readLine();

                if(response != null && response.equalsIgnoreCase("YES")) {
                    if (stmt != null) {
                        stmt.close();
                    }
                    System.out.println(" ");
                    System.out.print("Note : This will delete the servers permanently and cannot be undone. Are you sure to proceed for Deletion? Enter 'yes' or 'no' ");
                    String confirmation = in.readLine();
                    if(confirmation != null && confirmation.equalsIgnoreCase("YES")) {
                        stmt1 = con.createStatement();
                        con.setAutoCommit(false);

                        stmt.addBatch("delete from dma_scan_details where scan_log_id in (select id FROM DMA_SCAN_LOG dsl WHERE SERVER_ID in (select name from dma_server where deleted is not null))");
                        stmt.addBatch("delete FROM DMA_SCAN_LOG dsl WHERE SERVER_ID in (select name from dma_server where deleted is not null)");
                        stmt.addBatch("delete from dma_script_exec_chunk where script_exec_id in (select id from DMA_SCRIPT_EXEC dse WHERE SERVER_ID in (select ID from dma_server where deleted is not null))");
                        stmt.addBatch("delete from DMA_SCRIPT_EXEC dse WHERE SERVER_ID in (select ID from dma_server where deleted is not null)");
                        stmt.addBatch("delete from dma_step_output_chunk where step_output_id in (select id from dma_step_output where execution_state_id in ( select id FROM DMA_EXECUTION_STATE des WHERE SERVER_ID in (select ID from dma_server where deleted is not null)))");
                        stmt.addBatch("delete from dma_step_output where execution_state_id in ( select id FROM DMA_EXECUTION_STATE des WHERE SERVER_ID in (select ID from dma_server where deleted is not null))");
                        stmt.addBatch("delete from dma_execution_state_chunk where execution_state_id in (select id FROM DMA_EXECUTION_STATE des WHERE SERVER_ID in (select ID from dma_server where deleted is not null))");
                        stmt.addBatch("delete from dma_execution_state_chunk where execution_state_id in (select id FROM DMA_EXECUTION_STATE des WHERE SERVER_ID in (select ID from dma_server where deleted is not null))");
                        stmt.addBatch("delete FROM DMA_EXECUTION_STATE des WHERE SERVER_ID in (select ID from dma_server where deleted is not null)");
                        stmt.addBatch("delete FROM DMA_SERVER ds WHERE ds.deleted is not null");
                        int[] results = stmt.executeBatch();
                        int i = 0;
                        for (int res:results) {
                            i++;
                            if(res > 0)
                                System.out.println("Deleted Successfully" + res +"rows from the delete query" + i);
                            else
                                System.out.println("No rows were being deleted in the delete query" + i);
                        }
                        con.commit();
                    }
                } else {
                    System.out.println("User response for purging deleted servers data from DMA database : " + response + ". Hence exitting");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
                if (out != null) {
                    out.close();
                } if (stmt1 != null)
                    stmt1.close();
            } catch (Exception mysqlEx) {
                System.out.println(mysqlEx.toString());
            }

        }
    }
}
class PasswordField {

    public static String readPassword() {

        String password = null;
        try {
            //Obtaining a reference to the console.
            Console con = System.console();
            // Checking If there is no console available, then exit.
            if (con == null) {
                System.out.print("No console available");
                // console is not available. please Run code on cmd.
            } else {
                char[] pwd = con.readPassword("Enter Password : ");
                password = String.valueOf(pwd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return password;
    }
}
