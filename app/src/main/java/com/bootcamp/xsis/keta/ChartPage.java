package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;
import com.bootcamp.xsis.keta.Adapter.ListOrder;
import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;
import com.bootcamp.xsis.keta.Adapter.showMenu;

import org.w3c.dom.Text;

import java.time.MonthDay;
import java.time.Year;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class ChartPage extends AppCompatActivity {

    private SessionManagerKP sessionKP;
    Context mcontext;
    private SQLiteDbHelper sqLiteDBHelper;
    public QueryHelper sqlHelper;
    showMenu[] showMenus;

    //data array
    String[] data;
    String nameUser;
    int subtotal;
    int qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chart_page);
    TextView toalakhir = (TextView)findViewById(R.id.TotalAkhir);
    TextView qtynya = (TextView)findViewById(R.id.qtynya);

        this.mcontext = mcontext;
        this.showMenus = showMenus;
        sqLiteDBHelper = new SQLiteDbHelper(getApplicationContext());
        sqlHelper = new QueryHelper(sqLiteDBHelper);
        sqLiteDBHelper = new SQLiteDbHelper(mcontext);
        sessionKP = new SessionManagerKP(getApplicationContext());

        final Intent intent = (Intent) getIntent();
        final int id_users = intent.getExtras().getInt("idnya");
        showMenu[] praInsertOrder = sqlHelper.orderPesan(id_users);
        if (praInsertOrder.length == 0){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }else {
            ListView listView = (ListView) findViewById(R.id.lixt_cart);
            sqLiteDBHelper = new SQLiteDbHelper(mcontext);
            showMenus = sqlHelper.orderPesan(id_users);
            listView.setAdapter(new ListOrder(getApplicationContext(),showMenus));
            final showMenu[] xxx = sqlHelper.getTotal(id_users);
            final String nameUser = xxx[0].getNama_user();
            int xyz = xxx[0].getSubtotal();
            toalakhir.setText(String.valueOf(xyz));

            Button backToMenu = (Button) findViewById(R.id.backToShop);
            backToMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            });


            Button order = (Button) findViewById(R.id.order);
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkOrderKodePesanan(id_users, xxx[0].getNama_user());
                    String id_userAgain = String.valueOf(id_users);

                    int TotalBelanja = xxx[0].getSubtotal();

                    Intent j = new Intent(ChartPage.this, PaymentPage.class);
                    j.putExtra("kode_pesannya",sessionKP.kodePesan());
                    j.putExtra("totalbelanjannya",TotalBelanja);
                    j.putExtra("idNya",id_userAgain);
                    j.putExtra("nameUser",nameUser);
                    startActivity(j);

                }
            });
        }
    }

    private void checkOrderKodePesanan(int id_users, String namaUser){
        Log.d("debugKodeOrder1", ""+sessionKP.kodePesan());
        Log.d("debugKodeOrder2", ""+sqlHelper.getOrderKodePesanan(id_users));

        if (sessionKP.isOrderIn() == false ){
//            Toast.makeText(this, "False ", Toast.LENGTH_SHORT).show();
            String kodePesanan = Konstanta.generateOrderKodePesan(namaUser, id_users);
            sessionKP.createKodePesanSession(kodePesanan);
        }else{
//            Toast.makeText(this, "True", Toast.LENGTH_SHORT).show();
        }
    }


}
