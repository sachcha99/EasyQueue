package com.example.easyqueue.ShedController;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.easyqueue.Home;
import com.example.easyqueue.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ShedListView extends AppCompatActivity {

    ListView ShedListView;
    List<Shed> ShedList;
    FloatingActionButton BackBtn;
    final String url = "https://easy-queue-application.herokuapp.com/shedDetails/";
    RequestQueue allSheds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shed_listview);
        BackBtn = (FloatingActionButton) findViewById(R.id.btn_back);
        ShedListView = (ListView) findViewById(R.id.shed_listview);
        allSheds = Volley.newRequestQueue(this);
        getAllSheds();



//        ShedList.add(new Shed("111","Pinidiya Shed","Hakmana Rd Matara","Available","Available",200.0,"11.55AM","4.30PM",155,"Available",55.5,"07.00AM","2.30PM",115));
//        ShedList.add(new Shed("111","Harischandra Shed","Galle Rd Matara","NotAvailable","NotAvailable",100.0,"1.05PM","12.30AM",512,"NotAvailable",569,"20.55PM","15.55PM",415));
//        ShedList.add(new Shed("111","Gamini Shed","Akkuress Rd Matara","Available","Available",104.0,"10.35AM","2.30PM",215,"Available",1440.0,"10.40AM","2.30PM",715));
//        ShedList.add(new Shed("111","Udaya Shed","Colombo Rd Colombo","NotAvailable","Available",510.0,"8.15AM","2.30PM",145,"NotAvailable",170.0,"11.55","19.30PM",315));
//        ShedList.add(new Shed(
//                "111",
//                "Piliyandala Shed",
//                "Kandy Rd Kandy",
//                "Available",
//                "NotAvailable",1000.0,
//                "9.50AM",
//                "3.30PM",
//                151,
//                "Available",
//                310.0,
//                "8.25AM",
//                "21.30PM",
//                15)
//        );


        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShedListView.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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


    /**
     Create new Shed
     *
     *
     * @return
     * @param*/
    private void getAllSheds()
    {
         ShedList = new ArrayList<Shed>();
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
                                ShedList.add(new Shed(
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


//                                ShedList.add(new Shed(
//                                        "111",
//                                        "Piliyandala Shed",
//                                        "Kandy Rd Kandy",
//                                        "Available",
//                                        "NotAvailable",
//                                        1000.0,
//                                        "9.50AM",
//                                        "3.30PM",
//                                        151,
//                                        "Available",
//                                        310.0,
//                                        "8.25AM",
//                                        "21.30PM",
//                                        15)
//                                );

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //call here
                            onSuccess(ShedList);
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
        allSheds.add(getRequest);
    }

    public void onSuccess(List<Shed> list){

        ShedListAdapter adapter = new ShedListAdapter(this,R.layout.activity_shed_card_view,list);
        ShedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shed shedClick =   list.get(i);
                Gson gson = new Gson();
                String shedClickData = gson.toJson(shedClick);
                Intent intent = new Intent(ShedListView.this, ShedDetailsView.class);
                intent.putExtra("shedClick",shedClickData);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        ShedListView.setAdapter(adapter);
    }
}

