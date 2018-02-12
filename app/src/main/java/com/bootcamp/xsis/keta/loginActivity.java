package com.bootcamp.xsis.keta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;

public class loginActivity extends Activity implements View.OnClickListener {

    SessionManager session;
    //    DataHelper dataHelper;
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    Cursor cursor;
    Context context;
    Button b_SignUp, b_SignIn;
    CheckBox check;

    EditText l_phone, l_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // dataHelper = new DataHelper(this);
        session = new SessionManager( getApplicationContext());
        context = this;
        dbHelper = new SQLiteDbHelper(context);
        queryHelper = new QueryHelper(dbHelper);

        l_phone = (EditText) findViewById(R.id.l_phone);
        l_password = (EditText) findViewById(R.id.l_password);
        b_SignUp = (Button)findViewById(R.id.b_signUp);
        b_SignIn = (Button) findViewById(R.id.b_SignIn);
        check = (CheckBox) findViewById(R.id.check);

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ){
                    l_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    l_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        b_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = l_phone.getText().toString();
                String pass = l_password.getText().toString();

                if (phone.length() ==0) {
                    l_phone.setError("Insert your phone number ");
                }
                if (pass.trim().length() ==0) {
                    l_password.setError("Insert Your password");
                }else if(phone.trim().length()> 0 && pass.trim().length()>0) {
                    cursor = queryHelper.login(phone ,pass);
                    if (cursor.getCount() >0){
                        cursor.moveToPosition(0);
                        session.createLoginSession(phone , pass);
                        String username = cursor.getString(1);
                        Toast.makeText(getApplicationContext(), " WELCOME TO KETAXSIS RESTAURANT \n"+"\n                        "+            username    , Toast.LENGTH_SHORT).show();// Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Your Account is not Registered !", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Your Account is not Registered !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b_SignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent callSignUp = new Intent(this, signUpActivity.class);
        startActivity(callSignUp);
    }
}