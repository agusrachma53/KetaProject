package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bootcamp.xsis.keta.Adapter.listMenuCustom;
import com.bootcamp.xsis.keta.Adapter.showMenu;
import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;

/**
 * Created by Jack Ma on 1/12/2018.
 */

public class Tab1 extends Fragment {
    Context mcontext;
    private SQLiteDbHelper sqLiteDBHelper;
    public QueryHelper sqlHelper;
    showMenu[] showMenus;
    String category_produk;



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab,container,false);

        category_produk = "MK";

        mcontext = getActivity();
        sqLiteDBHelper = new SQLiteDbHelper(mcontext);
        sqlHelper = new QueryHelper(sqLiteDBHelper);
        ListView listView = (ListView)rootView.findViewById(R.id.list_item_product);
        showMenus = sqlHelper.Menunya(category_produk);
        listView.setAdapter(new listMenuCustom(mcontext,showMenus));
        listView.setOnItemClickListener(onListClick);
        return rootView;
    }
    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        Context context;

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            showMenu selected = showMenus[i];
            Intent intent = new Intent(getActivity(), ProductDetail.class);
            intent.putExtra("namaProduct",selected.getNama_produk());
            intent.putExtra("idSelect","plus");
            intent.putExtra(Konstanta.ID_EXTRA_1,selected.getGambar_produk());
            startActivity(intent);
        }
    };


}
