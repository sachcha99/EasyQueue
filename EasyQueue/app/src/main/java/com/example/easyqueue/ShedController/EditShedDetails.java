package com.example.easyqueue.ShedController;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easyqueue.Auth.Login;
import com.example.easyqueue.DBHelper.DBHelper;
import com.example.easyqueue.Home;
import com.example.easyqueue.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class EditShedDetails extends AppCompatActivity {
    private EditText ShedName,ShedAddress,petrolCapacity,petrolArrived,petrolFinished,petrolQS,petrolQE,petrolQLength,dieselQS,dieselQE,dieselQLength,dieselCapacity,dieselArrived,dieselFinished;
    private Spinner typeSpinnerShed,typeSpinnerPetrol,typeSpinnerDiesel;
    Button UpdateBtn;
    FloatingActionButton BackBtn;
    RequestQueue queue;
    Shed shedObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shed_details);
        queue = Volley.newRequestQueue(this);
        Gson gson = new Gson();
        String shedClickData = getIntent().getStringExtra("adminShed");
        shedObj = gson.fromJson(shedClickData, Shed.class);
        System.out.println("s"+"  "+shedObj.getDieselLiter());

        petrolCapacity= findViewById(R.id.inp_petrolLiter);
        dieselCapacity= findViewById(R.id.inp_dieselLiter);
        petrolQS= findViewById(R.id.inp_petrolQS);
        petrolQE= findViewById(R.id.inp_petrolQE);
        petrolQLength= findViewById(R.id.inp_petrolCount);
        dieselQS= findViewById(R.id.inp_dieselQS);
        dieselQE= findViewById(R.id.inp_dieselQE);
        dieselQLength= findViewById(R.id.inp_dieselCount);
        ShedName = findViewById(R.id.inp_ShedName);
        ShedAddress = findViewById(R.id.inp_ShedAddress);
        petrolArrived= findViewById(R.id.inp_petrolArrived);
        petrolFinished= findViewById(R.id.inp_petrolFinish);
        dieselArrived = findViewById(R.id.inp_dieselArrived);
        dieselFinished = findViewById(R.id.inp_dieselFinish);

        typeSpinnerShed = findViewById(R.id.spinner2);
        typeSpinnerPetrol = findViewById(R.id.spinner4);
        typeSpinnerDiesel = findViewById(R.id.spinner3);
        UpdateBtn = findViewById(R.id.btn_update);
        BackBtn = findViewById(R.id.btn_back);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ShedType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinnerShed.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinnerPetrol.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinnerDiesel.setAdapter(adapter3);



        ShedName.setText(shedObj.getName());
        ShedAddress.setText(shedObj.getAddress());
        petrolCapacity.setText(Double.toString(shedObj.getPetrolLiter()));
        dieselCapacity.setText(Double.toString(shedObj.getDieselLiter()));
        petrolQS.setText(shedObj.getPetrolQueueStartTime());
        petrolQE.setText(shedObj.getDieselQueueStartTime());
        petrolQLength.setText(Integer.toString(shedObj.getPetrolVehicleCount()));
        dieselQS.setText(shedObj.getDieselQueueStartTime());
        dieselQE.setText(shedObj.getDieselQueueEndTime());
        dieselQLength.setText(Integer.toString(shedObj.getDieselVehicleCount()));
        petrolArrived.setText(shedObj.getPetrolArrivedime());
        petrolFinished.setText(shedObj.getPetrolFinishedTime());
        dieselArrived.setText(shedObj.getDieselArrivedime());
        dieselFinished.setText(shedObj.getDieselFinishedTime());
        if(shedObj.getStatus().equals("Open")){
            typeSpinnerShed.setSelection(0);
        }else{
            typeSpinnerShed.setSelection(1);
        }
        if(shedObj.getDieselStatus().equals("Available")){
            typeSpinnerDiesel.setSelection(0);
        }else{
            typeSpinnerDiesel.setSelection(1);
        }

        if(shedObj.getPetrolStatus().equals("Available")){
            typeSpinnerPetrol.setSelection(0);
        }else{
            typeSpinnerPetrol.setSelection(1);
        }

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditShedDetails.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
          String  url = "https://easy-queue-application.herokuapp.com/shedDetails/update/"+shedObj.getId();
            @Override
            public void onClick(View view) {
                StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                Toast.makeText(EditShedDetails.this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditShedDetails.this, Home.class);
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
                        params.put("shedName", ShedName.getText().toString());
                        params.put("address", ShedAddress.getText().toString());
                        params.put("shedStatus", typeSpinnerShed.getSelectedItem().toString());
                        params.put("petrolStatus", typeSpinnerPetrol.getSelectedItem().toString());
                        params.put("dieselStatus", typeSpinnerDiesel.getSelectedItem().toString());
                        params.put("petrolQueueLength", String.valueOf(petrolQLength.getText()));
                        params.put("dieselQueueLength", String.valueOf(dieselQLength.getText()));
                        params.put("petrolCapacity",  String.valueOf(petrolCapacity.getText()));
                        params.put("dieselCapacity",  String.valueOf(dieselCapacity.getText()));
                        params.put("petrolQueueEndTime",  String.valueOf(petrolQE.getText()));
                        params.put("dieselQueueEndTime",  String.valueOf(dieselQE.getText()));
                        params.put("petrolArrivalTime",  String.valueOf(petrolArrived.getText()));
                        params.put("petrolFinishTime",  String.valueOf(petrolFinished.getText()));
                        params.put("dieselArrivalTime",  String.valueOf(dieselArrived.getText()));
                        params.put("dieselFinishTime",  String.valueOf(dieselFinished.getText()));


                        return params;
                    }

                };

                queue.add(putRequest);
            }
        });
    }
}