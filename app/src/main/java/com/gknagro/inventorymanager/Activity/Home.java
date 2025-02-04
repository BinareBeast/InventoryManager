package com.gknagro.inventorymanager.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gknagro.inventorymanager.ModelClass.Item;
import com.gknagro.inventorymanager.R;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {

    CardView cardView;

    List<Item> items;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.home, container, false);
        cardView = view.findViewById(R.id.makebill);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Cart.class));
            }
        });
        return view;
    }
}