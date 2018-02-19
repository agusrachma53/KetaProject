package com.bootcamp.xsis.keta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bootcamp.xsis.keta.Adapter.ConvertData;
import com.bootcamp.xsis.keta.Adapter.WebServices;
import com.bootcamp.xsis.keta.Adapter.customerRealm;
import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;
import com.bootcamp.xsis.keta.model.Customer;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class loginActivity extends Activity implements View.OnClickListener {

    SessionManager session;
    //    DataHelper dataHelper;
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    Cursor cursor;
    Context context;
    Button b_SignUp, b_SignIn;
    CheckBox check;
    private Realm realm;

    EditText l_phone, l_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // dataHelper = new DataHelper(this);
        session = new SessionManager( getApplicationContext());
        context = this;
        dbHelper = new SQLiteDbHelper(context);
        queryHelper = new QueryHelper(dbHelper);

        l_phone = (EditText) findViewById(R.id.l_phone);
        l_password = (EditText) findViewById(R.id.l_password);
        b_SignUp = (Button)findViewById(R.id.b_signUp);
        b_SignIn = (Button) findViewById(R.id.b_SignIn);
        check = (CheckBox) findViewById(R.id.check);

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ){
                    l_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    l_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        b_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = l_phone.getText().toString();
                String pass = l_password.getText().toString();

                if (phone.length() ==0) {
                    l_phone.setError("Insert your phone number ");
                }
                if (pass.trim().length() ==0) {
                    l_password.setError("Insert Your password");
                }else if(phone.trim().length()> 0 && pass.trim().length()>0) {
//                    cursor = queryHelper.login(phone ,pass);
                    realm.init(context);
                    realm = Realm.getDefaultInstance();
                    SignIn(phone,pass);
                }else{
                    Toast.makeText(getApplicationContext(), "Please Insert Phone and Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b_SignUp.setOnClickListener(this);

    }

    public void SignIn(final String phone, final String password){

        String BaseUrl = "https://ph0001.babastudio.org/afand_store/serviceforajax/m_login_2.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ConvertData convertData = new ConvertData(context);
                customerRealm customerRealm = convertData.LoginCustomer(response);
                Log.d("Respon yah",""+response);
                checkUser(phone,customerRealm);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("errornya",""+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", String.valueOf(phone));
                params.put("password",password);
                return params;
            }
        };

        WebServices.getmInstance(context).addToRequestque(stringRequest);

    }

    public void checkUser(final String phone, final customerRealm getCustomer){

                    Log.d("Lah Uga",""+getCustomer.getCustomer_name());
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm bgRealm) {

                            Customer customer2 = bgRealm.createObject(Customer.class);
                            customer2.setCustomer_id(getCustomer.getCustomer_id());
                            customer2.setCustomer_name(getCustomer.getCustomer_name());
                            customer2.setCustomer_address(getCustomer.getCustomer_address());
                            customer2.setCustomer_country(getCustomer.getCustomer_country());
                            customer2.setCustomer_province(getCustomer.getCustomer_province());
                            customer2.setCustomer_city(getCustomer.getCustomer_city());
                            customer2.setCustomer_post_code(getCustomer.getCustomer_post_code());
                            customer2.setCustomer_email(getCustomer.getCustomer_email());
                            customer2.setCustomer_username(getCustomer.getCustomer_username());
                            customer2.setCustomer_password(getCustomer.getCustomer_password());
                            customer2.setCustomer_gander(getCustomer.getCustomer_gander());
                            customer2.setCustomer_phone(getCustomer.getCustomer_phone());
                            customer2.setCustomer_bank_account(getCustomer.getCustomer_bank_account());
                            customer2.setCustomer_create_date(getCustomer.getCustomer_create_date());


                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            final RealmResults<Customer> realcustomers = realm.where(Customer.class).equalTo("customer_phone",phone).findAll();
                            for(Customer customer: realcustomers){
                                session.createLoginSession(customer.getCustomer_phone() , customer.getCustomer_password(), String.valueOf(customer.getCustomer_id()));
                                Toast.makeText(getApplicationContext(), " WELCOME TO KETAXSIS RESTAURANT \n"+"\n"+customer.getCustomer_name(), Toast.LENGTH_SHORT).show();// Staring MainActivity
                                onBackPressed();
                            }
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Toast.makeText(getApplicationContext(), "Your Account is Not Registered", Toast.LENGTH_SHORT).show();

                        }
                    });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent callSignUp = new Intent(this, signUpActivity.class);
        startActivity(callSignUp);
    }
}