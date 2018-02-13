package com.bootcamp.xsis.keta.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS Notebook on 13/02/2018.
 */

public class ConvertData {

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
                String nama_user = dataProduct.getString("nama_user");
                String id_kategori_produk = dataProduct.getString("id_kategori_produk");
                String id_produk = dataProduct.getString("id_produk");
                String kuantity = dataProduct.getString("kuantity");
                String subtotal = dataProduct.getString("subtotal");
                String total = dataProduct.getString("total");


                setData.setId_user(Integer.parseInt(id_user));
                setData.setNama_user(nama_user);
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

}
