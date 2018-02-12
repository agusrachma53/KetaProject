package com.bootcamp.xsis.keta.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bootcamp.xsis.keta.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by Jack Ma on 1/17/2018.
 */

public class listMenuCustom extends BaseAdapter {

    private Context context;
    private showMenu[] showMenus;
    public listMenuCustom(Context context, showMenu[] showMenus){
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
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.place_for_list_tab,null);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon_product);
        TextView name_product = (TextView) convertView.findViewById(R.id.name_product);
        TextView price_product = (TextView) convertView.findViewById(R.id.price_product);
        TextView id_produknya = (TextView) convertView.findViewById(R.id.id_produnya);
        if (showMenus != null){
            String imagenya = showMenus[position].getGambar_produk();
            String imageUri = imagenya;
            Picasso.with(context).load(imageUri).into(icon);
            name_product.setText(showMenus[position].getNama_produk());
            price_product.setText(String.valueOf(showMenus[position].getHarga_produk()));
            id_produknya.setText(showMenus[position].getId_product());
        }
        return convertView;
    }



}
