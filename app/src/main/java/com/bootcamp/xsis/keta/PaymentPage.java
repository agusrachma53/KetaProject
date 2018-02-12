package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bootcamp.xsis.keta.Adapter.showMenu;
import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PaymentPage extends AppCompatActivity {

    private SessionManager session;
    private SessionManagerKP sessionKP;
    Context mContext;
    showMenu[] daftar;
    ArrayAdapter<CharSequence> adapter;
    Button btn1, btn2 ;
    Cursor cursor;
    private String Alamat, ATM, NamaUser, KodePesan, nameUser, subtotalnya;
    private int total;
    private TextView textsubtotal, texttotal, textNama_user, textKodePesan, textbiayaKirim;
    private EditText editText;
    private Spinner spinATM;
    private SQLiteDbHelper dbHelper;
    private QueryHelper qrHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);
        mContext = this;
        dbHelper = new SQLiteDbHelper(mContext);
        qrHelper = new QueryHelper(dbHelper);
        session = new SessionManager(getApplicationContext());
        sessionKP = new SessionManagerKP(getApplicationContext());
        this.mContext = mContext;

        Intent inten = (Intent) getIntent();
        final String ambilID = inten.getExtras().getString("idNya");
        final int TotalNya = inten.getExtras().getInt("totalbelanjannya");
        final String CodePesananya = inten.getExtras().getString("kode_pesannya");
        nameUser = inten.getExtras().getString("nameUser");

        //adapter for spinner ATM
        adapter = ArrayAdapter.createFromResource(this, R.array.atm,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        textNama_user = (TextView)findViewById(R.id.textNamaUser);
        textKodePesan = (TextView)findViewById(R.id.textKodePesanan);
        textsubtotal = (TextView)findViewById(R.id.txtSubTotal);
        texttotal = (TextView)findViewById(R.id.txtTotal);
        textbiayaKirim = (TextView)findViewById(R.id.txtBiayaKirim);
        btn1 = (Button) findViewById(R.id.btnBatal);
        btn2 = (Button) findViewById(R.id.btnBayar);
        editText = (EditText) findViewById(R.id.editAlamat);

        spinATM = (Spinner)findViewById(R.id.spinATM);
        spinATM.setAdapter(adapter);

        this.mContext = mContext;
        this.daftar = daftar;
        SQLiteDbHelper sqLiteDBHelper = new SQLiteDbHelper(getApplicationContext());
        QueryHelper sqlHelper = new QueryHelper(sqLiteDBHelper);
        sqLiteDBHelper = new SQLiteDbHelper(mContext);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        LinearLayout insertPoint = (LinearLayout)findViewById(R.id.listProductPayment);
        showMenu[] menus = sqlHelper.orderPesan(Integer.parseInt(ambilID));
//        List views = new ArrayList();
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
            Picasso.with(mContext).load(imageUri).into(imageView);
            insertPoint.addView(view);
        }

        //nemampilkan Nama User dan Kode Pesanan
        textNama_user.setText(nameUser);
        textKodePesan.setText(sessionKP.kodePesan());

        kalkulasiOrder(TotalNya, ambilID);

        spinATM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0 && editText.getText().toString().length() ==0){
                    Toast.makeText(getBaseContext()," Please Select ATM ", Toast.LENGTH_SHORT).show();
                    btn2.setEnabled(false);
                }else if(i == 0 && editText.getText().toString().length() !=0){
//                    Toast.makeText(getBaseContext()," Please Select ATM ", Toast.LENGTH_SHORT).show();
                    btn2.setEnabled(false);
                }else if(i != 0 && editText.getText().toString().length() ==0){
//                    Toast.makeText(getBaseContext(),"Please Insert Your Address..", Toast.LENGTH_SHORT).show();
                    btn2.setEnabled(false);
                }
                else {
                    btn2.setEnabled(true);
                    Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(i)+" selected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String convertEditText = editText.getText().toString();

                if (convertEditText.length() == 0  && spinATM.getSelectedItem().toString().length()==0) {
                    editText.setError("Fill your Address !! ");
                    btn2.setEnabled(false);
                }else if (convertEditText.length() != 0  && spinATM.getSelectedItem().toString().length()==0) {
//                  editText.setError("Fill your Address !! ");
                    btn2.setEnabled(false);
                }else if (convertEditText.length() == 0  && spinATM.getSelectedItem().toString().length()!=0) {
//                    editText.setError("Fill your Address !! ");
                    btn2.setEnabled(false);
                }
                else{
                    btn2.setEnabled(true);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nemampilkan Nama User dan Kode Pesanan
                Alamat = editText.getText().toString();
                ATM = spinATM.getSelectedItem().toString();
                String namaUser = textNama_user.getText().toString();

                String kdPesan = sessionKP.kodePesan();
//                Toast.makeText(PaymentPage.this,"Ini Kodenya == " + kdPesan, Toast.LENGTH_SHORT).show();

                int subTotal = Integer.parseInt(textsubtotal.getText().toString());
                total = Integer.parseInt(texttotal.getText().toString());

                cursor = qrHelper.order(Integer.parseInt(ambilID), sessionKP.kodePesan());
                if(cursor.getCount() == 0){
                    //insert new
                    qrHelper.klikOrder(Alamat, ATM, Integer.parseInt(ambilID), CodePesananya, subTotal, total);
                }
                else{
                    //update
                    qrHelper.updateOrder(Alamat, ATM, Integer.parseInt(ambilID), CodePesananya, subTotal, total);
                }

                sendMessage(ambilID,CodePesananya);
//                Toast toast = Toast.makeText(getApplicationContext(),"Payment Process" + namaUser , Toast.LENGTH_LONG);
//                toast.show();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void sendMessage(String idx, String KodePesanan){
        // Do something in response to button
        Intent intent =new Intent(PaymentPage.this, ShowPayment.class);

        Alamat = editText.getText().toString();
        ATM = spinATM.getSelectedItem().toString();
        NamaUser = textNama_user.getText().toString();
        KodePesan = textKodePesan.getText().toString();
        subtotalnya = textsubtotal.getText().toString();

        intent.putExtra("idNya", idx);
        intent.putExtra("alamat", Alamat);
        intent.putExtra("atm", ATM);
        intent.putExtra("nama", NamaUser);
        intent.putExtra("kode", KodePesanan);
        intent.putExtra("total", total);
        intent.putExtra("nameUser",nameUser);
//        intent.putExtra("subtotal", subtotalnya);
        startActivity(intent);
    }

    private void kalkulasiOrder(int totalBayar, String ambilID){
        //cek orderpesanan exist or not
        Cursor cursor = qrHelper.order(Integer.parseInt(ambilID), sessionKP.kodePesan());
        if(cursor.getCount() == 0){
            Log.d("debugOrder", "kalkulasiOrder cursor.getCount() == 0");
        }
        else{
            Log.d("debugOrder", "kalkulasiOrder SET");
            cursor.moveToFirst();
            cursor.moveToPosition(0);

            //alamat
            editText.setText(cursor.getString(3));
            //atm
            spinATM.setSelection(getSelectedPosition(cursor.getString(4)));
        }

        int biaya_kirim = 20000;
        textbiayaKirim.setText(""+biaya_kirim);
        textsubtotal.setText(""+totalBayar);
        total = Integer.parseInt(String.valueOf(totalBayar))+Integer.parseInt(String.valueOf(biaya_kirim));
        texttotal.setText(""+total);
    }
    private int getSelectedPosition(String selected){
        int position = adapter.getPosition(selected);
        Log.d("debug", "selected : "+position);

        return position;
    }
}
