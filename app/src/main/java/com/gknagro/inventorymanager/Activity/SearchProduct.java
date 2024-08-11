package com.gknagro.inventorymanager.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.UUID;

public class SearchProduct extends AppCompatActivity implements RecyclerViewInterface {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private List<Product> productList;

    private SearchView searchView;


    Dialog dialog  ;
    Button increasebtn,decreasebtn,addtocartbtn,backbtn,cartButton;
    EditText quantityedt;
    int quantity =0;
    String name;
    int price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);


        //Recycler view & searchview declaration
        recyclerView = findViewById(R.id.productview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("products");
        fetchProducts();
        searchView = findViewById(R.id.searchproduct);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText(newText);
                return true;
            }
        });

        //dialog declaration
        dialog = new Dialog(SearchProduct.this);
        dialog.setContentView(R.layout.quantitydialouge);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        //dialog elements initialization
        increasebtn = dialog.findViewById(R.id.increase);
        decreasebtn = dialog.findViewById(R.id.decrease);
        backbtn = dialog.findViewById(R.id.back);
        addtocartbtn = dialog.findViewById(R.id.addtocart);
        quantityedt = dialog.findViewById(R.id.quantity);

        //dialog definitions

        backbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                quantity =0;
                quantityedt.setText(String.valueOf(quantity));
                dialog.dismiss();

            }
        });
        increasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    quantity++;
                    quantityedt.setText(String.valueOf(quantity));
            }
        });
        decreasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity != 0){
                    quantity--;

                }
                quantityedt.setText(String.valueOf(quantity));
            }
        });


        addtocartbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(quantity==0){
                    Toast.makeText(SearchProduct.this,"Enter the Quantity",Toast.LENGTH_SHORT).show();
                }
                else{
                    Product product = new Product(name,price,quantity);
                    auth = FirebaseAuth.getInstance();
                    user = auth.getCurrentUser();
                    String userid = user.getUid();
                    String orderid = databaseReference.push().getKey();
                    databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userid).child("orders").child(orderid);

                    databaseReference.setValue(product) .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(SearchProduct.this,"Added to cart",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        cartButton = findViewById(R.id.btn);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchProduct.this,CartActivity.class));
                Toast.makeText(SearchProduct.this,"Cart activity",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProducts() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MainActivity", "DatabaseError: " + databaseError.getMessage());
            }
        });
    }

    private void searchText(String newText) {
        ArrayList<Product> searchlist = new ArrayList<>();

        for (Product produt : productList) {
            if (produt.getName().toLowerCase().contains(newText.toLowerCase())) {
                searchlist.add(produt);
            }
        }
        adapter.searchdata(searchlist);
    }

    @Override
    public void onItemClick(int position) {
        Product details = productList.get(position);

        name = details.getName();
        price = details.getPrice();
        dialog.show();

    }



}