package com.bootcamp.xsis.keta;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashViewActivity extends Activity {
    private SessionManager session;
    private long splashDelay = 3000;
    private SQLiteDbHelper dbHelper;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_view);

        dbHelper = new SQLiteDbHelper(this);
        session = new SessionManager(getApplicationContext());

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();

                Bundle bundle = ActivityOptions.makeCustomAnimation(getBaseContext(),
                        R.anim.fade_in, R.anim.fade_out).toBundle();
                if (session.isLoggedIn() == true){
                    Intent lTrue = new Intent(SplashViewActivity.this, MainActivity.class);
                    startActivity(lTrue, bundle);

                }else if(session.isLoggedIn()== false){
                    Intent lFalse = new Intent(SplashViewActivity.this, loginActivity.class);
                    startActivity(lFalse, bundle);
                }

            }
        };
        Timer timer = new Timer();
        timer.schedule(task, splashDelay);
        importDatabase();
    }

    private void importDatabase(){
        try {
            dbHelper.createDatabaseFromImportedSQL();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "importDbFromSQLFile IOException : "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}