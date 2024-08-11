package com.gknagro.inventorymanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gknagro.inventorymanager.ModelClass.Product;
import com.gknagro.inventorymanager.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<Product> cartItemList;

    public CartAdapter(Context context, List<Product> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product cartItem = cartItemList.get(position);
        holder.productNameTextView.setText(cartItem.getName());
        holder.quantityTextView.setText(String.valueOf(cartItem.getQuantity()));
        holder.priceTextView.setText(String.valueOf(cartItem.getPrice()));
        holder.totalpriceTextView.setText(String.valueOf(cartItem.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView,  quantityTextView, priceTextView, totalpriceTextView;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productname);
            quantityTextView = itemView.findViewById(R.id.productquantity);
            priceTextView = itemView.findViewById(R.id.productprice);
            totalpriceTextView = itemView.findViewById(R.id.totalprice);
        }
    }
}