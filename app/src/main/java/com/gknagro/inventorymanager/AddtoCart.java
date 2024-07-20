package com.gknagro.inventorymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddtoCart extends AppCompatActivity {
    EditText id,name,price,quantity;
    Button addcart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_cart);

        id = findViewById(R.id.Pid);
        name = findViewById(R.id.Pname);
        price = findViewById(R.id.Pprice);
        quantity = findViewById(R.id.Pqantity);
        addcart = findViewById(R.id.addtocart);

        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pid = id.getText().toString();
                String Pname = name.getText().toString();
                int Pprice = Integer.parseInt(price.getText().toString());
                int Pquantity = Integer.parseInt(quantity.getText().toString());
                insertValues(Pid,Pname,Pprice,Pquantity);
            }
        });

    }
    protected void insertValues(String id,String name, int price,int quantity){
        new Product(id,name,price,quantity);
    }
}