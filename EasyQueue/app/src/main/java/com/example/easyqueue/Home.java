package com.example.easyqueue;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.RequestQueue;
import com.example.easyqueue.Auth.Login;
import com.example.easyqueue.DBHelper.DBHelper;
import com.example.easyqueue.ShedController.EditShedDetails;
import com.example.easyqueue.ShedController.Shed;
import com.example.easyqueue.ShedController.ShedDetailsView;
import com.example.easyqueue.ShedController.ShedListAdapter;
import com.example.easyqueue.ShedController.ShedListView;
import com.example.easyqueue.UserController.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Home extends AppCompatActivity implements  View.OnClickListener {

    FloatingActionButton logoutBtn;
    TextView TitleTxt,TxtThings;
    Button ShedListBtn,UpdateBtn,ExitPetrol,ExitDiesel;
    TextView ShedName,ShedAddress,ShedStatus,PetrolStatus,PetrolQueueStartTime, PetrolQueueEndTime,DieselStatus, DieselQueueStartTime, DieselQueueEndTime,DieselVehicleCount,PetrolVehicleCount,PetrolLiter,LastUpdate,PetrolArrived,PetrolFinished,DieselArrived,DieselFinished;
    TextView Title,DotGreen,DotRed,DieselLiter,AdminPetArrived,AdminDesArrived,AdminPetFinished,AdminDesFinished,DotGreenAdmin,DotRedAdmin;
    LinearLayout ImageLayer,AdminHomeView,imageAdminLayer,AllShedLayer,UserQueueLayout,PetrolLayout,DieselLayout;
    TextView ShedNameAdmin,ShedAddressAdmin,ShedStatusAdmin,AdminPetrolStatus,AdminPetrolQueueStartTime, AdminPetrolQueueEndTime,AdminDieselStatus, AdminDieselQueueStartTime, AdminDieselQueueEndTime,AdminDieselVehicleCount,AdminPetrolVehicleCount,APetrolLiter,ADieselLiter;
    RequestQueue queue;
    Shed QueueShed;
    User obj;
    List<Shed> ShedQueueList;


    private SQLiteDatabase sqLiteDatabaseObj;
    private Cursor cursor;
    private String email,id;
    DBHelper DB;
    User user;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);






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
        UserQueueLayout = findViewById(R.id.userQueueLayout);

        logoutBtn.setOnClickListener(this);
        UpdateBtn.setOnClickListener(this);
        ShedListBtn.setOnClickListener(this);


        Title = findViewById(R.id.txt_titleName);
        DotGreen = findViewById(R.id.txt_statusDotGreen);
        DotRed = findViewById(R.id.txt_statusDotRed);

        ShedName = findViewById(R.id.txt_shedName);
        ShedAddress = findViewById(R.id.txt_shedAddress);
        ShedStatus = findViewById(R.id.txt_shedStatus);

        ShedNameAdmin = findViewById(R.id.txt_shedNameAdmin);
        ShedAddressAdmin = findViewById(R.id.txt_shedAddressAdmin);
        ShedStatusAdmin = findViewById(R.id.txt_shedStatusAdmin);

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
        AdminPetArrived = findViewById(R.id.txt_arrivedPetTimeAdmin);
        AdminDesArrived = findViewById(R.id.txt_arrivedDesTimeAdmin);
        AdminPetFinished = findViewById(R.id.txt_finishedPetTimeAdmin);
        AdminDesFinished = findViewById(R.id.txt_finishedDesTimeAdmin);
        DotGreenAdmin = findViewById(R.id.txt_statusDotGreenAdmin);
        DotRedAdmin = findViewById(R.id.txt_statusDotRedAdmin);


        ExitPetrol = findViewById(R.id.btn_ExitPet);
        ExitDiesel = findViewById(R.id.btn_exitDes);

        PetrolLayout = findViewById(R.id.layoutPetrol);
        DieselLayout = findViewById(R.id.layoutDiesel);




        email = getIntent().getStringExtra("email");
        DB = new DBHelper(this);



        CheckShed();

        }


    protected void onResume() {
        super.onResume();
        logoutBtn.setOnClickListener(this);
        UpdateBtn.setOnClickListener(this);
        ShedListBtn.setOnClickListener(this);
        ExitDiesel.setOnClickListener(this);
        ExitPetrol.setOnClickListener(this);
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

    private void CheckShed(){
        sharedPreferences = getSharedPreferences("UserSharedPref",MODE_PRIVATE);
        Gson gsons = new Gson();
        String jsong = sharedPreferences.getString("Token", "");
        obj = gsons.fromJson(jsong, User.class);
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
        if(user.getRole().equals("Owner")){
            TxtThings.setVisibility(View.GONE);
            AllShedLayer.setVisibility(View.GONE);
            ImageLayer.setVisibility(View.GONE);
            AdminHomeView.setVisibility(View.VISIBLE);
            imageAdminLayer.setVisibility(View.VISIBLE);
            getAdminShed();
        }else if(user.getShedId().equals("-")){
            UserQueueLayout.setVisibility(View.GONE);
            ImageLayer.setVisibility(View.VISIBLE);
        }else{
            getUserShed();
        }
        System.out.println(user.getRole());
        TitleTxt.setText("Hey "+user.getName());


    }



//    private void GoToUpdate() {
//        Intent intent= new Intent(Home.this, EditShedDetails.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }

    private void GoToShedList() {
        Intent intent= new Intent(Home.this, ShedListView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    private void Logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSharedPref",MODE_PRIVATE);
        sharedPreferences.edit().remove("Token").commit();
        startActivity(new Intent(Home.this, Login.class));
    }


    /**
     Create User Shed
     **/
    private void getUserShed()
    {
        ShedQueueList = new ArrayList<Shed>();
        String url = "https://easy-queue-application.herokuapp.com/shedDetails/"+user.getShedId();
        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray  response) {
                        // display response on Success
//                        Log.d("Response", response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject responseObj = response.getJSONObject(i);
                                System.out.println(responseObj);
                                ShedQueueList.add(new Shed(
                                        responseObj.getString("_id"),
                                        responseObj.getString("ownerID"),
                                        responseObj.getString("shedName"),
                                        responseObj.getString("address"),
                                        responseObj.getString("shedStatus"),
                                        responseObj.getString("petrolStatus"),
                                        Double.parseDouble(responseObj.getString("petrolCapacity")),
                                        responseObj.getString("petrolQueueStartTime"),
                                        responseObj.getString("petrolQueueEndTime"),
                                        Integer.parseInt(responseObj.getString("petrolQueueLength")),
                                        responseObj.getString("dieselStatus"),
                                        Double.parseDouble(responseObj.getString("dieselCapacity")),
                                        responseObj.getString("dieselQueueStartTime"),
                                        responseObj.getString("dieselQueueEndTime"),
                                        Integer.parseInt(responseObj.getString("dieselQueueLength")),
                                        responseObj.getString("petrolArrivalTime"),
                                        responseObj.getString("petrolFinishTime"),
                                        responseObj.getString("dieselArrivalTime"),
                                        responseObj.getString("dieselFinishTime"),
                                        responseObj.getString("updatedAt"))

                                );


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //call here
                            onSuccess(ShedQueueList);
                        }
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




    public void onSuccess(List<Shed> list){
                System.out.println("XXXX"+list.get(0).getName());
                Title.setText(list.get(0).getName());
                ShedName.setText(list.get(0).getName());
                ShedAddress.setText(list.get(0).getAddress());
                ShedStatus.setText(list.get(0).getStatus());

                LastUpdate.setText(list.get(0).getLastUpdate().substring(0, 10)+" / "+list.get(0).getLastUpdate().substring(11, 16));

                PetrolStatus.setText(list.get(0).getPetrolStatus());
                PetrolLiter.setText("("+Double.toString(list.get(0).getPetrolLiter())+"L)");
                PetrolArrived.setText(list.get(0).getPetrolArrivedime());
                PetrolFinished.setText(list.get(0).getPetrolFinishedTime());
                PetrolQueueStartTime.setText(list.get(0).getPetrolQueueStartTime());
                PetrolQueueEndTime.setText(list.get(0).getPetrolQueueEndTime());
                PetrolVehicleCount.setText(Integer.toString(list.get(0).getPetrolVehicleCount()));

                DieselStatus.setText(list.get(0).getDieselStatus());
                DieselLiter.setText("("+Double.toString(list.get(0).getDieselLiter())+"L)");
                DieselArrived.setText(list.get(0).getDieselArrivedime());
                DieselFinished.setText(list.get(0).getDieselFinishedTime());
                DieselQueueStartTime.setText(list.get(0).getDieselQueueStartTime());
                DieselQueueEndTime.setText(list.get(0).getDieselQueueEndTime());
                DieselVehicleCount.setText(Integer.toString(list.get(0).getDieselVehicleCount()));

                if (user.getFuelType().equals("Petrol")) {
                    DieselLayout.setVisibility(View.GONE);
                    ExitPetrol.setVisibility(View.VISIBLE);
                } else if (user.getFuelType().equals("Diesel")) {
                    PetrolLayout.setVisibility(View.GONE);
                    ExitDiesel.setVisibility(View.VISIBLE);
                }
                if (list.get(0).getStatus().equals("NotAvailable")) {
                    DotGreen.setVisibility(View.GONE);
                    DotRed.setVisibility(View.VISIBLE);
                }
                ImageLayer.setVisibility(View.GONE);
                UserQueueLayout.setVisibility(View.VISIBLE);

                ExitPetrol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showExitPumpBox(list.get(0),v,"Petrol");
                    }
                });
    }

    private void showExitPumpBox(Shed shedObj, View view, String type) {

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(view.getRootView().getContext());
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
                    Toast.makeText(Home.this, "Please enter the pump fuel capacity.", Toast.LENGTH_LONG).show();
                }else if(Double.parseDouble(Capacity)>0 ){
                    user.setFuelType("-");
                    user.setShedId("-");
                    if(type=="Petrol"){
                        if(Double.parseDouble(Capacity)<=shedObj.getPetrolLiter()){
                            UpdateSheds(shedObj,Double.parseDouble(Capacity),"Petrol");
                            boolean status =  updateUserDB("-","-");
                            if(status == true){
                                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(user);
                                prefsEditor.putString("Token", json);
                                prefsEditor.commit();
                                Toast.makeText(Home.this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
                                CheckShed();
                            }else{
                                Toast.makeText(Home.this, "Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Home.this, "Please enter the valid  pump fuel capacity.", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        if(Double.parseDouble(Capacity)<=shedObj.getDieselLiter()){
                            UpdateSheds(shedObj,Double.parseDouble(Capacity),"Diesel");
                            boolean status =  updateUserDB("-","-");
                            if(status == true){
                                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(user);
                                prefsEditor.putString("Token", json);
                                prefsEditor.commit();
                                Toast.makeText(Home.this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
                                CheckShed();
                            }else{
                                Toast.makeText(Home.this, "Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Home.this, "Please enter the valid  pump fuel capacity.", Toast.LENGTH_LONG).show();
                        }

                    }

                }else{
                    Toast.makeText(Home.this, "Please enter the valid pump fuel capacity.", Toast.LENGTH_LONG).show();
                }


                b.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setFuelType("-");
                user.setShedId("-");
                if(type=="Petrol"){
                    UpdateSheds(shedObj,00.0,"Petrol");
                    boolean status =  updateUserDB("-","-");
                    if(status == true){
                        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        prefsEditor.putString("Token", json);
                        prefsEditor.commit();
                        Toast.makeText(Home.this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
                        CheckShed();
                    }else{
                        Toast.makeText(Home.this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    UpdateSheds(shedObj,00.0,"Diesel");
                    boolean status =  updateUserDB("-","-");
                    if(status == true){
                        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        prefsEditor.putString("Token", json);
                        prefsEditor.commit();
                        Toast.makeText(Home.this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
                        CheckShed();
                    }else{
                        Toast.makeText(Home.this, "Update Failed", Toast.LENGTH_SHORT).show();
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
        String url = "https://easy-queue-application.herokuapp.com/shedDetails/update/"+shedObj.getId();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String time = dateFormat.format(new Date()).toString();
        if(type.equals("Petrol")){
            shedObj.setPetrolLiter(shedObj.getPetrolLiter() - capacity);
            shedObj.setPetrolVehicleCount(shedObj.getPetrolVehicleCount()-1);
            if(shedObj.getPetrolLiter()==0){
                shedObj.setPetrolStatus("Not Available");
                shedObj.setPetrolFinishedTime(time);
            }
            if(shedObj.getPetrolVehicleCount()==0){
                shedObj.setPetrolQueueEndTime(time);
            }
        }else{
            shedObj.setDieselLiter(shedObj.getDieselLiter() - capacity);
            shedObj.setDieselVehicleCount(shedObj.getDieselVehicleCount()-1);
            if(shedObj.getDieselLiter()==0){
                shedObj.setDieselStatus("Not Available");
                shedObj.setDieselFinishedTime(time);
            }
            if(shedObj.getDieselVehicleCount()==0){
                shedObj.setDieselQueueEndTime(time);
            }
        }
        if(shedObj.getPetrolStatus().equals("Not Available") && shedObj.getDieselStatus().equals("Not Available")){
            shedObj.setStatus("Not Available");
        }
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        CheckShed();
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

        queue.add(putRequest);
    }


    private boolean updateUserDB(String id, String type) {
        sqLiteDatabaseObj = DB.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(DBHelper.Column_FuelType,type);
        contentValues.put(DBHelper.Column_ShedID, id);
        return sqLiteDatabaseObj.update(DBHelper.TABLE_NAME, contentValues, DBHelper.Column_Email + "=?", new String[]{user.getEmail()})>0;

    }


    private void getAdminShed() {
        ShedQueueList = new ArrayList<Shed>();
        String url = "https://easy-queue-application.herokuapp.com/shedDetails/"+user.getShedId();
        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray  response) {
                        // display response on Success
//                        Log.d("Response", response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject responseObj = response.getJSONObject(i);
                                System.out.println(responseObj.getString("shedName"));
                                ShedQueueList.add(new Shed(
                                        responseObj.getString("_id"),
                                        responseObj.getString("ownerID"),
                                        responseObj.getString("shedName"),
                                        responseObj.getString("address"),
                                        responseObj.getString("shedStatus"),
                                        responseObj.getString("petrolStatus"),
                                        Double.parseDouble(responseObj.getString("petrolCapacity")),
                                        responseObj.getString("petrolQueueStartTime"),
                                        responseObj.getString("petrolQueueEndTime"),
                                        Integer.parseInt(responseObj.getString("petrolQueueLength")),
                                        responseObj.getString("dieselStatus"),
                                        Double.parseDouble(responseObj.getString("dieselCapacity")),
                                        responseObj.getString("dieselQueueStartTime"),
                                        responseObj.getString("dieselQueueEndTime"),
                                        Integer.parseInt(responseObj.getString("dieselQueueLength")),
                                        responseObj.getString("petrolArrivalTime"),
                                        responseObj.getString("petrolFinishTime"),
                                        responseObj.getString("dieselArrivalTime"),
                                        responseObj.getString("dieselFinishTime"),
                                        responseObj.getString("updatedAt"))

                                );


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //call here
                            onAdminSuccess(ShedQueueList);
                        }
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

    public void onAdminSuccess(List<Shed> list){
        System.out.println("Admin Call");
        ShedNameAdmin.setText(list.get(0).getName());
        ShedAddressAdmin.setText(list.get(0).getAddress());
        ShedStatusAdmin.setText(list.get(0).getStatus());

        LastUpdate.setText(list.get(0).getLastUpdate().substring(0, 10)+" / "+list.get(0).getLastUpdate().substring(11, 16));

        AdminPetrolStatus.setText(list.get(0).getPetrolStatus());
        APetrolLiter.setText("("+Double.toString(list.get(0).getPetrolLiter())+"L)");
        ADieselLiter.setText("("+Double.toString(list.get(0).getDieselLiter())+"L)");
        AdminPetrolQueueStartTime.setText(list.get(0).getPetrolQueueStartTime());
        AdminPetrolQueueEndTime.setText(list.get(0).getPetrolQueueEndTime());
        AdminPetrolVehicleCount.setText(Integer.toString(list.get(0).getPetrolVehicleCount()));
        AdminDieselStatus.setText(list.get(0).getDieselStatus());
        AdminDieselQueueStartTime.setText(list.get(0).getDieselQueueStartTime());
        AdminDieselQueueEndTime.setText(list.get(0).getDieselQueueEndTime());
        AdminDieselVehicleCount.setText(Integer.toString(list.get(0).getPetrolVehicleCount()));
        AdminPetArrived.setText(list.get(0).getPetrolArrivedime());
        AdminDesArrived.setText(list.get(0).getDieselArrivedime());
        AdminPetFinished.setText(list.get(0).getPetrolFinishedTime());
        AdminDesFinished.setText(list.get(0).getDieselFinishedTime());

        if (list.get(0).getStatus().equals("Not Available")) {
            DotGreenAdmin.setVisibility(View.GONE);
            DotRedAdmin.setVisibility(View.VISIBLE);
        }

        ExitPetrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitPumpBox(list.get(0),v,"Petrol");
            }
        });

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shed shedClick =   list.get(0);
                Gson gson = new Gson();
                String shedClickData = gson.toJson(shedClick);
                Intent intent = new Intent(Home.this, EditShedDetails.class);
                intent.putExtra("adminShed",shedClickData);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        });
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


