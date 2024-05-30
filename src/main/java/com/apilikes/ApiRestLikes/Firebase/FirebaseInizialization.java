package com.apilikes.ApiRestLikes.Firebase;

import java.io.FileInputStream;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseInizialization {
    @PostConstruct
    public void initializeApp(){
        FileInputStream serviceAccount = null;
        try{
            serviceAccount = new FileInputStream("./andino-db-firebase-key.json");
            @SuppressWarnings("deprecation")
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
