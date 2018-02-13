package com.bootcamp.xsis.keta;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bootcamp.xsis.keta.Adapter.ConvertData;
import com.bootcamp.xsis.keta.Adapter.CustomAdapterMenu;
import com.bootcamp.xsis.keta.Adapter.WebServices;
import com.bootcamp.xsis.keta.Adapter.listMenuCustom;
import com.bootcamp.xsis.keta.Adapter.showMenu;
import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;

/**
 * Created by Jack Ma on 1/12/2018.
 */

public class Tab1 extends Fragment {
    private Context mcontext;
    private String baseUrl;
    private ProgressDialog mProgressDialog;
    private AlertDialog.Builder builder;
    private RecyclerView recyclerView;
    private String category_produk = "MK";



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab,container,false);

        mcontext = getActivity();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_item_product);

        mProgressDialog = new ProgressDialog(mcontext);
        builder = new AlertDialog.Builder(mcontext);
        mProgressDialog.setMessage("Loading ...");
        mProgressDialog.show();

        getdata();
        return rootView;
    }

    private void getdata(){

        baseUrl = "https://ph0001.babastudio.org/afand_store/serviceforajax/m_product.php?catProduct="+category_produk;
        StringRequest request = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();

                ConvertData convertData = new ConvertData();
                CustomAdapterMenu customAdapterMenu = new CustomAdapterMenu(convertData.getAllProduct(response),getContext());
                customAdapterMenu.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(customAdapterMenu);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
            }
        });

        WebServices.getmInstance(mcontext).addToRequestque(request);

    }


}
