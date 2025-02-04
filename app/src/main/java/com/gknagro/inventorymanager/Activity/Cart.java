package com.gknagro.inventorymanager.Activity;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gknagro.inventorymanager.Adapter.CartList;
import com.gknagro.inventorymanager.Adapter.ProductList;
import com.gknagro.inventorymanager.ModelClass.Item;
import com.gknagro.inventorymanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    TextView additems, totalamount,invoiceno, date,totalitem;
    RecyclerView recyclerView;
    CartList cartList;
    List<Item> cartitem;
    DatabaseReference databaseReference;
    Button proceedtobuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.cart);

        totalamount = findViewById(R.id.totalAmount);
        String uid = FirebaseAuth.getInstance().getUid();
        recyclerView = findViewById(R.id.cartItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartitem = new ArrayList<>();




        DatabaseReference orderReference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("orders");
        orderReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartitem.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Item item = dataSnapshot.getValue(Item.class);
                    if (item != null) {
                        cartitem.add(item);
                    }
                }if (cartList != null) {
                    cartList.notifyDataSetChanged(); // Notify the adapter if it's not null
                }
                updateTotalAmount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        cartList = new CartList(this,cartitem);
        recyclerView.setAdapter(cartList);

        int cartcount = cartList.getItemCount();
        totalitem = findViewById(R.id.totalItems);
        totalitem.setText("Items ( "+cartcount+" )");

        additems = findViewById(R.id.additem);
        additems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cart.this,AdminActivity.class).putExtra("fragmentid",2));
            }
        });
        proceedtobuy = findViewById(R.id.proceedtobuy);
        proceedtobuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void updateTotalAmount() {
        // Calculate the total amount based on cartitem list
        double total = 0;
        for (Item item : cartitem) {
            total += item.getPrice() * item.getPieces();
        }
        totalamount.setText("₹ " + total);
    }
}