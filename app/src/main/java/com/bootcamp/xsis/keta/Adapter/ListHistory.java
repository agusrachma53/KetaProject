package com.bootcamp.xsis.keta.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;
import com.bootcamp.xsis.keta.R;
import com.bootcamp.xsis.keta.SessionManager;

/**
 * Created by XSIS-NB on 1/25/2018.
 */

public class ListHistory extends BaseAdapter {
    private SQLiteDbHelper sqLiteDBHelper;
    public QueryHelper sqlHelper;
    private Context context;
    private showMenu[] showMenus;
    SessionManager session;

    public ListHistory(Context context, showMenu[] showMenus){
        this.context = context;
        this.showMenus = showMenus;
    }

    @Override
    public int getCount() {
        if (showMenus == null){
        return 0;
    }else{
        return showMenus.length;
    }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.custompaymentshow,null);

        return view;
    }
    private void finish() {
        finish();
    }
}
