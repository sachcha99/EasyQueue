package com.example.easyqueue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.easyqueue.Auth.Login;
import com.example.easyqueue.UserController.User;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_main);
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        Gson gsons = new Gson();
        String jsong = mPrefs.getString("Token", "");
        User obj = gsons.fromJson(jsong, User.class);
        System.out.println(obj+"dddddddddddddddddddddddddddddddd");
        if(obj!= null){
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

    }
}