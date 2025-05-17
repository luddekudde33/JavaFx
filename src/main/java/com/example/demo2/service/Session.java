package com.example.demo2.service;

import com.example.demo2.Model.User;

public class Session {
    private static User currentUser;
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
}
