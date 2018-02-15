package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bootcamp.xsis.keta.Adapter.ConvertData;
import com.bootcamp.xsis.keta.Adapter.CustomAdapterChart;
import com.bootcamp.xsis.keta.Adapter.CustomAdapterMenu;
import com.bootcamp.xsis.keta.Adapter.WebServices;
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
import java.util.List;

public class ChartPage extends AppCompatActivity {

    private SessionManagerKP sessionKP;
    Context mcontext;
    private SQLiteDbHelper sqLiteDBHelper;
    public QueryHelper sqlHelper;
    showMenu[] showMenus;
    String baseUrl;

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

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.detail_custom_actionbar);
        getSupportActionBar().getCustomView();

        final Intent intent = (Intent) getIntent();
        final int id_users = intent.getExtras().getInt("idnya");
        showMenu[] praInsertOrder = sqlHelper.orderPesan(id_users);

            ImageButton backToMenu = (ImageButton) findViewById(R.id.backtomenu);
            backToMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            });

            getDataChart();

//
//            Button order = (Button) findViewById(R.id.order);
//            order.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    checkOrderKodePesanan(id_users, xxx[0].getNama_user());
//                    String id_userAgain = String.valueOf(id_users);
//
//                    int TotalBelanja = xxx[0].getSubtotal();
//
//                    Intent j = new Intent(ChartPage.this, PaymentPage.class);
//                    j.putExtra("kode_pesannya",sessionKP.kodePesan());
//                    j.putExtra("totalbelanjannya",TotalBelanja);
//                    j.putExtra("idNya",id_userAgain);
//                    j.putExtra("nameUser",nameUser);
//                    startActivity(j);
//
//                }
//            });
    }


    private void getDataChart(){

        baseUrl = "https://ph0001.babastudio.org/afand_store/serviceforajax/m_product.php?idUser=3";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.lixt_cart);
                ConvertData convertData = new ConvertData(mcontext);
                CustomAdapterChart customAdapterChart = new CustomAdapterChart(convertData.getAllProductChart(response),getApplicationContext());
                customAdapterChart.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(customAdapterChart);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        WebServices.getmInstance(mcontext).addToRequestque(stringRequest);

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }

    private void checkOrderKodePesanan(int id_users, String namaUser){

        if (sessionKP.isOrderIn() == false ){
//            Toast.makeText(this, "False ", Toast.LENGTH_SHORT).show();
            String kodePesanan = Konstanta.generateOrderKodePesan(namaUser, id_users);
            sessionKP.createKodePesanSession(kodePesanan);
        }else{
//            Toast.makeText(this, "True", Toast.LENGTH_SHORT).show();
        }
    }


}
