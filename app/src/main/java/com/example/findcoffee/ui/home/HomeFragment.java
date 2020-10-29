package com.example.findcoffee.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static RecyclerView recyclerView;
    private static ArrayList<HomeViewModel> data;
    private int area = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        data = new ArrayList<HomeViewModel>();
        ArrayList<String> names = null;
        ArrayList<String> locations = null;
        jsonAPIGetter apiGetter = new jsonAPIGetter();
        boolean isReady = apiGetter.search("Carlton", area);
        if(isReady) {
            names = apiGetter.getTradingNames();
            locations = apiGetter.getStreetAddress();
        }
        if(names!=null) {
            for (int i = 0; i < names.size();i++){
                String trading_name = names.get(i);
                String streetAddress = locations.get(i);
                data.add(new HomeViewModel(
                        trading_name,
                        streetAddress,
                        R.drawable.coffee_placeholder
                ));
            }
        }
       /* String url ="https://data.melbourne.vic.gov.au/resource/xt2y-tnn9.json?clue_small_area=Carlton&census_year=2019";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                    textView.setText("Response is: "+ response.substring(0,500));
                        JSONArray responseJson;

                        if (response != null) {
                            try {
                                responseJson = new JSONArray(response);
//                                responseJson.length()
                                ArrayList<Integer> visited = new ArrayList<Integer>(responseJson.length());
                                for(int i=0;i<responseJson.length();i++)
                                {
                                    JSONObject listObject = responseJson.getJSONObject(i);
                                    String streetAddress = null;
                                    String trading_name = null;

                                    int base_property_id = listObject.getInt("base_property_id");
                                    boolean checkVisited = visited.contains(base_property_id);
                                    if(! checkVisited){
                                        visited.add(base_property_id);
                                        if(listObject.has("street_address"))
                                        {
                                            streetAddress = listObject.getString("street_address");
                                        }
                                        if(listObject.has("trading_name"))
                                        {
                                            trading_name = listObject.getString("trading_name");
                                        }
                                        data.add(new HomeViewModel(
                                                trading_name,
                                                streetAddress,
                                                R.drawable.coffee_placeholder
                                        ));
                                    }
                                }

                                recyclerView.setAdapter(new CoffeeShopAdapter(data));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//            textView.setText("That didn't work!");
                Log.d("Response", "Did Not work");

            }
        });*/




        // Add the request to the RequestQueue.
//        queue.add(stringRequest);

        //
//        data = new ArrayList<HomeViewModel>();
//        for (int i = 0; i < CoffeeShopData.nameArray.length; i++) {
//            data.add(new HomeViewModel(
//                    CoffeeShopData.nameArray[i],
//                    CoffeeShopData.versionArray[i],
//                    CoffeeShopData.id_[i],
//                    CoffeeShopData.drawableArray[i]
//            ));
//        }
//
//        recyclerView.setAdapter(new CoffeeShopAdapter(data));





        return root;
    }

//    public HomeViewModel homeViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//
//        return root;
//    }
}