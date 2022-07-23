package com.example.coleenskitchenbakeryapp;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;


public class myadapter extends FirebaseRecyclerAdapter<Products,myadapter.myviewholder>{

    Context context;

    public myadapter(@NonNull FirebaseRecyclerOptions<Products> options, Context context,String id) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int i, @NonNull Products products) {
        holder.pname.setText(products.getPname());
        holder.description.setText(products.getDescription());
        holder.price.setText("Rs."+products.getPrice());
        Picasso.get().load(products.getImg()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Product_Details_Activity.class);
                intent.putExtra("pid", products.getPid());
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new myviewholder(view);

    }
    public class myviewholder  extends RecyclerView.ViewHolder {

        ImageView image;
        TextView pname, description, price;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.img);
            pname = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.desc);
            price = (TextView) itemView.findViewById(R.id.price);
        }
    }
}