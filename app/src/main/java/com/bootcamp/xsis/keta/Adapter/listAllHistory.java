package com.bootcamp.xsis.keta.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;
import com.bootcamp.xsis.keta.DetailHistory;
import com.bootcamp.xsis.keta.History;
import com.bootcamp.xsis.keta.Konstanta;
import com.bootcamp.xsis.keta.R;
import com.bootcamp.xsis.keta.SessionManager;

/**
 * Created by Jack Ma on 1/25/2018.
 */

public class listAllHistory extends BaseAdapter {

    private SQLiteDbHelper sqLiteDBHelper;
    public QueryHelper sqlHelper;
    private Context context;
    private showMenu[] showMenus;
    SessionManager session;

    public listAllHistory(Context context, showMenu[] showMenus){
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
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.customhistorychart,null);

        TextView name_history = (TextView) convertView.findViewById(R.id.name_history);
        TextView kodePesananHistory = (TextView) convertView.findViewById(R.id.kodePesananHistory);
        TextView totalnyadong = (TextView) convertView.findViewById(R.id.totalnyadong);

        final String namaUser = showMenus[position].getNama_user();
        final String Code = showMenus[position].getKode_pesanan();
        final String total = String.valueOf(showMenus[position].getTotalAkhir());

        name_history.setText(namaUser);
        kodePesananHistory.setText(Code);
        totalnyadong.setText(String.valueOf(total));

        sqLiteDBHelper = new SQLiteDbHelper(context);
        sqlHelper = new QueryHelper(sqLiteDBHelper);

        Button delete = (Button) convertView.findViewById(R.id.del_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlHelper.deleteHistory(Code);
                Intent refresh = new Intent(context,History.class);
                refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(refresh);
            }
        });

        return convertView;
    }

    public void deleetHistory(String CodePemesanan){sqlHelper.deleteHistory(CodePemesanan);}
}
