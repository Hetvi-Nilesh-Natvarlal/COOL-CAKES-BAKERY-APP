package com.example.coleenskitchenbakeryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PizzaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    myadapter pizzaadapter;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_pizza);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       id=getIntent().getStringExtra("id");
        Query query = FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("cat_id").equalTo("104");

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(query,Products.class)
                .build();

        pizzaadapter =new myadapter(options,getApplicationContext(),id);
        recyclerView.setAdapter(pizzaadapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        pizzaadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pizzaadapter.stopListening();
    }

}