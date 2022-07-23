package com.example.coleenskitchenbakeryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference sref;
    private  DatabaseReference oref;
    private  DatabaseReference cref,href;
    private String id,uid;
  ArrayList<ProOrder> list;
    Button cancelorder,confor;
    FirebaseAuth fb;

String ddate;
  RecyclerView recyclerView;
    ProAdapter proadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = (RecyclerView) findViewById(R.id.orecview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ddate=getIntent().getStringExtra("ddate");

        href=FirebaseDatabase.getInstance().getReference("Usually");
        oref= FirebaseDatabase.getInstance().getReference("OrdersProducts");

        user= FirebaseAuth.getInstance().getCurrentUser();
        sref= FirebaseDatabase.getInstance().getReference("OrdersInfo");
        id=user.getUid();
        uid=getIntent().getStringExtra("uid");
        cref=FirebaseDatabase.getInstance().getReference("CartList");


        recyclerView = (RecyclerView) findViewById(R.id.orecview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

       list=new ArrayList<>();
        proadapter=new ProAdapter( getApplicationContext(),list,href,ddate);
        recyclerView.setAdapter(proadapter);


        final TextView odeldate=(TextView)findViewById(R.id.ordevdate);
       final TextView ototalprice= (TextView)findViewById(R.id.orprice);
       final TextView oname=(TextView)findViewById(R.id.orname);
        final TextView ophone=(TextView)findViewById(R.id.orphone);
        final TextView ostate=(TextView)findViewById(R.id.orstate);
        final TextView ocity=(TextView)findViewById(R.id.orcity);
        final TextView olocality=(TextView)findViewById(R.id.orlocality);
        final TextView opincode=(TextView)findViewById(R.id.orpin);
        cancelorder=(Button)findViewById(R.id.cco);
       confor=(Button)findViewById(R.id.co);

        ordisplayship(odeldate,ototalprice,oname,ophone,ostate,ocity,olocality,opincode);


       cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText question = new EditText(v.getContext());
                AlertDialog.Builder qbox = new AlertDialog.Builder(v.getContext());
                qbox.setTitle("Do You Want To Cancel Your Order??");
                qbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String savecurrentdate;

                        Calendar callfordate= Calendar.getInstance();

                        SimpleDateFormat currentdate= new SimpleDateFormat("MMM dd,yyy");
                        savecurrentdate= currentdate.format(callfordate.getTime());
                        String ll=FirebaseAuth.getInstance().getCurrentUser().getUid();

                        href.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                                {
                                    if(dataSnapshot.child("uid").getValue().toString().equals(ll)) {
                                        if (dataSnapshot.child("date").getValue().toString().equals(savecurrentdate)) {
                                            dataSnapshot.getRef().removeValue();
                                            Toast.makeText(OrderActivity.this,"Your Order has been Cancelled ",Toast.LENGTH_LONG).show();
                                            Intent intent =new Intent(OrderActivity.this,MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                qbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                qbox.create().show();

            }
        });

        confor.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Toast.makeText(OrderActivity.this,"Your Order has been placed, Please Wait For your Order to be confirmed, Thank You For Shopping ",Toast.LENGTH_LONG).show();
                 Intent intent =new Intent(OrderActivity.this,MainActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(intent);
             }

        });

       oref.child(id).child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    ProOrder order=snapshot1.getValue(ProOrder.class);
                    list.add(order);
                }
                proadapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void ordisplayship(TextView odeldate, TextView ototalprice, TextView oname, TextView ophone, TextView ostate, TextView ocity, TextView olocality, TextView opincode) {
        sref.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Order order=snapshot.getValue(Order.class);
                if(order!=null)
                {
                    odeldate.setText("Delivery Date:"+order.getDdate());
                    ototalprice.setText("Price:"+order.getTotalPrice());
                    oname.setText("Name:"+order.getName());
                    ophone.setText("Phone No:"+order.getPhoneno());
                    ostate.setText("State:"+order.getState());
                    ocity.setText("City:"+order.getCity());
                    olocality.setText("Locality:"+order.getLocality());
                    opincode.setText("Pin Code"+order.getPincode());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        String savecurrentdate;

        Calendar callfordate= Calendar.getInstance();

        SimpleDateFormat currentdate= new SimpleDateFormat("MMM dd,yyy");
        savecurrentdate= currentdate.format(callfordate.getTime());
        String ll=FirebaseAuth.getInstance().getCurrentUser().getUid();

        href.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    if(dataSnapshot.child("uid").getValue().toString().equals(ll)) {
                        if (dataSnapshot.child("date").getValue().toString().equals(savecurrentdate)) {
                            dataSnapshot.getRef().removeValue();
                            Toast.makeText(OrderActivity.this,"Your Order has been Cancelled ",Toast.LENGTH_LONG).show();
                            Intent intent =new Intent(OrderActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}