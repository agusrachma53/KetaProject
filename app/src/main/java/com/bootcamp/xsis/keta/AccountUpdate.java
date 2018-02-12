package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;

import static java.lang.Integer.parseInt;

public class AccountUpdate extends AppCompatActivity {

    SessionManager sessionManager;
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    Context context;
    Cursor cursor;
    private int IdUser;
    private String nama, phones, passwords;

    EditText p_nama, p_phone, p_pass;
    TextView p_IdUser;
    Button but_Update;
    private int ambil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_update);

        context = this;
        sessionManager = new SessionManager(getApplicationContext());
        dbHelper = new SQLiteDbHelper(context);
        queryHelper = new QueryHelper(dbHelper);
        Intent getId = (Intent) getIntent();
        ambil = getId.getExtras().getInt("id");


        p_IdUser = (TextView) findViewById(R.id.p_IdUser);
        p_nama = (EditText) findViewById(R.id.p_Name);
        p_phone = (EditText) findViewById(R.id.p_Phone);
        p_pass = (EditText) findViewById(R.id.p_Password);

        cursor = queryHelper.loginId(ambil);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            p_IdUser.setText(cursor.getString(0));
            p_IdUser.setVisibility(View.GONE);
            p_nama.setText(cursor.getString(1));
            p_phone.setText(cursor.getString(2));
            p_pass.setText(cursor.getString(3));
        }


        but_Update = (Button) findViewById(R.id.b_UpdateAccount);
        but_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confim");
                builder.setMessage("Are you Sure to update Account ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cursor = queryHelper.loginId(ambil);
                        if (cursor.getCount()== 0){
                            Toast.makeText(context, "Data Kosong", Toast.LENGTH_SHORT).show();
                        }else {
                            SQLiteDatabase update = dbHelper.getWritableDatabase();
                            update.execSQL("UPDATE login SET nama_user='" +
                                    p_nama.getText().toString() + "', phone_user='" +
                                    p_phone.getText().toString() + "', pass_user='" +
                                    p_pass.getText().toString() + "' WHERE id_user = '" + parseInt(p_IdUser.getText().toString()) + "'");
                            Toast.makeText(context, "Your Account have Updated \n Please Login Again ", Toast.LENGTH_SHORT).show();
                        }
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
        });

    }

}
