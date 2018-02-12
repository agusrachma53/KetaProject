package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bootcamp.xsis.keta.Adapter.showMenu;
import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShowPayment extends AppCompatActivity {


    Context context;
    Cursor cursor, cursorId, cursorOrdemenu;
    private TextView txtShow, txtList, txtAtm, txtNameUser, txtAddress, txtKodee, textTotal, txtNoBank;
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    private  String nama, alamat, atm, kodebook, totalBiaya, itemList, phone, pass, nameUser;
    private int total, noRek, s_idUser;
    private Button btnBayar, btnBatal;
    private showMenu[] menus;
    private SessionManager session;
    private SessionManagerKP sessionKP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_payment);

        Intent getData = (Intent) getIntent();
        final String idUser = getData.getExtras().getString("idNya");
        final String namaUser = getData.getExtras().getString("nama");
        final String KodePesanan = getData.getExtras().getString("kode");
        final String subtotalnya = getData.getExtras().getString("subtotal");
        alamat = getData.getExtras().getString("alamat");
        final String atm = getData.getExtras().getString("atm");
        final int totalbayar = getData.getExtras().getInt("total");


/*        dbHelper = new SQLiteDbHelper(context);
        queryHelper = new QueryHelper(dbHelper);*/
        session = new SessionManager(getApplicationContext());
        sessionKP = new SessionManagerKP(getApplicationContext());

        txtAddress = (TextView) findViewById(R.id.txtAlamat);
        txtAtm = (TextView) findViewById(R.id.txtATM);
        txtNameUser = (TextView) findViewById(R.id.txtNamaUser);
        textTotal = (TextView) findViewById(R.id.txtTotalBayar);
        EditText editNorek = (EditText) findViewById(R.id.editNoRek);
        EditText editKodePesanan = (EditText) findViewById(R.id.editKodePesan);
        EditText idNorek = (EditText) findViewById(R.id.editNoRek);
        int noRek = 1450045965;
        idNorek.setEnabled(false);
        editKodePesanan.setEnabled(false);
        idNorek.setText(""+noRek);
        editKodePesanan.setText(KodePesanan);

        btnBayar = (Button) findViewById(R.id.buttonbayar);
        btnBatal = (Button) findViewById(R.id.buttonbatal);

        txtAddress.setText(alamat);
        txtAtm.setText(atm);
        txtNameUser.setText(namaUser);
        textTotal.setText(String.valueOf(totalbayar));

        this.context = context;
        this.menus = menus;
        SQLiteDbHelper sqLiteDBHelper = new SQLiteDbHelper(getApplicationContext());
        final QueryHelper sqlHelper = new QueryHelper(sqLiteDBHelper);
        sqLiteDBHelper = new SQLiteDbHelper(context);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
        LinearLayout insertPoint = (LinearLayout)findViewById(R.id.placeForLooping);
        showMenu[] menus = sqlHelper.orderPesan(Integer.parseInt(idUser));
        List views = new ArrayList();
        for(int i = 0; i < menus.length; i++){
            View view = inflater.inflate(R.layout.custompaymentshow,null);
            // Definition
            TextView name = (TextView) view.findViewById(R.id.name_showPayment);
            TextView qty = (TextView) view.findViewById(R.id.qtynya_showPayment);
            TextView priceChart = (TextView) view.findViewById(R.id.price_showPaymentt);
            TextView subtotal = (TextView) view.findViewById(R.id.subtotal_showPayment);
            ImageView imageView = (ImageView) view.findViewById(R.id.icon_showPayment);
            name.setText(menus[i].getNama_produk());
            qty.setText( String.valueOf(menus[i].getQuantity()));
            priceChart.setText(String.valueOf(menus[i].getHarga_produk()));
            subtotal.setText(String.valueOf(menus[i].getSubtotal()));
            String imagenya = menus[i].getGambar_produk();
            String imageUri = imagenya;
            Picasso.with(context).load(imageUri).into(imageView);
            insertPoint.addView(view);
        }
//        tampilkanDataOrder();
        final SQLiteDbHelper finalSqLiteDBHelper = sqLiteDBHelper;
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                alertbox.setMessage(" Are you sure to confirm payment ?");
                alertbox.setTitle("Warning");
                alertbox.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Your Order Success", Toast.LENGTH_LONG).show();
                        Log.d("masuk", "masuk");
                        String Table = "ordermenu";
                        sqlHelper.deletemenu(idUser,Table);
                        sessionKP.confirm();
                        Intent intent = new Intent(ShowPayment.this, MainActivity.class);
                        startActivity(intent);
                        Log.d("masuk", "Hasil Hapus");

                    }
                });
                alertbox.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertbox.show();
            }

        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String tampilkanItemOrder(){
        String result = "";

        for(int t=0; t<menus.length; t++){
            result += menus[t].getNama_produk() + " ---> " +
                    menus[t].getHarga_produk() + "\n";
        }
        return result;
    }
}
