package com.example.findcoffee;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class ShopView extends AppCompatActivity implements OnMapReadyCallback {


    private LatLng latLng;
    private String shopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_view_details);


        Intent intent = getIntent();
        shopName = intent.getStringExtra("shopName");
//        String shopThumb = intent.getStringExtra("shopThumb");
        String shopAddress = intent.getStringExtra("shopAddress");

        String shopFeatured_image = intent.getStringExtra("shopFeatured_image");


        String shopAddressLon = intent.getStringExtra("shopAddressLon");
        String shopAddressLat = intent.getStringExtra("shopAddressLat");


        TextView shopName_View = (TextView) findViewById(R.id.textView_shopName);
        TextView shopAddress_View = (TextView) findViewById(R.id.textView_shopAddress);
        ImageView shopThumb_View = (ImageView) findViewById(R.id.image_coffee_thumb);

        shopName_View.setText(shopName);
        shopAddress_View.setText(shopAddress);
//        Picasso.get().load(shopThumb).into(shopThumb_View);

        if (shopFeatured_image.isEmpty()){
            shopThumb_View.setImageResource(R.drawable.coffee_placeholder);
        }else {
            int borderSize = 5;
            int color = Color.BLACK;

            Picasso.get().load(shopFeatured_image).transform(new CircleTransform(borderSize, color)).resize(380, 380).centerCrop().into(shopThumb_View);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.shopMap);

        latLng = new LatLng(Double.parseDouble(shopAddressLat), Double.parseDouble(shopAddressLon));
        mapFragment.getMapAsync(this);



    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
//        if (googleMap != null) {
//            marker = googleMap.addMarker(new MarkerOptions()
//                    .position(latLng).title(place_name)
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
//                    .draggable(false).visible(true));
//        }
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Your current location");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
//
//        //move map camera
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(shopName));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
    }
}


