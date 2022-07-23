package com.example.coleenskitchenbakeryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ProAdapter extends RecyclerView.Adapter<ProAdapter.MyViewHolder> {

Context context;
ArrayList<ProOrder> list;
DatabaseReference href;
String ddate;

    public ProAdapter(Context context, ArrayList<ProOrder> list, DatabaseReference href, String ddate) {
        this.context=context;
        this.list=list;
        this.href=href;
        this.ddate=ddate;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.order_items,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       ProOrder order=list.get(position);
       holder.pname.setText("Name:"+order.getPname());
       holder.flavour.setText("Flavours:"+order.getFlavours());
       holder.price.setText("Price:Rs."+order.getPrice());
       holder.desprice.setText("Delivery Price:Rs."+order.getDevprice());
       holder.quantity.setText("Quantity:"+order.getQuantity());
       holder.wordings.setText("Wordings:"+order.getWordings());
       holder.desc.setText("Description Enter:"+order.getDesc_enter());
        Picasso.get().load(order.getImg_url()).into(holder.imgurl);

            String Auth=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ordersref = FirebaseDatabase.getInstance().getReference().child("OrdersInfo").child(Auth);
        String uid=UUID.randomUUID().toString();

        int prices =(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()))+(Integer.parseInt(order.getDevprice()));


        ordersref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                href.child(uid).setValue(snapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            final HashMap<String,Object> omap = new HashMap<>();
                            omap.put("pid",order.getPid());
                            omap.put("date",order.getDate());
                            omap.put("pname",order.getPname());
                            omap.put("flavour",order.getFlavours());
                            omap.put("price",order.getPrice());
                            omap.put("deliprice",order.getDevprice());
                            omap.put("wordings",order.getWordings());
                            omap.put("quantity",order.getQuantity());
                            omap.put("img_url",order.getImg_url());
                            omap.put("desc",order.getDesc_enter());
                            omap.put("uuid",uid);
                            omap.put("TotalPrice",String.valueOf(prices));

                            href.child(uid).updateChildren(omap);

                            Toast.makeText(context,"Successful",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pname,flavour,price,desprice,quantity,wordings,desc;
        ImageView imgurl;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pname = (TextView) itemView.findViewById(R.id.porpname);
            flavour= (TextView) itemView.findViewById(R.id.porflavours);
            price=(TextView) itemView.findViewById(R.id.porprice);
            desprice=(TextView) itemView.findViewById(R.id.pordevprice);
            quantity=(TextView) itemView.findViewById(R.id.porquantity);
            wordings=(TextView) itemView.findViewById(R.id.porwordings);
            desc=(TextView) itemView.findViewById(R.id.pordescenter);
            imgurl=(ImageView)itemView.findViewById(R.id.porimg);

        }
    }
}
