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
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
    private showMenu showMenus;
    private String baseUrl;
    private int totalqty;
    private ProgressDialog mProgressDialog;
    private TextView name_product;
    private TextView price_produc;
    private TextView total,actCharts,Total;
    private int actChart;
    private CharSequence hasil;
    private int hasil2;
    private ImageView icon;
    private ArrayAdapter<String> dataAdapter;
    private Spinner qtyList;
    private Button GoToChart;
    SessionManager session;


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
        session = new SessionManager( getApplicationContext());

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

                    if(session.qty() == null){
                        totalqty = 0;
                    }else{
                        totalqty = Integer.parseInt(session.qty());
                    }

                     /* Parsing TO Chart Page */
                    GoToChart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mProgressDialog.show();

                            /* count subtotal */
                            int qty3 = Integer.parseInt(String.valueOf(qtyList.getSelectedItem().toString()));
                            final int price3 = datanya.getHarga_produk();
                            int hasil4 = price3 * qty3;

                            /* Count QTY */
                            actChart = qty3;
                            hasil = actCharts.getText();
                            hasil2 = Integer.parseInt((String) hasil) + actChart;
                            session.sessionChart(String.valueOf(hasil2));
                            totalqty = Integer.parseInt(session.qty());
                            actCharts.setText(String.valueOf(totalqty));

                            /* Go To Cart */
                            GotoChart(idProduct,qty3,hasil4,idSelect,dataarray);
                        }
                    });

                    actCharts.setText(String.valueOf(totalqty));

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

        baseUrl = "https://ph0001.babastudio.org/afand_store/serviceforajax/m_ordermenu.php?idUser=3&idProduk="+idProduct;
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

                convertData.insertData(setData,baseUrl);

                ConstraintLayout container = (ConstraintLayout) findViewById(R.id.detailContainer);
                Snackbar snackbar = Snackbar.make(container,"Success Add Cart",Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        WebServices.getmInstance(mcontext).addToRequestque(stringRequest);
    }
}
