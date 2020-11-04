package com.example.findcoffee.ui.explore;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.findcoffee.ui.home.HomeViewModel;
import com.example.findcoffee.zomatoApiGetter;

import java.util.ArrayList;
import java.util.HashMap;
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
//        ArrayList<String> names = new ArrayList<>();
//        ArrayList<String> locations = new ArrayList<>();
//        jsonAPIGetter apiGetter = new jsonAPIGetter();

//        Map<String, String> map = new HashMap<String, String>();
//        map.put("clue_small_area=", "Carlton");
////        map.put("trading_name=", "Unibite");
////        map.put("clue_small_area=", "Carlton");

        bar = root.findViewById(R.id.progressBar);
        bar.setVisibility(VISIBLE);
        recyclerView.setVisibility(GONE);

        Map<String, String> map = new HashMap<String, String>();
        map.put("entity_id=", "259");
        map.put("entity_type=", "city");
        map.put("establishment_type=", "1");
        map.put("category=", "6");
        //sorting by rating
        map.put("sort=", "rating");



        zomatoApiGetter zomato = new zomatoApiGetter(this);
        zomato.search(map,queue,9999);

//        final TextView textView = root.findViewById(R.id.text_explore);
        exploreViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }

    public void drawShop(String name, String address, String thumb,String addressLon,String addressLat,String cuisines,String featured_image,String menu_url, String photos_url,String price_range,String timings,String storeUrl,String events_url){
        data.add(new HomeViewModel(
                name,
                address,
                thumb,addressLon,addressLat,cuisines,featured_image,menu_url,photos_url,price_range,timings,storeUrl,events_url
        ));
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