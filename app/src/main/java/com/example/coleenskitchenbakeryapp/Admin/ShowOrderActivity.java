package com.example.coleenskitchenbakeryapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.coleenskitchenbakeryapp.R;
import com.example.coleenskitchenbakeryapp.productsconfirm;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowOrderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowOrderAdapter myadapter;
    private DatabaseReference ref;
    TextView here;
    ArrayList<productsconfirm> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);

        here=findViewById(R.id.here);

        recyclerView = (RecyclerView) findViewById(R.id.recshow);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref=FirebaseDatabase.getInstance().getReference("Usually");

        list=new ArrayList<>();
        myadapter=new ShowOrderAdapter( list,getApplicationContext());
        recyclerView.setAdapter(myadapter);

        here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ShowOrderActivity.this,ShowHistoryActivity.class);
                startActivity(intent);
            }
        });

        ref.orderByChild("date").addValueEventListener(new ValueEventListener() {
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