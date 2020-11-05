package com.example.findcoffee.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.findcoffee.CircleTransform;
import com.example.findcoffee.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CoffeeShopAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<HomeViewModel> dataSet;

    public CoffeeShopAdapter(ArrayList<HomeViewModel> data) {
        this.dataSet = new ArrayList<HomeViewModel>(data);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.coffeeshop_cardview;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
//        view.setOnClickListener(new RecyclerViewHolder(view));
        return new RecyclerViewHolder(view,dataSet);
    }

    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(position).getName());
        textViewVersion.setText(dataSet.get(position).getVersion());
//        imageView.setImageDrawable(dataSet.get(position).getImage());

        if (dataSet.get(position).getImage().isEmpty()){
            holder.imageViewIcon.setImageResource(R.drawable.coffee_placeholder);
        }else {
            int borderSize = 5;
            int color = Color.BLACK;
            int cornerRadius = 10;
            Picasso.get().load(dataSet.get(position).getImage()).transform(new CircleTransform(borderSize, cornerRadius, color)).into(holder.imageViewIcon);
        }
//        imageView.setImageResource(dataSet.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }



}
