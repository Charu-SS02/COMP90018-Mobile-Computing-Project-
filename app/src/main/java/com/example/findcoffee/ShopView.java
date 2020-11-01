package com.example.findcoffee;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ShopView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_view_details);


        Intent intent = getIntent();
        String shopName = intent.getStringExtra("shopName");
        String shopThumb = intent.getStringExtra("shopThumb");
        String shopAddress = intent.getStringExtra("shopAddress");


        TextView shopName_View = (TextView) findViewById(R.id.textView_shopName);
        TextView shopAddress_View = (TextView) findViewById(R.id.textView_shopAddress);
        ImageView shopThumb_View = (ImageView) findViewById(R.id.image_coffee_thumb);

        shopName_View.setText(shopName);
        shopAddress_View.setText(shopAddress);
        Picasso.get().load(shopThumb).into(shopThumb_View);

    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
    }
}
