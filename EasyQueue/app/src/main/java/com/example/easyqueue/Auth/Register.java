package com.example.easyqueue.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyqueue.DBHelper.DBHelper;
import com.example.easyqueue.R;

public class Register extends AppCompatActivity {
    private EditText Name,Email,MobileNo,Address,Password,ShedName,ShedAddress;
    private Spinner RoleSpinner;
    private Button Register;
    private TextView goLogin,LBTxt;
    private LinearLayout ShedDetails;
    String roleType,name,email,mobileNo,address,role,password,shedId,fuelType;
    SQLiteDatabase sqLiteDatabaseObj;
    DBHelper DB;
    String SQLiteDataBaseQueryHolder ;
    Boolean EditTextEmptyHolder;
    String F_Result = "Not_Found";
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

            Name= findViewById(R.id.inp_name);
            Email= findViewById(R.id.inp_email);
            MobileNo= findViewById(R.id.inp_mobileNo);
            Address= findViewById(R.id.inp_Adress);
            Password= findViewById(R.id.inp_password);

            LBTxt = findViewById(R.id.txt_lb);

            ShedName = findViewById(R.id.inp_ShedName);
            ShedAddress = findViewById(R.id.inp_ShedAddress);

            ShedDetails = findViewById(R.id.shedDetailsLayout);

            goLogin= findViewById(R.id.btn_gotoLogin);
            RoleSpinner = findViewById(R.id.spinner);
            Register = findViewById(R.id.btn_register);


            DB = new DBHelper(this);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.role, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            RoleSpinner.setAdapter(adapter);

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
                SQLiteDBTableBuild();
                // Checking EditText is empty or Not.
                CheckEditTextStatus();
                // Method to check Email is already exists or not.
                CheckingEmailExists();
                // Empty EditText After done inserting process.

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
            SubmitUser();
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
    }










    private void SubmitUser() {
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
            ClearEditText();
        }
        // This block will execute if any of the registration EditText is empty.
        else {
            // Printing toast message if any of EditText is empty.
            Toast.makeText(Register.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();
        }
    }

}