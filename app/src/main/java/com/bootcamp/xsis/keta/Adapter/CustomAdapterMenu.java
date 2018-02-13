package com.bootcamp.xsis.keta.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bootcamp.xsis.keta.ProductDetail;
import com.bootcamp.xsis.keta.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ASUS Notebook on 13/02/2018.
 */

public class CustomAdapterMenu extends RecyclerView.Adapter<CustomAdapterMenu.Holder> {

    private List<showMenu> mListData;
    private Context mContext;


    public CustomAdapterMenu(List<showMenu> mListData, Context mContext){
        this.mListData = mListData;
        this.mContext = mContext;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.place_for_list_tab,null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        final showMenu model = mListData.get(position);

        /* Set Data Makanan */
        holder.name_product.setText(model.getNama_produk());
        holder.price_product.setText(String.valueOf(model.getHarga_produk()));
        String imagenya = model.getGambar_produk();
        String imageUri = imagenya;
        Picasso.with(mContext).load(imageUri).into(holder.icon_product);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,ProductDetail.class);
                i.putExtra("idProduct",model.getId_product());
                i.putExtra("idSelect","plus");
                mContext.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {

        return mListData.size();

    }

    public class Holder extends RecyclerView.ViewHolder {

        public TextView name_product,price_product;
        public ImageView icon_product;
        public RelativeLayout container;


        public Holder(View itemView) {
            super(itemView);


            icon_product = (ImageView) itemView.findViewById(R.id.icon_product);
            name_product = (TextView) itemView.findViewById(R.id.name_product);
            price_product = (TextView) itemView.findViewById(R.id.price_product);
            container = (RelativeLayout) itemView.findViewById(R.id.container);

        }


    }
}
