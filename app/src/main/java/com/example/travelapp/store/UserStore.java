package com.example.travelapp.store;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.store.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class UserStore extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public UserStore(Context context) {

        gson = new Gson();
        sharedPreferences = context.getSharedPreferences("Data", MODE_PRIVATE);
    }

    public User getUser() {
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {
        }.getType();
        User user = gson.fromJson(json, type);
        if (user == null) {
            user = new User("undefined", "undefined");
        }
        return user;
    }

    public void setUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(user);
        editor.putString("User", json).apply();
    }

    public void removeUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("User").apply();
    }
}
