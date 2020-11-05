package com.example.findcoffee.ui.search;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchFragment extends Fragment {

    private SearchFragment searchFragment = this;
    private SearchViewModel dashboardViewModel;
    private static RecyclerView recyclerView;
    private static ArrayList<HomeViewModel> data;
    public RequestQueue queue;
    double currentLong;
    double currentLat;

    private static ImageView arrow;
    private static TextView searcHint;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        final EditText searchEdit = root.findViewById(R.id.editTextSearch);
        searchEdit.getText().toString();

        recyclerView = root.findViewById(R.id.recycler_Search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        final zomatoApiGetter zomato = new zomatoApiGetter(searchFragment);
        queue = Volley.newRequestQueue(getActivity());
        data = new ArrayList<HomeViewModel>();
//        queue = Volley.newRequestQueue(getActivity());



        arrow = root.findViewById(R.id.arrow);
        searcHint = root.findViewById(R.id.searcHint);
//        bar = root.findViewById(R.id.progressBar2);
//        bar.setVisibility(VISIBLE);
//        recyclerView.setVisibility(GONE);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void afterTextChanged(Editable editable) {
                String searchInputString = editable.toString();
                if(searchInputString.length() >= 3){

                    Log.d("Search", searchInputString);

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("entity_id=", "259");
                    map.put("entity_type=", "city");
                    map.put("establishment_type=", "1");
                    map.put("category=", "6");

                    map.put("q=", searchInputString);
                    map.put("radius=", "500");


//        apiGetter.search(map, this, 10);

                    data.clear();
                    zomato.search(map,queue,10);
                }

            }
        });

//        final TextView textView = root.findViewById(R.id.text_search);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
//                thumb,addressLon,addressLat,cuisines,featured_image,menu_url,photos_url,price_range,timings,storeUrl,events_url
//        ));
    }

    public void emptyData(){
        data.clear();
    }

    public static RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static ArrayList<HomeViewModel> getData() {
        if(! data.isEmpty()){
            arrow.setVisibility(GONE);
            searcHint.setVisibility(GONE);

        }
        return data;
    }
}