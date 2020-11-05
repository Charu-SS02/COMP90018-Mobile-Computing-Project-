package com.example.findcoffee;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class userPhotosAdapter extends PagerAdapter {

    String photos[];
    Context c;
    LayoutInflater layoutInflater;

    public userPhotosAdapter(Context context,String[] getPhotos){
        this.c = context;
        this.photos = getPhotos;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return photos.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Object initItem(@NonNull ViewGroup layout, final int p ){
        View itemView = layoutInflater.inflate(R.layout.view_photos_card, layout, false);

        ImageView imgView = (ImageView) itemView.findViewById(R.id.viewPhotosCard);

        Picasso.get().load(photos[p]).into(imgView);

        Objects.requireNonNull(layout).addView(itemView);

        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup layout, int position, Object object) {

        layout.removeView((LinearLayout) object);
    }
}
