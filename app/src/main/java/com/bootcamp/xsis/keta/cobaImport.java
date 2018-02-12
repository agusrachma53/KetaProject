package com.bootcamp.xsis.keta;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;

import java.io.IOException;

public class cobaImport extends AppCompatActivity {
    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;
    SessionManager sessionManager;
    private Cursor cursor;
    private Context context;
    TextView tImport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coba_import);

        tImport = (TextView) findViewById(R.id.tampilImport);
        context = this;
        sessionManager = new SessionManager(getApplicationContext());
        dbHelper = new SQLiteDbHelper(context);
        queryHelper = new QueryHelper(dbHelper);
        importDbFromSQLFile();
        DataLogin();

    }

    private void DataLogin() {
        String phone =sessionManager.phone();
        String pass = sessionManager.pass();

        Cursor cursor = queryHelper.readOrderPesanan();
        int [] idUser = new  int[cursor.getCount()];
        String [] nameUser = new String[cursor.getCount()];
        String [] phoneUser = new String[cursor.getCount()];
        String [] passwordUser = new String[cursor.getCount()];
        cursor.moveToFirst();

        for (int n =0; n<cursor.getCount(); n++ ){
            cursor.moveToPosition(n);
            idUser [n] = cursor.getInt(0);
            nameUser[n] = cursor.getString(1);
            phoneUser[n] = cursor.getString(2);
            passwordUser[n] = cursor.getString(3);
        }
        cursor.close();

        //show data login
        String Hasil = " ";

        for (int i =0; i<idUser.length; i++){
            Hasil+= idUser[i]+ "-" + nameUser[i] + "-" + phoneUser[i] + "-" + passwordUser[i]+ "\n";
        }
        tImport.setText(Hasil);
    }

    private void importDbFromSQLFile(){
        try {
            dbHelper.createDatabaseFromImportedSQL();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "importDbFromSQLFile IOException : "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}

