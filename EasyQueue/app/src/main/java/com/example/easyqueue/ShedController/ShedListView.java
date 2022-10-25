package com.example.easyqueue.ShedController;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.example.easyqueue.Home;
import com.example.easyqueue.R;

import java.util.ArrayList;
import java.util.List;


public class ShedListView extends AppCompatActivity {

    ListView ShedListView;
    List<Shed> ShedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shed_listview);

        ShedList = new ArrayList<>();




        ShedList.add(new Shed(R.drawable.bg_image,111,"Pinidiya Shed","Hakmana Rd Matara","Open","11.55","2.30"));
        ShedList.add(new Shed(R.drawable.bg_image,111,"name1","address","petrol","1","2"));
        ShedList.add(new Shed(R.drawable.bg_image,111,"name1","address","diesel","1","2"));
        ShedList.add(new Shed(R.drawable.bg_image,111,"name","address","status","1","2"));
        ShedList.add(new Shed(R.drawable.bg_image,111,"name","address","status","1","2"));

        ShedListView = (ListView) findViewById(R.id.shed_listview);




        ShedListAdapter adapter = new ShedListAdapter(this,R.layout.activity_shed_view_card,ShedList);
        ShedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shed shedClick = ShedList.get(i);
                System.out.println(shedClick.getAddress());
                Intent intent = new Intent(ShedListView.this, EditShedDetails.class);
                startActivity(intent);

            }
        });
        ShedListView.setAdapter(adapter);




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

