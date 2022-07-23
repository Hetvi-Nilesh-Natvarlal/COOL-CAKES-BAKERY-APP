package com.example.coleenskitchenbakeryapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class cartviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView image;
    TextView pname, flavours,quantity,deliveryprice, price;
    private ItemClickListener itemClickListener;

    public cartviewholder(@NonNull View itemView) {
        super(itemView);

        image = (ImageView) itemView.findViewById(R.id.cart_pro_img);
        pname = (TextView) itemView.findViewById(R.id.cart_pro_name);
        flavours = (TextView) itemView.findViewById(R.id.cart_pro_flavours);
        price = (TextView) itemView.findViewById(R.id.cart_pro_price);
        deliveryprice=(TextView) itemView.findViewById(R.id.cart_pro_devprice);
        quantity=(TextView)itemView.findViewById(R.id.cart_pro_quantity);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }
}
