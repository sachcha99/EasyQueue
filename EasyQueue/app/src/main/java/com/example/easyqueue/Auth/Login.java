package com.example.easyqueue.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyqueue.DBHelper.DBHelper;
import com.example.easyqueue.Home;
import com.example.easyqueue.R;

public class Login extends AppCompatActivity {
    private EditText Email,Password;
    private Button Login;
    private TextView goRegister;
    DBHelper DB;
    private Boolean EditTextEmptyHolder;
    private SQLiteDatabase sqLiteDatabaseObj;
    private Cursor cursor;
    private String TempPassword = "NOT_FOUND";
    private String email, password,Role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email= findViewById(R.id.inp_email);
        Password= findViewById(R.id.inp_password);
        goRegister =  findViewById(R.id.btn_createAccount);
        Login= (Button) findViewById(R.id.btn_login);

        DB = new DBHelper(this);

        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 email = Email.getText().toString();
                 password = Password.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    EditTextEmptyHolder = false;
                } else {
                    EditTextEmptyHolder = true;
                }

                // Calling login method.
                SignIn();

                ClearEditText();
            }
        });
    }


    @SuppressLint("Range")
    private void SignIn() {
        if (EditTextEmptyHolder) {
            // Opening SQLite database write permission.
            sqLiteDatabaseObj = DB.getWritableDatabase();
            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(DBHelper.TABLE_NAME, null, " " + DBHelper.Column_Email + "=?", new String[]{email}, null, null, null);
            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();
                    // Storing Password associated with entered email.
                    TempPassword = cursor.getString(cursor.getColumnIndex(DBHelper.Column_Password));
                    Role = cursor.getString(cursor.getColumnIndex(DBHelper.Column_Role));
                    // Closing cursor.
                    cursor.close();
                }
            }
            // Calling method to check final result ..
            CheckFinalResult();
        } else {
            //If any of login EditText empty then this block will be executed.
            Toast.makeText(Login.this, "Please Enter UserName or Password.", Toast.LENGTH_LONG).show();
        }
    }

    private void ClearEditText() {
        Email.getText().clear();
        Password.getText().clear();
    }


    // Checking entered password from SQLite database email associated password.
    public void CheckFinalResult() {
        if (TempPassword.equalsIgnoreCase(password)) {
            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
            System.out.println(Role);
            if(Role.equals("User")){
                // Going to Dashboard activity after login success message.
                Intent intent = new Intent(Login.this, Home.class);
                // Sending Email to Dashboard Activity using intent.
                intent.putExtra("email", email);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }else if(Role.equals("Owner")){
                // Going to Dashboard activity after login success message.
                Intent intent = new Intent(Login.this, Home.class);
                // Sending Email to Dashboard Activity using intent.
                intent.putExtra("email", email);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else {
            Toast.makeText(Login.this, "UserName or Password is Wrong, Please Try Again.", Toast.LENGTH_LONG).show();
        }
        TempPassword = "NOT_FOUND";
    }
}