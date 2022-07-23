package com.example.coleenskitchenbakeryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Birthday_CakesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    myadapter birthdayadapter;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday__cakes);

        id=getIntent().getStringExtra("id");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_birthday);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("cat_id").equalTo("100");

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(query,Products.class)
                .build();

        birthdayadapter =new myadapter(options,getApplicationContext(),id);
        recyclerView.setAdapter(birthdayadapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        birthdayadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        birthdayadapter.stopListening();
    }
}