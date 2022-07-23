package com.example.coleenskitchenbakeryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    myadapter myadapter;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            id=getIntent().getStringExtra("id");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView buttomnav = findViewById(R.id.bottom_navigation);
        buttomnav.setOnNavigationItemSelectedListener(navlistener);

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), Products.class)
                .build();

        myadapter =new myadapter(options,getApplicationContext(),id);
        recyclerView.setAdapter(myadapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        myadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myadapter.stopListening();
    }

   @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation,menu);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent=null;
            Fragment sfragment=null;
            int c=0;
            switch(item.getItemId()){
                case R.id.nav_home:
                    intent = new Intent(MainActivity.this, MainActivity.class);
                    c=c+1;
                    break;
                case R.id.nav_category:
                    intent = new Intent(MainActivity.this, CategoryActivity.class);
                    c=c+1;
                    break;
              case R.id.navi_cart:
                  intent = new Intent(MainActivity.this, CartActivity.class);
                  c=c+1;
                     break;
                case R.id.navi_order:
                  sfragment=new OrderFragment();
                  break;
                case R.id.nav_account:
                    sfragment=new AccountFragment();
                    break;
            }
            if(c==1) {
                startActivity(intent);
            }
            else{
                getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,sfragment).commit();
            }
            return true;
        }
    };
}