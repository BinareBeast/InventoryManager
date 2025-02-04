package com.gknagro.inventorymanager.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.InputType;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.gknagro.inventorymanager.Adapter.ProductList;
import com.gknagro.inventorymanager.ModelClass.Item;
import com.gknagro.inventorymanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Product extends Fragment {

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    private ProductList productList;
    private List<Item> productitems;
    private DatabaseReference databaseReference;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.product, container, false);

        floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddProduct.class));
            }
        });
        recyclerView = view.findViewById(R.id.productlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productitems = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productitems.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Item item = dataSnapshot.getValue(Item.class);
                    if (item != null) {
                        productitems.add(item);
                    }
                }if (productList != null) {
                    productList.notifyDataSetChanged(); // Notify the adapter if it's not null
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        productList = new ProductList(getContext(),productitems);
        recyclerView.setAdapter(productList);

        searchView = view.findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productList.filter(newText);
                return true;
            }
        });
        return view;
    }
}