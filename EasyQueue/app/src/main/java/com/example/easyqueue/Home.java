package com.example.easyqueue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Home extends AppCompatActivity implements  View.OnClickListener {

    FloatingActionButton logoutBtn;
    Button ShedListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoutBtn = findViewById(R.id.btn_Logout);
        ShedListBtn = findViewById(R.id.btn_ShedList);
        logoutBtn.setOnClickListener(this);
        ShedListBtn.setOnClickListener(this);
    }

    protected void onResume() {
        super.onResume();
        logoutBtn.setOnClickListener(this);
        ShedListBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Logout: Logout();
                break;
            case R.id.btn_ShedList: GoToShedList();
                break;
        }
    }

    private void GoToShedList() {
        startActivity(new Intent(Home.this, ShedHomeActivity.class));
    }

    private void Logout() {
    }

}