package com.example.travelapp.ui.valid;

import android.util.Patterns;

public class Validation {

    // A placeholder password validation check
    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    // A placeholder username validation check
    public static boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }
}
