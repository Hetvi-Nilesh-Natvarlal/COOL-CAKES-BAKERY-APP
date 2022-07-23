package com.example.coleenskitchenbakeryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ColleensFragment extends Fragment {

    ImageView whatsapp;
    ImageView email;
    ImageView insta,map;
    TextView logout;
    FirebaseAuth auth;


    public ColleensFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_colleens, container, false);
        whatsapp=v.findViewById(R.id.whatsapp);
        email=v.findViewById(R.id.gmail);
        insta=v.findViewById(R.id.insta);
        map=v.findViewById(R.id.map);
        logout=v.findViewById(R.id.logoutt);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText question = new EditText(v.getContext());
                AlertDialog.Builder qbox = new AlertDialog.Builder(v.getContext());
                qbox.setTitle("Logout!!");
                qbox.setMessage("Do You Want To Logout");

                qbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(auth.getInstance().getCurrentUser()!=null)
                        {
                            SessionManagement sessionManagement=new SessionManagement(getContext());
                            sessionManagement.removeSession();
                            Toast.makeText(getContext(), "Signed Out", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getActivity(), Login_New_Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
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

        whatsapp.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            gotoUrl("https://api.whatsapp.com/send?phone=1234567890");
                                        }
                                    });


             email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoUrl("mailto:coolcakesbakery@gmail.com");
                }
        });

             insta.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     gotoUrl("https://instagram.com/cool_cakes_bakery_2022");
                 }
             });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("geo:25.617100,91.9145000");//https://map.google.com/
            }
        });
        return v;
    }

    private void gotoUrl(String s) {
            Uri uri= Uri.parse(s);
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}