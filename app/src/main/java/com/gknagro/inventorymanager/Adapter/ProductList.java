package com.gknagro.inventorymanager.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.gknagro.inventorymanager.Activity.Product;
import com.gknagro.inventorymanager.ModelClass.Item;
import com.gknagro.inventorymanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends RecyclerView.Adapter<ProductList.ProductViewHolder> {

    private List<Item> productitem;
    Context context;
    private List<Item> copyproductlist;

    public ProductList(Context context, List<Item> productitem){

        this.context = context;
        this.productitem = productitem;
        copyproductlist = new ArrayList<>(productitem);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitems,parent,false);
        return new ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductList.ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Item item = productitem.get(position);
        holder.id.setText("ID\t" + item.getId());
        holder.price.setText(" ₹ "+String.valueOf(item.getPrice()));
        holder.stock.setText("IN Stock\t" +String.valueOf(item.getPieces()));
        holder.name.setText(item.getName());
        holder.totalprice.setText("Stock Value\t  ₹"+String.valueOf(item.getSubtotal()));
//        String name = holde
        String firstletter = String.valueOf(item.getName().charAt(0)).toUpperCase();
        holder.productlogo.setText(firstletter);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuantityDialog(item,position);
            }
        });
    }

    public void filter(String text){
        if(text.isEmpty()){
            productitem = new ArrayList<>(copyproductlist);
        }else{
            List<Item> filterlist = new ArrayList<>();
            for(Item item: copyproductlist){
                if(item.getName().toLowerCase().contains(text.toLowerCase()) ){
                    filterlist.add(item);
                }
            }
            productitem.clear();
            productitem.addAll(filterlist);

        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return productitem.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView id, name, price, stock, totalprice,productlogo;
        public ProductViewHolder(View view){
            super(view);
                id = view.findViewById(R.id.itemid);
                name = view.findViewById(R.id.itemname);
                price = view.findViewById(R.id.itemprice);
                stock = view.findViewById(R.id.itemquantity);
                totalprice = view.findViewById(R.id.totalprice);
                productlogo = view.findViewById(R.id.productlogo);
        }
    }
    int quantity = 0;
    private void showQuantityDialog(Item product,int position) {

        Item item = productitem.get(position);
        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(context).inflate(R.layout.quantitydialog, null);
        TextView enterquantity = dialogView.findViewById(R.id.enterquantity);
        TextView increasequantity = dialogView.findViewById(R.id.increasequantity);
        TextView decreasequantity = dialogView.findViewById(R.id.decreasequantity);
        TextView subtotal = dialogView.findViewById(R.id.subtotal);
        Button addButton = dialogView.findViewById(R.id.addtocart);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        // Initialize quantity as 1 (or set it from EditText if you want)
        int[] quantity = {0 };  // Using array to modify the value inside onClick listener

        // Set up the initial subtotal
        subtotal.setText("₹" + (product.getPrice() * quantity[0]));

        // Increase quantity button
        increasequantity.setOnClickListener(v -> {
            if (product.getPieces() >= quantity[0]) {
                quantity[0]++;
                enterquantity.setText(String.valueOf(quantity[0])); // Update the quantity EditText
                subtotal.setText("₹" + (product.getPrice() * quantity[0])); // Update subtotal
            } else {
                enterquantity.setError("Not enough stock");
            }
        });

        // Decrease quantity button
        decreasequantity.setOnClickListener(v -> {
            if (quantity[0] >= 1) {
                quantity[0]--;
                enterquantity.setText(String.valueOf(quantity[0])); // Update the quantity EditText
                subtotal.setText("₹" + (product.getPrice() * quantity[0])); // Update subtotal
            }
        });


//        FirebaseAuth firebaseAuth ;

        // When Add to Cart button is clicked
        addButton.setOnClickListener(v -> {

            DatabaseReference databaseReference;
            String uid = FirebaseAuth.getInstance().getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);

            String id = item.getId();
            String name = item.getName();
            int price = item.getPrice();


                    Item cartitem = new Item(id,name,price,quantity[0],quantity[0]*price);

                    databaseReference.child("orders").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                snapshot.getRef().child("pieces").setValue(quantity[0]);
                                snapshot.getRef().child("subtotal").setValue(quantity[0]*price);
                            }
                            else{
                                databaseReference.child("orders").child(id).setValue(cartitem);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



            dialog.dismiss();

        });

        dialog.show();
    }



}
