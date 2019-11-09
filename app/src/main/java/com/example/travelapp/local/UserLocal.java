package com.example.travelapp.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.travelapp.R;

public class UserLocal {

    private static String userId;
    private static String token;

    public static void getStore(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.store_name), Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(String.valueOf(R.string.userId_store), null);
        token = sharedPreferences.getString(String.valueOf(R.string.token_store), null);
    }

    public static void setStore(Context context, String userId, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.store_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(R.string.userId_store), userId);
        editor.putString(String.valueOf(R.string.token_store), token);
        editor.apply();
    }

    public static String getToken() {
        return token;
    }

    public static String getUserId() {
        return userId;
    }
}
