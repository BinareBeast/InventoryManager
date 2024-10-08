package com.gknagro.inventorymanager.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gknagro.inventorymanager.ModelClass.Product;
import com.gknagro.inventorymanager.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private List<Product> productList;
    private final RecyclerViewInterface recyclerViewInterface;


    public ProductAdapter(List<Product> productList, RecyclerViewInterface recyclerViewInterface) {
        this.productList = productList;
        this.recyclerViewInterface = recyclerViewInterface;
    }
   
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView priceTextView;
        public TextView idTextView;

        public ProductViewHolder(View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.pname);
            priceTextView = itemView.findViewById(R.id.pprice);
            idTextView = itemView.findViewById(R.id.pid);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(recyclerViewInterface!= null){
                       int pos = getAbsoluteAdapterPosition();
                       if(pos != RecyclerView.NO_POSITION){
                           recyclerViewInterface.onItemClick(pos);
                       }

                   }
                }
            });

        }
    }
    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchitem, parent, false);
        return new ProductViewHolder(itemView,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product currentProduct = productList.get(position);
        holder.nameTextView.setText(currentProduct.getName());
        holder.priceTextView.setText(String.valueOf(currentProduct.getPrice()));
        holder.idTextView.setText(String.valueOf(currentProduct.getId()));

    }

    public void searchdata(ArrayList<Product> list){
        productList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return productList.size() ;
    }

}
