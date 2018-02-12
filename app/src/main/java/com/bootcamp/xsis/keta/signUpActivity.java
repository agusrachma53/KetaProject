package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;

import static java.lang.Integer.parseInt;


public class signUpActivity extends AppCompatActivity {

    private EditText e_name, e_phone, e_password;
    private String b_name, b_phone , b_pass;
    private Button b_Register;
    //    DataHelper dataHelper;
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    Context context;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        context = this;
        dbHelper = new SQLiteDbHelper(context);
        queryHelper = new QueryHelper(dbHelper);
        //Data helper
//        dataHelper = new DataHelper(this);

        //declare
        e_name = (EditText) findViewById(R.id.e_Name);
        e_phone = (EditText) findViewById(R.id.e_Phone);
        e_password = (EditText) findViewById(R.id.e_Password);
        b_Register = (Button) findViewById(R.id.b_Register);



        e_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*validasi harus diisi */
                String name = e_name.getText().toString();
                String phone = e_phone.getText().toString();
                String pass = e_password.getText().toString();
                if (name.trim().length() ==0 || name.trim().length() < 5) {
                    e_name.setError("fill your name min 5 Character");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        e_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*validasi harus diisi */
                String phone = e_phone.getText().toString();
                if (phone.trim().length() ==0 || phone.trim().length() < 5) {
                    e_phone.setError("fill your phone min 5 Character");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        e_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*validasi harus diisi */
                String pass = e_password.getText().toString();
                if (pass.trim().length() ==0 || pass.trim().length() < 5) {
                    e_password.setError("fill your Password min 5 Character");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        b_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_name = e_name.getText().toString();
                b_phone = e_phone.getText().toString();
                b_pass = e_password.getText().toString();

                if (b_name.trim().length() > 5 && b_phone.trim().length()>5 && b_pass.trim().length()>5){
                    cursor = queryHelper.login(b_phone, b_pass);
                    if (cursor.getCount()> 0){
                        Toast.makeText(getApplicationContext(), "Your Account Already Exsist \n Please Another Phone Number", Toast.LENGTH_SHORT).show();

                    }else if(cursor.getCount()==0){

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Confimation");
                        builder.setMessage(" Are you sure the Account is correct ?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            queryHelper.signUp(b_name, b_phone,b_pass);

                            Toast.makeText(getApplicationContext(), "Register is Success", Toast.LENGTH_SHORT).show();
                            Intent callSignUp = new Intent(signUpActivity.this, loginActivity.class);
                            startActivity(callSignUp);
                            finish();

                            Intent pLogin = new Intent(getApplicationContext(), loginActivity.class);
                            startActivity(pLogin);
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }else if (b_name.length() == 0 || b_phone.length() == 0 || b_pass.length()==0){
                    e_name.setError("Empty");
                    e_phone.setError("Empty");
                    e_password.setError("Empty");

                }
            }
        });
    }
}