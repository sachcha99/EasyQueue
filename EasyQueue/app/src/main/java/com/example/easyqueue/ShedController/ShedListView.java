package com.example.easyqueue.ShedController;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.example.easyqueue.Home;
import com.example.easyqueue.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ShedListView extends AppCompatActivity {

    ListView ShedListView;
    List<Shed> ShedList;
    FloatingActionButton BackBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shed_listview);

        ShedList = new ArrayList<>();
        BackBtn = (FloatingActionButton) findViewById(R.id.btn_back);




        ShedList.add(new Shed("111","Pinidiya Shed","Hakmana Rd Matara","Available","Available",200.0,"11.55AM","4.30PM",155,"Available",55.5,"07.00AM","2.30PM",115));
        ShedList.add(new Shed("111","Harischandra Shed","Galle Rd Matara","NotAvailable","NotAvailable",100.0,"1.05PM","12.30AM",512,"NotAvailable",569,"20.55PM","15.55PM",415));
        ShedList.add(new Shed("111","Gamini Shed","Akkuress Rd Matara","Available","Available",104.0,"10.35AM","2.30PM",215,"Available",1440.0,"10.40AM","2.30PM",715));
        ShedList.add(new Shed("111","Udaya Shed","Colombo Rd Colombo","NotAvailable","Available",510.0,"8.15AM","2.30PM",145,"NotAvailable",170.0,"11.55","19.30PM",315));
        ShedList.add(new Shed("111","Piliyandala Shed","Kandy Rd Kandy","Available","NotAvailable",1000.0,"9.50AM","3.30PM",151,"Available",310.0,"8.25AM","21.30PM",15));


        ShedListView = (ListView) findViewById(R.id.shed_listview);




        ShedListAdapter adapter = new ShedListAdapter(this,R.layout.activity_shed_card_view,ShedList);
        ShedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shed shedClick =   ShedList.get(i);
                Gson gson = new Gson();
                String shedClickData = gson.toJson(shedClick);
                Intent intent = new Intent(ShedListView.this, ShedDetailsView.class);
                intent.putExtra("shedClick",shedClickData);
                startActivity(intent);
            }
        });
        ShedListView.setAdapter(adapter);


        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShedListView.this, Home.class));
            }
        });


    }



    private void showConfirmBox(Shed shedclick) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_queueinbox, null);
        dialogBuilder.setView(dialogView);


        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.btn_cancel);

        // dialogBuilder.setTitle(bookingId);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(Booking_DetailsList.this,EditBookingDetails.class);
//                intent.putExtra("Bookingid",bookingId);
//                intent.putExtra("Roomno",roomNo);
//                intent.putExtra("Adultno",adultNo);
//                intent.putExtra("Childno",childNo);
//                intent.putExtra("Checkin",checkIn);
//                intent.putExtra("Checkout",checkOut);
//                startActivity(intent);


            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b.dismiss();
            }
        });
    }





}

