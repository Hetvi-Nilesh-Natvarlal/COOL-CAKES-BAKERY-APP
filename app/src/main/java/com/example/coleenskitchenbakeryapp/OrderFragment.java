package com.example.coleenskitchenbakeryapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OrderFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference ref,href;
    private DatabaseReference sref;
    RecyclerView recyclerView,recyclerView1;
    FinalOrderAdapter myadapter;
    String id;
    FirebaseAuth auth;
    ArrayList<productsconfirm> list;
    ImageView impyz;



    public OrderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_order, container, false);


        user= FirebaseAuth.getInstance().getCurrentUser();
        id=user.getUid();
        sref= FirebaseDatabase.getInstance().getReference("OrdersInfo");

       impyz=v.findViewById(R.id.imgv);

        impyz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=getFragmentManager().beginTransaction();
                ft.replace(R.id.wrapper,new OrderHistoryFragment());
                ft.commit();

            }
        });



        id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        recyclerView = (RecyclerView) v.findViewById(R.id.orderfinal);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String Auth=auth.getInstance().getCurrentUser().getUid();
        ref=FirebaseDatabase.getInstance().getReference("Usually");


        list=new ArrayList<>();
        myadapter=new FinalOrderAdapter( list,getContext());
        recyclerView.setAdapter(myadapter);


        ref.orderByChild("uid").equalTo(Auth).addValueEventListener(new ValueEventListener() {
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
        return v;
    }
}