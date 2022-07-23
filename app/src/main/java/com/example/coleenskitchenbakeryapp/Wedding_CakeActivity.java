package com.example.coleenskitchenbakeryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Wedding_CakeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    myadapter weddingadapter;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding__cake);

        id=getIntent().getStringExtra("id");

       recyclerView = (RecyclerView) findViewById(R.id.recyclerview_wedding);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("cat_id").equalTo("101");

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(query,Products.class)
                .build();

        weddingadapter  =new myadapter(options,getApplicationContext(),id);
        recyclerView.setAdapter(weddingadapter );

    }

    @Override
    protected void onStart() {
        super.onStart();
        weddingadapter .startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        weddingadapter .stopListening();
    }
}