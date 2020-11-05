package com.example.findcoffee.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView textViewName;
    TextView textViewVersion;
    ImageView imageViewIcon;

    public RecyclerViewHolder(final View itemView, final ArrayList<HomeViewModel> dataSet) {
        super(itemView);
        this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
        this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);



        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int position = getAdapterPosition();

                Context context = view.getContext();

                Intent intent = new Intent (context, ShopView.class);
                intent.putExtra("shopName",dataSet.get(position).getName());
                intent.putExtra("shopThumb",  dataSet.get(position).getImage());
                intent.putExtra("shopAddress",  dataSet.get(position).getVersion());


                intent.putExtra("shopAddress",  dataSet.get(position).getVersion());

                intent.putExtra("shopAddress",  dataSet.get(position).getVersion());

                intent.putExtra("shopAddress",  dataSet.get(position).getVersion());

                context.startActivity(intent);
//                Log.d("CLCICK","String "+itemView.getContext()+" -- "+position+dataSet.get(position).getImage());
            }
        });

    }

}
