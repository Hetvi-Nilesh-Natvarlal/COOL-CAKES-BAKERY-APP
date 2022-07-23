package com.example.coleenskitchenbakeryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Cup_CakeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    myadapter cupcakeadapter;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cup__cake);

        id=getIntent().getStringExtra("id");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_cupcake);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("cat_id").equalTo("102");

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(query,Products.class)
                .build();

       cupcakeadapter =new myadapter(options,getApplicationContext(),id);
        recyclerView.setAdapter(cupcakeadapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
       cupcakeadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
       cupcakeadapter.stopListening();
    }
}