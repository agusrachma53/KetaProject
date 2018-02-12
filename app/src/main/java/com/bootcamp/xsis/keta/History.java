package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bootcamp.xsis.keta.Adapter.listAllHistory;
import com.bootcamp.xsis.keta.Adapter.showMenu;
import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;

public class History extends AppCompatActivity {

    Context mcontext;
    private SQLiteDbHelper sqLiteDBHelper;
    public QueryHelper sqlHelper;
    showMenu[] showMenus;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        session = new SessionManager(getApplicationContext());

        this.mcontext = mcontext;
        this.showMenus = showMenus;

        sqLiteDBHelper = new SQLiteDbHelper(getApplicationContext());
        sqlHelper = new QueryHelper(sqLiteDBHelper);

        String phone = session.phone();

        final showMenu[] idUser = sqlHelper.detailUser(phone);

        ListView listView = (ListView) findViewById(R.id.ListHistory);



        if(idUser.length > 0){
            for(int i = 0; i < idUser.length; i++){
                showMenus = sqlHelper.orderpesanan(idUser[i].getId_user());
                Log.d("lengthya",""+showMenus.length);
                if(showMenus.length > 0){
                    listView.setAdapter(new listAllHistory(getApplicationContext(),showMenus));
                }else{
                    Intent refresh = new Intent(History.this,MainActivity.class);
                    refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(refresh);
                }

            }
        }




//        showMenus = sqlHelper.orderpesanan(idUser[0].getId_user());
//        listView.setAdapter(new listAllHistory(getApplicationContext(),showMenus));






















//        session = new SessionManager(getApplicationContext());
//        this.mcontext = mcontext;
//        this.menus = menus;
//        sqLiteDBHelper = new SQLiteDbHelper(getApplicationContext());
//        sqlHelper = new QueryHelper(sqLiteDBHelper);
//        sqLiteDBHelper = new SQLiteDbHelper(mcontext);
//
//        String phone = session.phone();
//        String pass = session.pass();
//        SQLiteDbHelper sqLiteDBHelper = new SQLiteDbHelper(getApplicationContext());
//        QueryHelper sqlHelper = new QueryHelper(sqLiteDBHelper);
//        final showMenu[] idUser = sqlHelper.detailUser(phone);
//        Log.d("idUsernya ",""+idUser[0].getId_user());
//        final showMenu[] showMenus = sqlHelper.orderpesanan(idUser[0].getId_user());
//
//        if(showMenus.length > 0){
//            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
//            LinearLayout insertPoint = (LinearLayout)findViewById(R.id.listHistory);
//            for(int i = 0; i < showMenus.length; i++){
//                View view = inflater.inflate(R.layout.customhistorychart,null);
//                TextView name = (TextView) view.findViewById(R.id.name_history);
//                TextView kodePesanan = (TextView) view.findViewById(R.id.kodePesananHistory);
//                name.setText(showMenus[i].getNama_user());
//                kodePesanan.setText(showMenus[i].getKode_pesanan());
//                insertPoint.addView(view);
//
//                Button delButton = (Button) findViewById(R.id.del_button);
//                delButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        deleetHistory(showMenus[0].getKode_pesanan());
//                        Intent i = new Intent(getApplicationContext(), History.class);
//                        startActivity(i);
//                    }
//                });
//
//                Button showHistory = (Button) findViewById(R.id.showHistory);
//                showHistory.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent i = new Intent(getApplicationContext(), DetailHistory.class);
//                        String idnya = String.valueOf(idUser[0].getId_user());
//                        String KodePesanan = showMenus[0].getKode_pesanan();
//                        i.putExtra("idnyaDonk",idnya);
//                        i.putExtra("KodePesanan",KodePesanan);
//                        startActivity(i);
//                    }
//                });
//            }
//        }else{
//            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(i);
//        }

    }

    public void deleetHistory(String CodePemesanan){sqlHelper.deleteHistory(CodePemesanan);}
}

