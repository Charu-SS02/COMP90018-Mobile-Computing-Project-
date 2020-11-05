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
import android.widget.RatingBar;
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
    TextView textViewPriceRange;

    TextView textViewShopDist;
    ImageView imageViewIcon;

    RatingBar userRating;

    public RecyclerViewHolder(final View itemView, final ArrayList<HomeViewModel> dataSet) {
        super(itemView);
        this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
        this.textViewShopDist = (TextView) itemView.findViewById(R.id.shopDist);
        this.textViewPriceRange = (TextView) itemView.findViewById(R.id.price_range);

        this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);

        this.userRating = (RatingBar) itemView.findViewById(R.id.rating);





        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int position = getAdapterPosition();

                Context context = view.getContext();

                Intent intent = new Intent (context, ShopView.class);
                intent.putExtra("shopName",dataSet.get(position).getName());
                intent.putExtra("shopThumb",  dataSet.get(position).getImage());
                intent.putExtra("shopAddress",  dataSet.get(position).getVersion());

                intent.putExtra("shopAddressLon",  dataSet.get(position).getAddressLon());
                intent.putExtra("shopAddressLat",  dataSet.get(position).getAddressLat());
                intent.putExtra("shopCuisines",  dataSet.get(position).getCuisines());
                intent.putExtra("shopFeatured_image",  dataSet.get(position).getFeatured_image());
                intent.putExtra("shopMenu_url",  dataSet.get(position).getMenu_url());
                intent.putExtra("shopPhotos_url",  dataSet.get(position).getPhotos_url());
                intent.putExtra("shopPrice_range",  dataSet.get(position).getPrice_range());
                intent.putExtra("shopTimings",  dataSet.get(position).getTimings());
                intent.putExtra("shopStoreUrl",  dataSet.get(position).getStoreUrl());
                intent.putExtra("shopEvents_url",  dataSet.get(position).getEvents_url());

                intent.putExtra("shopUser_rating",  dataSet.get(position).getRating());

                context.startActivity(intent);
//                Log.d("CLCICK","String "+itemView.getContext()+" -- "+position+dataSet.get(position).getImage());
            }
        });

    }

}
