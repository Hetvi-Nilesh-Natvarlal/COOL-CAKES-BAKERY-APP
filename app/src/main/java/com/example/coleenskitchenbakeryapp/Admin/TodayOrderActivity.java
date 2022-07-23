package com.example.coleenskitchenbakeryapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.coleenskitchenbakeryapp.R;
import com.example.coleenskitchenbakeryapp.productsconfirm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TodayOrderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowOrderAdapter myadapter;
    private FirebaseUser user;
    private DatabaseReference ref;
    private DatabaseReference sref;
    String id;
    FirebaseAuth auth;
    ArrayList<productsconfirm> list;
    String savecurrentdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_order);


        recyclerView = (RecyclerView) findViewById(R.id.rectoday);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Calendar callfordate= Calendar.getInstance();
        SimpleDateFormat currentdate= new SimpleDateFormat("MMM dd,yyy");
        savecurrentdate= currentdate.format(callfordate.getTime());

        ref=FirebaseDatabase.getInstance().getReference("Usually");

        list=new ArrayList<>();
        myadapter=new ShowOrderAdapter( list,getApplicationContext());
        recyclerView.setAdapter(myadapter);



        ref.orderByChild("date").equalTo(savecurrentdate.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    productsconfirm productsconfirm=snapshot1.getValue(productsconfirm.class);
                    list.add(productsconfirm);
                }
                myadapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}