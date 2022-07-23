package com.example.coleenskitchenbakeryapp.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coleenskitchenbakeryapp.R;
import com.example.coleenskitchenbakeryapp.productsconfirm;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowOrderAdapter  extends RecyclerView.Adapter<ShowOrderAdapter.myviewholder> {
    Context context;
    ArrayList<productsconfirm> list;
    DatabaseReference ref;
    FirebaseAuth auth;
    String Auth;

    public ShowOrderAdapter(ArrayList<productsconfirm> list, Context context) {
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showproduct, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        productsconfirm productsconfirm = list.get(position);
        holder.name.setText("Date Of Ordering:" + productsconfirm.getDate());
        holder.flavour.setText("Quantity:" + productsconfirm.getQuantity());
        holder.price.setText("Name:" + productsconfirm.getPname());
        holder.deliprice.setText("Product Id:" + productsconfirm.getPid());
        holder.quantity.setText("Description Entered:" + productsconfirm.getDesc());
        holder.wordings.setText("Order Id: " + productsconfirm.getUuid());
        holder.desc.setText("Words to add:" + productsconfirm.getWordings());
        holder.pid.setText("Flavours:"+productsconfirm.getFlavour());
        holder.date.setText("Price: Rs."+productsconfirm.getPrice());
        holder.uuid.setText("Delivery Price: Rs."+productsconfirm.getDeliprice());
        Picasso.get().load(productsconfirm.getImg_url()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AccountInfoActivity.class);
                intent.putExtra("uuid", productsconfirm.getUuid());
                intent.putExtra("uid", productsconfirm.getUid());
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,flavour,price,deliprice,quantity,wordings,desc,pid,date,uuid;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.a_showimg);
            name = (TextView) itemView.findViewById(R.id.a_showName);
            flavour = (TextView) itemView.findViewById(R.id.a_showflavours);
            price = (TextView) itemView.findViewById(R.id.a_showPrice);
            deliprice= (TextView) itemView.findViewById(R.id.a_showdevprice);
            quantity=  (TextView) itemView.findViewById(R.id.a_showquantity);
            wordings= (TextView) itemView.findViewById(R.id.a_showcatid);
            desc= (TextView) itemView.findViewById(R.id.a_showweight);
            pid=(TextView)itemView.findViewById(R.id.a_showpid);
            date=(TextView)itemView.findViewById(R.id.a_showcatname);
            uuid=(TextView)itemView.findViewById(R.id.a_showdesc);

        }
    }

}
