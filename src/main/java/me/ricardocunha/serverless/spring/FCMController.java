package me.ricardocunha.serverless.spring;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import me.ricardocunha.serverless.spring.helper.AndroidPushNotificationsService;
import me.ricardocunha.serverless.spring.helper.FirebaseResponse;
import me.ricardocunha.serverless.spring.model.Message;

@RestController
@EnableWebMvc
public class FCMController {
    @RequestMapping(path = "/message", method = RequestMethod.POST)
    public Message createPet(@RequestBody Message message) {
        if (message.getSubject() == null || message.getMessage() == null) {
            return null;
        }

        return null;
    }

    //private static final Logger log = LoggerFactory.getLogger(FCMController.class);

    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> send() {


        JSONObject body = new JSONObject();
        // JsonArray registration_ids = new JsonArray();
        // body.put("registration_ids", registration_ids);
        body.put("to", "drVa0QOapKs:APA91bH0F8HQQpMfuXvTfFHwlcIFMj1LB4QRP0weoNBD8cBNKRjfvPFHy_cxZEvczzw7btyNaZocdjfmD9XAdu72ysUH8zmGlEP6-y8_qi1cua-QmKla_NCd8bWQw9NWFOptE1BslAli");
        body.put("priority", "high");
        // body.put("dry_run", true);

        JSONObject notification = new JSONObject();
        notification.put("body", "body string here");
        notification.put("title", "title string here");
        // notification.put("icon", "myicon");

        JSONObject data = new JSONObject();
        data.put("key1", "value1");
        data.put("key2", "value2");

        body.put("notification", notification);
        body.put("data", data);

        HttpEntity<String> request = new HttpEntity<>(body.toString());
        if (androidPushNotificationsService == null){
        	androidPushNotificationsService= new AndroidPushNotificationsService();
        }

        CompletableFuture<FirebaseResponse> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            FirebaseResponse firebaseResponse = pushNotification.get();
            if (firebaseResponse.getSuccess() == 1) {
                //log.info("push notification sent ok!");
            	System.out.println("push notification sent ok!");
            } else {
                //log.error("error sending push notifications: " + firebaseResponse.toString());
            	System.out.println("error sending push notifications: " + firebaseResponse.toString());
            }
            return new ResponseEntity<>(firebaseResponse.toString(), HttpStatus.OK);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("the push notification cannot be send.", HttpStatus.BAD_REQUEST);
    }

    public static void main(String args[]){
    	new FCMController().send();
    }
    
}
