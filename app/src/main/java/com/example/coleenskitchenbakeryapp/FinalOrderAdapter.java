package com.example.coleenskitchenbakeryapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FinalOrderAdapter  extends RecyclerView.Adapter<FinalOrderAdapter.myviewholder> {
    Context context;
    ArrayList<productsconfirm> list;
    DatabaseReference ref,href;
    FirebaseAuth auth;
    String Auth;

    public FinalOrderAdapter(ArrayList<productsconfirm> list, Context context) {
        this.list=list;
        this.context=context;

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_items, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        productsconfirm productsconfirm=list.get(position);
        holder.name.setText("Name:"+productsconfirm.getPname());
        holder.flavour.setText("Flavour:"+productsconfirm.getFlavour());
        holder.price.setText("Quantity:"+productsconfirm.getQuantity());
        holder.deliprice.setText("Price: Rs."+productsconfirm.getPrice());
        holder.quantity.setText("Delivery Price :Rs."+productsconfirm.getDeliprice());
        holder.wordings.setText("Wordings:"+productsconfirm.getWordings());
        holder.desc.setText("Description Entered:"+productsconfirm.getDesc());
        holder.porpor.setText("Click Here To See Shipment Details");
        Picasso.get().load(productsconfirm.getImg_url()).into(holder.image);


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText question = new EditText(v.getContext());
                AlertDialog.Builder qbox = new AlertDialog.Builder(v.getContext());
                qbox.setMessage("Are You Sure You Want To Delete The Order??");

                qbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Auth=auth.getInstance().getCurrentUser().getUid();
                        ref=FirebaseDatabase.getInstance().getReference("Usually");
                        ref.child(productsconfirm.getUuid()).removeValue();
                        Toast.makeText(context,"Remove Successfully",Toast.LENGTH_LONG).show();

                    }
                });

                qbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                qbox.create().show();

            }
        });

        holder.porpor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ShipmentInfoActivity.class);
                intent.putExtra("uuid",productsconfirm.getUuid());
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
        TextView name,flavour,price,deliprice,quantity,wordings,desc,porpor;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.porimg);
            name = (TextView) itemView.findViewById(R.id.porpname);
            flavour = (TextView) itemView.findViewById(R.id.porflavours);
            price = (TextView) itemView.findViewById(R.id.porprice);
            deliprice= (TextView) itemView.findViewById(R.id.pordevprice);
            quantity=  (TextView) itemView.findViewById(R.id.porquantity);
            wordings= (TextView) itemView.findViewById(R.id.porwordings);
            desc= (TextView) itemView.findViewById(R.id.pordescenter);
            porpor=itemView.findViewById(R.id.porpor);
        }
    }
}
