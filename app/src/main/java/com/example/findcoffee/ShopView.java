package com.example.findcoffee;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.findcoffee.ui.arView.ARActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class ShopView extends AppCompatActivity implements OnMapReadyCallback {


    private LatLng latLng;
    private String shopName;

    ViewPager viewPager;
    String[] photos;

    userPhotosAdapter userPAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_view_details);


        Intent intent = getIntent();
        shopName = intent.getStringExtra("shopName");
//        String shopThumb = intent.getStringExtra("shopThumb");
        final String shopAddress = intent.getStringExtra("shopAddress");

        String shopFeatured_image = intent.getStringExtra("shopFeatured_image");


        final String shopAddressLon = intent.getStringExtra("shopAddressLon");
        final String shopAddressLat = intent.getStringExtra("shopAddressLat");


        String shopshopUser_rating = intent.getStringExtra("shopUser_rating");
        String shopshopTiming = intent.getStringExtra("shopTimings");
        String menuURL = intent.getStringExtra("shopMenu_url");
        String eventURL = intent.getStringExtra("shopEvents_url");
        String storeURL = intent.getStringExtra("shopStoreUrl");

//        String[] shopUser_photosUrl = intent.getStringArrayExtra("shopUser_photosUrl");
        String shopPrice_range = intent.getStringExtra("shopPrice_range");


        TextView shopName_View = (TextView) findViewById(R.id.textView_shopName);
        TextView shopAddress_View = (TextView) findViewById(R.id.textView_shopAddress);
        ImageView shopThumb_View = (ImageView) findViewById(R.id.image_coffee_thumb);
        TextView shopTiming = (TextView) findViewById(R.id.textView_ShopTiming);
        TextView shopMenu = (TextView) findViewById(R.id.textView_ShopMenu);
        TextView shopEvents = (TextView) findViewById(R.id.textView_Event);
        TextView shopStore = (TextView) findViewById(R.id.textView_Store);

        TextView textView_ShopPrice = (TextView) findViewById(R.id.textView_ShopPrice);

        FloatingActionButton button_ar = (FloatingActionButton) findViewById(R.id.button_AR);
//        Button button_ar = (Button) findViewById(R.id.button_AR);
        button_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopView.this, ARActivity.class);
                intent.putExtra("shop_name", shopName);
                intent.putExtra("shop_lon", shopAddressLon);
                intent.putExtra("shop_lat", shopAddressLat);
                startActivity(intent);
            }
        });

        RatingBar userRating = (RatingBar) findViewById(R.id.ratingBar);

        userRating.setRating(Float.parseFloat(shopshopUser_rating));

        shopName_View.setText(shopName);
        shopAddress_View.setText(shopAddress);
        shopMenu.setText(menuURL);
        shopEvents.setText(eventURL);
        shopStore.setText(storeURL);

        if(shopshopTiming.isEmpty()){
            shopTiming.setText("Not available");
        }else{
            shopTiming.setText(shopshopTiming);
        }

//        Picasso.get().load(shopThumb).into(shopThumb_View);

        if (shopFeatured_image.isEmpty()){
            shopThumb_View.setImageResource(R.drawable.coffee_cup_placeholder);
        }else {
            int borderSize = 5;
            int color = Color.BLACK;

            Picasso.get().load(shopFeatured_image).resize(380, 380).centerCrop().into(shopThumb_View);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.shopMap);

        latLng = new LatLng(Double.parseDouble(shopAddressLat), Double.parseDouble(shopAddressLon));
        mapFragment.getMapAsync(this);

        if (shopPrice_range.isEmpty()){
            textView_ShopPrice.setText("0");
        }else{
            String setDollar="";
            for(int i=0;i < Integer.parseInt(shopPrice_range) ;i++){
                setDollar = setDollar+"$";
            }
            textView_ShopPrice.setText(setDollar);
        }


//        viewPager = (ViewPager)findViewById(R.id.viewPhotos);
//
//        userPAdapter = new userPhotosAdapter(this,shopUser_photosUrl);
//
//        viewPager.setAdapter(userPAdapter);




    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(shopName));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
    }
}


