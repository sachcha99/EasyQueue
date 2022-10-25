package com.example.easyqueue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.RequestQueue;
import com.example.easyqueue.ShedController.ShedListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;


public class Home extends AppCompatActivity implements  View.OnClickListener {

    FloatingActionButton logoutBtn;
    Button ShedListBtn;
    final String url = "https://192.168.1.104:7249/api/Shed/";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        queue = Volley.newRequestQueue(this);
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

        startActivity(new Intent(Home.this, ShedListView.class));
//        getdata();


    }

    private void Logout() {
    }


    /**
     Create new Shed
     **/
    private void getdata()
    {
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response on Success
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);
    }
}


