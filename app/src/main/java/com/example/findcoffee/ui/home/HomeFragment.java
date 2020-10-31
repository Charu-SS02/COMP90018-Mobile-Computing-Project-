package com.example.findcoffee.ui.home;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.findcoffee.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

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



        return root;
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