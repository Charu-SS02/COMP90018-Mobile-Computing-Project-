package com.example.findcoffee.ui.home;

import android.location.Address;
import android.location.Geocoder;
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

import com.example.findcoffee.R;
import com.example.findcoffee.zomatoApiGetter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

//    private HomeViewModel homeViewModel;
    private static RecyclerView recyclerView;
    private static ArrayList<HomeViewModel> data;
//    private int area = 1;
//    public RequestQueue queue;

    ArrayList<String> cafeNames = new ArrayList<>();
    ArrayList<String> cafeAddresses = new ArrayList<>();
    ArrayList<LatLng> cafeCoordinates = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

//        queue = Volley.newRequestQueue(getActivity());
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

        zomatoApiGetter zomato = new zomatoApiGetter(this);
        zomato.search(map,getFragmentManager(),9999);

        Geocoder coder = new Geocoder(root.getContext());

        cafeNames.add("Humble Rays");
        cafeAddresses.add("71 Bouverie Street, Carlton, Melbourne");

        cafeNames.add("Top Paddock");
        cafeAddresses.add("658 Church Street, Richmond, Melbourne");

        for (int i = 0; i < cafeNames.size(); i++) {
            try {
                List<Address> address = coder.getFromLocationName(cafeAddresses.get(i), 5);
                Address location = address.get(0);
                cafeCoordinates.add(new LatLng(location.getLatitude(), location.getLongitude()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        for (int i = 0; i < cafeCoordinates.size(); i++) {
            googleMap.addMarker(new MarkerOptions()
                    .position(cafeCoordinates.get(i))
                    .title(cafeNames.get(i))
            );
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cafeCoordinates.get(0)));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
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