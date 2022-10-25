package com.example.easyqueue.ShedController;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;


import com.example.easyqueue.APIClient;
import com.example.easyqueue.APIInterface;
import com.example.easyqueue.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShedListView extends AppCompatActivity {

    ListView ShedListView;
    List<Shed> ShedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shed_listview);

        ShedList = new ArrayList<>();




        ShedList.add(new Shed(R.drawable.bg_image,111,"name","address","status","1","2"));
        ShedList.add(new Shed(R.drawable.bg_image,111,"name","address","status","1","2"));
        ShedList.add(new Shed(R.drawable.bg_image,111,"name","address","status","1","2"));
        ShedList.add(new Shed(R.drawable.bg_image,111,"name","address","status","1","2"));
        ShedList.add(new Shed(R.drawable.bg_image,111,"name","address","status","1","2"));

        ShedListView = (ListView) findViewById(R.id.shed_listview);

        ShedListAdapter adapter = new ShedListAdapter(this,R.layout.activity_shed_card_view,ShedList);
        ShedListView.setAdapter(adapter);




    }




}

