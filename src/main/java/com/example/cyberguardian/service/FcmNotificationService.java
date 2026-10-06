package com.example.cyberguardian.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FcmNotificationService {

    public String sendNotification(
            String fcmToken,
            String title,
            String body) {

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(notification)
                .build();

        try {

            String response = FirebaseMessaging
                    .getInstance()
                    .send(message);

            System.out.println("FCM notification sent: " + response);

            return response;

        } catch (Exception e) {

            System.out.println(
                    "FCM notification failed: " + e.getMessage()
            );

            throw new RuntimeException(
                    "Failed to send FCM notification",
                    e
            );
        }
    }
}