package me.ricardocunha.serverless.spring.helper;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import me.ricardocunha.serverless.spring.model.Message;
@Service
public class AndroidPushRegisterService {

    
    private String FirebaseServerKey = null;
    
    public AndroidPushRegisterService(){
    	this.FirebaseServerKey = System.getProperty("FIREBASE_SERVER_KEY");
    }

    /**
     * 
     */
    public Message sendPushNotification(Message message) throws Exception {
        String pushMessage = "{\"data\":{\"title\":\"" +
        		message.getSubject() +
                "\",\"message\":\"" +
                message.getMessage() +
                "\"},\"to\":\"" +
                message.getDeviceToken() +
                "\"}";
        // Create connection to send FCM Message request.
        URL url = new URL("https://fcm.googleapis.com/fcm/send");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "key=" + FirebaseServerKey);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        // Send FCM message content.
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(pushMessage.getBytes());

        //System.out.println(conn.getResponseCode());
        //System.out.println(conn.getResponseMessage());
        message.setResponseCode(conn.getResponseCode());
        message.setResponseMessage(conn.getResponseMessage());
        return message;
    }
}
