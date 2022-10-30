package com.example.easyqueue.ShedController;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easyqueue.Auth.Login;
import com.example.easyqueue.DBHelper.DBHelper;
import com.example.easyqueue.Home;
import com.example.easyqueue.R;
import com.example.easyqueue.UserController.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ShedDetailsView extends AppCompatActivity implements  View.OnClickListener {
    FloatingActionButton BackBtn;
    Button ShedEditBtn,JoinPetrol,JoinDiesel,ExitPetrol,ExitDiesel;
    TextView Title,DotGreen,DotRed;
    TextView ShedName,ShedAddress,ShedStatus,PetrolStatus,PetrolQueueStartTime, PetrolQueueEndTime,DieselStatus, DieselQueueStartTime, DieselQueueEndTime,DieselVehicleCount,PetrolVehicleCount,PetrolLiter,LastUpdate,PetrolArrived,PetrolFinished,DieselArrived,DieselFinished;
    TextView AdminPetrolStatus,AdminPetrolQueueStartTime, AdminPetrolQueueEndTime,AdminDieselStatus, AdminDieselQueueStartTime, AdminDieselQueueEndTime,AdminDieselVehicleCount,AdminPetrolVehicleCount,DieselLiter,APetrolLiter,ADieselLiter;
    LinearLayout PetrolLayout,DieselLayout,AdminLayout;
    Shed shedObj;
    User userObj;
    DBHelper DB;
    SQLiteDatabase sqLiteDatabaseObj;
    String url = "http://172.28.7.197:5000/shedDetails/update/";
    RequestQueue allSheds;
    String QueueType;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shed_details_view);
        allSheds = Volley.newRequestQueue(this);
        Gson gson = new Gson();
        String shedClickData = getIntent().getStringExtra("shedClick");
        shedObj = gson.fromJson(shedClickData, Shed.class);


        sharedPreferences = getSharedPreferences("UserSharedPref",MODE_PRIVATE);
        String json = sharedPreferences.getString("Token", "");
        userObj = gson.fromJson(json, User.class);
        QueueType=userObj.getFuelType();
        DB = new DBHelper(this);

        Title = findViewById(R.id.txtTitle);
        DotGreen = findViewById(R.id.txt_statusDotGreen);
        DotRed = findViewById(R.id.txt_statusDotRed);

        ShedName = findViewById(R.id.txt_shedName);
        ShedAddress = findViewById(R.id.txt_shedAddress);
        ShedStatus = findViewById(R.id.txt_shedStatus);
        LastUpdate= findViewById(R.id.txt_updateAT);



        PetrolStatus = findViewById(R.id.txt_petrolStatus);
        PetrolLiter = findViewById(R.id.txt_petrolCount);
        PetrolArrived = findViewById(R.id.txt_arrivedPetTime);
        PetrolFinished = findViewById(R.id.txt_finishedPetTime);
        PetrolQueueStartTime = findViewById(R.id.txt_queueStart);
        PetrolQueueEndTime = findViewById(R.id.txt_queueEnd);
        PetrolVehicleCount = findViewById(R.id.txt_noVehicle);

        DieselStatus = findViewById(R.id.txt_dieselStatus);
        DieselLiter = findViewById(R.id.txt_dieselCount);
        DieselArrived = findViewById(R.id.txt_arrivedDesTime);
        DieselFinished= findViewById(R.id.txt_finishedDesTime);
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
        if(userObj.getShedId().equals("-")) {
            ExitDiesel.setVisibility(View.GONE);
            ExitPetrol.setVisibility(View.GONE);
            if (shedObj.getStatus().equals("NotAvailable")) {
                DotGreen.setVisibility(View.GONE);
                DotRed.setVisibility(View.VISIBLE);
                JoinPetrol.setVisibility(View.GONE);
                JoinDiesel.setVisibility(View.GONE);
            }

            if (!shedObj.getPetrolStatus().equals("Available")) {
                JoinPetrol.setVisibility(View.GONE);
            }
            if (!shedObj.getDieselStatus().equals("Available")) {
                JoinDiesel.setVisibility(View.GONE);
            }

            if (!shedObj.getPetrolStatus().equals("Available") && shedObj.getPetrolLiter() > 1) {
                JoinPetrol.setVisibility(View.GONE);
            }

            if (!shedObj.getDieselStatus().equals("Available") && shedObj.getDieselLiter() > 1) {
                JoinDiesel.setVisibility(View.GONE);
            }
        }else if(shedObj.getId().equals(userObj.getShedId())){
            if (QueueType.equals("Petrol")) {
                DieselLayout.setVisibility(View.GONE);
                ExitPetrol.setVisibility(View.VISIBLE);
                JoinPetrol.setVisibility(View.GONE);
            } else if (QueueType.equals("Diesel")) {
                PetrolLayout.setVisibility(View.GONE);
                ExitDiesel.setVisibility(View.VISIBLE);
                JoinDiesel.setVisibility(View.GONE);
            } else {
                ExitDiesel.setVisibility(View.GONE);
                ExitPetrol.setVisibility(View.GONE);
            }
        }
//            if (shedObj.getStatus().equals("NotAvailable")) {
//                DotGreen.setVisibility(View.GONE);
//                DotRed.setVisibility(View.VISIBLE);
//                JoinPetrol.setVisibility(View.GONE);
//                JoinDiesel.setVisibility(View.GONE);
//            }
//            String userType = "Admin1";
//            if (userType.equals("Admin")) {
//                AdminLayout.setVisibility(View.VISIBLE);
//                PetrolLayout.setVisibility(View.GONE);
//                DieselLayout.setVisibility(View.GONE);
//
//            }
//
//            if (QueueType.equals("Petrol")) {
//                DieselLayout.setVisibility(View.GONE);
//                ExitPetrol.setVisibility(View.VISIBLE);
//                JoinPetrol.setVisibility(View.GONE);
//            } else if (QueueType.equals("Diesel")) {
//                PetrolLayout.setVisibility(View.GONE);
//                ExitDiesel.setVisibility(View.VISIBLE);
//                JoinDiesel.setVisibility(View.GONE);
//            } else {
//                ExitDiesel.setVisibility(View.GONE);
//                ExitPetrol.setVisibility(View.GONE);
//            }
//
//            if (!shedObj.getPetrolStatus().equals("Available")) {
//                JoinPetrol.setVisibility(View.GONE);
//            }
//            if (!shedObj.getDieselStatus().equals("Available")) {
//                JoinDiesel.setVisibility(View.GONE);
//            }
//
//            if (!shedObj.getPetrolStatus().equals("Available") && shedObj.getPetrolLiter() > 1) {
//                JoinPetrol.setVisibility(View.GONE);
//            }



        Title.setText(shedObj.getName());
        ShedName.setText(shedObj.getName());
        ShedAddress.setText(shedObj.getAddress());
        ShedStatus.setText(shedObj.getStatus());

        LastUpdate.setText(shedObj.getLastUpdate().substring(0, 10)+" / "+shedObj.getLastUpdate().substring(11, 16));

        PetrolStatus.setText(shedObj.getPetrolStatus());
        PetrolLiter.setText("("+Double.toString(shedObj.getPetrolLiter())+"L)");
        PetrolArrived.setText(shedObj.getPetrolArrivedime());
        PetrolFinished.setText(shedObj.getPetrolFinishedTime());
        PetrolQueueStartTime.setText(shedObj.getPetrolQueueStartTime());
        PetrolQueueEndTime.setText(shedObj.getPetrolQueueEndTime());
        PetrolVehicleCount.setText(Integer.toString(shedObj.getPetrolVehicleCount()));

        DieselStatus.setText(shedObj.getDieselStatus());
        DieselLiter.setText("("+Double.toString(shedObj.getDieselLiter())+"L)");
        DieselArrived.setText(shedObj.getDieselArrivedime());
        DieselFinished.setText(shedObj.getDieselFinishedTime());
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
        Intent intent = new Intent(ShedDetailsView.this, ShedListView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
        showExitPumpBox(shedObj,view,"Diesel");
    }
    private void ExitPetrolQueue(View view) {

        showExitPumpBox(shedObj,view,"Petrol");
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
                    UpdateJoinQueue(shedObj,"Petrol");
                    boolean status = updateUserDB(shedObj.getId(),"Petrol");
                    userObj.setFuelType("Petrol");
                    userObj.setShedId(shedObj.getId());
                    if(status == true){
                        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(userObj);
                        prefsEditor.putString("Token", json);
                        prefsEditor.commit();
                        Toast.makeText(ShedDetailsView.this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ShedDetailsView.this, ShedListView.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ShedDetailsView.this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    UpdateJoinQueue(shedObj,"Diesel");
                    userObj.setFuelType("Diesel");
                    userObj.setShedId(shedObj.getId());
                    boolean status =  updateUserDB(shedObj.getId(),"Diesel");
                    if(status == true){
                        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(userObj);
                        prefsEditor.putString("Token", json);
                        prefsEditor.commit();
                        Toast.makeText(ShedDetailsView.this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ShedDetailsView.this, ShedListView.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ShedDetailsView.this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }

                }
                b.dismiss();
            }


        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b.dismiss();
            }
        });
    }

    private boolean updateUserDB(String id, String type) {
        sqLiteDatabaseObj = DB.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(DBHelper.Column_FuelType,type);
        contentValues.put(DBHelper.Column_ShedID, id);
        return sqLiteDatabaseObj.update(DBHelper.TABLE_NAME, contentValues, DBHelper.Column_Email + "=?", new String[]{userObj.getEmail()})>0;

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
            desc.setText("Are You Want To Exit Petrol Queue ?");
        }else{
            desc.setText("Are You Want To Exit Diesel Queue ?");
        }
        // dialogBuilder.setTitle(bookingId);
        final AlertDialog b = dialogBuilder.create();
        b.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        b.show();


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Capacity = capacity.getText().toString();
                if(Capacity.isEmpty()){
                    Toast.makeText(ShedDetailsView.this, "Please enter the pump fuel capacity.", Toast.LENGTH_LONG).show();
                }else if(Double.parseDouble(Capacity)>0 ){
                    userObj.setFuelType("-");
                    userObj.setShedId("-");
                    if(type=="Petrol"){
                        if(Double.parseDouble(Capacity)<=shedObj.getPetrolLiter()){
                            UpdateSheds(shedObj,Double.parseDouble(Capacity),"Petrol");
                            boolean status =  updateUserDB("-","-");
                            if(status == true){
                                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(userObj);
                                prefsEditor.putString("Token", json);
                                prefsEditor.commit();
                                Toast.makeText(ShedDetailsView.this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ShedDetailsView.this, ShedListView.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(ShedDetailsView.this, "Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ShedDetailsView.this, "Please enter the valid  pump fuel capacity.", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        if(Double.parseDouble(Capacity)<=shedObj.getDieselLiter()){
                            UpdateSheds(shedObj,Double.parseDouble(Capacity),"Diesel");
                            boolean status =  updateUserDB("-","-");
                            if(status == true){
                                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(userObj);
                                prefsEditor.putString("Token", json);
                                prefsEditor.commit();
                                Toast.makeText(ShedDetailsView.this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ShedDetailsView.this, ShedListView.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(ShedDetailsView.this, "Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ShedDetailsView.this, "Please enter the valid  pump fuel capacity.", Toast.LENGTH_LONG).show();
                        }

                    }

                }else{
                    Toast.makeText(ShedDetailsView.this, "Please enter the valid pump fuel capacity.", Toast.LENGTH_LONG).show();
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
                userObj.setFuelType("-");
                userObj.setShedId("-");
                if(type=="Petrol"){
                        UpdateSheds(shedObj,00.0,"Petrol");
                        boolean status =  updateUserDB("-","-");
                        if(status == true){
                            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(userObj);
                            prefsEditor.putString("Token", json);
                            prefsEditor.commit();
                            Toast.makeText(ShedDetailsView.this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ShedDetailsView.this, ShedListView.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else{
                            Toast.makeText(ShedDetailsView.this, "Update Failed", Toast.LENGTH_SHORT).show();
                        }


                }else{
                        UpdateSheds(shedObj,00.0,"Diesel");
                        boolean status =  updateUserDB("-","-");
                        if(status == true){
                            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(userObj);
                            prefsEditor.putString("Token", json);
                            prefsEditor.commit();
                            Toast.makeText(ShedDetailsView.this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ShedDetailsView.this, ShedListView.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else{
                            Toast.makeText(ShedDetailsView.this, "Update Failed", Toast.LENGTH_SHORT).show();
                        }


                }
                b.dismiss();
            }
        });
    }


    /**
     Update Shed
     *
     *
     * @return
     * @param*/
    private void UpdateSheds(Shed shedObj, Double capacity, String type)
    {
        url = "http://172.28.7.197:5000/shedDetails/update/"+shedObj.getId();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String time = dateFormat.format(new Date()).toString();
        if(type.equals("Petrol")){
            shedObj.setPetrolLiter(shedObj.getPetrolLiter() - capacity);
            shedObj.setPetrolVehicleCount(shedObj.getPetrolVehicleCount()-1);
            if(shedObj.getPetrolLiter()==0){
                shedObj.setPetrolStatus("NotAvailable");
                shedObj.setPetrolFinishedTime(time);
            }
            if(shedObj.getPetrolVehicleCount()==0){
                shedObj.setPetrolQueueEndTime(time);
            }
        }else{
            shedObj.setDieselLiter(shedObj.getDieselLiter() - capacity);
            shedObj.setDieselVehicleCount(shedObj.getDieselVehicleCount()-1);
            if(shedObj.getDieselLiter()==0){
                shedObj.setDieselStatus("NotAvailable");
                shedObj.setDieselFinishedTime(time);
            }
            if(shedObj.getDieselVehicleCount()==0){
                shedObj.setDieselQueueEndTime(time);
            }
        }
        if(shedObj.getPetrolStatus().equals("NotAvailable") && shedObj.getDieselStatus().equals("NotAvailable")){
            shedObj.setStatus("NotAvailable");
        }
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        Intent intent = new Intent(ShedDetailsView.this, ShedListView.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("petrolStatus", shedObj.getPetrolStatus());
                params.put("dieselStatus", shedObj.getDieselStatus());
                params.put("petrolQueueLength", String.valueOf(shedObj.getPetrolVehicleCount()));
                params.put("dieselQueueLength", String.valueOf(shedObj.getDieselVehicleCount()));
                params.put("petrolCapacity",  String.valueOf(shedObj.getPetrolLiter()));
                params.put("dieselCapacity",  String.valueOf(shedObj.getDieselLiter()));
                params.put("petrolQueueEndTime",  String.valueOf(shedObj.getPetrolQueueEndTime()));
                params.put("dieselQueueEndTime",  String.valueOf(shedObj.getDieselQueueEndTime()));
                params.put("petrolFinishTime",  String.valueOf(shedObj.getPetrolFinishedTime()));
                params.put("dieselFinishTime",  String.valueOf(shedObj.getDieselFinishedTime()));
                return params;
            }

        };

        allSheds.add(putRequest);
    }











    /**
     Join Shed
     *
     *
     * @return
     * @param*/
    private void UpdateJoinQueue(Shed shedObj,String type)
    {
        url = "http://192.168.1.105:5000/shedDetails/update/"+shedObj.getId();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String time = dateFormat.format(new Date()).toString();
        if(type.equals("Petrol")){
            if(shedObj.getPetrolVehicleCount()==0){
                shedObj.setPetrolQueueStartTime(time);
            }
            shedObj.setPetrolVehicleCount(shedObj.getPetrolVehicleCount()+1);
            shedObj.setPetrolQueueEndTime("  -");
        }else{
            if(shedObj.getDieselVehicleCount()==0){
                shedObj.setDieselQueueStartTime(time);
            }
            shedObj.setDieselVehicleCount(shedObj.getDieselVehicleCount()+1);
            shedObj.setDieselQueueEndTime("  -");
        }
        System.out.println(shedObj.getPetrolQueueEndTime());
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("petrolQueueLength", String.valueOf(shedObj.getPetrolVehicleCount()));
                params.put("dieselQueueLength", String.valueOf(shedObj.getDieselVehicleCount()));
                params.put("petrolQueueEndTime",  String.valueOf(shedObj.getPetrolQueueEndTime()));
                params.put("dieselQueueEndTime",  String.valueOf(shedObj.getDieselQueueEndTime()));
                params.put("petrolQueueStartTime",  String.valueOf(shedObj.getPetrolQueueStartTime()));
                params.put("dieselQueueStartTime",  String.valueOf(shedObj.getDieselQueueStartTime()));
                return params;
            }

        };

        allSheds.add(putRequest);
    }
}