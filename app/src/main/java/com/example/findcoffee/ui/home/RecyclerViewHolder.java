package com.example.findcoffee.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.findcoffee.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView textViewName;
    TextView textViewVersion;
    ImageView imageViewIcon;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
        this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
