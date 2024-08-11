package com.gknagro.inventorymanager.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gknagro.inventorymanager.Adapter.CartAdapter;
import com.gknagro.inventorymanager.Adapter.ProductAdapter;
import com.gknagro.inventorymanager.Adapter.RecyclerViewInterface;
import com.gknagro.inventorymanager.ModelClass.Product;
import com.gknagro.inventorymanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private   List<Product> cartList;
    private   DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(this,cartList);
        recyclerView.setAdapter(cartAdapter);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String userid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userid).child("orders");
        displayCart();

    }
    private void displayCart() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cartList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    if(product != null)cartList.add(product);
                }
                if(cartAdapter != null)cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}