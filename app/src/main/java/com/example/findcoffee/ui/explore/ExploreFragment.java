package com.example.findcoffee.ui.explore;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.findcoffee.R;
import com.example.findcoffee.ui.home.HomeFragment;
import com.example.findcoffee.ui.home.HomeViewModel;
import com.example.findcoffee.zomatoApiGetter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ExploreFragment extends Fragment {

    private ExploreViewModel exploreViewModel;
    private static RecyclerView recyclerView;
    private static ArrayList<HomeViewModel> data;
    //    private int area = 1;
    public RequestQueue queue;
    static ProgressBar bar;
    double currentLong;

    double currentLat;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        exploreViewModel =
                ViewModelProviders.of(this).get(ExploreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_explore, container, false);

        recyclerView = root.findViewById(R.id.recycler_Explore);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        queue = Volley.newRequestQueue(getActivity());
        data = new ArrayList<HomeViewModel>();


        bar = root.findViewById(R.id.progressBar);
        bar.setVisibility(VISIBLE);
        recyclerView.setVisibility(GONE);

        final Map<String, String> map = new HashMap<String, String>();
        map.put("entity_id=", "259");
        map.put("entity_type=", "city");
        map.put("establishment_type=", "1");
        map.put("category=", "6");
        //sorting by rating
        map.put("sort=", "rating");


        final zomatoApiGetter zomato = new zomatoApiGetter(this);


        zomato.search(map, queue, 9999);


        exploreViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });


        currentLat = HomeFragment.currentLat;
        currentLong = HomeFragment.currentLong;


        return root;
    }


    public void drawShop(String name, String address, String thumb,String addressLon,String addressLat,String cuisines,String featured_image,String menu_url, String photos_url,String price_range,String timings,String storeUrl,String events_url,String aggregate_rating){

            float[] dist = new float[1];
            Location.distanceBetween(Double.parseDouble(addressLat), Double.parseDouble(addressLon), currentLat, currentLong, dist);

            data.add(new HomeViewModel(
                    name,
                    address,
                    thumb,addressLon,addressLat,cuisines,featured_image,menu_url,photos_url,price_range,timings,storeUrl,events_url,aggregate_rating,dist
            ));


//        data.add(new HomeViewModel(
//                name,
//                address,
//                thumb,addressLon,addressLat,cuisines,featured_image,menu_url,photos_url,price_range,timings,storeUrl,events_url,dist
//        ));
    }

    public static RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static ArrayList<HomeViewModel> getData()
    {
        if(! data.isEmpty()){
            Log.d("getData1",data+"");
            bar.setVisibility(GONE);
            recyclerView.setVisibility(VISIBLE);
        }
        return data;
    }
}