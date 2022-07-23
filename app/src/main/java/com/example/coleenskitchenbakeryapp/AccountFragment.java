package com.example.coleenskitchenbakeryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment  extends Fragment {


    private FirebaseUser user;
    private DatabaseReference ref;
    private DatabaseReference sref;
    private String id;
    ImageView info;

    public AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.account_fragment,container,false);

        info=v.findViewById(R.id.infoinfo);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=getFragmentManager().beginTransaction();
                ft.replace(R.id.wrapper,new ColleensFragment());
                ft.commit();
            }
        });

        user= FirebaseAuth.getInstance().getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference("Users");
        id=user.getUid();
       sref= FirebaseDatabase.getInstance().getReference("OrdersInfo");

        final TextView fullname=(TextView)v.findViewById(R.id.accountname);
        final TextView email=(TextView)v.findViewById(R.id.accountemail);
        final TextView sname=(TextView)v.findViewById(R.id.shipname);
        final TextView state=(TextView)v.findViewById(R.id.shipstate);
        final TextView city=(TextView)v.findViewById(R.id.shipcity);
        final TextView locality=(TextView)v.findViewById(R.id.shiplocality);
        final TextView pincode=(TextView)v.findViewById(R.id.shippin);


        displayvalues(fullname,email);
        displayship(sname,state,city,locality,pincode);

        return v;

    }

    private void displayship(TextView sname, TextView state, TextView city, TextView locality, TextView pincode) {

        sref.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Order order=snapshot.getValue(Order.class);
                if(order!=null)
                {
                    String na=order.getName();
                    String to=order.getState();
                    String cit=order.getCity();
                    String local=order.getLocality();
                    String pin=order.getPincode();

                    sname.setText("Name:"+na);
                    state.setText("State:"+to);
                    city.setText("City:"+cit);
                    locality.setText("Locality:"+local);
                    pincode.setText("Pincode:"+pin);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayvalues(TextView fullname, TextView email) {

        ref.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                if(user!=null)
                {
                    String na=user.getName();
                    String em=user.getEmailId();

                    fullname.setText("Name:"+na);
                    email.setText("Email Address:"+em);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
