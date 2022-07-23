package com.example.coleenskitchenbakeryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShipmentInfoActivity extends AppCompatActivity {

    private DatabaseReference sref;

    TextView name,phone,tot,delidate,state,city,locality,pincode,delstate,orstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_info);

        name=(TextView)findViewById(R.id.newname);
        tot=(TextView)findViewById(R.id.newtotprice);
        state=(TextView)findViewById(R.id.newstate);
        city=(TextView)findViewById(R.id.newcity);
        locality=(TextView)findViewById(R.id.newlocality);
        pincode=(TextView)findViewById(R.id.newpincode);
        delstate=(TextView)findViewById(R.id.newdelstate);
        orstate=(TextView)findViewById(R.id.neworstate);

        sref= FirebaseDatabase.getInstance().getReference().child("Usually");

        String uuid=getIntent().getStringExtra("uuid");


        sref.child(uuid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productsconfirm order=snapshot.getValue(productsconfirm.class);
                if(order!=null)
                {
                    name.setText("Name:"+order.getName());
                    phone.setText("Phone No:"+order.getPhoneno());
                    tot.setText("Total Price:"+order.getTotalPrice());
                    delidate.setText("Delivery Price:"+order.getDdate());
                    state.setText("State:"+order.getState());
                    city.setText("City:"+order.getCity());
                    locality.setText("Locality"+order.getLocality());
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