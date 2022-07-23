package com.example.coleenskitchenbakeryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;




public class Product_Details_Activity extends AppCompatActivity {

    private ImageView pro_img;
    private TextView pname,price,desc,weight,flavours,quantity,devprice,url;
    private String pro_id,id;
    private Button addtocart;
    private FirebaseAuth auth;
    private EditText enter_quantity,wordins,enter_desc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details_);

        auth = FirebaseAuth.getInstance();
        pro_id = getIntent().getStringExtra("pid");
        pro_img = (ImageView) findViewById(R.id.pro_img_det);
        pname = (TextView) findViewById(R.id.pro_name_det);
        price = (TextView) findViewById(R.id.pro_price_det);
        desc = (TextView) findViewById(R.id.pro_desc_det);
        weight = (TextView) findViewById(R.id.pro_weight_det);
        flavours = (TextView) findViewById(R.id.pro_flavours_det);
        quantity = (TextView) findViewById(R.id.pro_Quantity_det);
        devprice = (TextView) findViewById(R.id.pro_devprice_det);
        wordins = (EditText) findViewById(R.id.enter_words);
        enter_desc = (EditText) findViewById(R.id.enter_desc);



        addtocart = (Button) findViewById(R.id.add_to_cart);
        enter_quantity = (EditText) findViewById(R.id.enter_quantity);
        url = (TextView) findViewById(R.id.invisible);
        id = getIntent().getStringExtra("id");
        getProductDetails(pro_id);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }


            private void Check() {

                if (TextUtils.isEmpty(enter_quantity.getText().toString())) {
                    Toast.makeText(Product_Details_Activity.this, "Please enter the quantity you want to purchase", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(enter_quantity.getText().toString()) == 0 ||enter_quantity.getText().toString().length() > 2 || Integer.parseInt(enter_quantity.getText().toString()) < 1 || Integer.parseInt(enter_quantity.getText().toString()) > 10) {
                    Toast.makeText(Product_Details_Activity.this, "Quantity Should be on the range of 1-10", Toast.LENGTH_SHORT).show();
                } else {
                    addingtocartlist();

                }
            }

            private void addingtocartlist() {
                String saveCurrentTime, savecurrentdate;

                Calendar callfordate = Calendar.getInstance();

                SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd,yyy");
                savecurrentdate = currentdate.format(callfordate.getTime());


                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(callfordate.getTime());

                String Auth = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                DatabaseReference cartlistref = FirebaseDatabase.getInstance().getReference().child("CartList").child(Auth);

                final HashMap<String, Object> cartmap = new HashMap<>();
                cartmap.put("pid", pro_id);
                cartmap.put("pname", pname.getText().toString());
                cartmap.put("price", price.getText().toString());
                cartmap.put("flavours", flavours.getText().toString());
                cartmap.put("date", savecurrentdate);
                cartmap.put("time", saveCurrentTime);
                cartmap.put("uid", Auth);
                cartmap.put("devprice", devprice.getText().toString());
                cartmap.put("quantity", enter_quantity.getText().toString());
                cartmap.put("img_url", url.getText().toString());
                if (TextUtils.isEmpty(wordins.getText().toString())) {
                    cartmap.put("wordings", "Null");
                } else {
                    cartmap.put("wordings", wordins.getText().toString());
                }
                if (TextUtils.isEmpty(enter_desc.getText().toString())) {
                    cartmap.put("desc_enter", "Null");
                } else {
                    cartmap.put("desc_enter", enter_desc.getText().toString());
                }
                cartlistref.child(pro_id).updateChildren(cartmap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(Product_Details_Activity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Product_Details_Activity.this, CartActivity.class);
                                    intent.putExtra("id", pro_id);
                                    startActivity(intent);

                                }
                            }
                        });
            }
        });
    }

    private void getProductDetails(String pro_id) {

        DatabaseReference pro_ref = FirebaseDatabase.getInstance().getReference().child("Products");
        pro_ref.child(pro_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Products products = snapshot.getValue(Products.class);
                    url.setText(products.getImg());
                    pname.setText(products.getPname());
                    price.setText(products.getPrice());
                    desc.setText(products.getDescription());
                    flavours.setText(products.getFlavours());
                    quantity.setText(products.getQuantity());
                    devprice.setText(products.getDevprice());
                    weight.setText(products.getWeight());
                    Picasso.get().load(products.getImg()).into(pro_img);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}