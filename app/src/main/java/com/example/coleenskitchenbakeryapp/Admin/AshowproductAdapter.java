package com.example.coleenskitchenbakeryapp.Admin;

import android.content.Context;
import android.content.DialogInterface;
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

import com.example.coleenskitchenbakeryapp.Products;
import com.example.coleenskitchenbakeryapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AshowproductAdapter extends FirebaseRecyclerAdapter<Products, AshowproductAdapter.myviewholder> {

    Context context;

    public AshowproductAdapter(FirebaseRecyclerOptions<Products> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int i, @NonNull Products products) {
        holder.showpname.setText("Weight:"+products.getWeight());
        holder.showdescription.setText("Delivery Price: Rs."+ products.getDevprice());
        holder.showprice.setText("Product Id:"+products.getPid());
        holder.showpid.setText("Category Id:"+products.getCat_id());
        holder.showcatid.setText("Price: Rs."+products.getPrice());
        holder.showcatname.setText("Quantity:"+products.getQuantity());
        holder.showweight.setText("Desc:"+products.getDescription());
        holder.showquantity.setText("Flavours:"+products.getFlavours());
        holder.showflavour.setText("Name:"+products.getPname());
        holder.showdeliveryprice.setText("Category Name:"+products.getCat_name());
        Picasso.get().load(products.getImg()).into(holder.showimg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText question = new EditText(v.getContext());
                AlertDialog.Builder qbox = new AlertDialog.Builder(v.getContext());
                qbox.setTitle("Removed Item");
                qbox.setMessage("Do You Want to Remove This item?? ");

                qbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText question = new EditText(v.getContext());
                        AlertDialog.Builder qbox = new AlertDialog.Builder(v.getContext());
                        qbox.setMessage("Are You Sure You Want to Remove this item?? ");

                        qbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Task<Void> ref= FirebaseDatabase.getInstance().getReference().child("Products")
                                      .child(products.getPid()).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Toast.makeText(context,"Item Removed",Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });
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
                qbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                qbox.create().show();
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showproduct, parent, false);
        return new myviewholder(view);

    }
    public class myviewholder  extends RecyclerView.ViewHolder {

        ImageView showimg;
        TextView showpname, showdescription, showpid,showprice,showcatid,showcatname,showdeliveryprice,showquantity,showweight,showflavour;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            showimg = (ImageView) itemView.findViewById(R.id.a_showimg);
            showpname = (TextView) itemView.findViewById(R.id.a_showName);
            showdescription = (TextView) itemView.findViewById(R.id.a_showdesc);
            showprice = (TextView) itemView.findViewById(R.id.a_showPrice);
            showpid=itemView.findViewById(R.id.a_showpid);
            showcatid=itemView.findViewById(R.id.a_showcatname);
            showcatname=itemView.findViewById(R.id.a_showcatid);
            showdeliveryprice=itemView.findViewById(R.id.a_showdevprice);
            showquantity=itemView.findViewById(R.id.a_showquantity);
            showweight=itemView.findViewById(R.id.a_showweight);
            showflavour=itemView.findViewById(R.id.a_showflavours);
        }
    }
}
