package com.bootcamp.xsis.keta.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bootcamp.xsis.keta.R;

public class ListChartCustom extends BaseAdapter {
    private Context context;
    private showMenu[] values;

//    public ListChartCustom(Context context, showMenu[] values){
//        super();
//        this.context = context;
//        this.values = values;
//    }

    @Override
    public int getCount() {
        if(values == null){
            return 0;
        }else {
            return values.length;
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
        view = layoutInflater.inflate(R.layout.activity_list_chart_custom, null);

        TextView nmProduk = (TextView) view.findViewById(R.id.namaProduk);
        TextView hrgaProduk = (TextView) view.findViewById(R.id.hargaProduk);
        TextView kuantityproduk = (TextView) view.findViewById(R.id.kuantityProduk);
        TextView totalHarga = (TextView) view.findViewById(R.id.totalHargaProduk);

        if(values != null){
            //set nama kota
//            icon.setText(values[i].get_gambar_produk());
            nmProduk.setText(values[i].getNama_produk());
            hrgaProduk.setText(String.valueOf(values[i].getHarga_produk()));
            kuantityproduk.setText(String.valueOf(values[i].getQuantity()));
            totalHarga.setText(String.valueOf(values[i].getSubtotal()));
        }
        return view;
    }
}
