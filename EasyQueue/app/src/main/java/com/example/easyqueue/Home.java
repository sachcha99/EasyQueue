package com.example.easyqueue;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.RequestQueue;
import com.example.easyqueue.Auth.Login;
import com.example.easyqueue.DBHelper.DBHelper;
import com.example.easyqueue.ShedController.EditShedDetails;
import com.example.easyqueue.ShedController.ShedListView;
import com.example.easyqueue.UserController.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;


public class Home extends AppCompatActivity implements  View.OnClickListener {

    FloatingActionButton logoutBtn;
    TextView TitleTxt,TxtThings;
    Button ShedListBtn,UpdateBtn;
    LinearLayout ImageLayer,AdminHomeView,imageAdminLayer,AllShedLayer;
    final String url = "https://192.168.1.104:7249/api/Shed/";
    RequestQueue queue;


    private SQLiteDatabase sqLiteDatabaseObj;
    private Cursor cursor;
    private String email,id;
    DBHelper DB;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSharedPref",MODE_PRIVATE);


        Gson gsons = new Gson();
        String jsong = sharedPreferences.getString("Token", "");
        User obj = gsons.fromJson(jsong, User.class);




        queue = Volley.newRequestQueue(this);
        logoutBtn = findViewById(R.id.btn_Logout);
        ShedListBtn = findViewById(R.id.btn_ShedList);
        UpdateBtn = findViewById(R.id.btn_adminEditShed);
        TitleTxt = findViewById(R.id.txt_title);
        TxtThings = findViewById(R.id.txt_things);
        ImageLayer = findViewById(R.id.user_img);
        AllShedLayer = findViewById(R.id.allshedLayout);
        imageAdminLayer = findViewById(R.id.admin_img);
        AdminHomeView =findViewById(R.id.admin_home);

        logoutBtn.setOnClickListener(this);
        UpdateBtn.setOnClickListener(this);
        ShedListBtn.setOnClickListener(this);
        email = getIntent().getStringExtra("email");
        DB = new DBHelper(this);
        if(obj!= null){
            user =obj;
        }else{
            user =  loadData();
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(user);
            prefsEditor.putString("Token", json);
            prefsEditor.commit();
        }


//        TitleTxt.setText("Hey "+user.getName());
        TitleTxt.setText("Hey Madhura");
        System.out.println(user.getRole());
        if(user.getRole().equals("Owner")){
            TxtThings.setVisibility(View.GONE);
            AllShedLayer.setVisibility(View.GONE);
            ImageLayer.setVisibility(View.GONE);
            AdminHomeView.setVisibility(View.VISIBLE);
            imageAdminLayer.setVisibility(View.VISIBLE);
        }
    }

    protected void onResume() {
        super.onResume();
        logoutBtn.setOnClickListener(this);
        UpdateBtn.setOnClickListener(this);
        ShedListBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Logout: Logout();
                break;
            case R.id.btn_ShedList: GoToShedList();
                break;
            case R.id.btn_adminEditShed: GoToUpdate();
                break;
        }
    }

    private void GoToUpdate() {
        Intent intent= new Intent(Home.this, EditShedDetails.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void GoToShedList() {
        Intent intent= new Intent(Home.this, ShedListView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
//        getdata();


    }

    private void Logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSharedPref",MODE_PRIVATE);
        sharedPreferences.edit().remove("Token").commit();
        startActivity(new Intent(Home.this, Login.class));
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

    @SuppressLint("Range")
    public User loadData() {
        sqLiteDatabaseObj = DB.getWritableDatabase();
        User user = new User();
        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(DBHelper.TABLE_NAME, null, " " + DBHelper.Column_Email + "=?", new String[]{email}, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                // Storing Password associated with entered email.
                String id = cursor.getString(cursor.getColumnIndex(DBHelper.Column_ID));
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.Column_Name));
                String email = cursor.getString(cursor.getColumnIndex(DBHelper.Column_Email));
                String mobile = cursor.getString(cursor.getColumnIndex(DBHelper.Column_Mobile));
                String address = cursor.getString(cursor.getColumnIndex(DBHelper.Column_Address));
                String fuelType = cursor.getString(cursor.getColumnIndex(DBHelper.Column_FuelType));
                String role = cursor.getString(cursor.getColumnIndex(DBHelper.Column_Role));
                String shedID = cursor.getString(cursor.getColumnIndex(DBHelper.Column_ShedID));

                 user = new User(id,name,email,address,mobile,role,fuelType,shedID);

                // Closing cursor.
                cursor.close();
            }
        }
        return user;
    }
}


