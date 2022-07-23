package com.example.coleenskitchenbakeryapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OrderHistoryFragment extends Fragment {

    RecyclerView recyclerView;
    HistoryCustomerAdapter adapter;
    ArrayList<History> list;
    private DatabaseReference ref;
    FirebaseAuth auth;
    String Auth;

    public OrderHistoryFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View v=inflater.inflate(R.layout.fragment_order_history, container, false);

        list=new ArrayList<>();
        recyclerView = (RecyclerView)v.findViewById(R.id.custhist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Auth=auth.getInstance().getCurrentUser().getUid();
        ref= FirebaseDatabase.getInstance().getReference("History");

        adapter =new HistoryCustomerAdapter(list,getContext());
        recyclerView.setAdapter(adapter);

        ref.orderByChild("uid").equalTo(Auth).addValueEventListener(new ValueEventListener() {
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

    return v;
    }
}