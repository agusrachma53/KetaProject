package com.bootcamp.xsis.keta.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bootcamp.xsis.keta.ChartPage;
import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;
import com.bootcamp.xsis.keta.Konstanta;
import com.bootcamp.xsis.keta.MainActivity;
import com.bootcamp.xsis.keta.ProductDetail;
import com.bootcamp.xsis.keta.R;
import com.bootcamp.xsis.keta.SessionManager;
import com.squareup.picasso.Picasso;

/**
 * Created by Jack Ma on 1/19/2018.
 */

public class ListOrder extends BaseAdapter {
    private SQLiteDbHelper sqLiteDBHelper;
    public QueryHelper sqlHelper;
    private Context context;
    private showMenu[] showMenus;
    SessionManager session;

    public ListOrder(Context context, showMenu[] showMenus){
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
        LayoutInflater  layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.item_list_view_chart_custom,null);
        this.context = context;
        sqLiteDBHelper = new SQLiteDbHelper(context);
        sqlHelper = new QueryHelper(sqLiteDBHelper);
        session = new SessionManager(context);
        final String phone =  session.phone();
        final showMenu[] getUser = sqlHelper.detailUser(phone);
        final showMenu[] getOrder = sqlHelper.orderPesan(getUser[0].getId_user());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.icon_chart);
        TextView textName = (TextView) convertView.findViewById(R.id.name_chart);
        TextView textPrice = (TextView) convertView.findViewById(R.id.price_chart);
        TextView subtotal = (TextView) convertView.findViewById(R.id.subtotal);
        TextView qtynya = (TextView) convertView.findViewById(R.id.qtynya);

        String imagenya = showMenus[position].getGambar_produk();
        String imageUri = imagenya;
        Picasso.with(context).load(imageUri).into(imageView);
        textName.setText(getOrder[position].getNama_produk());

        textPrice.setText(String.valueOf(getOrder[position].getHarga_produk()));
        int subtotalnya =  getOrder[position].getSubtotal();
        subtotal.setText(String.valueOf(subtotalnya));
        qtynya.setText(String.valueOf(getOrder[position].getQuantity()));
        Button edit = (Button) convertView.findViewById(R.id.editPlease);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent detail = new Intent(context , ProductDetail.class);
                String id_produk1 = showMenus[position].getId_product();
                Log.d("posisi", ""+getOrder[position].getId_product());
                String nama_product = getOrder[position].getNama_produk();
                detail.putExtra("namaProduct",nama_product);
                Log.d("jajajajajajaj",""+nama_product);
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detail);
            }
        });

        Button delete = (Button) convertView.findViewById(R.id.del_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setMessage(" Are you sure delete item ?");
                alertbox.setTitle("Warning");
                alertbox.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id_produk = showMenus[position].getId_product();
                        String Table = "ordermenu";
                        sqLiteDBHelper = new SQLiteDbHelper(context);
                        sqlHelper = new QueryHelper(sqLiteDBHelper);
                        sqlHelper.delete(id_produk,Table);
                        Intent refresh = new Intent(context,ChartPage.class);
                        int idUsernya = getUser[0].getId_user();
                        refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        refresh.putExtra("idnya",idUsernya);
                        context.startActivity(refresh);
                        Toast.makeText(context, "Item was deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
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
        return convertView;
    }


    private void finish() {
        finish();
    }

}
