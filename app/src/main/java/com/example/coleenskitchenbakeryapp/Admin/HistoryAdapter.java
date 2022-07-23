package com.example.coleenskitchenbakeryapp.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coleenskitchenbakeryapp.R;
import com.example.coleenskitchenbakeryapp.productsconfirm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.myviewholder> {

        Context context;
        ArrayList<productsconfirm> list;
        DatabaseReference ref,href;
        FirebaseAuth auth;
        String uuid;

    public HistoryAdapter(ArrayList<productsconfirm> list, Context context, String uuid) {
        this.list=list;
        this.context=context;
        this.uuid=uuid;
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
        holder.desc.setText("Delivery State:" + productsconfirm.getDelistate());
        holder.pid.setText("Flavours:"+productsconfirm.getFlavour());
        holder.date.setText("Price: Rs."+productsconfirm.getPrice());
        holder.uuid.setText("Delivery Price: Rs."+productsconfirm.getDeliprice());
        Picasso.get().load(productsconfirm.getImg_url()).into(holder.image);

        href= FirebaseDatabase.getInstance().getReference().child("Usually").child(uuid);
        ref=FirebaseDatabase.getInstance().getReference().child("History");
    href.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            ref.child(uuid).setValue(snapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        final HashMap<String,Object> omap = new HashMap<>();
                        omap.put("date",productsconfirm.getDate());
                        omap.put("ddate",productsconfirm.getDdate());
                        omap.put("deliprice",productsconfirm.getDeliprice());
                        omap.put("desc",productsconfirm.getDesc());
                        omap.put("flavour",productsconfirm.getFlavour());
                        omap.put("img_url",productsconfirm.getImg_url());
                        omap.put("name",productsconfirm.getPname());
                        omap.put("pid",productsconfirm.getPid());
                        omap.put("price",productsconfirm.getPrice());
                        omap.put("quantity",productsconfirm.getQuantity());
                        omap.put("uid",productsconfirm.getUid());
                        omap.put("uuid",uuid);
                        omap.put("wordings",productsconfirm.getWordings());

                        ref.child(uuid).updateChildren(omap);
                        href.removeValue();
                        Toast.makeText(context,"Successful",Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

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

public class myviewholder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView name, flavour, price, deliprice, quantity, wordings, desc, pid, date, uuid;

    public myviewholder(@NonNull View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.a_showimg);
        name = (TextView) itemView.findViewById(R.id.a_showName);
        flavour = (TextView) itemView.findViewById(R.id.a_showflavours);
        price = (TextView) itemView.findViewById(R.id.a_showPrice);
        deliprice = (TextView) itemView.findViewById(R.id.a_showdevprice);
        quantity = (TextView) itemView.findViewById(R.id.a_showquantity);
        wordings = (TextView) itemView.findViewById(R.id.a_showcatid);
        desc = (TextView) itemView.findViewById(R.id.a_showweight);
        pid = (TextView) itemView.findViewById(R.id.a_showpid);
        date = (TextView) itemView.findViewById(R.id.a_showcatname);
        uuid = (TextView) itemView.findViewById(R.id.a_showdesc);

    }

}

}
