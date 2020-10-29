package com.example.findcoffee.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.findcoffee.MainActivity;
import com.example.findcoffee.R;
import com.example.findcoffee.ShopView;
import com.example.findcoffee.ui.explore.ExploreFragment;

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView textViewName;
    TextView textViewVersion;
    ImageView imageViewIcon;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
        this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
    }


    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        Intent intent = new Intent (context, ShopView.class);
        context.startActivity(intent);
        Log.d("CLCICK","String "+this.textViewName.getText().toString());
    }
}
