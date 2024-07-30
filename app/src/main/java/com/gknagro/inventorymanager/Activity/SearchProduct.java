package com.gknagro.inventorymanager.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gknagro.inventorymanager.Adapter.ProductAdapter;
import com.gknagro.inventorymanager.Constructors.Product;
import com.gknagro.inventorymanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchProduct extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private DatabaseReference databaseReference;
    private List<Product> productList;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        recyclerView = findViewById(R.id.productview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchproduct);

        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("products");
        fetchProducts();

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
    }

    private void fetchProducts() {
        databaseReference.addValueEventListener(new ValueEventListener() {
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

    private void searchText(String newText){
        ArrayList<Product> searchlist = new ArrayList<>();

        for(Product produt: productList){
         if( produt.getName().toLowerCase().contains(newText.toLowerCase())){
             searchlist.add(produt);
         }
        }
        adapter.searchdata(searchlist);
    }

}