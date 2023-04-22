package com.example.chatmessenger.notifications.entity

data class PushNotification(
    val data: NotificationData,
    val to: String
)