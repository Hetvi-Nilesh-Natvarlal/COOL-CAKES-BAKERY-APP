package com.example.coleenskitchenbakeryapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.coleenskitchenbakeryapp.R;
import com.example.coleenskitchenbakeryapp.productsconfirm;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        RecyclerView recyclerView;
        HistoryAdapter myadapter;
        DatabaseReference ref;
        ArrayList<productsconfirm> list;
        String uuid;

            recyclerView = (RecyclerView) findViewById(R.id.hist);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ref= FirebaseDatabase.getInstance().getReference("Usually");
            uuid= getIntent().getStringExtra("uuid");

            list=new ArrayList<>();
            myadapter=new HistoryAdapter( list,getApplicationContext(),uuid);
            recyclerView.setAdapter(myadapter);

           ref.orderByChild("uuid").equalTo(uuid).addValueEventListener(new ValueEventListener() {
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