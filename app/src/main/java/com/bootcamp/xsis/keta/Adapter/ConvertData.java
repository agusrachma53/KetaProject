package com.bootcamp.xsis.keta.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bootcamp.xsis.keta.R;
import com.bootcamp.xsis.keta.SessionManager;

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

                String id_produk = dataProduct.getString("id_produk");
                String nama_produk = dataProduct.getString("nama_produk");
                String gambar_produk = dataProduct.getString("gambar_produk");
                String desk_produk = dataProduct.getString("desk_produk");
                String id_kategori_produk = dataProduct.getString("id_kategori_produk");
                String harga_produk = dataProduct.getString("harga_produk");
                String kategori_produk = dataProduct.getString("kategori_produk");
                String Quantity = dataProduct.getString("kuantity");
                String subtotal = dataProduct.getString("subtotal");

                showMenu setData = new showMenu();
                setData.setId_product(id_produk);
                setData.setNama_produk(nama_produk);
                setData.setGambar_produk(gambar_produk);
                setData.setDesk_produk(desk_produk);
                setData.setId_kategori_produk(id_kategori_produk);
                setData.setHarga_produk(Integer.parseInt(harga_produk));
                setData.setKategorii_produk(kategori_produk);
                setData.setQuantity(Integer.parseInt(Quantity));
                setData.setSubtotal(Integer.parseInt(subtotal));
                mListData.add(setData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mListData;
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
