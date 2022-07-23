package com.example.coleenskitchenbakeryapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.coleenskitchenbakeryapp.R;
import com.example.coleenskitchenbakeryapp.productsconfirm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class AccountInfoActivity extends AppCompatActivity {


    String uid,uuid;
    private FirebaseUser user;
    private DatabaseReference ref;
    private DatabaseReference sref;
    private String id;

    TextView name,phone,tot,delidate,state,city,locality,pincode,delstate,orstate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info2);

        uid=getIntent().getStringExtra("uid");
        uuid=getIntent().getStringExtra("uuid");

        user= FirebaseAuth.getInstance().getCurrentUser();
        id=user.getUid();
        sref= FirebaseDatabase.getInstance().getReference().child("Usually");

        name=(TextView)findViewById(R.id.adminname);
        tot=(TextView)findViewById(R.id.admintotprice);
        delidate=(TextView)findViewById(R.id.adminddate);
        state=(TextView)findViewById(R.id.adminstate);
        city=(TextView)findViewById(R.id.admincity);
        locality=(TextView)findViewById(R.id.adminlocality);
        pincode=(TextView)findViewById(R.id.adminpincode);
        delstate=(TextView)findViewById(R.id.admindelstate);
        orstate=(TextView)findViewById(R.id.adminorstate);

        orstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText question = new EditText(v.getContext());
                AlertDialog.Builder qbox = new AlertDialog.Builder(v.getContext());
                qbox.setTitle("Do You Want To Take The Order??");

                qbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sref.child(uuid).child("orderstate").setValue("Confirmed");
                        Toast.makeText(AccountInfoActivity.this,"Set  Order State to Confirmed",Toast.LENGTH_LONG).show();
                    }
                });

                qbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sref.child(uuid).child("orderstate").setValue("Rejected");
                        Toast.makeText(AccountInfoActivity.this,"Set  Order State to Rejected",Toast.LENGTH_LONG).show();
                    }
                });

                qbox.create().show();
            }
        });

        delstate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            EditText question = new EditText(v.getContext());
                                            AlertDialog.Builder qbox = new AlertDialog.Builder(v.getContext());
                                            qbox.setTitle("Delivery Status Update");

                                            qbox.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    sref.child(uuid).child("delistate").setValue("Delivered");
                                                    Intent intent = new Intent(AccountInfoActivity.this, HistoryActivity.class);
                                                    intent.putExtra("uuid", uuid);
                                                    startActivity(intent);
                                                    Toast.makeText(AccountInfoActivity.this, "Set  Order State to Delivered", Toast.LENGTH_LONG).show();
                                                }
                                            });


                                            qbox.create().show();
                                        }
                                    });

        sref.child(uuid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productsconfirm order=snapshot.getValue(productsconfirm.class);
                if(order!=null)
                {
                    name.setText("Name:"+order.getName());
                    phone.setText("Phone No:"+order.getPhoneno());
                    tot.setText("Total Price:"+order.getTotalPrice());
                    delidate.setText("Delivery Date:"+order.getDdate());
                    state.setText("State:"+order.getState());
                    city.setText("City:"+order.getCity());
                    locality.setText("Locality:"+order.getLocality());
                    pincode.setText("Pincode:"+order.getPincode());
                    delstate.setText("Delivery State:"+order.getDelistate());
                    orstate.setText("Order State:"+order.getOrderstate());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}