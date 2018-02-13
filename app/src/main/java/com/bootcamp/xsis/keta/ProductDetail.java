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
import java.util.List;


public class ProductDetail extends AppCompatActivity {

    private Context mcontext;
    private SQLiteDbHelper sqLiteDBHelper;
    public QueryHelper sqlHelper;
    private showMenu[] showMenus;
    private SessionManager session;
    private String baseUrl;
    private ProgressDialog mProgressDialog;
    private TextView Total,name_product,price_produc,total;
    private ImageView icon;
    private  ArrayAdapter<String> dataAdapter;
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
                    ConvertData convertData = new ConvertData();
                    final showMenu datanya =  convertData.getSpesificData(response);

                    final String dataarray[] = new String[7];
                    dataarray[0] = datanya.getId_product();
                    dataarray[1] = datanya.getNama_produk();
                    dataarray[2] = datanya.getGambar_produk();
                    dataarray[3] = datanya.getDesk_produk();
                    dataarray[4] = datanya.getId_kategori_produk();
                    dataarray[5] = String.valueOf(datanya.getHarga_produk());
                    dataarray[6] = datanya.getKategorii_produk();


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

    public void GotoChart(String idProduct, final int qtyAja, int price, final String idSelect, final String dataarray[]){

        String table = "ordermenu";

        baseUrl = "https://ph0001.babastudio.org/afand_store/serviceforajax/m_ordermenu.php?idUser=1&idProduk='"+ idProduct +"'";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                ConvertData convertData = new ConvertData();
                showMenu data = convertData.getOrderMenu(response);

                for(int i = 0; i <  dataarray.length; i++){

                    int qty2 = 0;
                    if(idSelect != null){
                        qty2 = data.getQuantity() + qtyAja;
                    }else{
                        qty2  = qtyAja;
                    }

                    Log.d("qtynyaaja",""+qty2);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        WebServices.getmInstance(mcontext).addToRequestque(stringRequest);



//        session = new SessionManager(getApplicationContext());
//        String phone =  session.phone();
//        this.showMenus = showMenus;
//        this.mcontext = mcontext;
//        sqLiteDBHelper = new SQLiteDbHelper(mcontext);
//        sqlHelper = new QueryHelper(sqLiteDBHelper);
//
//        int idUser;
//        String nameUser;
//        showMenu[] praInsertOrder = sqlHelper.detailUser(phone);

//        nameUser = praInsertOrder[0].getNama_user();
//        phone = praInsertOrder[0].getPhoneNumber();

//        String nameProduct = name_product;
//        showMenu[] preInsert = sqlHelper.showDetailMenu(nameProduct);
//        String id_pr_GoToChart = preInsert[0].getId_product();
//        String nama_pr_GoToChart = preInsert[0].getNama_produk();
//        String gambar_pr_GOToChart = preInsert[0].getGambar_produk();
//        String desk_pr_GoToChart = preInsert[0].getDesk_produk();
//        String category_pr_GoToChart = preInsert[0].getKategorii_produk();
//        int harga_pr_GoToChart = preInsert[0].getHarga_produk();
//        showMenu[] showMenus = sqlHelper.detailUser(phone);
//        idUser  = showMenus[0].getId_user();
//        nameUser = showMenus[0].getNama_user();
//        phone = showMenus[0].getPhoneNumber();
//        nameProduct = name_product;


        //rumus subtotal dan total

//        int subtotal = harga_pr_GoToChart * qtyAja;
//
//        String table = "ordermenu";
//        Cursor countData = sqlHelper.cekData(id_pr_GoToChart,idUser,table);
//        if(countData.getCount() > 0){
//            showMenu[] getSubtotal = sqlHelper.orderPesan2(idUser,id_pr_GoToChart);
//            int getQty = getSubtotal[0].getQuantity();
//
//            int qty2 = 0;
//            if(idSelect != null){
//                qty2 = getQty + qtyAja;
//            }else{
//                qty2  = qtyAja;
//            }
//
//            SQLiteDatabase db = sqLiteDBHelper.getWritableDatabase();
//            db.execSQL("UPDATE "+table+" SET kuantity = '"+ qty2 +"' WHERE id_produk = '"+id_pr_GoToChart+"' AND id_user = '"+idUser+"'");
//            db.execSQL("UPDATE ordermenu SET subtotal = '"+ subtotal + "' WHERE id_produk = '"+id_pr_GoToChart+"' AND id_user = '"+idUser+"'");
//            db.execSQL("UPDATE history SET quantity = '"+ qty2 +"' WHERE idProduk = '"+id_pr_GoToChart+"' AND namaUser = '"+nameUser+"'");
//            db.execSQL("UPDATE history SET subtotal = '"+ subtotal + "' WHERE idProduk = '"+id_pr_GoToChart+"' AND namaUser = '"+nameUser+"'");
//        }else{
////            showMenu[] data = sqlHelper.tambahQty(id_pr_GoToChart,table,idUser);
//            SQLiteDatabase db = sqLiteDBHelper.getWritableDatabase();
//            db.execSQL("INSERT INTO ordermenu(id_user,nama_user,id_kategori_produk,id_produk,kuantity,subtotal) " +
//                    "VALUES('" + idUser + "','" +nameUser+"','"+ category_pr_GoToChart  +"','"+
//                    id_pr_GoToChart +"','"+qtyAja+"','"+price+"')");
//            db.execSQL("INSERT INTO history (idUser,namaUser,idKategoriProduk,idProduk,quantity,subtotal,imageProduk) " +
//                    "VALUES('"+idUser+"','"+nameUser+"','"+ category_pr_GoToChart  +"','"+
//                    id_pr_GoToChart +"','"+qtyAja+"','"+price+"','"+gambar_pr_GOToChart+"')");
//        }
//        Intent goToChartnya = new Intent(ProductDetail.this,ChartPage.class);
//        goToChartnya.putExtra("idnya",idUser);
//        startActivity(goToChartnya);

    }
}
