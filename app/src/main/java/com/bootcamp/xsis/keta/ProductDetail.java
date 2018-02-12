package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;
import com.bootcamp.xsis.keta.Adapter.showMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ProductDetail extends AppCompatActivity {

    Context mcontext;
    private SQLiteDbHelper sqLiteDBHelper;
    public QueryHelper sqlHelper;
    showMenu[] showMenus;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        this.mcontext = mcontext;
        mcontext = getApplication();
        this.showMenus = showMenus;
        session = new SessionManager(getApplicationContext());

        sqLiteDBHelper = new SQLiteDbHelper(mcontext);
        sqlHelper = new QueryHelper(sqLiteDBHelper);

        final Intent intent = (Intent) getIntent();
        final String nama_product = intent.getExtras().getString("namaProduct");
        final String idSelect = intent.getExtras().getString("idSelect");
        Log.d("bisalah ayooo",""+idSelect);

        final TextView Total = (TextView) findViewById(R.id.name_of_total);
        final TextView name_product = (TextView) findViewById(R.id.name_of_item_detail2);
        final TextView price_produc = (TextView) findViewById(R.id.name_of_price2);
        final Spinner qtyList = (Spinner) findViewById(R.id.qtySpinner);
        final TextView total = (TextView) findViewById(R.id.name_of_total);
        final Button GoToChart = (Button) findViewById(R.id.goToChart);

        final showMenu[] showMenus = sqlHelper.showDetailMenu(nama_product);

        if (showMenus.length > 0) {
            for (int i = 0; i < showMenus.length; i++) {
                String select = showMenus[i].getNama_produk();
                name_product.setText(select);
                final int price = showMenus[i].getHarga_produk();
                price_produc.setText(String.valueOf(price));
                ImageView icon = (ImageView) findViewById(R.id.icon_detail_view);
                String imagenya = showMenus[i].getGambar_produk();
                String imageUri = imagenya;
                Picasso.with(getApplicationContext()).load(imagenya).into(icon);

                int n = 10;
                List<String> list = new ArrayList<String>();
                for (int x = 1; x <= n; x++) {
                    list.add("" + x);
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                qtyList.setAdapter(dataAdapter);

                qtyList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String convertEditText = qtyList.getSelectedItem().toString();
                        int hasil = price * Integer.parseInt(convertEditText);
                        String hasil2 = String.valueOf(hasil);
                        total.setText(hasil2);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                total.setText(String.valueOf(price));
                /* Parsing TO Chart Page */
                GoToChart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String qty2 = qtyList.getSelectedItem().toString();
                        int qty3 = Integer.parseInt(qty2);
                        final int price3 = showMenus[0].getHarga_produk();
                        int hasil4 = price3 * qty3;
                        GotoChart(nama_product,qty3,hasil4,idSelect);
                    }
                });

                 /* Back To List Menu */
                Button goBackListMenu = (Button) findViewById(R.id.back);
                goBackListMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }

        }
    }

    public void GotoChart(String name_product, int qtyAja, int price, String idSelect){

        session = new SessionManager(getApplicationContext());
        String phone =  session.phone();
        this.showMenus = showMenus;
        this.mcontext = mcontext;
        sqLiteDBHelper = new SQLiteDbHelper(mcontext);
        sqlHelper = new QueryHelper(sqLiteDBHelper);

        int idUser;
        String nameUser;
        showMenu[] praInsertOrder = sqlHelper.detailUser(phone);


        nameUser = praInsertOrder[0].getNama_user();
        phone = praInsertOrder[0].getPhoneNumber();

        String nameProduct = name_product;
        showMenu[] preInsert = sqlHelper.showDetailMenu(nameProduct);
        String id_pr_GoToChart = preInsert[0].getId_product();
        String nama_pr_GoToChart = preInsert[0].getNama_produk();
        String gambar_pr_GOToChart = preInsert[0].getGambar_produk();
        String desk_pr_GoToChart = preInsert[0].getDesk_produk();
        String category_pr_GoToChart = preInsert[0].getKategorii_produk();
        int harga_pr_GoToChart = preInsert[0].getHarga_produk();
        showMenu[] showMenus = sqlHelper.detailUser(phone);
        idUser  = showMenus[0].getId_user();
        nameUser = showMenus[0].getNama_user();
        phone = showMenus[0].getPhoneNumber();
        nameProduct = name_product;


        //rumus subtotal dan total

        int subtotal = harga_pr_GoToChart * qtyAja;

        String table = "ordermenu";
        Cursor countData = sqlHelper.cekData(id_pr_GoToChart,idUser,table);
        if(countData.getCount() > 0){
            showMenu[] getSubtotal = sqlHelper.orderPesan2(idUser,id_pr_GoToChart);
            int getQty = getSubtotal[0].getQuantity();

            int qty2 = 0;
            if(idSelect != null){
                qty2 = getQty + qtyAja;
                Log.d("gogogogog",""+qty2);
            }else{
                qty2  = qtyAja;
            }
//            int qty2 = getQty + qtyAja;
//            int subtotalx = getSubtotal[0].getSubtotal();
//            int subtotaly = subtotalx + subtotal;
            SQLiteDatabase db = sqLiteDBHelper.getWritableDatabase();
            db.execSQL("UPDATE "+table+" SET kuantity = '"+ qty2 +"' WHERE id_produk = '"+id_pr_GoToChart+"' AND id_user = '"+idUser+"'");
            db.execSQL("UPDATE ordermenu SET subtotal = '"+ subtotal + "' WHERE id_produk = '"+id_pr_GoToChart+"' AND id_user = '"+idUser+"'");
            db.execSQL("UPDATE history SET quantity = '"+ qty2 +"' WHERE idProduk = '"+id_pr_GoToChart+"' AND namaUser = '"+nameUser+"'");
            db.execSQL("UPDATE history SET subtotal = '"+ subtotal + "' WHERE idProduk = '"+id_pr_GoToChart+"' AND namaUser = '"+nameUser+"'");
        }else{
//            showMenu[] data = sqlHelper.tambahQty(id_pr_GoToChart,table,idUser);
            SQLiteDatabase db = sqLiteDBHelper.getWritableDatabase();
            db.execSQL("INSERT INTO ordermenu(id_user,nama_user,id_kategori_produk,id_produk,kuantity,subtotal) " +
                    "VALUES('" + idUser + "','" +nameUser+"','"+ category_pr_GoToChart  +"','"+
                    id_pr_GoToChart +"','"+qtyAja+"','"+price+"')");
            db.execSQL("INSERT INTO history (idUser,namaUser,idKategoriProduk,idProduk,quantity,subtotal,imageProduk) " +
                    "VALUES('"+idUser+"','"+nameUser+"','"+ category_pr_GoToChart  +"','"+
                    id_pr_GoToChart +"','"+qtyAja+"','"+price+"','"+gambar_pr_GOToChart+"')");
        }
//        Toast.makeText(getApplicationContext(),"Success Add To Cart"+qtyAja,Toast.LENGTH_LONG).show();
        Intent goToChartnya = new Intent(ProductDetail.this,ChartPage.class);
        goToChartnya.putExtra("idnya",idUser);
        startActivity(goToChartnya);

    }
}









//

//



//

//
//    }



//}}
