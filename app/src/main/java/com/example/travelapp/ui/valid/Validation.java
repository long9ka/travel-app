package com.example.travelapp.ui.valid;

import android.util.Patterns;

public class Validation {

    public static boolean isFullNameValid(String fullName) {
        return fullName != null && fullName.trim().length() > 0;
    }

    public static boolean isEmailValid(String email) {
        return email != null && email.trim().length() > 0 && email.contains("@");
    }

    public static boolean isPhoneValid(String phone) {
        return phone != null  && phone.trim().length() > 0;
    }

    public static boolean isAddressValid(String address) {
        return address != null && address.trim().length() > 0;
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

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
