package com.example.coleenskitchenbakeryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HistoryCustomerAdapter extends RecyclerView.Adapter<HistoryCustomerAdapter.myviewholder> {

    ArrayList<History> list;
    Context context;

    public HistoryCustomerAdapter(ArrayList<History> list, Context context) {
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        History hist=list.get(position);
        holder.pname.setText("Name:"+hist.getName());
        holder.price.setText("Price:"+hist.getPrice());
        holder.flavours.setText("Order Date:"+hist.getDate());
        holder.deliveryprice.setText("Delivery Date:"+hist.getDdate());
        holder.quantity.setText("Quantity:"+hist.getQuantity());
        Picasso.get().load(hist.getImg_url()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView pname, flavours,quantity,deliveryprice, price;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.cart_pro_img);
            pname = (TextView) itemView.findViewById(R.id.cart_pro_name);
            flavours = (TextView) itemView.findViewById(R.id.cart_pro_flavours);
            price = (TextView) itemView.findViewById(R.id.cart_pro_price);
            deliveryprice=(TextView) itemView.findViewById(R.id.cart_pro_devprice);
            quantity=(TextView)itemView.findViewById(R.id.cart_pro_quantity);

        }
    }
}


