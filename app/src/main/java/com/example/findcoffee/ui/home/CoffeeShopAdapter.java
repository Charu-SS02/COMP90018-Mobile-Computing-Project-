package com.example.findcoffee.ui.home;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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
        TextView textViewPriceRange = holder.textViewPriceRange;
        TextView textViewShopDist = holder.textViewShopDist;

        ImageView imageView = holder.imageViewIcon;

        RatingBar userRating = holder.userRating;
        userRating.setRating(Float.parseFloat(dataSet.get(position).getRating()));



        textViewName.setText(dataSet.get(position).getName());
        textViewVersion.setText(dataSet.get(position).getVersion());

        if (dataSet.get(position).getPrice_range().isEmpty()){
            textViewPriceRange.setText("0");
        }else{
            String setDollar="";
            for(int i=0;i < Integer.parseInt((dataSet.get(position).getPrice_range())) ;i++){
                setDollar = setDollar+"$";
            }
            textViewPriceRange.setText(setDollar);
        }

        float[] data = dataSet.get(position).getDist();
        for(int i=0;i<dataSet.get(position).getDist().length;i++){
            if((int) data[i] < 800){
                textViewShopDist.setText((int) data[i] +" m");
            }else{
                int newDist = ((int) data[i])/1000;
                Log.d("LocationDistance",newDist+" -- -- "+ (int)data[i]);
                textViewShopDist.setText(newDist +" km");
            }
        }


        Log.d("USER_RATING",dataSet.get(position).getRating());




//        imageView.setImageDrawable(dataSet.get(position).getImage());

        if (dataSet.get(position).getImage().isEmpty()){
            holder.imageViewIcon.setImageResource(R.drawable.coffee_placeholder);
        }else {
            int borderSize = 5;
            int color = Color.BLACK;
            int cornerRadius = 10;
            Picasso.get().load(dataSet.get(position).getImage()).into(holder.imageViewIcon);
        }
//        imageView.setImageResource(dataSet.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }



}
