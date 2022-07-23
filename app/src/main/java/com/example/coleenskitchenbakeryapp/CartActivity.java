package com.example.coleenskitchenbakeryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseAuth auth;
    TextView tot_price;
    Button confirmorder,moreitems;
    String pid;
    int allprice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = (RecyclerView) findViewById(R.id.cartrec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tot_price=findViewById(R.id.totprice);
        confirmorder=findViewById(R.id.confirm_order);
        moreitems=findViewById(R.id.Addmore_order);
        pid=getIntent().getStringExtra("id");
        String Auth=auth.getInstance().getCurrentUser().getUid();

        moreitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        confirmorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(CartActivity.this,"Total Price is Rs."+allprice,Toast.LENGTH_LONG).show();
                FirebaseDatabase.getInstance().getReference().child("OrdersInfo").child(Auth).child("TotalPrice").setValue(String.valueOf(allprice));
                Intent intent = new Intent(CartActivity.this,ConfirmOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

}

    @Override
    protected void onStart() {
        super.onStart();
        String Auth=auth.getInstance().getCurrentUser().getUid();


        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("CartList").child(Auth), Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart,cartviewholder> adapter=new FirebaseRecyclerAdapter<Cart, cartviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull cartviewholder holder, int i, @NonNull Cart cart) {
                holder.pname.setText("Name:"+cart.getPname());
                holder.price.setText("Price:"+cart.getPrice());
                holder.flavours.setText("Flavours:"+cart.getFlavours());
                holder.deliveryprice.setText("Delivery Price:"+cart.getDevprice());
                holder.quantity.setText("Quantity:"+cart.getQuantity());
                Picasso.get().load(cart.getImg_url()).into(holder.image);

                int one = (Integer.parseInt(cart.getPrice()))*(Integer.parseInt(cart.getQuantity()))+(Integer.parseInt(cart.getDevprice()));
                allprice=allprice+one;
                tot_price.setText("Total Price="+String.valueOf(allprice));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence op[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"
                                };
                        AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(op, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if(which==0)
                                {
                                    Intent intent = new Intent(CartActivity.this,Product_Details_Activity.class);
                                    intent.putExtra("pid",cart.getPid());
                                    startActivity(intent);

                                }
                               else if(which==1)
                                {
                                    FirebaseDatabase.getInstance().getReference().child("CartList").child(Auth).child(cart.getPid())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                int rem= (Integer.parseInt(cart.getPrice()))*(Integer.parseInt(cart.getQuantity()))+(Integer.parseInt(cart.getDevprice()));
                                                allprice=allprice-rem;
                                                tot_price.setText("Total Price="+String.valueOf(allprice));
                                                Toast.makeText(CartActivity.this,"Remove Successfully",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public cartviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items,parent,false);
                cartviewholder holder=new cartviewholder(v);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}