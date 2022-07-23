package com.example.coleenskitchenbakeryapp.Admin;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.coleenskitchenbakeryapp.Imageclass;
import com.example.coleenskitchenbakeryapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {

    EditText apid, acatid, apname, acatname, aflavours, adesc, aprice, adevprice, aweight, aquantity;
    ImageView aimg;
    Button ok;
    ProgressBar progress;
    FirebaseStorage storage;
    Uri imguri;
    DatabaseReference dref, f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        BottomNavigationView buttomnav = findViewById(R.id.bottom_navigation_admin);
        buttomnav.setOnNavigationItemSelectedListener(navlistener);

        f = FirebaseDatabase.getInstance().getReference().child("Products");
        dref = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        apid = (EditText) findViewById(R.id.a_pid);
        acatid = (EditText) findViewById(R.id.a_catid);
        apname = (EditText) findViewById(R.id.a_pname);
        acatname = (EditText) findViewById(R.id.a_cat);
        aflavours = (EditText) findViewById(R.id.a_flavour);
        adesc = (EditText) findViewById(R.id.a_desc);
        aprice = (EditText) findViewById(R.id.a_price);
        adevprice = (EditText) findViewById(R.id.a_devprice);
        aweight = (EditText) findViewById(R.id.a_weight);
        aquantity = (EditText) findViewById(R.id.a_quantity);
        aimg = (ImageView) findViewById(R.id.insertimg);
        ok = (Button) findViewById(R.id.a_ok);
        progress = (ProgressBar) findViewById(R.id.progressbar);
        progress.setVisibility(View.INVISIBLE);


        aimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgetcontext.launch("image/*");
            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Acheckproduct();
            }

            private void Acheckproduct() {
                progress.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(apid.getText().toString())) {
                    Toast.makeText(AdminActivity.this, "Please enter the Products Id", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(acatid.getText().toString())) {
                    Toast.makeText(AdminActivity.this, "Please enter the Category Id", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(apname.getText().toString())) {
                    Toast.makeText(AdminActivity.this, "Please enter the Product name", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(acatname.getText().toString())) {
                    Toast.makeText(AdminActivity.this, "Please enter the Category name", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(aflavours.getText().toString())) {
                    Toast.makeText(AdminActivity.this, "Product Flavours are Missing!!", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(adesc.getText().toString())) {
                    Toast.makeText(AdminActivity.this, "Product Description is Missing!!", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(aprice.getText().toString())) {
                    Toast.makeText(AdminActivity.this, "Please enter the Price of the Product", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(adevprice.getText().toString())) {
                    Toast.makeText(AdminActivity.this, "Please enter Delivery Price.If there is no Delivery Price Please Enter 1", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(aweight.getText().toString())) {
                    Toast.makeText(AdminActivity.this, "Please enter the Weight in kg", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(aquantity.getText().toString())) {
                    Toast.makeText(AdminActivity.this, "Please enter the Quantity as Per the given weight", Toast.LENGTH_LONG).show();
                } else {
                    AddtoDatabase();


                }
            }

            private void AddtoDatabase() {
                int pid = Integer.parseInt(apid.getText().toString());
                String s = String.valueOf(pid);

                uploadImage(s);
            }

            private void uploadImage(String s) {
                String l = s;
                if (imguri != null) {
                    StorageReference ref = storage.getReference().child(System.currentTimeMillis() + "." + getFileExtension(imguri));
                    ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    dref = dref.child("Image");
                                    Imageclass image1 = new Imageclass(uri.toString());
                                    //String image = dref.push().getKey();
                                   // dref.child(image).setValue(image1);
                                    f.child(l).setValue(image1);
                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Products");
                                    final HashMap<String, Object> apmap = new HashMap<>();
                                    apmap.put("cat_id", acatid.getText().toString());
                                    apmap.put("cat_name", acatname.getText().toString());
                                    apmap.put("description", adesc.getText().toString());
                                    apmap.put("devprice", adevprice.getText().toString());
                                    apmap.put("flavours", aflavours.getText().toString());
                                    apmap.put("pid", apid.getText().toString());
                                    apmap.put("pname", apname.getText().toString());
                                    apmap.put("price", aprice.getText().toString());
                                    apmap.put("quantity", aquantity.getText().toString());
                                    apmap.put("weight", aweight.getText().toString());


                                    db.child(s).updateChildren(apmap);
                                    progress.setVisibility(View.INVISIBLE);
                                    Toast.makeText(AdminActivity.this, "Upload Success", Toast.LENGTH_LONG).show();
                                }

                            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    startActivity(new Intent(AdminActivity.this, ShowProductActivity.class));
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminActivity.this, "Upload UnSuccess", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            private String getFileExtension(Uri imguri) {
                ContentResolver cr = getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                return mime.getExtensionFromMimeType(cr.getType(imguri));
            }
        });
    }


    ActivityResultLauncher<String> mgetcontext = registerForActivityResult(new ActivityResultContracts.GetContent()
            , new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        aimg.setImageURI(result);
                        imguri = result;
                    }
                }
            });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.a_bottom_navigation, menu);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = null;
            switch (item.getItemId()) {
                case R.id.a_add:
                    intent = new Intent(AdminActivity.this, AdminActivity.class);
                    break;
                case R.id.a_dispro:
                    intent=new Intent(AdminActivity.this,ShowProductActivity.class);
                    break;
                case R.id.a_disor:
                   intent=new Intent(AdminActivity.this,ShowOrderActivity.class);
                    break;
                case R.id.a_disortoday:
                    intent=new Intent(AdminActivity.this,TodayOrderActivity.class);
                    break;
                case R.id.a_disdeltoday:
                    intent=new Intent(AdminActivity.this,TodayDeliveryActivity.class);
                    break;
            }
            startActivity(intent);
            return true;
        }
    };
}
