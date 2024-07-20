package com.gknagro.inventorymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProduct extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText prid,prname,prprice;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

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
        databaseReference.child("products").child(id).setValue(product);
        Toast.makeText(this,"Launch Success!",Toast.LENGTH_LONG).show();
    }
}