package com.gknagro.inventorymanager.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gknagro.inventorymanager.ModelClass.Product;
import com.gknagro.inventorymanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProduct extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText prid,prname,prprice;
    Button submit;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        auth = FirebaseAuth.getInstance();
        prid= findViewById(R.id.id);
        prname = findViewById(R.id.name);
        prprice = findViewById(R.id.price);
        submit = findViewById(R.id.addnewproduct);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });

    }
    protected void insert(){
        String id = prid.getText().toString().trim();
        String name = prname.getText().toString().trim();
        String price = prprice.getText().toString().trim();

        if(id.isEmpty() || name.isEmpty() || price.isEmpty()){
            Toast.makeText(this,"Enter Required Fields",Toast.LENGTH_SHORT).show();
            return;
        }
        addNewProduct(id,name,Integer.parseInt(price));

    }
    private void addNewProduct(String id, String name, int price) {
        Product product = new Product(id, name, price);
        databaseReference.child("products").push().setValue(product);
//        databaseReference.child("products").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).setValue(product);
        Toast.makeText(this,"Launch Success!",Toast.LENGTH_LONG).show();
    }
}