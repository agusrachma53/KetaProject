package com.bootcamp.xsis.keta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bootcamp.xsis.keta.Adapter.ConvertData;
import com.bootcamp.xsis.keta.Adapter.CustomAdapterMenu;
import com.bootcamp.xsis.keta.Adapter.WebServices;
import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;
import com.bootcamp.xsis.keta.Adapter.showMenu;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductDetail extends AppCompatActivity {

    private Context mcontext;
    private SessionManager session;
    private showMenu showMenus;
    private String baseUrl;
    private ProgressDialog mProgressDialog;
    private TextView Total;
    private TextView name_product;
    private TextView price_produc;
    private TextView total,actCharts;
    private int actChart,hasil,hasil2;
    private ImageView icon;
    private ArrayAdapter<String> dataAdapter;
    private Spinner qtyList;
    private Button GoToChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.detail_custom_actionbar);
        getSupportActionBar().getCustomView();


        final Intent intent = (Intent) getIntent();
        final String idProduct = intent.getExtras().getString("idProduct");
        final String idSelect = intent.getExtras().getString("idSelect");

        Total = (TextView) findViewById(R.id.name_of_total);
        name_product = (TextView) findViewById(R.id.name_of_item_detail2);
        price_produc = (TextView) findViewById(R.id.name_of_price2);
        qtyList = (Spinner) findViewById(R.id.qtySpinner);
        total = (TextView) findViewById(R.id.name_of_total);
        GoToChart = (Button) findViewById(R.id.goToChart);
        actCharts = (TextView) findViewById(R.id.actChart);

        mProgressDialog = new ProgressDialog(ProductDetail.this);
        mProgressDialog.setMessage("Loading ...");
        mProgressDialog.show();
        getdata(idProduct,idSelect);


        /* Back To List Menu */
        ImageButton goBackListMenu = (ImageButton) findViewById(R.id.backtomenu);
        goBackListMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });



    }


    public void getdata(final String idProduct, final String idSelect){

            baseUrl = "https://ph0001.babastudio.org/afand_store/serviceforajax/m_product.php?idProduct="+idProduct;

            StringRequest request = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    mProgressDialog.dismiss();
                    ConvertData convertData = new ConvertData(mcontext);
                    final showMenu datanya =  convertData.getSpesificData(response);

                    String nama_produk;
                    final String dataarray[] = new String[5];
                    dataarray[0] = datanya.getNama_produk();
                    dataarray[1] = datanya.getGambar_produk();
                    dataarray[2] = datanya.getDesk_produk();
                    dataarray[3] = datanya.getId_kategori_produk();
                    dataarray[4] = datanya.getKategorii_produk();


                    name_product.setText(datanya.getNama_produk());
                    price_produc.setText(String.valueOf(datanya.getHarga_produk()));

                    icon = (ImageView) findViewById(R.id.icon_detail_view);
                    String imagenya = datanya.getGambar_produk();
                    Picasso.with(ProductDetail.this).load(imagenya).into(icon);

                    int n = 10;
                    List<String> list = new ArrayList<String>();
                    for (int x = 1; x <= n; x++) {
                        list.add("" + x);
                    }

                    dataAdapter = new ArrayAdapter<String>(ProductDetail.this,android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    qtyList.setAdapter(dataAdapter);

                    qtyList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String convertEditText = qtyList.getSelectedItem().toString();
                            int hasil = datanya.getHarga_produk() * Integer.parseInt(convertEditText);
                            String hasil2 = String.valueOf(hasil);
                            total.setText(hasil2);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                     /* Parsing TO Chart Page */
                    GoToChart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String qty2 = qtyList.getSelectedItem().toString();
                            int qty3 = Integer.parseInt(qty2);
                            final int price3 = datanya.getHarga_produk();
                            int hasil4 = price3 * qty3;
                            mProgressDialog.show();
                            GotoChart(idProduct,qty3,hasil4,idSelect,dataarray);
                            actChart = Integer.parseInt(qty2);
                            hasil = actChart;
                            hasil2 = hasil + actChart;
                            actCharts.setText(String.valueOf(hasil2));
                        }
                    });


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mProgressDialog.dismiss();
                }
            });

            WebServices.getmInstance(mcontext).addToRequestque(request);

    }

    public void GotoChart(final String idProduct, final int qtyAja, final int price, final String idSelect, final String dataarray[]){

        String table = "ordermenu";

        baseUrl = "https://ph0001.babastudio.org/afand_store/serviceforajax/m_ordermenu.php?idUser=1&idProduk="+idProduct;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                ConvertData convertData = new ConvertData(mcontext);
                showMenu data = convertData.getOrderMenu(response);

                int qty2 = 0;
                if (idSelect != null) {
                    qty2 = data.getQuantity() + qtyAja;
                } else {
                    qty2 = qtyAja;
                }

                int Subtotal = price * qty2;

                showMenu setData = new showMenu();
                setData.setId_user(3);
                setData.setNama_user("Agus Rachman");
                setData.setId_kategori_produk(dataarray[3]);
                setData.setId_product(idProduct);
                setData.setQuantity(qty2);
                setData.setSubtotal(Subtotal);
                setData.setTotalAkhir(Subtotal);

                String inserData = convertData.insertData(setData,baseUrl);
                Log.d("Messagenya",""+inserData);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        WebServices.getmInstance(mcontext).addToRequestque(stringRequest);
    }
}
