package com.example.easyqueue.ShedController;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.easyqueue.Home;
import com.example.easyqueue.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class ShedDetailsView extends AppCompatActivity implements  View.OnClickListener {
    FloatingActionButton BackBtn;
    Button ShedEditBtn,JoinPetrol,JoinDiesel,ExitPetrol,ExitDiesel;
    TextView Title,DotGreen,DotRed;
    TextView ShedName,ShedAddress,ShedStatus,PetrolStatus,PetrolQueueStartTime, PetrolQueueEndTime,DieselStatus, DieselQueueStartTime, DieselQueueEndTime,DieselVehicleCount,PetrolVehicleCount,PetrolLiter;
    TextView AdminPetrolStatus,AdminPetrolQueueStartTime, AdminPetrolQueueEndTime,AdminDieselStatus, AdminDieselQueueStartTime, AdminDieselQueueEndTime,AdminDieselVehicleCount,AdminPetrolVehicleCount,DieselLiter,APetrolLiter,ADieselLiter;
    LinearLayout PetrolLayout,DieselLayout,AdminLayout;
    Shed shedObj;

    String Queue ="Pet";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shed_details_view);

        Gson gson = new Gson();
        String shedClickData = getIntent().getStringExtra("shedClick");
        shedObj = gson.fromJson(shedClickData, Shed.class);
        System.out.println(shedObj.getName());

        Title = findViewById(R.id.txtTitle);
        DotGreen = findViewById(R.id.txt_statusDotGreen);
        DotRed = findViewById(R.id.txt_statusDotRed);

        ShedName = findViewById(R.id.txt_shedName);
        ShedAddress = findViewById(R.id.txt_shedAddress);
        ShedStatus = findViewById(R.id.txt_shedStatus);

        PetrolStatus = findViewById(R.id.txt_petrolStatus);
        PetrolLiter = findViewById(R.id.txt_petrolCount);
        PetrolQueueStartTime = findViewById(R.id.txt_queueStart);
        PetrolQueueEndTime = findViewById(R.id.txt_queueEnd);
        PetrolVehicleCount = findViewById(R.id.txt_noVehicle);

        DieselStatus = findViewById(R.id.txt_dieselStatus);
        DieselLiter = findViewById(R.id.txt_dieselCount);
        DieselQueueStartTime = findViewById(R.id.txt_queueStartDes);
        DieselQueueEndTime = findViewById(R.id.txt_queueEndDes);
        DieselVehicleCount = findViewById(R.id.txt_noVehicleDes);



        AdminPetrolStatus = findViewById(R.id.txt_petrolAdminStatus);
        APetrolLiter = findViewById(R.id.txt_petrolAdminLiter);
        ADieselLiter = findViewById(R.id.txt_dieselLiterAdmin);
        AdminPetrolQueueStartTime = findViewById(R.id.txt_queueStartAdmin);
        AdminPetrolQueueEndTime = findViewById(R.id.txt_queueEndAdmin);
        AdminPetrolVehicleCount = findViewById(R.id.txt_noVehicleAdmin);
        AdminDieselStatus = findViewById(R.id.txt_dieselStatusAdmin);
        AdminDieselQueueStartTime = findViewById(R.id.txt_queueStartDesAdmin);
        AdminDieselQueueEndTime = findViewById(R.id.txt_queueEndDesAdmin);
        AdminDieselVehicleCount = findViewById(R.id.txt_noVehicleDesAdmin);



        JoinPetrol = findViewById(R.id.btn_JoinPet);
        JoinDiesel = findViewById(R.id.btn_joinDes);

        ExitPetrol = findViewById(R.id.btn_ExitPet);
        ExitDiesel = findViewById(R.id.btn_exitDes);
        ShedEditBtn = findViewById(R.id.btn_adminEditShed);

        BackBtn = findViewById(R.id.btn_back);


        PetrolLayout = findViewById(R.id.layoutPetrol);
        DieselLayout = findViewById(R.id.layoutDiesel);
        AdminLayout = findViewById(R.id.layoutAdmnFuel);

        if(shedObj.getStatus().equals("NotAvailable")){
            DotGreen.setVisibility(View.GONE);
            DotRed.setVisibility(View.VISIBLE);
            JoinPetrol.setVisibility(View.GONE);
            JoinDiesel.setVisibility(View.GONE);
        }
        String userType="Admin1";
        if(userType.equals("Admin")){
            AdminLayout.setVisibility(View.VISIBLE);
            PetrolLayout.setVisibility(View.GONE);
            DieselLayout.setVisibility(View.GONE);

        }

        if(Queue.equals("Pet")){
            DieselLayout.setVisibility(View.GONE);
            ExitPetrol.setVisibility(View.VISIBLE);
            JoinPetrol.setVisibility(View.GONE);
        }else if(Queue.equals("des")){
            PetrolLayout.setVisibility(View.GONE);
            ExitDiesel.setVisibility(View.VISIBLE);
            JoinDiesel.setVisibility(View.GONE);
        }else{
            ExitDiesel.setVisibility(View.GONE);
            ExitPetrol.setVisibility(View.GONE);
        }

        if(!shedObj.getPetrolStatus().equals("Available") && shedObj.getPetrolLiter()>1){
            JoinPetrol.setVisibility(View.GONE);
        }



        Title.setText(shedObj.getName());
        ShedName.setText(shedObj.getName());
        ShedAddress.setText(shedObj.getAddress());
        ShedStatus.setText(shedObj.getStatus());

        PetrolStatus.setText(shedObj.getPetrolStatus());
        PetrolLiter.setText("("+Double.toString(shedObj.getPetrolLiter())+"L)");
        PetrolQueueStartTime.setText(shedObj.getPetrolQueueStartTime());
        PetrolQueueEndTime.setText(shedObj.getPetrolQueueEndTime());
        PetrolVehicleCount.setText(Integer.toString(shedObj.getPetrolVehicleCount()));

        DieselStatus.setText(shedObj.getDieselStatus());
        DieselLiter.setText("("+Double.toString(shedObj.getDieselLiter())+"L)");
        DieselQueueStartTime.setText(shedObj.getDieselQueueStartTime());
        DieselQueueEndTime.setText(shedObj.getDieselQueueEndTime());
        DieselVehicleCount.setText(Integer.toString(shedObj.getDieselVehicleCount()));



        AdminPetrolStatus.setText(shedObj.getPetrolStatus());
        APetrolLiter.setText("("+Double.toString(shedObj.getPetrolLiter())+"L)");
        ADieselLiter.setText("("+Double.toString(shedObj.getDieselLiter())+"L)");
        AdminPetrolQueueStartTime.setText(shedObj.getPetrolQueueStartTime());
        AdminPetrolQueueEndTime.setText(shedObj.getPetrolQueueEndTime());
        AdminPetrolVehicleCount.setText(Integer.toString(shedObj.getPetrolVehicleCount()));
        AdminDieselStatus.setText(shedObj.getDieselStatus());
        AdminDieselQueueStartTime.setText(shedObj.getDieselQueueStartTime());
        AdminDieselQueueEndTime.setText(shedObj.getDieselQueueEndTime());
        AdminDieselVehicleCount.setText(Integer.toString(shedObj.getPetrolVehicleCount()));

    }

    protected void onResume() {
        super.onResume();
        JoinDiesel.setOnClickListener(this);
        JoinPetrol.setOnClickListener(this);
        ExitDiesel.setOnClickListener(this);
        ExitPetrol.setOnClickListener(this);
        ShedEditBtn.setOnClickListener(this);
        BackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_JoinPet: JoinPetrolQueue(view);
                break;
            case R.id.btn_joinDes: JoinDieselQueue(view);
                break;
            case R.id.btn_ExitPet: ExitPetrolQueue(view);
                break;
            case R.id.btn_exitDes: ExitDieselQueue(view);
                break;
            case R.id.btn_adminEditShed: EditShed();
                break;
            case R.id.btn_back: GoToBack();
                break;
        }
    }




    private void GoToBack() {
        startActivity(new Intent(ShedDetailsView.this, ShedListView.class));
    }

    private void EditShed() {
    }
    private void JoinPetrolQueue(View view) {
        showConfirmBox(shedObj,view,"Petrol");
    }

    private void JoinDieselQueue(View view) {
        showConfirmBox(shedObj,view,"Diesel");
    }
    private void ExitDieselQueue(View view) {
        showExitPumpBox(shedObj,view,"Petrol");
    }
    private void ExitPetrolQueue(View view) {

        showExitPumpBox(shedObj,view,"Diesel");
    }


    private void showConfirmBox(Shed shedObj, View view, String type) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getRootView().getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_queueinbox, null);
        dialogBuilder.setView(dialogView);

        TextView desc = (TextView) dialogView.findViewById(R.id.txt_dialogDesc);
        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        if(type=="Petrol"){
            desc.setText("Are You Want To Join Petrol Queue ?");
        }else{
            desc.setText("Are You Want To Join Diesel Queue ?");
        }
        // dialogBuilder.setTitle(bookingId);
        final AlertDialog b = dialogBuilder.create();
        b.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        b.show();


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(type=="Petrol"){
                    Date currentTime = Calendar.getInstance().getTime();
                    System.out.println(currentTime.getHours()+" "+currentTime.getMinutes());
                }else{
                    Date currentTime = Calendar.getInstance().getTime();
                    System.out.println(currentTime);
                }
                b.dismiss();
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












    private void showExitPumpBox(Shed shedObj, View view, String type) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getRootView().getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_queueexitbox, null);
        dialogBuilder.setView(dialogView);

        TextView desc = (TextView) dialogView.findViewById(R.id.txt_dialogDesc);
        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.btn_confirmwithfuel);
        final EditText capacity = (EditText) dialogView.findViewById(R.id.pumpExitCapacity);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.btn_cancelwithoutfuel);
        if(type=="Petrol"){
            desc.setText("Are You Want To Join Petrol Queue ?");
        }else{
            desc.setText("Are You Want To Join Diesel Queue ?");
        }
        // dialogBuilder.setTitle(bookingId);
        final AlertDialog b = dialogBuilder.create();
        b.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        b.show();


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(type=="Petrol"){
                    Date currentTime = Calendar.getInstance().getTime();
                    System.out.println(currentTime.getHours()+" "+currentTime.getMinutes());
                }else{
                    Date currentTime = Calendar.getInstance().getTime();
                    System.out.println(currentTime);
                }
                b.dismiss();
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