package com.bootcamp.xsis.keta.Adapter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bootcamp.xsis.keta.SessionManager;
import com.bootcamp.xsis.keta.model.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS Notebook on 13/02/2018.
 */

public class ConvertData {

    private showMenu showMenus;
    private String successMessage;
    private Context context;
    private SessionManager session;

    public ConvertData(Context context){
        this.context = context;
    }

    public List<showMenu> mListData;

    public List<showMenu> getAllProduct(String response){

        mListData = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for(int i =0; i < jsonArray.length(); i++){

                JSONObject dataProduct = jsonArray.getJSONObject(i);

                String id_produk = dataProduct.getString("id_produk");
                String nama_produk = dataProduct.getString("nama_produk");
                String gambar_produk = dataProduct.getString("gambar_produk");
                String desk_produk = dataProduct.getString("desk_produk");
                String id_kategori_produk = dataProduct.getString("id_kategori_produk");
                String harga_produk = dataProduct.getString("harga_produk");
                String kategori_produk = dataProduct.getString("kategori_produk");

                showMenu setData = new showMenu();
                setData.setId_product(id_produk);
                setData.setNama_produk(nama_produk);
                setData.setGambar_produk(gambar_produk);
                setData.setDesk_produk(desk_produk);
                setData.setId_kategori_produk(id_kategori_produk);
                setData.setHarga_produk(Integer.parseInt(harga_produk));
                setData.setKategorii_produk(kategori_produk);
                mListData.add(setData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mListData;
    }

    public List<showMenu> getAllProductChart(String response){

        mListData = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for(int i =0; i < jsonArray.length(); i++){

                JSONObject dataProduct = jsonArray.getJSONObject(i);

                showMenu setData = new showMenu();

                    String id_produk = dataProduct.getString("id_produk");
                    String nama_produk = dataProduct.getString("nama_produk");
                    String gambar_produk = dataProduct.getString("gambar_produk");
                    String desk_produk = dataProduct.getString("desk_produk");
                    String id_kategori_produk = dataProduct.getString("id_kategori_produk");
                    String harga_produk = dataProduct.getString("harga_produk");
                    String kategori_produk = dataProduct.getString("kategori_produk");
                    String Quantity = dataProduct.getString("kuantity");

                    setData.setId_product(id_produk);
                    setData.setNama_produk(nama_produk);
                    setData.setGambar_produk(gambar_produk);
                    setData.setDesk_produk(desk_produk);
                    setData.setId_kategori_produk(id_kategori_produk);
                    setData.setHarga_produk(Integer.parseInt(harga_produk));
                    setData.setKategorii_produk(kategori_produk);
                    setData.setQuantity(Integer.parseInt(Quantity));

                    if(dataProduct.getString("subtotal") != null){
                        String subtotal = dataProduct.getString("subtotal");
                        setData.setSubtotal(Integer.parseInt(subtotal));
                    }

                mListData.add(setData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mListData;
    }

    public showMenu getTotal(String response){
        showMenu setData = new showMenu();
        try {

            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            int total = 0;
            for(int i =0; i < jsonArray.length(); i++) {
                JSONObject dataProduct = jsonArray.getJSONObject(i);
                    total += Integer.parseInt(dataProduct.getString("subtotal"));
            }
            setData.setTotalAkhir(total);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return setData;
    }

    public showMenu getSpesificData(String response){

        showMenu setData = new showMenu();

        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length();i++){
                JSONObject dataProduct = jsonArray.getJSONObject(i);
                String id_produk = dataProduct.getString("id_produk");
                String nama_produk = dataProduct.getString("nama_produk");
                String gambar_produk = dataProduct.getString("gambar_produk");
                String desk_produk = dataProduct.getString("desk_produk");
                String id_kategori_produk = dataProduct.getString("id_kategori_produk");
                String harga_produk = dataProduct.getString("harga_produk");
                String kategori_produk = dataProduct.getString("kategori_produk");



                setData.setId_product(id_produk);
                setData.setNama_produk(nama_produk);
                setData.setGambar_produk(gambar_produk);
                setData.setDesk_produk(desk_produk);
                setData.setId_kategori_produk(id_kategori_produk);
                setData.setHarga_produk(Integer.parseInt(harga_produk));
                setData.setKategorii_produk(kategori_produk);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return setData;
    }

    public customerRealm LoginCustomer(String response){

        customerRealm customer = new customerRealm();;

        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if(jsonArray.length() > 0){
                for (int i = 0; i < jsonArray.length();i++){
                    JSONObject dataProduct = jsonArray.getJSONObject(i);
                    int  customer_id = dataProduct.getInt("customer_id");
                    String  customer_name = dataProduct.getString("customer_name");
                    String  customer_address = dataProduct.getString("customer_address");
                    String  country = dataProduct.getString("country");
                    String  province = dataProduct.getString("province");
                    String  city = dataProduct.getString("city");
                    int  post_code = dataProduct.getInt("post_code");
                    String  customer_email = dataProduct.getString("customer_email");
                    String  username = dataProduct.getString("username");
                    String  password = dataProduct.getString("password");
                    String  gender = dataProduct.getString("gender");
                    String  phone_no = dataProduct.getString("phone_no");
                    String  bank_account = dataProduct.getString("bank_account");
                    String  create_date = dataProduct.getString("create_date");

                    customer.setCustomer_id(customer_id);
                    customer.setCustomer_name(customer_name);
                    customer.setCustomer_address(customer_address);
                    customer.setCustomer_country(country);
                    customer.setCustomer_province(province);
                    customer.setCustomer_city(city);
                    customer.setCustomer_post_code(post_code);
                    customer.setCustomer_email(customer_email);
                    customer.setCustomer_username(username);
                    customer.setCustomer_password(password);
                    customer.setCustomer_gander(gender);
                    customer.setCustomer_phone(phone_no);
                    customer.setCustomer_bank_account(bank_account);
                    customer.setCustomer_create_date(create_date);
                }
            }else{
                Log.d("responya",""+jsonObject.getString("message"));
                Toast.makeText(context,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public showMenu getOrderMenu(String response){

        showMenu setData = new showMenu();

        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length();i++){
                JSONObject dataProduct = jsonArray.getJSONObject(i);
                String id_user = dataProduct.getString("id_user");
                String id_kategori_produk = dataProduct.getString("id_kategori_produk");
                String id_produk = dataProduct.getString("id_produk");
                String kuantity = dataProduct.getString("kuantity");
                String subtotal = dataProduct.getString("subtotal");
                String total = dataProduct.getString("total");

                setData.setId_user(Integer.parseInt(id_user));
                setData.setId_kategori_produk(id_kategori_produk);
                setData.setId_product(id_produk);
                setData.setQuantity(Integer.parseInt(kuantity));
                setData.setSubtotal(Integer.parseInt(subtotal));
                setData.setTotalAkhir(Integer.parseInt(total));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return setData;
    }

    public void insertData(final showMenu dataInsert, String baseUrl) {

        baseUrl = "https://ph0001.babastudio.org/afand_store/serviceforajax/m_insertOrderMenu.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(dataInsert.getId_user()));
                params.put("nama_user",dataInsert.getNama_user());
                params.put("id_kategori_produk",dataInsert.getId_kategori_produk());
                params.put("id_produk",dataInsert.getId_product());
                params.put("kuantity", String.valueOf(dataInsert.getQuantity()));
                params.put("subtotal", String.valueOf(dataInsert.getSubtotal()));
                params.put("total", String.valueOf(dataInsert.getTotalAkhir()));
                return params;
            }
        };
        WebServices.getmInstance(context).addToRequestque(stringRequest);
    };




}
