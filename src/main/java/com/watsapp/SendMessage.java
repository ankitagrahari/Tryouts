package com.watsapp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SendMessage {

    private String ACCOUNT_SID="ABCDEFGHIJKLMNOPQRSTVWXYZ";
    private String AUTH_TOKEN="123abc456def789ghi";

    private void sendMessageUsingTwilio(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+xxxxxxxxxxx"),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                "Hello there!")
                .create();

//        ProxiedTwilioClientCreator clientCreator = new ProxiedTwilioClientCreator(
//                ACCOUNT_SID, AUTH_TOKEN, PROXY_HOST, PROXY_PORT);
//        TwilioRestClient twilioRestClient = clientCreator.getClient();
//        Twilio.setRestClient(twilioRestClient);

        System.out.println(message.getSid());
    }

    public static void main(String[] args) {
        SendMessage obj = new SendMessage();
        obj.sendMessageUsingTwilio();
    }
}
