package com.example.coleenskitchenbakeryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class ConfirmOrderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText person_name,phone_no,state,city,pincode,locality;
    private TextView del_date;
    private Button confirm_order;
    private  String Auth;
    FirebaseAuth auth;
  List<Cart> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);


        Auth = auth.getInstance().getCurrentUser().getUid();
        person_name = findViewById(R.id.shipment_name);
        phone_no = findViewById(R.id.shipment_phonenumber);
        state = findViewById(R.id.shipment_state);
        city = findViewById(R.id.shipment_city);
        pincode = findViewById(R.id.shipment_pincode);
        locality = findViewById(R.id.shipment_locality);
        confirm_order = findViewById(R.id.confirm_button);
        del_date = findViewById(R.id.editTextDate);
        list=new ArrayList<>();

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfirmOrderActivity.this,"Deliver Only Within Karnataka Bangalore",Toast.LENGTH_LONG).show();
            }
        });


        del_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker = new com.example.coleenskitchenbakeryapp.DatePicker();
                datepicker.show(getSupportFragmentManager(), "date picker");

            }
        });


        confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }

            private void Check() {
                if (TextUtils.isEmpty(person_name.getText().toString())) {
                    Toast.makeText(ConfirmOrderActivity.this, "Please Provide your full name", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(phone_no.getText().toString())) {
                    Toast.makeText(ConfirmOrderActivity.this, "Please Enter Your phone number", Toast.LENGTH_LONG).show();
                } else if ((phone_no.getText().toString().length()) != 10) {
                    Toast.makeText(ConfirmOrderActivity.this, "Invalid Phone No", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(state.getText().toString())) {
                    Toast.makeText(ConfirmOrderActivity.this, "Please Enter Your State", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(city.getText().toString())) {
                    Toast.makeText(ConfirmOrderActivity.this, "Please Enter Your City", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(locality.getText().toString())) {
                    Toast.makeText(ConfirmOrderActivity.this, "Please Enter Your Locality", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(pincode.getText().toString())) {
                    Toast.makeText(ConfirmOrderActivity.this, "Please Enter Your Pincode", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(del_date.getText().toString())) {
                    Toast.makeText(ConfirmOrderActivity.this, "Please Enter Your Date of Delivery", Toast.LENGTH_LONG).show();
                } else if (state.getText().toString().equalsIgnoreCase("Karnataka")) {
                    if (city.getText().toString().equalsIgnoreCase("Bangalore")) {
                        if (pincode.getText().toString().length() == 6) {
                            if (Integer.parseInt(pincode.getText().toString()) >= 560001 && Integer.parseInt(pincode.getText().toString()) <= 560099) {
                                    ConfirmOrder();
                            } else {
                                Toast.makeText(ConfirmOrderActivity.this, "Not deliver to this pincode", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ConfirmOrderActivity.this, "Invalid Pincode", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ConfirmOrderActivity.this, "Delivery Only Within Bangalore", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ConfirmOrderActivity.this, "Invalid Inputs: Delivery only in Karnataka Bangalore", Toast.LENGTH_LONG);
                }

            }

            private void ConfirmOrder() {

                DatabaseReference ordersref = FirebaseDatabase.getInstance().getReference().child("OrdersInfo").child(Auth);
                final HashMap<String, Object> ordermap = new HashMap<>();
                ordermap.put("name", person_name.getText().toString());
                ordermap.put("phoneno", phone_no.getText().toString());
                ordermap.put("state", state.getText().toString());
                ordermap.put("city", city.getText().toString());
                ordermap.put("locality", locality.getText().toString());
                ordermap.put("pincode", pincode.getText().toString());
                ordermap.put("ddate",del_date.getText().toString());
                ordermap.put("uid",Auth);
                ordermap.put("orderstate","Not Confirm");
                ordermap.put("delistate","Not Delivered");



                ordersref.updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            moveRecord(Auth);
                        }
                    }

                    private void moveRecord(String Auth) {

                        DatabaseReference frompath = FirebaseDatabase.getInstance().getReference().child("CartList").child(Auth);
                       final DatabaseReference topath = FirebaseDatabase.getInstance().getReference().child("OrdersProducts").child(Auth).child("Products");
                        frompath.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                topath.setValue(snapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(ConfirmOrderActivity.this,"Successful",Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(ConfirmOrderActivity.this,OrderActivity.class);
                                            intent.putExtra("ddate",del_date.getText().toString());
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

            }

        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String curdate= DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        del_date.setText(curdate);
    }

}