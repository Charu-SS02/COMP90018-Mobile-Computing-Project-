package com.example.findcoffee.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.findcoffee.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

//    private HomeViewModel homeViewModel;
    private static RecyclerView recyclerView;
    private static ArrayList<HomeViewModel> data;
//    private int area = 1;
    public RequestQueue queue;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        queue = Volley.newRequestQueue(getActivity());
        data = new ArrayList<HomeViewModel>();
//        ArrayList<String> names = new ArrayList<>();
//        ArrayList<String> locations = new ArrayList<>();
//        jsonAPIGetter apiGetter = new jsonAPIGetter();


//        Map<String, String> map = new HashMap<String, String>();
//        map.put("clue_small_area=", "Carlton");
////        map.put("trading_name=", "Unibite");
////        map.put("clue_small_area=", "Carlton");


        Map<String, String> map = new HashMap<String, String>();
        map.put("entity_id=", "259");
        map.put("entity_type=", "city");
        map.put("establishment_type=", "1");
        map.put("category=", "6");


//        apiGetter.search(map, this, 10);

        zomatoApiGetter zomato = new zomatoApiGetter();
        zomato.search(map,this,9999);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Sydney"));

        LatLng melbourne = new LatLng(-37.852, 141.211);
        googleMap.addMarker(new MarkerOptions()
                .position(melbourne)
                .title("Melbourne"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void drawShop(String name, String address, String thumb){
        data.add(new HomeViewModel(
                name,
                address,
                thumb
        ));
    }

    public static RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static ArrayList<HomeViewModel> getData() {
        return data;
    }
}