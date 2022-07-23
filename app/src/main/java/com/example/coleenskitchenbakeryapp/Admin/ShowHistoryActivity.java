package com.example.coleenskitchenbakeryapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import com.example.coleenskitchenbakeryapp.History;
import com.example.coleenskitchenbakeryapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowHistoryAdapter adapter;
    ArrayList<History> list;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);

        list=new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recshowhistory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref= FirebaseDatabase.getInstance().getReference("History");

        adapter =new ShowHistoryAdapter(list,getApplicationContext());
        recyclerView.setAdapter(adapter);

        ref.orderByChild("ddate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                   History history=snapshot1.getValue(History.class);
                    list.add(history);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}