package com.gknagro.inventorymanager.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gknagro.inventorymanager.ModelClass.Item;
import com.gknagro.inventorymanager.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProduct extends AppCompatActivity {
    EditText idet,nameet,priceet,piceset;
    Button update;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_product);

        idet = findViewById(R.id.id);
        nameet = findViewById(R.id.name);
        priceet = findViewById(R.id.price);
        piceset = findViewById(R.id.pices);
        update = findViewById(R.id.addproduct);
        databaseReference = FirebaseDatabase.getInstance().getReference("products");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idet.getText().toString().trim();
                String name = nameet.getText().toString().trim();
                String price = priceet.getText().toString().trim();
                String pieces = piceset.getText().toString().trim();

                if (TextUtils.isEmpty(id) ){
                    idet.setError(" * Required");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    nameet.setError(" * Required");
                    return;
                }
                if(TextUtils.isEmpty(price)){
                    priceet.setError(" * Required");
                    return;
                }
                if(TextUtils.isEmpty(pieces)) {
                    piceset.setError(" * Required");
                    return;
                }

                int pricei = Integer.parseInt(price);
                int piecesi = Integer.parseInt(pieces);

                Item product = new Item(id,name,pricei,piecesi);
                databaseReference.child(product.getId()).setValue(product);
                startActivity(new Intent(AddProduct.this,AdminActivity.class).putExtra("fragmentid",2));
            }
        });


    }
}