package com.example.coleenskitchenbakeryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coleenskitchenbakeryapp.Admin.AdminLoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

        private EditText name;
        private EditText email;
        private EditText PhoneNo;
        private EditText password;
        private Button register;
        private TextView login;
        private FirebaseAuth auth;
        private DatabaseReference reference ;
        private TextView admin;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            admin=findViewById(R.id.textView);
            name = findViewById(R.id.name);
            email = findViewById(R.id.emailAddress);
            PhoneNo = findViewById(R.id.phno);
            password = findViewById(R.id.password);
            register = findViewById(R.id.button3);
            login=findViewById(R.id.textView5);


            reference = FirebaseDatabase.getInstance().getReference("Users");
            auth = FirebaseAuth.getInstance();

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RegisterActivity.this, Login_New_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String txtName = name.getText().toString();
                    String txtEmail = email.getText().toString();
                    String txtPhno = PhoneNo.getText().toString();
                    String txtPsw = password.getText().toString();


                    if (TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPhno) || TextUtils.isEmpty(txtPsw))
                        Toast.makeText(RegisterActivity.this, "Empty Credentials", Toast.LENGTH_LONG).show();
                    else if (txtPsw.length() < 6)
                        Toast.makeText(RegisterActivity.this, "Password to short", Toast.LENGTH_LONG).show();
                    else
                    {
                      registerUser(txtName, txtEmail, txtPhno, txtPsw);

                    }

                }
            });

            admin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText question = new EditText(v.getContext());
                    AlertDialog.Builder qbox = new AlertDialog.Builder(v.getContext());
                    qbox.setTitle("Interesting!!");
                    qbox.setMessage("Any Question??");
                    qbox.setView(question);

                    qbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String ans= question.getText().toString();

                                if(ans.equals("1234"))
                                {
                                    Intent intent = new Intent(RegisterActivity.this, AdminLoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this,"Thank You For Your Question",Toast.LENGTH_LONG).show();
                                }
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
        }

       private void registerUser(final String txtName, final String txtEmail, final String txtPhno, final String txtPsw) {
            auth.createUserWithEmailAndPassword(txtEmail, txtPsw).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult)
                {
                    String id=auth.getCurrentUser().getUid();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("Name", txtName);
                    map.put("EmailId", txtEmail);
                    map.put("PhoneNumber", txtPhno);
                    map.put("Id", auth.getCurrentUser().getUid());

                    reference.child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(RegisterActivity.this, "Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this, Login_New_Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
}