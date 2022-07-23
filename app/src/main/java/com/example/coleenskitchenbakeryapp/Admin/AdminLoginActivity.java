package com.example.coleenskitchenbakeryapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coleenskitchenbakeryapp.R;

public class AdminLoginActivity extends AppCompatActivity {

    private Button alogin;
    private EditText aemail,apassword;
    String mail,pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        alogin=(Button)findViewById(R.id.alogin);
        aemail=(EditText)findViewById(R.id.aemail);
        apassword=(EditText)findViewById(R.id.apassword);
        mail=aemail.getText().toString();
        pw=apassword.getText().toString();

        alogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alogincheck();
            }

            private void alogincheck() {
                if (TextUtils.isEmpty(aemail.getText().toString()))
                {
                    Toast.makeText(AdminLoginActivity.this,"Email Adress Not Entered!!",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(apassword.getText().toString()))
                {
                    Toast.makeText(AdminLoginActivity.this,"Password Not Entered!!",Toast.LENGTH_LONG).show();
                }
                else if(aemail.getText().toString().equals("admin@gmail.com"))
                {
                    if (apassword.getText().toString().equals("1234"))
                    {
                        startActivity(new Intent(AdminLoginActivity.this, AdminActivity.class));
                    }
                }

            }
        });
    }
}