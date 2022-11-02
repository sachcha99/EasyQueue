package com.example.easyqueue.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easyqueue.DBHelper.DBHelper;
import com.example.easyqueue.Home;
import com.example.easyqueue.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText Name,Email,MobileNo,Address,Password,ShedName,ShedAddress,petrolCapacity,dieselCapacity;
    private Spinner RoleSpinner,typeSpinnerShed,typeSpinnerPetrol,typeSpinnerDiesel;
    private Button Register;
    private TextView goLogin,LBTxt;
    private LinearLayout ShedDetails;
    String roleType,name,email,mobileNo,address,role,password,shedId,fuelType,shedName,shedAddress,shedStatus,petrolStatus,dieselStatus,petrolCapa,dieselCapa;
    SQLiteDatabase sqLiteDatabaseObj;
    DBHelper DB;
    RequestQueue queue;
    String SQLiteDataBaseQueryHolder ;
    Boolean EditTextEmptyHolder;
    String F_Result = "Not_Found";
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

            queue = Volley.newRequestQueue(this);
            Name= findViewById(R.id.inp_name);
            Email= findViewById(R.id.inp_email);
            MobileNo= findViewById(R.id.inp_mobileNo);
            Address= findViewById(R.id.inp_Address);
            Password= findViewById(R.id.inp_password);
            petrolCapacity= findViewById(R.id.inp_petrolLiter);
            dieselCapacity= findViewById(R.id.inp_dieselLiter);

            LBTxt = findViewById(R.id.txt_lb);

            ShedName = findViewById(R.id.inp_ShedName);
            ShedAddress = findViewById(R.id.inp_ShedAddress);

            ShedDetails = findViewById(R.id.shedDetailsLayout);

            goLogin= findViewById(R.id.btn_gotoLogin);
            RoleSpinner = findViewById(R.id.spinner);
            typeSpinnerShed = findViewById(R.id.spinner2);
            typeSpinnerPetrol = findViewById(R.id.spinner4);
            typeSpinnerDiesel = findViewById(R.id.spinner3);
            Register = findViewById(R.id.btn_register);


            DB = new DBHelper(this);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.role, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            RoleSpinner.setAdapter(adapter);

            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.ShedType, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinnerShed.setAdapter(adapter2);

            ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinnerPetrol.setAdapter(adapter3);

            ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinnerDiesel.setAdapter(adapter4);

            RoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                roleType = adapterView.getItemAtPosition(i).toString();

                if( roleType.equals("User")){
                    ShedDetails.setVisibility(View.GONE);
                    LBTxt.setVisibility(View.GONE);

                }else{
                    ShedDetails.setVisibility(View.VISIBLE);
                    LBTxt.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("SQLiteDBBuild");
                SQLiteDBBuild();
                // Creating SQLite table if dose n't exists.
                System.out.println("SQLiteDBTableBuild");
                SQLiteDBTableBuild();
                // Checking EditText is empty or Not.
                System.out.println("CheckEditTextStatus");
                CheckEditTextStatus();
                // Method to check Email is already exists or not.
                System.out.println("CheckingEmailExists");
                CheckingEmailExists();
                // Empty EditText After done inserting process.

                if(roleType.equals("Owner")){
                    CheckingShedDetials();
                }

            }
        });

    }




    public void SQLiteDBBuild(){
        sqLiteDatabaseObj = openOrCreateDatabase(DBHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    private void SQLiteDBTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "
                + DBHelper.TABLE_NAME
                + "(" + DBHelper.Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBHelper.Column_Name + " VARCHAR, "
                + DBHelper.Column_Email + " VARCHAR, "
                + DBHelper.Column_Mobile + " VARCHAR, "
                + DBHelper.Column_Address + " VARCHAR, "
                + DBHelper.Column_Role + " VARCHAR, "
                + DBHelper.Column_Password + " VARCHAR,"
                + DBHelper.Column_FuelType + " VARCHAR,"
                + DBHelper.Column_ShedID + " VARCHAR);");
    }
    private void CheckEditTextStatus() {
        // Getting value from All EditText and storing into String Variables.
        name = Name.getText().toString();
        email = Email.getText().toString();
        mobileNo = MobileNo.getText().toString();
        address = Address.getText().toString();
        password = Password.getText().toString();
        role = RoleSpinner.getSelectedItem().toString();
        fuelType="-";
        shedId="-";

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            EditTextEmptyHolder = false ;
        }
        else {
            EditTextEmptyHolder = true ;
        }

    }
    private void CheckingEmailExists() {
        // Opening SQLite database write permission.
        sqLiteDatabaseObj = DB.getWritableDatabase();
        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(DBHelper.TABLE_NAME, null, " " + DBHelper.Column_Email + "=?", new String[]{email}, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Email Found";
                // Closing cursor.
                cursor.close();
            }
        }
        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();
    }
    // Checking result
    public void CheckFinalResult(){
        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {
            // If email is exists then toast msg will display.
            Toast.makeText(Register.this,"Email Already Exists",Toast.LENGTH_LONG).show();
        }
        else {
            // If email already dose n't exists then user registration details will entered to SQLite database.
            if(roleType.equals("Owner")){
                submitShed();
            }else{
                SubmitUser("-");
            }

        }
        F_Result = "Not_Found" ;

    }
    private void ClearEditText() {
        Name.getText().clear();
        Address.getText().clear();
        Email.getText().clear();
        MobileNo.getText().clear();
        Password.getText().clear();
        ShedName.getText().clear();
        ShedAddress.getText().clear();
        petrolCapacity.getText().clear();
        dieselCapacity.getText().clear();
    }

    private void CheckingShedDetials() {
        System.out.println("CCCCCCCCCCCCCc");
        shedName =ShedName.getText().toString();
        shedAddress= ShedAddress.getText().toString();
        shedStatus = typeSpinnerShed.getSelectedItem().toString();
        petrolStatus= typeSpinnerPetrol.getSelectedItem().toString();
        dieselStatus= typeSpinnerDiesel.getSelectedItem().toString();
        petrolCapa= petrolCapacity.getText().toString();
        dieselCapa= dieselCapacity.getText().toString();
        if(TextUtils.isEmpty(shedName) || TextUtils.isEmpty(shedAddress) || TextUtils.isEmpty(petrolCapa) || TextUtils.isEmpty(dieselCapa)){
            EditTextEmptyHolder = false ;
        }
        else {
            EditTextEmptyHolder = true ;
        }
    }









    private void SubmitUser(String id) {
        shedId =id;
        // If editText is not empty then this block will executed.
        if(EditTextEmptyHolder == true)
        {
            // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder = "INSERT INTO " +DBHelper.TABLE_NAME +" (name,email,mobileNo,address,role,password,fuelType,shedID) VALUES('" +name +"', '" +email +"', '" +mobileNo +"', '" +address +"', '" +role +"', '" +password +"', '" +fuelType +"', '" +shedId +"');";
            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            // Closing SQLite database object.
            sqLiteDatabaseObj.close();
            // Printing toast message after done inserting.
            Toast.makeText(Register.this,"User Registered Successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Register.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            ClearEditText();
        }
        // This block will execute if any of the registration EditText is empty.
        else {
            // Printing toast message if any of EditText is empty.
            Toast.makeText(Register.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();
        }
    }

    private void submitShed() {
        String url = "https://easy-queue-application.herokuapp.com/shedDetails/create/";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject shedObject = new JSONObject(response);
                            System.out.println( shedObject.getJSONObject("data").getString("_id"));
                            SubmitUser(shedObject.getJSONObject("data").getString("_id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // response on Success
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
                params.put("shedName", shedName);
                params.put("address", shedAddress);
                params.put("ownerID", email);
                params.put("petrolArrivalTime", "-");
                params.put("petrolFinishTime", "-");
                params.put("dieselArrivalTime", "-");
                params.put("dieselFinishTime", "-");
                params.put("petrolQueueStartTime", "-");
                params.put("petrolQueueEndTime", "-");
                params.put("petrolQueueLength", "0");
                params.put("dieselQueueStartTime", "-");
                params.put("dieselQueueEndTime", "-");
                params.put("dieselQueueLength", "0");
                params.put("petrolStatus", petrolStatus);
                params.put("dieselStatus", dieselStatus);
                params.put("shedStatus", shedStatus);
                params.put("petrolCapacity", petrolCapa);
                params.put("dieselCapacity", dieselCapa);

                return params;
            }
        };
        queue.add(postRequest);
    }

}