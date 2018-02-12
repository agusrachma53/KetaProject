package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.bootcamp.xsis.keta.Adapter.ListHistory;
import com.bootcamp.xsis.keta.Adapter.showMenu;
import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;

public class DetailHistory extends AppCompatActivity {

    Context mcontext;
    private SQLiteDbHelper sqLiteDBHelper;
    public QueryHelper sqlHelper;
    showMenu[] showMenus;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        Intent getId = (Intent) getIntent();
        String namaUser = getId.getExtras().getString("namaUser");
        String KOdePesanan = getId.getExtras().getString("KodePesanan");
        int idUser = getId.getExtras().getInt("a");
//        Log.d("history.length",""+idUser);
        TextView nameHistory = (TextView) findViewById(R.id.userDetailHistory);
        TextView noTlp = (TextView) findViewById(R.id.tlpDetailHistory);
        TextView Code = (TextView) findViewById(R.id.codeHistory);
        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);

        this.mcontext = mcontext;
        this.showMenus = showMenus;

        sqLiteDBHelper = new SQLiteDbHelper(getApplicationContext());
        sqlHelper = new QueryHelper(sqLiteDBHelper);

        final showMenu[] history = sqlHelper.HistoryOrder(idUser,KOdePesanan);
        Log.d("history.length",""+history.length);
        for(int i =0; i < history.length; i++){
            Log.d("history.length",""+history.length);
        }
//        if(history.length > 0){
//            for(int i = 0; i < history.length; i++){
//                nameHistory.setText(showMenus[i].getNama_user());
//            }
//        }else{
//            Intent i = new Intent(DetailHistory.this, MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(i);
//        }






//        sqLiteDBHelper = new SQLiteDbHelper(getApplicationContext());
//        sqlHelper = new QueryHelper(sqLiteDBHelper);
//        sqLiteDBHelper = new SQLiteDbHelper(mcontext);
//        SQLiteDbHelper sqLiteDBHelper = new SQLiteDbHelper(getApplicationContext());
//        QueryHelper sqlHelper = new QueryHelper(sqLiteDBHelper);
//        Intent getId = (Intent) getIntent();
//        int idUser = Integer.parseInt(getId.getExtras().getString("idnyaDonk"));
//        String KOdePesanan = getId.getExtras().getString("KodePesanan");
//        Log.d("lkdsjkajd",""+idUser+" "+KOdePesanan);
//        session = new SessionManager(getApplicationContext());
//        String phone = session.phone();
//        final showMenu[] history = sqlHelper.HistoryOrder(idUser,KOdePesanan);
//

//        nameHistory.setText(history[0].getNama_user());
//        noTlp.setText(phone);
//        Code.setText(history[0].getKode_pesanan());
//        totalPrice.setText(String.valueOf(history[0].getTotalAkhir()));
//        ListView listView = (ListView) findViewById(R.id.listProductHistory);
//        showMenus = sqlHelper.HistoryOrder(idUser,KOdePesanan);
//        listView.setAdapter(new ListHistory(getApplicationContext(),showMenus));
    }
}
