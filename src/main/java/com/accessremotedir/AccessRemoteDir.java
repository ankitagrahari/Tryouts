package com.accessremotedir;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;

public class AccessRemoteDir {

    private static String SERVER = "16.166.49.87";
    private static int PORT = 22;

    public static void accessRD() throws JSchException, IOException {
        JSch jsch = new JSch();
        Session session = jsch.getSession("root", "16.166.49.87");
        session.connect();
        Channel channel = session.openChannel("exec");
        String command = "cat /etc/opt/opsware/vault/vault.conf";
        ((ChannelExec)channel).setCommand(command);

        InputStream in=channel.getInputStream();
        channel.connect();
        byte[] tmp=new byte[1024];
        while(true){
            while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                System.out.print(new String(tmp, 0, i));
            }
            if(channel.isClosed()){
                if(in.available()>0) continue;
                System.out.println("exit-status: "+channel.getExitStatus());
                break;
            }
            try{Thread.sleep(1000);}catch(Exception ee){}
        }
        channel.disconnect();
        session.disconnect();
    }

    public static void main(String[] args) {
        try {
            accessRD();
        } catch (IOException | JSchException e) {
            e.printStackTrace();
        }
    }
}
