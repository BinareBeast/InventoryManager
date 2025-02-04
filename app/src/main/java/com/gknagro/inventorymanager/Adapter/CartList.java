package com.gknagro.inventorymanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gknagro.inventorymanager.Activity.Cart;
import com.gknagro.inventorymanager.ModelClass.Item;
import com.gknagro.inventorymanager.R;

import org.w3c.dom.Text;

import java.util.List;

public class CartList  extends RecyclerView.Adapter<CartList.CartitemViewHolder> {

    private List<Item> cartitems;
    Context context;
    public CartList(Context context,List<Item> cartitems){
        this.cartitems = cartitems;
        this.context = context;
    }
    @NonNull
    @Override
    public CartitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitems,parent,false);
        return new CartitemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CartList.CartitemViewHolder holder, int position) {
        Item item = cartitems.get(position);
        holder.id.setText("ID\t" + item.getId());
        holder.name.setText(item.getName());
        holder.price.setText(" ₹ "+item.getPrice());
        holder.quantity.setText("Qty\t"+item.getPieces());

        String name = item.getName();
        String firstletter = "";
        if (name != null && name.length() > 0) {
            firstletter = String.valueOf(name.charAt(0)).toUpperCase();
        } else {
            firstletter = "?";  // Default character in case name is null or empty
        }
        holder.productlogo.setText(firstletter);
        holder.subtotal.setText("Stock Value\t  ₹"+item.getPrice()* item.getPieces());
    }

    @Override
    public int getItemCount() {
        return cartitems.size();
    }

    public class CartitemViewHolder extends RecyclerView.ViewHolder {
        TextView id,name,price,quantity,subtotal,productlogo;
        public CartitemViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.itemid);
            name = view.findViewById(R.id.itemname);
            price = view.findViewById(R.id.itemprice);
            quantity = view.findViewById(R.id.itemquantity);
            subtotal = view.findViewById(R.id.subtotal);
            productlogo = view.findViewById(R.id.productlogo);

        }
    }

}
